/*
 * Copyright (c) 2021 dfove.com Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microservice.fastdfs.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.IdUtil;
import com.microservice.core.LogComponent;
import com.microservice.fastdfs.DataConfig;
import com.microservice.fastdfs.DataTemplate;
import com.microservice.fastdfs.Microservices;
import common.annotation.RecordLog;
import common.constant.Log;
import common.enums.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.microservice.core.App;
import com.microservice.core.UserAnnotation;
import com.microservice.fastdfs.entity.DataAnalysisModel;
import com.microservice.fastdfs.entity.FastdfsEntity;
import com.microservice.fastdfs.service.DataAnalysis;
import com.microservice.fastdfs.service.DataCleaningRun;
import com.microservice.fastdfs.service.DataMessage;
import com.microservice.fastdfs.service.FastdfsService;
import com.microservice.fastdfs.service.FixedThreadPool;
import com.microservice.fastdfs.service.IFastdfsStorageService;

import common.entity.OperateResult;
import common.redis.IRedis;
import common.tools.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Title: FastdfsController
 * @Description: FastDFS????????????????????????????????????????????????
 * @author sunwuzhao
 * @date 2019-02-28
 * @version V1.0
 */
@Api(value = "?????????????????????????????????????????????", tags = { "?????????????????????????????????????????????" })
@Controller
@RequestMapping(value = "/dfove/v1/fastdfs")
public class FastdfsController {

	@Autowired
	private FastdfsService fastdfsService;
	@Autowired
	private IFastdfsStorageService fastdfsStorageService;
	@Autowired
	private DataAnalysis dataAnalysis;
	@Autowired
	private IRedis redis;
	private final static String DEUALTKEY = "DataAnalysisTemp";

	@Autowired
	private LogComponent logComponent;
	@Autowired
	private Microservices microservices;
	@Qualifier("RestTemplateEureka")
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DataConfig dataConfig;

	// ??????????????? ??????????????????
	private static final String[] SUFFIX_DEUALT = { "JPG", "PNG", "BMP", "JPEG", "GIF" };

	private final Logger logger = LoggerFactory.getLogger(FastdfsService.class);

