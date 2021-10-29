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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil<T1> {

	@SuppressWarnings("resource")
	public List<T1> doExceltoListModel(InputStream in, String suffix) throws Exception {
		List<T1> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		try {
			Workbook work;
			if ("xls".equals(suffix)) {
				work = new HSSFWorkbook(in);
			} else {
				work = new XSSFWorkbook(in);
			}
			Sheet sheet = work.getSheetAt(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				if (sheet.getRow(i) != null) {
					try {
						T1 t1 = rowtoModel(sheet.getRow(i));
						if (t1 != null) {
                            list.add(t1);
                        }
					} catch (Exception e) {
						sb.append("第" + (i + 1) + "行，第" + e.getMessage() + "列\n");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!"".equals(sb.toString())) {
			throw new Exception(sb.toString() + "发生错误");
		}
		return list;
	}

	public T1 rowtoModel(Row row) throws Exception {
		return null;
	}

}
