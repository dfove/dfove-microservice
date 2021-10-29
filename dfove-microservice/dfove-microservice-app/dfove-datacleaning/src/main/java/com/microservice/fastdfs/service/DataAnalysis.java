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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.fastdfs.DataConfig;
import com.microservice.fastdfs.DataTemplate;
import com.microservice.fastdfs.entity.DataAnalysisModel;
import com.microservice.fastdfs.entity.Ranges;

import common.entity.OperateResult;
import common.redis.IRedis;
import common.tools.DateUtil;

@Service
public class DataAnalysis {

	@Autowired
	private DataConfig dataConfig;

	@Autowired
	private IRedis redis;

	private final static String DEUALTKEY = "DataAnalysisTemp";

	@SuppressWarnings("unused")
	public DataAnalysisModel doDataAnalysis(MultipartFile file, String table, String id) throws Exception {

		List<Map<String, Object>> list = null;

		List<DataMessage> message = new ArrayList<>();

		DataAnalysisModel dam = new DataAnalysisModel();
		dam.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
		dam.setTable(table);
		dam.setInputStream(file.getInputStream());
		dam.setId(id);

		if ("xls".equals(dam.getExt()) || "xlsx".equals(dam.getExt())) {
			onExcelToData(dam);
		} else {
			importToList(dam);// table, null, file.getInputStream(), message
		}

		// redis.set(deualtkey + id, dam, 1800);
		if (dam.getDatamessage() != null && dam.getDatamessage().size() > 0) {

		}
		return dam;
	}

	public DataAnalysisModel onExcelToData(DataAnalysisModel dam) throws Exception {

		DataTemplate template = dataConfig.getValue(dam.getTable());
		if (template == null) {
			throw new Exception(dam.getTable() + "模板找不到!");
		}
		String[] columns = template.getImportColumns();
		String[] types = template.getImportTypes();
		String[] titles = template.getImportTitles();
		String[] dateTypes = template.getImportDateTypes();
		int[] columnsLength = template.getImportColumnsLength();
		if (columnsLength == null) {
			throw new Exception("长度配置找不到!");
		}
		dam.setDatetypes(dateTypes);

		String[][] notNull = template.getImportNotNull();
		Map<String, Ranges> ranges = template.getImportRanges();

		List<DataMessage> dataMessage = dam.getDatamessage() == null ? new ArrayList<>() : dam.getDatamessage();
		dam.setColumns(columns);
		dam.setDatamessage(dataMessage);

		try {
			Workbook workbook = null;
			if ("xlsx".equals(dam.getExt())) {
				workbook = new XSSFWorkbook(dam.getInputStream());
			} else {
				workbook = new HSSFWorkbook(dam.getInputStream());
			}
			Sheet sheet = workbook.getSheetAt(0);
			List<Map<String, Object>> list = new ArrayList<>();
			int rowCount = sheet.getPhysicalNumberOfRows();

			for (int r = 2; r < rowCount; r++) {
				Row row = sheet.getRow(r);
				Map<String, Object> map = new HashMap<>();
				int cellCount = columns.length;
				int e = 0;
				for (int c = 0; c < cellCount; c++) {
					try {
						map.put(columns[c], getCell(row.getCell(c), types[c], columnsLength[c]));
					} catch (NumberFormatException nfe) {
						e++;
						DataMessage dm = new DataMessage();
						dm.setRow(r + 1);
						dm.setColumn(titles[c]);
						row.getCell(c).setCellType(CellType.STRING);
						dm.setMessage("数据转换错误：类型:" + types[c] + "     数据:" + row.getCell(c).getStringCellValue()); // nfe.getMessage()
						dataMessage.add(dm);
					} catch (Exception e2) {
						e++;
						DataMessage dm = new DataMessage();
						dm.setRow(r + 1);
						dm.setColumn(titles[c]);
						dm.setMessage("数据异常：" + e2.getMessage());
						dataMessage.add(dm);
					}
				}
				if (e == 0) {
					int nnr = 0;
					try {
						nnr = notNullRanges(r + 1, map, notNull, ranges, dataMessage);
					} catch (Exception e2) {
						e2.printStackTrace();
						nnr++;
						DataMessage dm = new DataMessage();
						dm.setRow(r + 1);
						dm.setMessage("空值和范围值 异常：" + e2.getMessage());
						dataMessage.add(dm);
					}
					if (nnr == 0) {
						list.add(map);
					}
				}
			}

			dam.setData(list);
			dam.setCode("200");
			workbook.close();
			dam.getInputStream().close();
			dam.setInputStream(null);
			return dam;

		} catch (NullPointerException e) {
			e.printStackTrace();
			dam.setCode("400");
			dam.setErrmessage("空指针错误:" + e.getMessage());
			return dam;
		} catch (Exception e) {
			e.printStackTrace();
			dam.setCode("400");
			dam.setErrmessage(e.getMessage());
			return dam;
		}

	}

