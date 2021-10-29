package com.microservice.fastdfs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Yaml {

	public Map<String, String> load(FileInputStream fileInputStream) throws IOException {
		Map<String, String> map = new HashMap<>();
		InputStreamReader input = new InputStreamReader(fileInputStream, "UTF-8");
		BufferedReader bfr = new BufferedReader(input);
		String str;
		while ((str = bfr.readLine()) != null) {
			String[] strs = str.split(":");
			if (strs.length > 1) {
				map.put(strs[0], strs[1]);
			}
		}
		input.close();
		bfr.close();
		return map;
	}

}
