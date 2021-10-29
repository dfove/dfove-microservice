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
package common.entity;

import common.enums.OperateState;
import common.tools.JsonUtil;

/**
 * 全系统的通用操作结果。 如成功，则data有数据，message和code可空； 如失败，则data=null，message和code不为空
 * 
 * @author YangTianhua
 *
 * @param <T> 结果类型
 */
public class OperateResult<T> {

	private T data;

	private OperateState operateState;

	private String message;

	private String code;

	public OperateResult() {
		this(null);
	}

	public OperateResult(T data) {
		this(data, OperateState.Unknow);
	}

	public OperateResult(T data, OperateState operateState) {
		this(data, operateState, null);
	}

	public OperateResult(T data, OperateState operateState, String message) {
		this(data, operateState, message, null);
	}

	public OperateResult(T data, OperateState operateState, String message, String code) {
		this.operateState = operateState;
		this.data = ok() ? data : null;
		this.message = message == null && ok() ? "成功" : message;
		this.code = code == null && ok() ? "200" : code;
	}

	public void setOperateState(OperateState operateState) {
		this.operateState = operateState;
	}

	public OperateState getOperateState() {
		return this.operateState;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return this.data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public OperateResult<T> unknown(String message) {
		return unknown(message, null);
	}

	public OperateResult<T> unknown(String message, String code) {
		this.setOperateState(OperateState.Unknow);
		this.setMessage(message);
		this.setCode(code);
		return this;
	}

	public OperateResult<T> success(T data) {
		return success(data, null);
	}

	public OperateResult<T> success(T data, String message) {
		return success(data, message, null);
	}

	public OperateResult<T> success(T data, String message, String code) {
		this.setData(data);
		this.setOperateState(OperateState.Success);
		this.setMessage(message == null ? "成功" : message);
		this.setCode(code == null ? "200" : code);
		return this;
	}

	public OperateResult<T> failed(String message) {
		return failed(message, null);
	}

	public OperateResult<T> failed(String message, String code) {
		this.setData(null);
		this.setOperateState(OperateState.Failed);
		this.setMessage(message);
		this.setCode(code);
		return this;
	}

	public static OperateResult<String> s() {
		return new OperateResult<String>();
	}

	public static OperateResult<Boolean> b() {
		return new OperateResult<Boolean>();
	}

	public static OperateResult<Long> l() {
		return new OperateResult<Long>();
	}

	public static OperateResult<Integer> i() {
		return new OperateResult<Integer>();
	}

	public Boolean ok() {
		return this.operateState.equals(OperateState.Success);
	}

	/**
	 * 等价于this.ok() && this.getData()!=null && this.getData().ok();
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Boolean dataOk() {
		return dataOk((OperateResult<Object>) this.getData());
	}
	
	/**
	 * 等价于this.ok() && this.getData()!=null && this.getData().ok();
	 * @return
	 */
	public <T1> Boolean dataOk(OperateResult<T1> data) {
		return this.ok() && data != null && data.ok();
	}

	public String toJson() {
		return JsonUtil.objectToJson(this);
	}
}
