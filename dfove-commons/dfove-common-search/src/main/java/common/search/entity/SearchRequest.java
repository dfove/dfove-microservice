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
package common.search.entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import common.tools.DateUtil;
import common.tools.MapUtil;
import common.tools.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SearchRequest", description = "通用搜索请求")
public class SearchRequest<T> {

	@ApiModelProperty(value = "搜索参数")
	private T data;

	@ApiModelProperty(value = "排序，建议前端传入可读性比较好的选项，后端再转换，比如前端传入用户Id，后端转换为userId")
	private String orderBy;

	@ApiModelProperty(value = "每页记录数，如不分页则全取")
	private Integer pageSize;

	@ApiModelProperty(value = "自动组装筛选条件，只读")
	@JsonIgnore
	private String where;

	public SearchRequest() {

	}

	public SearchRequest(T data) {
		this(data, null, 10);
	}

	public SearchRequest(T data, String orderBy, Integer pageSize) {
		this.data = data;
		this.orderBy = orderBy;
		this.pageSize = pageSize;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return this.data;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public String getWhere() {

		if (this.where != null && !this.where.isEmpty()) {
            return where;
        }

		if (this.getData() == null) {
            return "";
        }

		List<Field> fields = MapUtil.getFields(data);
		StringBuffer sb = new StringBuffer();

		String name = null;
		Object value = null;
		String n = null;
		String v = null;

		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

			name = field.getName();
			if ("lastTimeBefore".equals(name)) {
                continue;
            }

			field.setAccessible(true);

			value = null;
			try {
				value = field.get(data);
			} catch (Exception e) {
			}
			if (value == null) {
                continue;
            }

			String type = field.getType().toString();

			FieldName fieldName = new FieldName(name, null);

			switch (type) {
			case "class java.lang.String":
				if (((String) value).isEmpty()) {
                    continue;
                }
				fieldName.setLike(true);
				n = formatName(field, fieldName);
				if (n == null) {
                    continue;
                }

				sb.append(" and ");

				if (name.startsWith("limit")) {
					sb.append("(");
					sb.append(n);
					sb.append(" = '*' or ");
					sb.append(n);
					sb.append(" like '%");
					sb.append(value);
					sb.append("%')");
				} else {
					sb.append(n);
					if (fieldName.getLike()) {
						sb.append(" like '%");
						sb.append(value);
						sb.append("%'");
					} else {
						sb.append(" = '");
						sb.append(value);
						sb.append("'");
					}
				}
				break;
			case "class java.util.Date":
			case "class java.lang.Integer":
			case "class java.lang.Long":
			case "class java.math.BigDecimal":

				v = formatValue(type, value);

				int m = 0;
		
				if (name.startsWith("min")) {
					m = 1;
					if (name.startsWith("minequ")) {
                        m = 3;
                    }
				} else if (name.startsWith("max")) {
					m = 2;
					if (name.startsWith("maxequ")) {
                        m = 4;
                    }
				}
	

				if (m == 0) {
					n = formatName(field, fieldName);
					if (n == null) {
                        continue;
                    }
					sb.append(" and ");
					sb.append(n);
					sb.append(" = ");
					sb.append(v);
					break;
				}
				if (m==1 || m==2) {					
					fieldName.setName(name.substring(3));
				}else {
					fieldName.setName(name.substring(6));					
				}
				n = formatName(field, fieldName);
				if (n == null) {
                    continue;
                }

				sb.append(" and ");
				if (name.startsWith("minLimit") || name.startsWith("maxLimit")) {
					sb.append("(");
					sb.append(n);
					sb.append(" = 0 or ");
					sb.append(n);
					if (m == 1) {
                        sb.append(" >= ");
                    } else {
                        sb.append(" <= ");
                    }
					sb.append(v);
					sb.append(")");
				} else {
					sb.append(n);					
					switch (m) {
					case 1:
						sb.append(" > ");
						break;
					case 2:
						sb.append(" < ");
						break;
					case 3:
						sb.append(" >= ");
						break;
					case 4:
						sb.append(" <= ");
						break;
					default:
						sb.append(" = ");
						break;
					}
					sb.append(v);					
				}

				break;
			case "interface java.util.List":
				if ("[]".equals(value.toString())) {
                    break;
                }
				sb.append(" and ");
				if (!name.startsWith("exc")) {
					fieldName.setName(singular(name));
					n = formatName(field, fieldName);
					if (n == null) {
                        continue;
                    }
					sb.append(n);
					sb.append(" in ");
				} else {
					fieldName.setName(singular(name));
					n = formatName(field, fieldName).replaceAll("exc_", "");
					if (n == null) {
                        continue;
                    }
					sb.append(n);
					sb.append(" not in ");
				}
				sb.append(formatValue(type, value));
				break;
			default:
				break;
			}
		}

		where = sb.toString();
		sb = null;
		if (where == null || where.isEmpty()) {
            where = "";
        } else {
            where = where.replaceFirst(" and ", " where ");
        }
//System.out.println(where);
		return where;
	}

	public void resetWhere() {
		this.where = null;
	}

	private String formatName(Field field, FieldName fieldName) {
		String name = StringUtil.camelToUnderline(fieldName.getName());

		if (field.isAnnotationPresent(FieldComment.class)) {
			for (Annotation c : field.getDeclaredAnnotations()) {
				if (c.annotationType().equals(FieldComment.class)) {
					FieldComment comment = ((FieldComment) c);
					String tableName = comment.tableName();
					if (tableName != null && !tableName.isEmpty()) {
						name = String.format("%s.%s", tableName, name);
					}
					if (fieldName.getLike() != null) {
						Boolean like = comment.like();
						fieldName.setLike(like == null || like);
					}
					Boolean manual = comment.manual();
					if (manual != null && manual) {
						name = null;
					}
					break;
				}
			}
		}

		fieldName.setName(name);
		return name;
	}

	private String formatValue(String type, Object value) {
		if ("class java.util.Date".equals(type)) {
            return "to_date('" + DateUtil.dateTime((Date) value, "yyyy-MM-dd HH:mm:ss") + "','yyyy-mm-dd hh24:mi:ss')";
        }

		if ("interface java.util.List".equals(type)) {
            return value.toString().replaceAll("\\[", "\\('").replaceAll("\\]", "\\')").replaceAll(", ", "', '");
        }

		return value.toString();
	}

	private String singular(String complex) {
		if (complex == null || complex.isEmpty()) {
            return complex;
        }

		int len = complex.length();

		if ("ies".equals(complex.substring(len - 3))) {
            return complex.substring(0, len - 3) + "y";
        }

		if ("ses".equals(complex.substring(len - 3))) {
            return complex.substring(0, len - 2);
        }

		if ("s".equals(complex.substring(len - 1))) {
            return complex.substring(0, len - 1);
        }

		return complex;
	}
}