	public DataAnalysisModel importToList(DataAnalysisModel dam) throws Exception { // String table, String split, InputStream in, List<DataMessage> messages

		DataTemplate template = dataConfig.getValue(dam.getTable());
		if (template == null) {
			throw new Exception(dam.getTable() + "模板找不到!");
		}
		String[] columns = template.getImportColumns();
		String[] types = template.getImportTypes();
		String[] titles = template.getImportTitles();
		int[] columnsLength = template.getImportColumnsLength();
		if (columnsLength == null) {
			throw new Exception("长度配置找不到!");
		}

		List<DataMessage> dataMessage = dam.getDatamessage() == null ? new ArrayList<>() : dam.getDatamessage();
		dam.setColumns(columns);
		dam.setDatamessage(dataMessage);

		List<Map<String, Object>> list = new ArrayList<>();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dam.getInputStream(), "UTF-8"));
		String str = null;
		int row = 0;
		while ((str = bufferedReader.readLine()) != null) {
			row++;
			Map<String, Object> map = new HashMap<>();
			String[] data = str.split(template.getSplit());
			int c = columns.length;
			int d = data.length;
			int e = 0;
			for (int i = 0; i < c; i++) {
				String value = "";
				try {
					if (i < d) {
						value = data[i];
						// map.put(columns[i], getText(data[i], types[i]));
					} else {
						value = null;
						// map.put(columns[i], getText(null, types[i]));
					}
					map.put(columns[i], getText(value, types[i], columnsLength[i]));
				} catch (NumberFormatException nfe) {
					e++;
					DataMessage dm = new DataMessage();
					dm.setRow(row + 1);
					dm.setColumn(titles[i]);
					// row.getCell(i).setCellType(CellType.STRING);
					dm.setMessage("数据转换错误：类型:" + types[c] + "     数据:" + value); // nfe.getMessage()
					dataMessage.add(dm);
				} catch (Exception e2) {
					e++;
					DataMessage dm = new DataMessage();
					dm.setRow(row + 1);
					dm.setColumn(titles[i]);
					dm.setMessage("数据异常：" + e2.getMessage());
					dataMessage.add(dm);
				}
			}
			if (e == 0) {
				list.add(map);
			}
		}
		dam.getInputStream().close();
		dam.setInputStream(null);
		return dam;

	}

	private Object getCell(Cell cell, String type, int length) throws Exception {
		if (cell == null &&!("UUID".equals(type)|| "NOW".equals(type))) {
            return "";
        }

		// try {
		switch (type) {
		case "STRING":
			cell.setCellType(CellType.STRING);
			String value = cell.getStringCellValue();
			if (value.length() > length) {
				throw new Exception("数据长度过多:" + value.length());
			}
			return value;
		case "DATE":
			try {
				try {
					return cell.getDateCellValue();
				} catch (Exception e) {
					cell.setCellType(CellType.STRING);
					String datevalue = cell.getStringCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						return sdf.parse(datevalue);
					} catch (Exception e2) {
						try {
							return sdf1.parse(datevalue);
						} catch (Exception e3) {
							try {
								return sdf2.parse(datevalue);
							} catch (Exception e4) {
								return sdf3.parse(datevalue);
							}
						}
					}
					// return DateUtil.toDate(datevalue);
				}
			} catch (Exception e) {
				cell.setCellType(CellType.STRING);
				throw new Exception("时间格式不对:" + cell.getStringCellValue());
				// return DateUtil.toDate(cell.getStringCellValue());
			}
		case "DECIMAL":
			try {
				return new BigDecimal((String.valueOf(cell.getNumericCellValue())));
			} catch (Exception e) {
				return new BigDecimal(cell.getStringCellValue());
			}
		case "INTEGER":
			cell.setCellType(CellType.STRING);
			if (cell.getStringCellValue().length() > length) {
				throw new Exception("数据长度过多:" + cell.getStringCellValue().length());
			}
			return Integer.parseInt(doubleString(cell.getStringCellValue()));
		case "LONG":
			cell.setCellType(CellType.STRING);
			if (cell.getStringCellValue().length() > length) {
				throw new Exception("数据长度过多:" + cell.getStringCellValue().length());
			}
//			System.out.println(doubleString(cell.getStringCellValue()));
			return Long.parseLong(doubleString(cell.getStringCellValue()));
		case "UUID":
			return (UUID.randomUUID().toString()).replaceAll("-", "");
		case "NOW":
			return new Date();
		default:
			return "";
		}
	}

	private Object getText(String object, String type, int length) {
		try {
			switch (type) {
			case "STRING":
				object = object == null ? "" : object;
				if (object.length() > length) {
					throw new Exception("数据长度过多:" + object.length());
				}
				return object;
			case "DATE":
				try {
					return DateUtil.toDate(object + "");
				} catch (Exception e) {
					throw new Exception("时间转换错误" + object);
					// return object;
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

	private int notNullRanges(int row, Map<String, Object> data, String[][] notnull, Map<String, Ranges> ranges, List<DataMessage> dataMessage) {
		int e = 0;
		if (notnull != null) {
			for (int i = 0; i < notnull.length; i++) {
				if (data.get(notnull[i][0]) == null || "".equals(data.get(notnull[i][0])) || " ".equals(data.get(notnull[i][0]))) {
					e++;
					DataMessage dm = new DataMessage(row, notnull[i][1], "数据不能为空");
					dataMessage.add(dm);
				}
			}
		}
		if (ranges != null) {
			for (Map.Entry<String, Ranges> entry : ranges.entrySet()) {
				Ranges ranges1 = entry.getValue();
				if ("1".equals(ranges1.getCet())) {
					if (!ranges(data.get(entry.getKey()), ranges1.getMaxValue(), ranges1.getMinValue(), ranges1.getValueType())) {
						e++;
						DataMessage dm = new DataMessage(row, ranges1.getColumn(), "数值：" + data.get(entry.getKey()) + " 不在范围内：" + ranges1.getMinValue() + "~" + ranges1.getMaxValue());
						dataMessage.add(dm);
					}
				} else if ("2".equals(ranges1.getCet())) {
					int k = 0;
					for (int i = 0; i < ranges1.getRanges().length; i++) {
						if (data.get(entry.getKey()).equals(ranges1.getRanges()[i])) {
							k++;
							break;
						}
					}
					if (k == 0) {
						e++;
						StringBuilder sb = new StringBuilder();
						sb.append("[");
						for (int i = 0; i < ranges1.getRanges().length; i++) {
							sb.append(ranges1.getRanges()[i]);
							if (i < ranges1.getRanges().length - 1) {
								sb.append(",");
							}
						}
						sb.append("]");
						DataMessage dm = new DataMessage(row, ranges1.getColumn(), "数值：" + data.get(entry.getKey()) + " 不在取值范围：" + sb.toString());
						dataMessage.add(dm);
					}
				}
			}
		}
		return e;
	}

	private boolean ranges(Object o, String max, String min, String type) {
		if (o == null || "".equals(o)) {
			return true;
		}
		switch (type) {
		case "Long":
			if (Long.valueOf(o + "") <= Long.valueOf(max) && Long.valueOf(o + "") >= Long.valueOf(min)) {
                return true;
            }
			break;
		case "Integer":
			if (Integer.valueOf(o + "") <= Integer.valueOf(max) && Integer.valueOf(o + "") >= Integer.valueOf(min)) {
                return true;
            }
			break;
		case "Short":
			if (Short.valueOf(o + "") <= Short.valueOf(max) && Short.valueOf(o + "") >= Short.valueOf(min)) {
                return true;
            }
			break;
		case "Float":
			if (Float.valueOf(o + "") <= Float.valueOf(max) && Float.valueOf(o + "") >= Float.valueOf(min)) {
                return true;
            }
			break;
		case "Double":
			if (Double.valueOf(o + "") <= Double.valueOf(max) && Double.valueOf(o + "") >= Double.valueOf(min)) {
                return true;
            }
			break;
		default:
			break;
		}
		return false;

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
