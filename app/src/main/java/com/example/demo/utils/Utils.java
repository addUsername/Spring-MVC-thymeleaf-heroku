package com.example.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.core.io.ClassPathResource;

import com.google.gson.Gson;

public class Utils{
	
	private String content = "";
	private TreeMap myMap;
	private TreeMap myPrettyMap;
	
	public Utils(){
	}
	
	public void readProvincias() {
		
		readFile();
		parseFile();
		prettyFile();
	}
	private void prettyFile() {
		
		myPrettyMap = new TreeMap<String, HashMap>();
		myMap.forEach((k ,v) -> {
			TreeMap aux = new TreeMap<String, String>();
			((List) v).forEach( i -> {
				String [] cod_idema = ((String) i).split(" - ");
				if(cod_idema.length < 2) {
					aux.put("Seleccione una provincia", "Seleccione una stacion");
				}
				aux.put(cod_idema[1],cod_idema[0]);
			});			
			myPrettyMap.put(k, aux );
		});
	}

	private void readFile() {
		
		try {
			InputStream is = new ClassPathResource("static/output.json").getInputStream();
			content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			System.out.println(content);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void parseFile() {
		
		Gson gson = new Gson();
		
		Map provinciasRaw = gson.fromJson(content, Map.class);
		provinciasRaw.forEach((k, v) -> ((List) v).remove(0));
		
		System.out.println(provinciasRaw);
		myMap = new TreeMap<String, String>(provinciasRaw);
	}

	public TreeMap getMyMap() {
		return myMap;
	}

	public TreeMap getMyPrettyMap() {
		return myPrettyMap;
	}
	
}