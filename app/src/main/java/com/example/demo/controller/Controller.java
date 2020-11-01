package com.example.demo.controller;

import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.services.AemetService;
import com.example.demo.utils.AemetDTO;
import com.example.demo.utils.AemetDTOWeek;
import com.example.demo.utils.AemetDTOWeek2;
import com.example.demo.utils.Utils;

@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	AemetService aemetService;
	
	private String choosenProv = "Seleccione una provincia";
	private String choosenIdema = "Seleccione una estación";
	private String choosenIdemaCod = "";
	private Boolean firstLoad = true;
	private boolean loadCharts = false;
	private TreeMap myMap;
	private TreeMap myPrettyMap;
	private String weekChart = null;
	private AemetDTO[] response = null;
	private AemetDTOWeek2[] responseWeek = null;
	
	//https://www.baeldung.com/spring-mvc-and-the-modelattribute-annotation
	@ModelAttribute
	public void addAttributes(Model model) {
		
		if(firstLoad) readJson();		
		model.addAttribute("provincias", myMap);
	    model.addAttribute("provinciasCleaned", myPrettyMap);
	    model.addAttribute("choosenProv", choosenProv);
	    model.addAttribute("choosenIdema", choosenIdema);
	    model.addAttribute("weekChart", response);
	    model.addAttribute("realWeekChart", responseWeek);
	    model.addAttribute("loadCharts", loadCharts);
	}
	
	@GetMapping("/")
    public String main(Model model) {
		//index.html
		firstLoad = false;
		return "index";
    }
	
	@GetMapping("/provincia/{provincia}")
    public RedirectView provincia(Model model, @PathVariable String provincia) {
		loadCharts = false;
		choosenProv = provincia;
		//return "redirect:/";
		return new RedirectView("/");
    }
	@GetMapping("/idema/{idema}")
    public RedirectView idema(Model model, @PathVariable String idema) {
		loadCharts = false;
		choosenIdema = idema;
		TreeMap myTree =  (TreeMap) myPrettyMap.get(choosenProv);
		choosenIdemaCod = (String) myTree.get(idema);
		//return "redirect:/";
		//modelAndView.setViewName("redirect:/");
		return new RedirectView("/");
    }
	@GetMapping("/show")
	public String show() {
		loadCharts = true;
		aemetService.setIdema(choosenIdemaCod);
		Future<AemetDTO[]> dataDay = aemetService.getData();
		Future<AemetDTOWeek2[]> dataWeek = aemetService.getDataWeek();
		
		try {
			response = dataDay.get();
			responseWeek = dataWeek.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	private void readJson() {
		Utils utils = new Utils();
		utils.readProvincias();
		myMap = utils.getMyMap();
		myPrettyMap = utils.getMyPrettyMap();		
	}

}