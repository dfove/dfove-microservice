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
package common.wx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class WxUtil {

	@SuppressWarnings({ "deprecation", "static-access", "resource" })
	public static WxResult getAccessToken(WxLogin wxLogin) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxLogin.getAppid() + "&secret=" + wxLogin.getAppSecret() + "&code=" + wxLogin.getCode() + "&grant_type=" + wxLogin.getAuthorizationCode();
		URI uri = URI.create(url);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);

		HttpResponse response;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
					sb.append(temp);
				}
				
				WxResult result = new JSONObject().parseObject(sb.toString().trim()).toJavaObject(WxResult.class);
				// String accessToken = object.getString("access_token");
				// String openID = object.getString("openid");
				// String refreshToken = object.getString("refresh_token");
				// Long expires_in = object.getLong("expires_in");
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
