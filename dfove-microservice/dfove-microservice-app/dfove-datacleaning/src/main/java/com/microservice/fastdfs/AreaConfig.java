package com.microservice.fastdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import common.entity.MapObject;

@Component(value = "areaConfig")
// @ConfigurationProperties(prefix = "area-config")
public class AreaConfig {
	private Map<String, String> map = new HashMap<String, String>();

	static {
		// Map map = null;

	}

	public void initMap() {

		Yaml yaml = new Yaml();
		// File("E:\\eclipses\\project4.9\\git\\jnyc-microservice\\source\\jnyc-microservice\\jnyc-microservice-app\\jnyc-fastdfs\\src\\main\\resources\\ssc.yml");
		File ymlFile;
		try {
			ymlFile = new File(ResourceUtils.getURL("classpath:").getPath() + "/ssc.yml");
			if (ymlFile != null && ymlFile.exists()) {
				map = yaml.load(new FileInputStream(ymlFile));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Map<String, String> getMap() {
		if (map.size() == 0) {
			initMap();
		}
		return this.map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getValue(String key) {
		try {
			return this.getMap().get(key);
		} catch (Exception e) {
			return null;
		}
	}
}
