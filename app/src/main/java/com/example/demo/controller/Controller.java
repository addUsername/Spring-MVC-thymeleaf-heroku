package com.example.demo.controller;

import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.services.AemetService;
import com.example.demo.utils.AemetDTO;
import com.example.demo.utils.Utils;

@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	AemetService aemetService;
	
	private String choosenProv = "Seleccione una provincia";
	private String choosenIdema = "Seleccione una estación";
	private String choosenIdemaCod = "";
	private Boolean firstLoad = true;
	private TreeMap myMap;
	private TreeMap myPrettyMap;
	private String weekChart = null;
	private AemetDTO[] response = null;
	
	//https://www.baeldung.com/spring-mvc-and-the-modelattribute-annotation
	@ModelAttribute
	public void addAttributes(Model model) {
		
		if(firstLoad) readJson();		
		model.addAttribute("provincias", myMap);
	    model.addAttribute("provinciasCleaned", myPrettyMap);
	    model.addAttribute("choosenProv", choosenProv);
	    model.addAttribute("choosenIdema", choosenIdema);
	    model.addAttribute("weekChart", response);
	}
	
	@GetMapping("/")
    public String main(Model model) {
		//index.html
		firstLoad = false;
		return "index";
    }
	
	@GetMapping("/provincia/{provincia}")
    public String provincia(Model model, @PathVariable String provincia) {		
		choosenProv = provincia;
		return "redirect:/";
    }
	@GetMapping("/idema/{idema}")
    public String idema(Model model, @PathVariable String idema) {
		choosenIdema = idema;
		TreeMap myTree =  (TreeMap) myPrettyMap.get(choosenProv);
		choosenIdemaCod = (String) myTree.get(idema);
		return "redirect:/";
    }
	@GetMapping("/show")
	public String show() {
		aemetService.setIdema(choosenIdemaCod);
		response = aemetService.getData();
		return "redirect:/";
	}
	private void readJson() {
		Utils utils = new Utils();
		utils.readProvincias();
		myMap = utils.getMyMap();
		myPrettyMap = utils.getMyPrettyMap();		
	}

}