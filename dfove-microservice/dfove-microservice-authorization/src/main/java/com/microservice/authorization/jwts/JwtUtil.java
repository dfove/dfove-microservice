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
package com.microservice.authorization.jwts;

import com.microservice.authorization.Jwt;
import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.entity.UserScope;
import common.logs.Logs;
import common.tools.DateUtil;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * 创建和验证jwt token
 * 
 */
public class JwtUtil {

	private static Logs _log = new Logs(JwtUtil.class);

	/**
	 * 创建 jwt token
	 * 
	 * @param domainConfig 配置信息
	 * @param UserScope    用户信息
	 * @return jwt-token
	 */
	public static String createToken(Jwt jwt, String secretKey, UserScope userScope) {

		// 密钥和过期时间
		Integer expiration = jwt.getExpiration();

		if (secretKey == null || secretKey.isEmpty() || expiration == null || expiration < 1) {
			return "";
		}

		Date now = new Date();
		Date expire = DateUtil.add(now, 13, expiration + 60);

		JwtBuilder builder = Jwts.builder().setClaims(userScope.toMap()).setIssuer(jwt.getIssuer()).setIssuedAt(now)
				.setExpiration(expire).signWith(SignatureAlgorithm.HS512, secretKey);

		return builder.compact();

	}

	/**
	 * 解析 jwt token
	 * 
	 * @param jwt token
	 * @return
	 */
	public static OperateResult<UserScope> parseToken(String token, String secretKey) {

		try {
			Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
			return new OperateResult<UserScope>().success(new UserScope(claims), null, "200");

		} catch (SignatureException | MalformedJwtException e) {
			return failed("签名不正确：" + e.getMessage(), ErrorCode.TOKEN_NOT_CORRECT);
		} catch (ExpiredJwtException e) {
			return failed("授权过期，请重新登录：" + e.getMessage(), ErrorCode.TOKEN_TIMEOUT);
		} catch (Exception e) {
			return failed("解析token时错误：" + e.getMessage(), ErrorCode.SYSTEM_ERROR);
		}
	}

	private static OperateResult<UserScope> failed(String message, String code) {
		_log.error(message);
		return new OperateResult<UserScope>().failed(message, code);
	}
}
