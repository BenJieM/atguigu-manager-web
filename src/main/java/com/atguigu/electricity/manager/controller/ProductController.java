package com.atguigu.electricity.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.electricity.manager.pojo.Product;
import com.atguigu.electricity.manager.service.ProductService;

@RestController
@RequestMapping("/page")
public class ProductController {

	@Autowired
    private	ProductService productService;
	
	/**
	 *添加商品
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/product",method=RequestMethod.POST)
	public ResponseEntity<Boolean> save(Product product){
		
		try {
			productService.save(product);
			return ResponseEntity.status(HttpStatus.CREATED).body(true);
		} catch (Exception e) {
			e.printStackTrace();
			//添加发生异常状态 500 返回 false
 			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}
	
}
