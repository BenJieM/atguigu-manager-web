package com.atguigu.electricity.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.electricity.manager.pojo.Category;
import com.atguigu.electricity.manager.service.CategoryService;

@RestController
@RequestMapping("/page")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * @return 查询所有的Category:商品分类
	 */
	@RequestMapping(value="/category",method=RequestMethod.GET)
	public ResponseEntity<List<Category>> selectAll(){
		
		try {
			List<Category> list = categoryService.queryAll();
			// 查询成功 返回200 , 并将list 数据返回
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			e.printStackTrace();
			//查询出错返回响应码 500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
}
