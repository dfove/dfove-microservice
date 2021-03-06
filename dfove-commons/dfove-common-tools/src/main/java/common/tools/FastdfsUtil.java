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
package common.tools;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: FastdfsUtil
 * @Description: FastDFS上传文件工具类
 * @author sunwuzhao
 * @date 2019-02-28
 * @version V1.0
 */
public class FastdfsUtil {
	//log
	private final Logger logger = LoggerFactory.getLogger(FastdfsUtil.class);
	// 声明跟踪器客户端对象
	private TrackerClient trackerClient = null;
	// 声明存储器客户端对象
	private StorageClient1 storageClient1 = null;
	// 声明跟踪器服务对象
	private TrackerServer trackerServer = null;
	// 声明存储器服务对象
	private StorageServer storageServer = null;
	
	// fastdfs配置文件
	private static Properties properties=null;
	// 懒汉式，没有初始化
	private static FastdfsUtil fastdfsUtil = null;
	
	// 私有构造函数
	private FastdfsUtil() {
		try {
			// 初始化配置文件
			if(properties!=null) {
				ClientGlobal.initByProperties(properties);
			}else {
				ClientGlobal.init("fastdfs.conf");
			}
			// 创建跟踪器客户端对象
			trackerClient = new TrackerClient();
			// 获取跟踪器连接
			trackerServer = trackerClient.getConnection();
			// 获取存储器客户端对象
			storageClient1 = new StorageClient1(trackerServer, storageServer);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * @Desc 初始化fastDFS配置参数
	 * @return Properties 配置文件
	 */
	public static void initByProperties(Properties prop) {
		properties=prop;
	}
	
	/**
	 * @Desc 静态，同步，公开访问点，懒汉式，只创建一次对象
	 * @return FastdfsUtil对象
	 */
	public static FastdfsUtil getInstance() {
		if (fastdfsUtil == null) {
			synchronized (FastdfsUtil.class) {
				if (fastdfsUtil==null) {					
					fastdfsUtil = new FastdfsUtil();
				}
			}
		}
		return fastdfsUtil;
	}

	/**
	 * @Desc local文件上传
	 * @param localFilePath 文件路径
	 * @case localFilePath=D:\\upload.jpg
	 * @return 返回文件标识，String
	 */
	public String uploadFile(String localFilePath) {
		String index = "";
		try {
			// 上传文件，返回文件标识
			index = storageClient1.upload_file1(localFilePath, null, null);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return index;
	}

	/**
	 * @Desc request文件上传
	 * @param buff 文件数据字节
	 * @param ext 文件后缀，case ext=jpg
	 * @return 返回文件标识，String
	 */
	public String uploadFile(byte[] buff, String ext) {
		String index = "";
		try {
			// 上传文件，返回文件标识
			index = storageClient1.upload_file1(buff, ext, null);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return index;
	}

	/**
	 * @Desc 文件下载
	 * @param outFilePath 保存文件路径
	 * @param inFilePath  文件标识路径
	 * @case outFilePath=D:\\download.jpg
	 * @case inFilePath=group1/M00/00/00/wKgB51x176OAFgj4AABlPOgYq40058.jpg
	 */
	public boolean downloadFile(String outFilePath, String inFilePath) {
		boolean result = false;
		try {
			// 根据文件标识下载文件
			byte[] by = storageClient1.download_file1(inFilePath);
			if(by.length>0) {
				// 将数据写入输出流
				FileOutputStream outputStream=new FileOutputStream(outFilePath);
				IOUtils.write(by,outputStream);
				outputStream.close();
				result=true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * @Desc 文件下载
	 * @param filePath 文件标识路径
	 * @case inFilePath=group1/M00/00/00/wKgB51x176OAFgj4AABlPOgYq40058.jpg
	 * @return byte[] 返回数据字节
	 */
	public byte[] downloadFile(String filePath) {
		byte[] bytes = null;
		if (StringUtils.isEmpty(filePath)) {
			return bytes;
		}
		try {
			// 根据文件标识下载文件
			bytes = storageClient1.download_file1(filePath);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return bytes;
	}

	/**
	 * @Desc 文件删除
	 * @param filePath 文件标识路径
	 * @case filePath=group1/M00/00/00/wKgB51x176OAFgj4AABlPOgYq40058.jpg
	 * @return 返回true=成功，false=失败
	 */
	public boolean deleteFile(String filePath) {
		boolean result = false;
		try {
			// 根据文件标识删除文件，返回0 则删除成功
			int i = storageClient1.delete_file1(filePath);
			if (i == 0) {
				// 删除成功"
				result = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * @Desc 文件信息
	 * @param filePath 文件标识 路径。
	 * @case filePath=group1/M00/00/00/wKgB51x176OAFgj4AABlPOgYq40058.jpg
	 * @return 返回文件信息Map<String, Object>
	 */
	public Map<String, Object> getFileInfo(String filePath) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 根据文件标识获取文件信息
			FileInfo fileInfo = storageClient1.get_file_info1(filePath);
			Object clazz = fileInfo.getClass();
			// 错误校验码
			long crc32 = fileInfo.getCrc32();
			// 创建时间
			Date createTime = fileInfo.getCreateTimestamp();
			// 文件大小
			long fileSize = fileInfo.getFileSize();
			// 文件IP地址
			String sourceIpAddr = fileInfo.getSourceIpAddr();
			resultMap.put("clazz", clazz);
			resultMap.put("crc32", crc32);
			resultMap.put("createTime", sdf.format(createTime));
			resultMap.put("fileSize", fileSize);
			resultMap.put("sourceIpAddr", sourceIpAddr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return resultMap;
	}

	/**
	 * @Desc fastDFS配置文件-case
	 * @return Properties 配置文件-case
	 */
	@SuppressWarnings("unused")
	private Properties getProperties() {
		Properties prop = new Properties();
		prop.setProperty("fastdfs.connect_timeout_in_seconds", "30");
		prop.setProperty("fastdfs.network_timeout_in_seconds", "60");
		prop.setProperty("fastdfs.charset", "UTF-8");
		prop.setProperty("fastdfs.http_anti_steal_token", "no");
		prop.setProperty("fastdfs.http_secret_key", "zgr888");
		prop.setProperty("fastdfs.http_tracker_http_port", "9990");
		prop.setProperty("fastdfs.tracker_servers", "192.168.1.231:22122");
		return prop;
	}
}
