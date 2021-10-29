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
package com.microservice.filestorage.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.microservice.core.RestTemplateUtil;
import com.microservice.filestorage.DataSplit;
import com.microservice.filestorage.FileTemplate;
import com.microservice.filestorage.entity.AdminPermission;
import com.microservice.filestorage.entity.FastdfsEntity;
import com.microservice.filestorage.entity.FastdfsModel;
import com.microservice.filestorage.entity.FileStateDetail;
import com.microservice.filestorage.entity.TestDate;
import com.microservice.filestorage.service.FileStorageService;
import com.microservice.filestorage.service.IFastdfsDetailService;
import com.microservice.filestorage.service.IFastdfsService;

import common.entity.OperateResult;
import common.enums.CollectionType;
import common.redis.IRedis;
import common.tools.DateUtil;
import common.tools.FileUtil;
import common.tools.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "数据入库接口", tags = { "数据入库接口" })
@RestController
@RequestMapping(value = "/dfove/v1/filestorage")
public class FileStorageController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(FileStorageController.class);

	@Autowired
	private IFastdfsService fastdfsService;

	@Autowired
	private IFastdfsDetailService fastdfsDetailService;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private DataSplit dataSplit;
	
	@Autowired
	private IRedis redis;
	@Autowired
	@Qualifier("RestTemplateEureka")
	private RestTemplate restTemplate;

	private final static String DEFAULT_TABLE = "TB_CASEPERSON";

	@ApiOperation(value = "Excel/文本入库1")
	// @ApiImplicitParam(paramType = "path", required = true, name = "caseUuid",
	// value = "案件标识", dataType = "String")
	@RequestMapping(value = "/import/file", method = RequestMethod.POST)
	public OperateResult<Integer> importExcel(@RequestBody FastdfsModel fastdfsModel) {
//		String url = "http://jnyc-fastdfs/dfove/v1/fastdfs/download/filebyte";
		String url = "http://jnyc-datacleaning/dfove/v1/fastdfs/download/filebyte";
		try {
			// 查找明细是否处理
			List<FileStateDetail> fileStateDetail = fastdfsDetailService.findbyFileStateId(fastdfsModel.getId());
			if (fileStateDetail != null) {
				for (int i = 0; i < fileStateDetail.size(); i++) {
					if ("1".equals(fileStateDetail.get(i).getType()) && "1".equals(fileStateDetail.get(i).getState())) {
						return new OperateResult<Integer>().failed("文件已处理");
					}
				}
			}

			// 查找记录
			FastdfsEntity rfastdfs = fastdfsService.find(fastdfsModel.getId());
			String tab = fastdfsModel.getTab();
			if (tab != null && !tab.isEmpty()) {
				rfastdfs.setTab(tab);
			}

			if (rfastdfs == null) {
				return new OperateResult<Integer>().failed("记录不存在");
			}
			/*
			 * if (rfastdfs.getMessage().equals("已入库")) { return new
			 * OperateResult<Integer>().failed("文件已入库"); } if
			 * (rfastdfs.getMessage().equals("已处理")) { return new
			 * OperateResult<Integer>().failed("文件已处理"); }
			 */

			// 获取文件
			OperateResult<OperateResult<Map<String, Object>>> result = RestTemplateUtil.post(restTemplate, url, rfastdfs, null, new ParameterizedTypeReference<OperateResult<Map<String, Object>>>() {
			}, true);
			if ("200".equals(result.getCode()) && "200".equals(result.getData().getCode())) {
				Object object = result.getData().getData().get("filebyte");
				boolean istext = !"xlsx".equals(rfastdfs.getExt());
				String string = "\"" + object + "\"";
				byte[] b = com.alibaba.fastjson.JSONArray.parseObject(string, byte[].class);
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(b);
				OperateResult<Integer> i = null;
				if (istext) {
					String split = fastdfsModel.getSplit() == null ? dataSplit.getMap().get("file").getSplit() : fastdfsModel.getSplit();
					if (tab == null || tab.isEmpty()) {
						return new OperateResult<Integer>().failed("入库表不存在");
					}
					// 文本入库
					i = fileStorageService.importToList(fastdfsModel.getId(), null, tab, split, tInputStringStream);
				} else {
					// excel入库
					i = fileStorageService.importDataExcel(rfastdfs, tInputStringStream);
				}
				return i;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new OperateResult<Integer>().failed(e.getMessage());
		}
		return new OperateResult<Integer>().failed("处理失败");
	}

	/**
	 * File 读取文件（txt/csv/excel） 根据标识符切割文件内容 入库
	 */
	@ApiOperation(value = "单文本文件入库", hidden = true)
	@RequestMapping(value = "/import/file1", method = RequestMethod.POST)
	public OperateResult<Integer> importFile(@RequestBody FastdfsModel fastdfsModel) {
		String filePath = "";
		String basepath = dataSplit.getMap().get(DEFAULT_TABLE).getScanPath();
		String fullpath = fastdfsModel.getFullPath();
		String filename = fastdfsModel.getOriginalFileName();
		String tab = fastdfsModel.getTab();
		String split = fastdfsModel.getSplit();

		if (fullpath != null && !fullpath.isEmpty() && filename != null && !filename.isEmpty()) {
			filePath = fullpath + "/" + filename;
		} else if (filename != null && !filename.isEmpty()) {
			filePath = basepath + filename;
		} else {
			return new OperateResult<Integer>().failed("文件名不能为空!");
		}
		try {
			return fileStorageService.importToList(filePath, tab, split);
		} catch (Exception e) {
			e.printStackTrace();
			return new OperateResult<Integer>().failed(e.getMessage());
		}
	}

	@ApiOperation(value = "文件路径扫描入库")
	@RequestMapping(value = "/import/filepath", method = RequestMethod.POST)
	public OperateResult<Integer> importFilePaths() {
		StringBuilder sb = new StringBuilder();
		// if (filepaths == null || filepaths.length == 0) {
		// filepaths = dataSplit.getMap().get("file").getFilepath();
		// }
		// try {
		// for (int i = 0; i < filepaths.length; i++) {
		// List<String> files = FileUtil.getAllFile(filepaths[i], false);
		// for (int j = 0; j < files.size(); j++) {
		// try {
		// fileStorageService.importToList(files.get(i), null);
		// } catch (Exception e) {
		// sb.append(files.get(i) + ":" + e.getMessage());
		// }
		// }
		// }
		// if (sb.length() == 0) {
		// return new OperateResult<Integer>().success(0);
		// } else {
		// return new OperateResult<Integer>().failed(sb.toString());
		// }
		// } catch (Exception e) {
		// // e.printStackTrace();
		// return new OperateResult<Integer>().failed(e.getMessage());
		// }
		int result = 0;
		Map<String, FileTemplate> maps = dataSplit.getMap();
		for (FileTemplate s : maps.values()) {
			List<String> files = FileUtil.getAllFile(s.getScanPath(), false);
			files = FileUtil.getAllFile(files, s.getRegx());
			for (int i = 0; i < files.size(); i++) {
				try {
					result = result + fileStorageService.importToList(files.get(i), s.getTable(), s.getRegx()).getData();
				} catch (IOException e) {
					e.printStackTrace();
					sb.append(e.getMessage());
				}
			}
		}
		if (sb.length() == 0) {
			return new OperateResult<Integer>().success(result);
		} else {
			return new OperateResult<Integer>().failed(sb.toString());
		}
	}


	
	
	@ApiOperation(value = "demo", hidden = true)
	@RequestMapping(value = "/demo2", method = RequestMethod.GET)
	public OperateResult<Map<String, Object>> add2() throws IOException {
		//// String filePath="D:\\Documents\\Desktop\\涉案人员文件模板 -1000.xls";
		// String filePath="D:\\Documents\\Desktop\\1-TB_CASEPERSON.txt";
		// try {
		// fileStorageService.importToList(filePath);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// String filepath = dataSplit.getMap().get(DEFAULT_TABLE).getScan_path();
		// FileInputStream fileInputStream = new FileInputStream(filepath);
		// byte[] b = new byte[fileInputStream.available()];
		// fileInputStream.read(b);
		//
		// BufferedInputStream bs = new BufferedInputStream(new
		// FileInputStream(filepath));
		// byte[] b1 = new byte[bs.available()];
		// bs.read(b1);
		// return new
		// OperateResult<String>(null).success(dataSplit.getMap().get("filepath"));

		// return new OperateResult<String>(null).success(new String(b1));
		// String filepath="affwafG:\\ssafsa"+"TB_CASEPERSON"+".txt";
		// String regx=".*TB_CASEPERSON*.txt";
		// int i=Pattern.matches(regx,filepath)?1:0;

		// return new OperateResult<String>(null).success(i+"");

		try {
			getClass();
			if (1 == 1) {
				throw new NullPointerException();
			}

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
			e.printStackTrace();
			// log.error("数据", e);
		}
		Map<String, Object> map =new HashMap<>();
		map.put("name", "小明");
		map.put("Age", 18);
		map.put("sf", null);
		
		Gson gson=new Gson();
		System.out.println(gson.toJson(new OperateResult<Map<String, Object>>(null).success(map)));
		return new OperateResult<Map<String, Object>>(null).success(map);
	}
	@ApiOperation(value = "demo", hidden = true)
	@RequestMapping(value = "/demo3", method = RequestMethod.GET)
	public Set<String> add3(String keys) throws IOException {

//		Map<String, Object> map =new HashMap<>();
//		map.put("name", "小明");
//		map.put("Age", 18);
//		map.put("sf", null);
//		
//		Gson gson=new Gson();
//		System.out.println(gson.toJson(new OperateResult<Map<String, Object>>(null).success(map)));
//		return gson.toJson(new OperateResult<Map<String, Object>>(null).success(map));
		Set<String>result=redis.keys(keys);//"jnyc.admin.permissions.*"
//		redis.dels("jnyc.admin.permissions.*");
		return result;//jnyc.admin.permissions
	}
	@ApiOperation(value = "demo", hidden = true)
	@RequestMapping(value = "/demo4", method = RequestMethod.GET)
	public List<AdminPermission> add4(String keys) throws IOException {

		List<AdminPermission> permissions = null;
		permissions = redis.get(keys, AdminPermission.class, CollectionType.ListType);
//		redis.dels("jnyc.admin.permissions.*");
		return permissions;//jnyc.admin.permissions
	}
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "demo", hidden = true)
	@RequestMapping(value = "/demo5", method = RequestMethod.GET)
	public String add5(String keys) throws IOException {
		Map<String, Object> map=new HashMap<>();
		map.put("date", new Date());
		String json=JsonUtil.objectToJson(map);
		map=JsonUtil.jsonToObject(json, Map.class);
		System.out.println(map);
		return "";
	}
	@ApiOperation(value = "demo", hidden = true)
	@RequestMapping(value = "/demo6", method = RequestMethod.GET)
	public String add6() throws IOException {
		try {
			Object o=null;
//			if (o==null) {
//				o="";
//			}
			System.out.println(o + ""+":---");
			System.out.println(Long.valueOf(o + "")+":---2");
//		if (Long.valueOf(o + "") <= Long.valueOf(max) && Long.valueOf(o + "") >= Long.valueOf(min))
			return "";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@ApiOperation(value = "demo7")
	@RequestMapping(value = "/demo7", method = RequestMethod.POST)
	public TestDate add7(@RequestBody TestDate t) throws IOException {
		System.out.println("Createtime:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getCreatetime()));
		System.out.println("Createtime1:"+DateUtil.toShortTimeString(t.getCreatetime1()));
//		System.out.println(t);
		return t;
	}
}
