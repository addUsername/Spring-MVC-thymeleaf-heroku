package com.example.demo;

import java.util.TreeMap;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.stereotype.Controller
public class Controller {

	private String choosenProv = "Seleccione una provincia";
	private String choosenIdema = "Seleccione una estación";
	private String choosenIdemaCod = "";
	private Boolean firstLoad = true;
	private TreeMap myMap;
	private TreeMap myPrettyMap;
	
	//https://www.baeldung.com/spring-mvc-and-the-modelattribute-annotation
	@ModelAttribute
	public void addAttributes(Model model) {
		
		if(firstLoad) readJson();		
		model.addAttribute("provincias", myMap);
	    model.addAttribute("provinciasCleaned", myPrettyMap);
	    model.addAttribute("choosenProv", choosenProv);
	    model.addAttribute("choosenIdema", choosenIdema);
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
		choosenIdemaCod =((TreeMap) myPrettyMap.get(choosenProv)).get(idema).toString(); 
		System.out.println(choosenIdemaCod);
		return "redirect:/";
    }
	@GetMapping("/show")
	public String show() {
		
		return "";
	}
	private void readJson() {
		Utils utils = new Utils();
		utils.readProvincias();
		myMap = utils.getMyMap();
		myPrettyMap = utils.getMyPrettyMap();		
	}

}