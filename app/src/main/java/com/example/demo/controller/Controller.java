package com.example.demo.controller;

import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.services.AemetService;
import com.example.demo.utils.AemetDTO;
import com.example.demo.utils.AemetDTOWeek2;
import com.example.demo.utils.Utils;

@org.springframework.stereotype.Controller
@Scope("session")
public class Controller {

	@Autowired
	AemetService aemetService;
	
	private int visitCounter = 0;
	
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
		//model.addAttribute("provincias", myMap);//quitar
	    model.addAttribute("provinciasCleaned", myPrettyMap);
	}
	
	@GetMapping("/")
    public String main(HttpSession session) {
		
		session.setAttribute("loadCharts", loadCharts);
		session.setAttribute("choosenProv", choosenProv);
		session.setAttribute("idemas", (myPrettyMap.get(choosenProv) == null)? "Selecione provincia" : myPrettyMap.get(choosenProv));
		session.setAttribute("choosenIdema", choosenIdema);
		session.setAttribute("choosenIdemaCod", choosenIdemaCod);
		firstLoad = false;
		return "index";
    }
	
	@GetMapping("/provincia/{provincia}")
    public RedirectView provincia(HttpSession session, @PathVariable String provincia) {
		loadCharts = false;
		choosenProv = provincia;
		return new RedirectView("/");
    }
	@GetMapping("/idema/{idema}")
    public RedirectView idema(HttpSession session, @PathVariable String idema) {
		loadCharts = false;
		choosenIdema = idema;
		TreeMap myTree =  (TreeMap) myPrettyMap.get(choosenProv);
		choosenIdemaCod = (String) myTree.get(idema);		
		return new RedirectView("/");
    }
	@GetMapping("/show")
	public String show(HttpSession session) {
		if(!choosenIdema.equals("Toledo"))visitCounter += 1;
		
		loadCharts = true;
		aemetService.setIdema(choosenIdemaCod);
		Future<AemetDTO[]> dataDay = aemetService.getData();
		Future<AemetDTOWeek2[]> dataWeek = aemetService.getDataWeek();
		
		try {
			session.setAttribute("weekChart",dataDay.get());
			session.setAttribute("realWeekChart", dataWeek.get());
		} catch (InterruptedException | ExecutionException e) {
			//e.printStackTrace();
		}
		//some statics
		System.out.println("-----------");
		System.out.print(new Date());
		System.out.println("[ show ] -- Looking for "+choosenProv+" "+choosenIdema);
		System.out.println("[ show ] -- total count "+ visitCounter);
		return "redirect:/";
	}
	private void readJson() {
		Utils utils = new Utils();
		utils.readProvincias();
		myMap = utils.getMyMap();
		myPrettyMap = utils.getMyPrettyMap();		
	}
}