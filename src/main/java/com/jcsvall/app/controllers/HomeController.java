package com.jcsvall.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping(value={"", "/", "learn"})
	public String principal(ModelMap model) {
		model.addAttribute("welcome", "Welcome ");
		return "learn/principal";
	}
}
