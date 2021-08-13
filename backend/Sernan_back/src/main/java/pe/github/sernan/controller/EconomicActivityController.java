package pe.github.sernan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.github.sernan.model.EconomicActivity;
import pe.github.sernan.service.EconomicActivityService;

@RestController
@RequestMapping(value = "/economicactivity/")
public class EconomicActivityController {

	@Autowired
	private EconomicActivityService economicActivityService;
	
	@RequestMapping(value = "list")
	public java.util.List<EconomicActivity> List()
    {
		java.util.List<EconomicActivity> EconomicActivity = economicActivityService.List();
		return EconomicActivity; 
    }

}
