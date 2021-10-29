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
package com.microservice.fastdfs.demo;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import common.tools.FastdfsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Title: TestFastdfsUploadLocalFile
 * @Description: 测试-fastdfs上传（本地 或 服务器生成的）文件
 * @author sunwuzhao
 * @date 2019-02-28
 * @version V1.0
 */
@Api(value = "组件示例-上传（本地 或 服务器生成的）文件", tags = { "组件示例-上传（本地 或 服务器生成的）文件" })
@Controller
@RequestMapping(value = "/dfove/v1/test/fastdfs")
public class TestFastdfsController {

	//log
	private final Logger logger = LoggerFactory.getLogger(TestFastdfsController.class);
	
	@ApiOperation(value = "前端上传文件", notes = "方式一：form表单提交文件-上传，方式二：ajax异步提交文件-上传", httpMethod = "POST")
	@RequestMapping(value = "pc/upload", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	@SuppressWarnings("unused")
	public String pcUploadFile(MultipartFile file) {
		//upload_result
		String uploadResult="";
		try {
			// 获取后缀名
			String originalFileName = file.getOriginalFilename();
			String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
			// 获取输入流字节数组byte[]
			DataInputStream dis = new DataInputStream(file.getInputStream());
			ByteArrayOutputStream baot = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			// 定义一个数组 用来读取
			int n = 0;
			// 每次读取输入流的量
			while ((n = dis.read(bytes)) != -1) {
				baot.write(bytes);
				// 将读取的字节流写入字节输出流
			}
			// 将字节输出流转为自己数组
			byte[] buff = baot.toByteArray();
			/** fastDFS文件上传 */
			uploadResult = FastdfsUtil.getInstance().uploadFile(buff, ext);
			dis.close();
			baot.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return JSON.toJSONString(uploadResult);
	}

	@ApiOperation(value = "前端下载文件", notes = "case参数：filePath=group1/M00/00/00/wKgB51x176OAFgj4AABlPOgYq40058.jpg", httpMethod = "GET")
	@RequestMapping(value = "pc/download", method = RequestMethod.GET)
	@ResponseBody
	public void pcDownloadFile(@RequestParam @ApiParam(value = "文件路径", required = true) String filePath,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 重置response对象中的缓冲区
		response.reset();
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			// 获取文件名
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
			// 设置下载文件名
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setCharacterEncoding("UTF-8");
			/** 获取fastdfs下载数据字节 */
			byte[] bytes = FastdfsUtil.getInstance().downloadFile(filePath);
			outputStream.write(bytes);
		} catch (Exception e) {
			String message = e.getMessage();
			logger.error(message);
			response.reset();
			response.setHeader("Content-Disposition","attachment; filename=" + System.currentTimeMillis() + "-failed.txt");
			response.setContentType("text/plain; charset=utf-8");
			byte[] bytes = ("下载失败！" + message).getBytes();
			outputStream.write(bytes);
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@ApiOperation(value = "本地上传文件", notes = "case参数：localFilePath=D:\\upload.jpg", httpMethod = "POST")
	@RequestMapping(value = "local/upload", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String localUploadFile(@RequestParam @ApiParam(value = "本地文件路径", required = true) String localFilePath) {
		/** fastdfs上传文件 */
		String index = FastdfsUtil.getInstance().uploadFile(localFilePath);
		return JSON.toJSONString(index);
	}

	@ApiOperation(value = "本地下载文件", notes = "case参数：outFilePath=D:\\download.jpg \n inFilePath=group1/M00/00/00/wKgB51x176OAFgj4AABlPOgYq40058.jpg", httpMethod = "GET")
	@RequestMapping(value = "local/download", method = RequestMethod.GET)
	@ResponseBody
	public Boolean localDownloadFile(@RequestParam @ApiParam(value = "保存文件路径", required = true) String outFilePath,
			@RequestParam @ApiParam(value = "文件标识路径", required = true) String inFilePath) {
		/** fastdfs下载文件 */
		boolean result = FastdfsUtil.getInstance().downloadFile(outFilePath, inFilePath);
		return result;
	}

	@ApiOperation(value = "删除文件", notes = "case参数：filePath=group1/M00/00/00/wKgB51x176OAFgj4AABlPOgYq40058.jpg", httpMethod = "GET|DELETE")
	@RequestMapping(value = "delete/file", method = { RequestMethod.GET, RequestMethod.DELETE })
	@ResponseBody
	public Boolean deleteFile(@RequestParam @ApiParam(value = "文件路径", required = true) String filePath) {
		/** fastdfs删除文件 */
		boolean result = FastdfsUtil.getInstance().deleteFile(filePath);
		return result;
	}
}