	@ApiOperation(value = "pc????????????-??????", notes = "????????????form??????????????????-?????????????????????ajax??????????????????-??????", httpMethod = "POST")
	@RequestMapping(value = "pc/upload", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	@UserAnnotation()
	public OperateResult<FastdfsEntity> uploadFile(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		String adminId=null;
		try {			
			 adminId=App.userScope().getUserId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		FastdfsEntity fastDfsFile = new FastdfsEntity();
		try {
			fastDfsFile = fastdfsService.uploadFile(file);

			String isAnalysis = request.getParameter("isAnalysis");// ????????????
			String isCleaning = request.getParameter("isCleaning");// ????????????
			String tab = request.getParameter("tab");
			DataAnalysisModel dam = null;
			long fileSize = file.getSize();

			// ??????
			if (isAnalysis != null && "1".equals(isAnalysis)) {
				if (fileSize >= 10 * 1024 * 1024) {
					return new OperateResult<FastdfsEntity>().failed("??????????????????,??????????????????10M");
				}
				if (tab != null && "".equals(tab)) {
					return new OperateResult<FastdfsEntity>().failed("tab????????????");
				}

				try {
					dam = dataAnalysis.doDataAnalysis(file, tab, fastDfsFile.getId());

					// ????????????
					String dataName = "";
					DataTemplate dataTemplate = dataConfig.getMap().get(tab);
					if (dataTemplate != null) {
						dataName = dataTemplate.getName();
					}
					sendOperateLog(Log.OPERATE_IMPORT, "??????" + dataName + "??????");
				} catch (IOException e) {
					e.printStackTrace();
					return new OperateResult<FastdfsEntity>().failed(e.getMessage());
				}
			}

			// ????????????
			if ("success".equals(fastDfsFile.getState())) {
				// fastDfsFile.setMessage("?????????");
				fastDfsFile.setMessage("temp~" + DateUtil.dateTime());
				fastDfsFile.setId(IdUtil.simpleUUID());
				OperateResult<String> l = fastdfsStorageService.add(fastDfsFile);
				if (l.getData() !=null) {
					// ??????
					if (isAnalysis != null && "1".equals(isAnalysis)) {
						redis.set(DEUALTKEY + l.getData(), dam, 1800);
					}
					fastDfsFile.setMessage("????????????");
					// fastDfsFile.setMessage(fastDfsFile.getMessage()+""+l.getData());
				} else {
					fastDfsFile.setMessage("????????????????????????????????????");
				}
			}

			try {
				// ??????
				if (isCleaning != null && "1".equals(isCleaning)) {
					try {
						FixedThreadPool.submit(new DataCleaningRun(file, tab, fastDfsFile.getId(), adminId, fastDfsFile.getOriginalFileName()));

						// ????????????
						String dataName = "";
						DataTemplate dataTemplate = dataConfig.getMap().get(tab);
						if (dataTemplate != null) {
							dataName = dataTemplate.getName();
						}
						sendOperateLog(Log.OPERATE_CLEANING, "??????" + dataName + "??????");
					} catch (Exception e) {
						e.printStackTrace();
						return new OperateResult<FastdfsEntity>().failed(e.getMessage());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ????????????
			if (isAnalysis != null && "1".equals(isAnalysis)) {
				int count = 0;
				if (dam != null && dam.getDatamessage() != null && dam.getDatamessage().size() > 0) {
					fastDfsFile.setFailedData(dam.getDatamessage());
					List<DataMessage> dm = dam.getDatamessage();
					Set<Integer> set = new HashSet<>();
					for (int i = 0; i < dm.size(); i++) {
						set.add(dm.get(i).getRow());
					}
					// count+=dam.getDatamessage().size();
					count += set.size();
				}
				if (dam != null) {
					fastDfsFile.setSuccessNumber(dam.getData().size());
					count += dam.getData().size();
				}
				fastDfsFile.setCount(count);
			}

			// ????????????
			return new OperateResult<FastdfsEntity>().success(fastDfsFile);
		} catch (Exception e) {
			e.printStackTrace();
			return new OperateResult<FastdfsEntity>().failed(e.getMessage());
		}

	}

	private void sendOperateLog(String operateType, String content) {
		String serviceUrl = microservices.getValue("log", AccessType.eureka.toString());
		String url = String.format("%s/" + Log.PATH_OPERATE_ADD, serviceUrl);
		logComponent.sendOperateLog(restTemplate, url, Log.MODULE_FILE, operateType, content);
	}

	@RecordLog(module = Log.MODULE_FILE, type = Log.OPERATE_UPLOAD, content = "pc??????????????????")
	@UserAnnotation
	@ApiOperation(value = "pc????????????-??????", notes = "????????????form??????????????????-?????????????????????ajax??????????????????-??????", httpMethod = "POST")
	@RequestMapping(value = "pc/upload/batch", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public OperateResult<List<FastdfsEntity>> uploadFileBatch(@RequestParam("file") MultipartFile[] multipartFiles) {
		List<FastdfsEntity> fastDfsFileList = new ArrayList<FastdfsEntity>();
		for (MultipartFile multipartFile : multipartFiles) {
			FastdfsEntity fastDfsFile = fastdfsService.uploadFile(multipartFile);
			fastDfsFileList.add(fastDfsFile);
		}
		return new OperateResult<List<FastdfsEntity>>().success(fastDfsFileList);
	}

	@RecordLog(module = Log.MODULE_FILE, type = Log.OPERATE_UPLOAD, content = "pc????????????")
	@UserAnnotation
	@ApiOperation(value = "pc????????????????????????????????????????????????", notes = "????????????form??????????????????-?????????????????????ajax??????????????????-????????????????????????_200x200.jpg", httpMethod = "POST")
	@RequestMapping(value = "pc/upload/thumb/image", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public OperateResult<FastdfsEntity> uploadImageAndCrtThumbImage(MultipartFile file) {
		FastdfsEntity fastDfsFile = fastdfsService.uploadImageAndCrtThumbImage(file);
		return new OperateResult<FastdfsEntity>().success(fastDfsFile);
	}

	@RecordLog(module = Log.MODULE_FILE, type = Log.OPERATE_UPLOAD, content = "???????????????")
	@UserAnnotation
	@ApiOperation(value = "????????????????????????????????????", notes = "?????????content=???????????????extension=????????????", httpMethod = "POST")
	@RequestMapping(value = "upload/string", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public OperateResult<FastdfsEntity> uploadString(@RequestParam @ApiParam(value = "?????????", required = true) String content, @RequestParam @ApiParam(value = "????????????", required = true) String extension) {
		FastdfsEntity fastDfsFile = fastdfsService.uploadString(content, extension);
		return new OperateResult<FastdfsEntity>().success(fastDfsFile);
	}

	@RecordLog(module = Log.MODULE_FILE, type = Log.OPERATE_UPLOAD, content = "app????????????")
	@UserAnnotation
	@ApiOperation(value = "app????????????", notes = "H5???????????????html5plus-uploader", httpMethod = "POST")
	@RequestMapping(value = "/app/upload", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public OperateResult<List<FastdfsEntity>> appUpload(HttpServletRequest request, HttpServletResponse response) {
		List<FastdfsEntity> fastDfsFileList = new ArrayList<FastdfsEntity>();
		// ?????????????????????????????????
		CommonsMultipartResolver coMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// ??????request??????????????????????????????
		if (coMultipartResolver.isMultipart(request)) {
			// ??????request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// ??????map??????-MultipartFile
			MultiValueMap<String, MultipartFile> multipartFileMap = multiRequest.getMultiFileMap();
			// ??????map??????
			for (Entry<String, List<MultipartFile>> entry : multipartFileMap.entrySet()) {
				// ???????????????MultipartFile
				List<MultipartFile> fileList = entry.getValue();
				// ??????fileList
				for (MultipartFile file : fileList) {
					// ????????????????????????
					if (!file.isEmpty()) {
						FastdfsEntity fastDfsFile = fastdfsService.uploadFile(file);
						fastDfsFileList.add(fastDfsFile);
					}
				}
			}
		}
		// return JSON.toJSONString(fastDfsFileList);
		return new OperateResult<List<FastdfsEntity>>().success(fastDfsFileList);
	}

	@RecordLog(module = Log.MODULE_FILE, type = Log.OPERATE_DOWNLOAD, content = "????????????")
	@UserAnnotation
	@ApiOperation(value = "????????????", notes = "?????????filePath=???????????????xx/download/file?filePath=xx.txt", httpMethod = "GET")
	@RequestMapping(value = "download/file", method = RequestMethod.GET)
	@ResponseBody
	public void downloadFile(@RequestParam @ApiParam(value = "????????????", required = true) String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			
			
		// ??????response?????????????????????
		response.reset();
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			// ???????????????
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
			String suffixname = filePath.substring(filePath.lastIndexOf(".") + 1);

//			System.out.println(suffixname);
			if (!dosuffixname(suffixname)) {
				// ?????????????????????
				response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
				// response.setHeader("content-type", "image/jpeg;charset=UTF-8");
			}
//			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			// ??????????????????
			byte[] bytes = fastdfsService.downloadFile(filePath);
			outputStream.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage();
			logger.error(message);
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + "-failed.txt");
			response.setContentType("text/plain; charset=utf-8");
			byte[] bytes = ("???????????????" + message).getBytes();
			outputStream.write(bytes);
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unused")
	@ApiOperation(value = "????????????-??????", notes = "?????????filePath=???????????????xx/download/file?filePath=xx.txt", httpMethod = "POST", hidden = true)
	@RequestMapping(value = "download/filebyte", method = RequestMethod.POST)
	@ResponseBody
	public OperateResult<Map<String, Object>> downloadbyte(@RequestBody FastdfsEntity fastdfsEntity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<>();
		try {
			// ???????????????
			String fileName = fastdfsEntity.getFileName();
			String suffixname = fastdfsEntity.getFileName().substring(fastdfsEntity.getFileName().lastIndexOf(".") + 1);
			// ??????????????????
			byte[] bytes = fastdfsService.downloadFile(fastdfsEntity.getFullPath());
			System.out.println("-------------------"+new String(bytes,"UTF-8"));
			map.put("filebyte", bytes);
			map.put("message", "??????");
			map.put("code", "200");
			map.put("fileName", fileName);
			map.put("suffixname", suffixname);
//			String s = JSONArray.toJSONString((bytes));
//			System.out.println(s);
//			byte[] byte1 = com.alibaba.fastjson.JSONArray.parseObject(s, byte[].class);
			return new OperateResult<Map<String, Object>>(null).success(map);
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage();
			logger.error(message);
			byte[] bytes = ("????????????:" + message).getBytes();
			map.put(message, message);
			return new OperateResult<Map<String, Object>>(map).failed(message);
		}
	}

	@RecordLog(module = Log.MODULE_FILE, type = Log.OPERATE_DELETE, content = "????????????")
	@UserAnnotation
	@ApiOperation(value = "????????????", notes = "?????????filePath=???????????????xx/delete/file?filePath=xx.txt", httpMethod = "GET|DELETE")
	@RequestMapping(value = "delete/file", method = { RequestMethod.GET, RequestMethod.DELETE })
	@ResponseBody
	public OperateResult<Boolean> deleteFile(@RequestParam @ApiParam(value = "????????????", required = true) String filePath) {
		boolean result = fastdfsService.deleteFile(filePath);
		return new OperateResult<Boolean>().success(result);
	}

	@RecordLog(module = Log.MODULE_FILE, type = Log.OPERATE_DELETE, content = "??????????????????")
	@UserAnnotation
	@ApiOperation(value = "????????????~??????", notes = "?????????filePath=???????????????xx/delete/file?filePath=xx.txt", httpMethod = "DELETE")
	@RequestMapping(value = "delete/filebatch", method = { RequestMethod.DELETE })
	@ResponseBody
	public OperateResult<List<Boolean>> deleteFileBatch(@RequestParam @ApiParam(value = "????????????", required = true) String filePathBatchs) {
		List<Boolean> results = new ArrayList<>();
		String[] filePathBatch = filePathBatchs.split(",");
		for (int i = 0; i < filePathBatch.length; i++) {
			// boolean result = fastdfsService.deleteFile(filePathBatch);
			results.add(fastdfsService.deleteFile(filePathBatch[i]));
		}
		return new OperateResult<List<Boolean>>().success(results);
	}

	// @ApiOperation(value = "????????????~??????1")
	// @RequestMapping(value = "delete/filebatch1", method = RequestMethod.POST)
	// @ResponseBody
	// public OperateResult<Boolean> deleteFileBatch1(String filePathBatch) {
	// System.out.println("---------------------------" + filePathBatch);
	// return new OperateResult<Boolean>().success(true);
	// }

	@RecordLog(module = Log.MODULE_FILE, type = Log.OPERATE_QUERY, content = "????????????")
	@UserAnnotation
	@ApiOperation(value = "????????????", notes = "?????????filePath=???????????????xx/file/info?filePath=xx.txt", httpMethod = "GET")
	@RequestMapping(value = "file/info", method = { RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public OperateResult<Map<String, Object>> fileInfo(@RequestBody @ApiParam(value = "????????????", required = true) String filePath) {
		Map<String, Object> result = fastdfsService.getFileInfo(filePath);
		return new OperateResult<Map<String, Object>>().success(result);
	}

	private boolean dosuffixname(String suffixname) {
		for (int i = 0; i < SUFFIX_DEUALT.length; i++) {
			if (SUFFIX_DEUALT[i].equalsIgnoreCase(suffixname)) {
//			System.out.println(suffixname.toUpperCase());
//				if (suffix_deualt[i].equalsIgnoreCase(suffixname.toUpperCase())) {
				return true;
			}
		}
		return false;
	}



	@RecordLog(module = Log.MODULE_FILE, type = Log.OPERATE_UPLOAD, content = "pc??????????????????????????????")
	@UserAnnotation
	@ApiOperation(value = "pc??????????????????????????????", notes = "????????????form??????????????????-?????????????????????ajax??????????????????-??????", httpMethod = "POST")
	@RequestMapping(value = "pc/upload/image", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public OperateResult<FastdfsEntity> uploadImageAndFolder(MultipartFile file) {
		FastdfsEntity fastDfsFile = fastdfsService.uploadImageAndFolder(file);
		return new OperateResult<FastdfsEntity>().success(fastDfsFile);
	}
}
