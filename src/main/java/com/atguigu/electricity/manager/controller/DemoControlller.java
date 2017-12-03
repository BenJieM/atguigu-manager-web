package com.atguigu.electricity.manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.electricity.manager.service.GetDemo;


@RestController
@RequestMapping("/demo")
public class DemoControlller {

	@Reference(interfaceClass=GetDemo.class)
	private GetDemo getDemo;
	
	@RequestMapping("/date")
	public String getDate(){
		return getDemo.getDate();
	}
}
