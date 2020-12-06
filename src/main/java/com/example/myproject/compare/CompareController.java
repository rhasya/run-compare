package com.example.myproject.compare;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompareController {
	
	@GetMapping("/hello")
	public String hello(@RequestParam(name="name") String name, Model model) {
		model.addAttribute("name", name);
		return "hello";
	}

}
