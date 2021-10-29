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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class FileUtil {
	/**
	 * 通过路径返回字符串
	 * 
	 * @param path：路径地址
	 * @return 返回字符串
	 */
	public static String readFile(String path) {
		BufferedReader reader = null;
		StringBuilder laststr = new StringBuilder();
		// String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr.toString();
	}

	/**
	 * 通过输入流返回字符串
	 * 
	 * @param stream:输入流
	 * @return 字符串
	 */
	public static String fromStream(InputStream stream) {
		byte[] bytes = new byte[0];
		try {
			bytes = new byte[stream.available()];
			stream.read(bytes);
			String result = new String(bytes);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static Map<String, String> fromProperties(InputStream stream) {

		try {
			Properties properties = new Properties();
			properties.load(stream);

			Map<String, String> map = new HashMap<String, String>();

			Enumeration<?> em = properties.propertyNames();
			while (em.hasMoreElements()) {
				String key = (String) em.nextElement();
				String value = properties.getProperty(key);
				map.put(key, value);
			}

			return map;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 通过文件夹路径返回所有文件名称
	 * 
	 * @param directoryPath:文件夹路径
	 * @param isAddDirectory:是否将文件夹一起添加到list返回
	 * @return
	 */
	public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
		List<String> list = new ArrayList<String>();
		File baseFile = new File(directoryPath);
		if (baseFile.isFile() || !baseFile.exists()) {
			return list;
		}
		File[] files = baseFile.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				if (isAddDirectory) {
					list.add(file.getAbsolutePath());
				}
				list.addAll(getAllFile(file.getAbsolutePath(), isAddDirectory));
			} else {
				list.add(file.getAbsolutePath());
			}
		}
		return list;
	}

	/**
	 * 输入list文件路径和正则表达式 返回符合正则的文件
	 * 
	 * @param files:数据文件
	 * @param regx:正则表达式
	 * @return
	 */
	public static List<String> getAllFile(List<String> files, String regx) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < files.size(); i++) {
			String filepath = files.get(i);
			filepath = filepath.substring(filepath.lastIndexOf("\\") + 1);
			if (Pattern.matches(regx, filepath)) {
				list.add(files.get(i));
			}
		}
		return list;
	}

	public static void writeFile(String filePath, Map<String, Object> map) {

	}

	/**
	 * 追加生成文件
	 * 
	 * @param filePath
	 * @param listMap
	 */
	public static void writeFileListMap(String filePath, List<Map<String, Object>> listMap) {
		File file = new File(filePath);
		try {

			File fileDirectory = new File(filePath.substring(0, filePath.lastIndexOf("/")));
			if (!fileDirectory.exists() && !fileDirectory.isDirectory()) {
//				System.out.println("//文件夹不存在");
				fileDirectory.mkdirs();
			}

			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file,true);
			if (listMap != null) {
				for (int i = 0; i < listMap.size(); i++) {
					Map<String, Object> map = listMap.get(i);
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						fileWriter.write(entry.getValue() + ",");
//						System.out.println(entry.getKey() + "--->" + entry.getValue());
					}
					fileWriter.write("\n");
					fileWriter.flush();
				}
			}

			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 追加生成文件
	 * 
	 * @param filePath
	 * @param listMap
	 */
	public static void writeFileListString(String filePath, List<String> listString) {

		File file = new File(filePath);
		try {

			File fileDirectory = new File(filePath.substring(0, filePath.lastIndexOf("/")));
			if (!fileDirectory.exists() && !fileDirectory.isDirectory()) {
				System.out.println("//文件夹不存在");
				fileDirectory.mkdirs();
			}
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fileWriter = new FileWriter(file,true);
			if (listString != null) {
				for (int i = 0; i < listString.size(); i++) {
					fileWriter.write(listString.get(i) + "\n");
					fileWriter.flush();
				}
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeFileListT(String filePath, List<Object> listString) {
		
	}

	/**
	 * 修改文件路径的文件名
	 * 
	 * @param filepath
	 * @param newfilename
	 */
	public static boolean renameTo(String filepath, String newfilename) {

		/*
		 * 方法一 File f = new File("E:/2/1.txt"); String c = f.getParent(); File mm = new
		 * File(c + File.pathSeparator + "new.txt");
		 * System.out.println(mm.getAbsolutePath()); if (f.renameTo(mm)) {
		 * System.out.println("修改成功!"); }else { System.out.println("修改失败!"); }
		 */
		/**
		 * 方法二
		 */
		File file = new File(filepath);
		String filename = file.getAbsolutePath();
		if (filename.indexOf("\\") >= 0) {
			filename = filename.substring(0, filename.lastIndexOf("\\") + 1);
		}
		return file.renameTo(new File(filename + newfilename));// 改名
	}

//	public static void main(String[] args) {
//		String filename = "E:/2/1.txt";
//		// renameTo(filename,"2.txt");
//		// String filename2 = "E:\\2\\2.txt";
//		// renameTo(filename2,"1.txt");
//
//		// Map<String, Object> map=new LinkedHashMap<String, Object>();
//		// map.put("aa1", "aa1");
//		// map.put("bb", "bb");
//		// map.put("aa0", "aa0");
//		// map.put("aa2", "aa2");
//		//
//		// List<Map<String, Object>> list=new ArrayList<>();
//		// list.add(map);
//		// list.add(map);
//		// writeFileListMap(filename,list);
//	}

}
