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
package com.microservice.filestorage.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import cn.hutool.core.util.IdUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.filestorage.DataConfig;
import com.microservice.filestorage.DataSplit;
import com.microservice.filestorage.DataTemplate;
import com.microservice.filestorage.entity.FastdfsEntity;
import com.microservice.filestorage.entity.FileStateDetail;
import com.microservice.filestorage.mapper.FileStateDetailMapper;

import common.entity.OperateResult;
import common.tools.DateUtil;

@Service
public class FileStorageService {
	@Autowired
	private DataConfig dataConfig;
	@Autowired
	private DataSplit dataSplit;
	@Autowired
	private IFastdfsService fastdfsService;
	@Autowired
	private FileStateDetailMapper fileStateDetailMapper;

	@Resource
	private IBusinessImportService businessImportService;

	@SuppressWarnings("resource")
	public OperateResult<Integer> importDataExcel(FastdfsEntity fastdfsEntity, InputStream inputStream) {
		String dataType = fastdfsEntity.getTab();
		System.out.println("----------------------------------------------------------------"+dataType);
		if (dataType == null || dataType.isEmpty()) {
			return OperateResult.i().failed("数据类型不能为空");
		}
		DataTemplate template = dataConfig.getValue(dataType);
		if (template == null) {
			return OperateResult.i().failed("没有设置该数据类型的模板");
		}
		String[] columns = template.getImportColumns();
		String[] types = template.getImportTypes();
		if (columns == null || columns.length < 1 || types == null || columns.length != types.length) {
			return OperateResult.i().failed("该数据类型导入列没有设置或设置错误或列和类型数量不一致");
		}

		try {
			OperateResult<Integer> result=new OperateResult<>();
			if(fastdfsEntity.getExt().equals("xls")){
//				HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(inputStream));
//				HSSFSheet sheet = workbook.getSheetAt(0);
//
//				if (sheet == null || sheet.getPhysicalNumberOfRows() < 2) {
//					return OperateResult.i().failed("数据为空");
//				}
//
//				int cc = sheet.getRow(0).getPhysicalNumberOfCells();
//				if (cc != columns.length) {
//					return OperateResult.i().failed(String.format("数据列[%d]和目标列[%d]数量不一致", cc, columns.length));
//				}
//
//				List<Map<String, Object>> list = new ArrayList<>();
//				int rowCount = sheet.getPhysicalNumberOfRows();
//				for (int r = 1; r < rowCount; r++) {
//					HSSFRow row = sheet.getRow(r);
//					Map<String, Object> map = new HashMap<>();
//					// int cellCount = row.getPhysicalNumberOfCells(); //出现中间空值 bug
//					// int cellCount = row.getLastCellNum();// 最后出现空值 bug
//					int cellCount = columns.length;
//					for (int c = 0; c < cellCount; c++) {
//						map.put(columns[c], getCell(row.getCell(c), types[c]));
//						if (c == 0) {
//						}
//					}
//					list.add(map);
//				}
//
//				if (list == null || list.size() < 1) {
//					return OperateResult.i().failed("无有效数据");
//				}
//
//				result = businessImportService.batchImport(template.getTableName(), columns, list, template.getImportWhere());
//				if (result.getData() > 0) {
//					// 更新
//					fastdfsService.update(fastdfsEntity);
//					// 生成明细
//					saveFileStateDetail(fastdfsEntity.getId(), dataType,result.getData());
//					// FileStateDetail fsd = new FileStateDetail();
//					// fsd.setFileStateId(result.getData().longValue());
//					// fsd.setTab(dataType);
//					// fsd.setTab("1");
//					// fileStateDetailMapper.add(fsd);
//				}

			}else  if (fastdfsEntity.getExt().equals("xlsx")){
				XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = workbook.getSheetAt(0);

				if (sheet == null || sheet.getPhysicalNumberOfRows() < 2) {
					return OperateResult.i().failed("数据为空");
				}

//				int cc = sheet.getRow(0).getPhysicalNumberOfCells();
//				if (cc != columns.length) {
//					return OperateResult.i().failed(String.format("数据列[%d]和目标列[%d]数量不一致", cc, columns.length));
//				}

				List<Map<String, Object>> list = new ArrayList<>();
//				int rowCount = sheet.getPhysicalNumberOfRows();
//				for (int r = 1; r < rowCount; r++) {
//					XSSFRow row = sheet.getRow(r);
//					Map<String, Object> map = new HashMap<>();
//					// int cellCount = row.getPhysicalNumberOfCells(); //出现中间空值 bug
//					// int cellCount = row.getLastCellNum();// 最后出现空值 bug
//					int cellCount = columns.length;
//					for (int c = 0; c < cellCount; c++) {
//						map.put(columns[c], getCell(row.getCell(c), types[c]));
//						if (c == 0) {
//						}
//					}
//					list.add(map);
//				}
//				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				//定义行
				//默认第一行为标题行，index = 0
				XSSFRow titleRow = sheet.getRow(0);
				//循环取每行的数据
				for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
					XSSFRow xssfRow = sheet.getRow(rowIndex);
					if (xssfRow == null) {
						continue;
					}

					Map<String, Object> map = new HashMap<>();
					//循环取每个单元格(cell)的数据
					for (int cellIndex = 0; cellIndex < xssfRow.getPhysicalNumberOfCells(); cellIndex++) {
						XSSFCell titleCell = titleRow.getCell(cellIndex);
						XSSFCell xssfCell = xssfRow.getCell(cellIndex);
						map.put(columns[cellIndex], xssfRow.getCell(cellIndex).toString());
					}
					list.add(map);
				}
				if (list == null || list.size() < 1) {
					return OperateResult.i().failed("无有效数据");
				}
				list.remove(0);
				result= businessImportService.batchImport(template.getTableName(), columns, list, template.getImportWhere());
				if (result.getData() > 0) {
					// 更新
					fastdfsService.update(fastdfsEntity);
					// 生成明细
					saveFileStateDetail(fastdfsEntity.getId(), dataType,result.getData());
					// FileStateDetail fsd = new FileStateDetail();
					// fsd.setFileStateId(result.getData().longValue());
					// fsd.setTab(dataType);
					// fsd.setTab("1");
					// fileStateDetailMapper.add(fsd);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return OperateResult.i().failed(e.getMessage());
		}
	}

	public OperateResult<Integer> importDataText(String dataType, List<Map<String, Object>> list,boolean isbath) {
		if (dataType == null || dataType.isEmpty()) {
			return OperateResult.i().failed("数据类型不能为空");
		}
		DataTemplate template = dataConfig.getValue(dataType);
		if (template == null) {
			return OperateResult.i().failed("没有设置该数据类型的模板");
		}
		String[] columns = template.getImportColumns();
		String[] types = template.getImportTypes();
		if (columns == null || columns.length < 1 || types == null || columns.length != types.length) {
			return OperateResult.i().failed("该数据类型导入列没有设置或设置错误或列和类型数量不一致");
		}

		try {			
			int c = 1000;
			OperateResult<Integer> result = null;
			if (list == null || list.size() < 1) {
                return OperateResult.i().failed("无有效数据");
            }
			if (list.size() > 10000) {
				int i = 0;
				if (list.size() % c == 0) {
					i = list.size() / c;
				} else {
					i = list.size() / c + 1;
				}
				for (int j = 0; j < i; j++) {

					// for (int k = j * c; k < (j+1) * c; k++) {
					// List<Map<String, Object>> L=list.subList(j*c , (j+1)*c);
					// for (int k = 0; k < L.size(); k++) {
					//
					// for (Map.Entry<String, Object> entry : L.get(k).entrySet()) {
					// //list.get(j).entrySet()
					// System.out.print(entry.getKey() + ":" + entry.getValue() + " ");
					// }
					// System.out.println();
					// }
					// }

//					long t0 = System.currentTimeMillis();
					result = businessImportService.batchImport(template.getTableName(), columns, list.subList(j * c, (j + 1) * c), template.getImportWhere());
//					long t1 = System.currentTimeMillis();
//					System.out.println((t1 - t0) + "---" + j);
				}

			} else {
				result = businessImportService.batchImport(template.getTableName(), columns, list, template.getImportWhere());
			}

			if (result.getData() > 0&&!isbath) {
				// 生成明细
				saveFileStateDetail(IdUtil.simpleUUID(), dataType,result.getData());
			}

			return result;

		} catch (Exception e) {
//			e.printStackTrace();
			return OperateResult.i().failed(e.getMessage());
		}
	}

	public OperateResult<Integer> importToList(String path, String table, String split) throws IOException {
		// 后缀名处理
		String ext = path.substring(path.lastIndexOf(".") + 1);
		int s = path.lastIndexOf("-") + 1;
		int e = path.lastIndexOf(".");
		if (e < s) {

		}
		table = table == null ? path.substring(s, e) : table;
		FileInputStream in = new FileInputStream(path);
		return importToList(IdUtil.randomUUID(),ext, table, split, in);
	}

	public OperateResult<Integer> importToList(String fastid,String textType, String table, String split, InputStream in) throws IOException {
		int readline =dataSplit.getMap().get("file").getReadline();
		readline=readline==0?1000:readline;
		int sum=0,k=0; 
		
		StringBuilder sbMessage = new StringBuilder();
		DataTemplate template = dataConfig.getValue(table);
		String[] columns = template.getImportColumns();
		String[] types = template.getImportTypes();

		List<Map<String, Object>> list = new ArrayList<>();
		if (textType != null && "xls".equals(textType)) {
			FastdfsEntity fastdfsEntity = new FastdfsEntity();
			fastdfsEntity.setTab(table);
			return importDataExcel(fastdfsEntity, in);
		} else {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {

				Map<String, Object> map = new HashMap<>();
				String[] data = str.split(split);
				int c = columns.length;
				int d = data.length;
				for (int i = 0; i < c; i++) {
					if (i < d) {
						map.put(columns[i], getText(data[i], types[i]));
					} else {
						map.put(columns[i], getText(null, types[i]));
					}
				}
				list.add(map);
				// 一次读取数量
				if (list.size() >= readline) {
					k++;
					OperateResult<Integer> result = importDataText(table, list,true);					
					if (result.ok()) {
						sum+=result.getData();
						list.clear();
					} else {
						sbMessage.append("第"+k*readline+"行~第"+(k+1)*readline+"行出错:"+result.getMessage());
						list.clear();
					}
				}
			}
			in.close();
			OperateResult<Integer> result = importDataText(table, list,true);
			result.setMessage(sbMessage.toString() + result.getMessage());
			System.out.println("result.getdate======"+result.getMessage()+"----result.getdate================="+result.getData() +"sum======================"+sum);
			result.setData(result.getData()+sum);
			
			//生成明细
			saveFileStateDetail(fastid, table,result.getData()+sum);
			
			return result;
		}
		// return new OperateResult<Integer>().failed("失败!");
	}
	
	public void analysisStorage() {
		
	}

	private Object getCell(XSSFCell cell, String type) {

		if (cell == null) {
            return "";
        }

		try {
			switch (type) {
			case "STRING":
				return cell.getStringCellValue();
			case "DATE":
				try {
					return cell.getDateCellValue();
				} catch (Exception e) {
					return DateUtil.toDate(cell.getStringCellValue());
				}
			case "DECIMAL":
				try {
					return new BigDecimal((String.valueOf(cell.getNumericCellValue())));
				} catch (Exception e) {
					return new BigDecimal(cell.getStringCellValue());
				}
			case "INTEGER":
				cell.setCellType(CellType.STRING);
				return Integer.parseInt(doubleString(cell.getStringCellValue()));
			case "LONG":
				cell.setCellType(CellType.STRING);
				return Long.parseLong(doubleString(cell.getStringCellValue()));
			case "UUID":
				return (UUID.randomUUID().toString()).replaceAll("-", "");
			case "NOW":
				return new Date();
			default:
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	private Object getText(String object, String type) {
		
		try {
			switch (type) {
			case "STRING":
				return object = object == null ? "" : object;
			case "DATE":
				try {
					return DateUtil.toDate(object + "");
				} catch (Exception e) {
					return object;
				}
			case "DECIMAL":
				try {
					return new BigDecimal((String.valueOf(object)));
				} catch (Exception e) {
					try {
						return new BigDecimal(object);
					} catch (Exception e2) {
						return new BigDecimal(0);
					}
				}
			case "INTEGER":
				return object = object == null ? "" : Integer.parseInt(doubleString(object)) + "";
			case "LONG":
				return object = object == null ? "" : Integer.parseInt(doubleString(object)) + "";
			case "UUID":
				return (UUID.randomUUID().toString()).replaceAll("-", "");
			case "NOW":
				return new Date();
			default:
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	public int saveFileStateDetail(String fastDfsid, String tab,int data) {

		FileStateDetail fsd = new FileStateDetail();
		fsd.setId(IdUtil.simpleUUID());
		fsd.setFileStateId(fastDfsid);
		fsd.setTab(tab);
		fsd.setMessage(data+"");
		fsd.setType("1");//入库
		fsd.setState("1");//状态
		return fileStateDetailMapper.add(fsd);
	}

	public void updateFileStateDetail(FileStateDetail fsd) {
		fileStateDetailMapper.update(fsd);
	}

	private String doubleString(String s) {
		try {
			int i = s.indexOf(".");
			if (i == -1) {
                return s;
            }
			return s.substring(0, i);
		} catch (Exception e) {
			return 0 + "";
		}
	}
}
