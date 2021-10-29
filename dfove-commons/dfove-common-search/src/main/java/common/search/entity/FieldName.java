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

public class FieldName {
	private String name = null;
	private Boolean like = true;

	public FieldName() {

	}

	public FieldName(String name) {
		this(name, true);
	}
	
	public FieldName(String name, Boolean like) {
		this.setName(name);
		this.setLike(like);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getLike() {
		return this.like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}
}