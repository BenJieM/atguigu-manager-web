package com.atguigu.electricity.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/page")
public class PageController {

	@RequestMapping(value="{pageName}")
	public ModelAndView page(@PathVariable("pageName") String pageName){
		ModelAndView modelAndView = new ModelAndView(pageName);
		return modelAndView;
	}
}
