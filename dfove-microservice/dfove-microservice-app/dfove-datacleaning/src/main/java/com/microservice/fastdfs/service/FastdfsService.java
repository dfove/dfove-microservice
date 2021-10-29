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
package com.microservice.fastdfs.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.microservice.fastdfs.entity.FastdfsEntity;

/**
 * @Title: FastdfsService
 * @Description: FastDFS分布式文件上传、下载、删除、查看
 * @author sunwuzhao
 * @date 2019-02-28
 * @version V1.0
 */
@Component
public class FastdfsService {

	private final Logger logger = LoggerFactory.getLogger(FastdfsService.class);

	@Autowired
	private FastFileStorageClient storageClient;
	
	/**
	 * @Desc 上传文件
	 * @param file 文件对象
	 * @return FastdfsEntity 文件信息
	 */
	public FastdfsEntity uploadFile(MultipartFile file) {
		FastdfsEntity fastdfsEntity = new FastdfsEntity();
		try {
			if (true) {
				// 获取文件信息
				String originalFileName = file.getOriginalFilename();
				String fileName = file.getName();
				String contentType = file.getContentType();
				String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
				long fileSize = file.getSize();
				fastdfsEntity.setOriginalFileName(originalFileName);
				fastdfsEntity.setFileName(fileName);
				fastdfsEntity.setContentType(contentType);
				fastdfsEntity.setExt(ext);
				fastdfsEntity.setFileSize(fileSize);
			}
			// fastDFS文件上传
			String fileExtName = FilenameUtils.getExtension(file.getOriginalFilename());
			StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtName, null);
			// 返回上传结果
			String fullPath = storePath.getFullPath();
			String group = storePath.getGroup();
			String path = storePath.getPath();
			fastdfsEntity.setFullPath(fullPath);
			fastdfsEntity.setGroup(group);
			fastdfsEntity.setPath(path);
		} catch (Exception e) {
			e.printStackTrace();
			String message=e.getMessage();
			fastdfsEntity.setMessage(message);
			fastdfsEntity.setState("failed");
			logger.error(message);
		}
		return fastdfsEntity;
	}

	/**
	 * @Desc 上传字符串，生成一个文件
	 * @param content 文件内容
	 * @param fileExtension 文件后缀
	 * @return FastdfsEntity 文件信息
	 */
	public FastdfsEntity uploadString(String content, String fileExtension) {
		FastdfsEntity fastdfsEntity = new FastdfsEntity();
		try {
			if (true) {
				// 获取文件信息
				fastdfsEntity.setExt(fileExtension);
			}
			// fastDFS文件上传
			byte[] buff = content.getBytes(Charset.forName("UTF-8"));
			ByteArrayInputStream stream = new ByteArrayInputStream(buff);
			StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
			// 返回上传结果
			String fullPath = storePath.getFullPath();
			String group = storePath.getGroup();
			String path = storePath.getPath();
			fastdfsEntity.setFullPath(fullPath);
			fastdfsEntity.setGroup(group);
			fastdfsEntity.setPath(path);
		} catch (Exception e) {
			String message=e.getMessage();
			fastdfsEntity.setMessage(message);
			fastdfsEntity.setState("failed");
			logger.error(message);
		}
		return fastdfsEntity;
	}

	/**
	 * @Desc 上传图片，并同时生成一个缩略图 ("JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP")
	 * @param file 文件对象
	 * @return FastdfsEntity 文件信息
	 */
	public FastdfsEntity uploadImageAndCrtThumbImage(MultipartFile file) {
		FastdfsEntity fastdfsEntity = new FastdfsEntity();
		try {
			if (true) {
				// 获取文件信息
				String originalFileName = file.getOriginalFilename();
				String fileName = file.getName();
				String contentType = file.getContentType();
				String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
				long fileSize = file.getSize();
				fastdfsEntity.setOriginalFileName(originalFileName);
				fastdfsEntity.setFileName(fileName);
				fastdfsEntity.setContentType(contentType);
				fastdfsEntity.setExt(ext);
				fastdfsEntity.setFileSize(fileSize);
			}
			if("JPG/JPEG/PNG/GIF/BMP/WBMP".indexOf(fastdfsEntity.getExt().toUpperCase())==-1) {
				String message="上传图片格式不对，支持的格式有JPG/JPEG/PNG/GIF/BMP/WBMP";
				fastdfsEntity.setMessage(message);
				fastdfsEntity.setState("failed");
				logger.error(message);
				return fastdfsEntity;
			}
			// fastDFS文件上传
			String fileExtName = FilenameUtils.getExtension(file.getOriginalFilename());
			StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), fileExtName, null);
			// 返回上传结果
			String fullPath = storePath.getFullPath();
			String group = storePath.getGroup();
			String path = storePath.getPath();
			fastdfsEntity.setFullPath(fullPath);
			fastdfsEntity.setGroup(group);
			fastdfsEntity.setPath(path);
		} catch (Exception e) {
			String message=e.getMessage();
			fastdfsEntity.setMessage(message);
			fastdfsEntity.setState("failed");
			logger.error(message);
		}
		return fastdfsEntity;
	}

	/**
	 * @Desc 下载文件
	 * @param filePath 文件路径
	 * @return byte 返回数据字节
	 */
	public byte[] downloadFile(String filePath) {
		byte[] bytes = null;
		if (StringUtils.isEmpty(filePath)) {
			return bytes;
		}
		try {
			String group = filePath.substring(0, filePath.indexOf("/"));
			String path = filePath.substring(filePath.indexOf("/") + 1);
			DownloadByteArray downloadByteArray = new DownloadByteArray();
			bytes = storageClient.downloadFile(group, path, downloadByteArray);
			return bytes;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return bytes;
	}

	/**
	 * @Desc 删除文件
	 * @param filePath 文件路径
	 * @return boolean 成功=true，失败=false
	 */
	public boolean deleteFile(String filePath) {
		boolean result = false;
		if (StringUtils.isEmpty(filePath)) {
			return result;
		}
		try {
			StorePath storePath = StorePath.praseFromUrl(filePath);
			storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * @Desc 获取文件信息
	 * @param filePath 文件访问地址
	 * @return Map<String,Object> 文件信息
	 */
	public Map<String, Object> getFileInfo(String filePath) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isEmpty(filePath)) {
			return resultMap;
		}
		try {
			StorePath storePath = StorePath.praseFromUrl(filePath);
			FileInfo fileInfo = storageClient.queryFileInfo(storePath.getGroup(), storePath.getPath());
			Object clazz = fileInfo.getClass();
			int crc32 = fileInfo.getCrc32();
			int createTime = fileInfo.getCreateTime();
			long fileSize = fileInfo.getFileSize();
			String sourceIpAddr = fileInfo.getSourceIpAddr();
			resultMap.put("clazz", clazz);
			resultMap.put("crc32", crc32);
			long millions=new Long(createTime).longValue()*1000;
			calendar.setTimeInMillis(millions);
			resultMap.put("createTime", sdf.format(calendar.getTime()));
			resultMap.put("fileSize", fileSize);
			resultMap.put("sourceIpAddr", sourceIpAddr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return resultMap;
	}



	/**
	 * @Desc pc上传图片到指定文件夹 ("JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP")
	 * @param file 文件对象
	 * @return FastdfsEntity 文件信息
	 */
	public FastdfsEntity uploadImageAndFolder(MultipartFile file) {
		FastdfsEntity fastdfsEntity=new FastdfsEntity();
		if (file.isEmpty()){
			fastdfsEntity.setMessage("文件不能为空");
			fastdfsEntity.setState("failed");
			return fastdfsEntity;
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		System.out.println(dateNowStr);
		try {
				Calendar now = Calendar.getInstance();
				String time=String.valueOf(now.getTimeInMillis());
				// 获取文件信息
				String originalFileName = file.getOriginalFilename();
				String fileName = dateNowStr+time;
				String contentType = file.getContentType();
				String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
				long fileSize = file.getSize();
				fastdfsEntity.setOriginalFileName(originalFileName);
				fastdfsEntity.setFileName(fileName+"."+ext);
				fastdfsEntity.setContentType(contentType);
				fastdfsEntity.setExt(ext);
				fastdfsEntity.setFileSize(fileSize);
			if("JPG/JPEG/PNG/GIF/BMP/WBMP".indexOf(fastdfsEntity.getExt().toUpperCase())==-1) {
				String message="上传图片格式不对，支持的格式有JPG/JPEG/PNG/GIF/BMP/WBMP";
				fastdfsEntity.setMessage(message);
				fastdfsEntity.setState("failed");
				logger.error(message);
				return fastdfsEntity;
			}
			String filePath="G:/data/run/serverFile/"+dateNowStr;
			// 文件上传
			File file1=new File(filePath+"/"+ fastdfsEntity.getFileName());
			file.transferTo(file1);
			if (!file1.getParentFile().exists()){
				file1.getParentFile().mkdirs();
			}
			fastdfsEntity.setFullPath(filePath+fileName);
			fastdfsEntity.setPath(filePath);
		} catch (Exception e) {
			String message=e.getMessage();
			fastdfsEntity.setMessage(message);
			fastdfsEntity.setState("failed");
			logger.error(message);
		}
		return fastdfsEntity;
	}
}
