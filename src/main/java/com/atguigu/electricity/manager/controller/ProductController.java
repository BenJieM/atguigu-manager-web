package com.atguigu.electricity.manager.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.electricity.manager.bean.DataTableJSONResponse;
import com.atguigu.electricity.manager.pojo.Product;
import com.atguigu.electricity.manager.service.ProductService;
import com.atguigu.electricity.manager.service.ProductdescService;

@RestController
@RequestMapping("/page")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductdescService productdescService;

	/**
	 * 添加商品
	 * 
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public ResponseEntity<Boolean> save(Product product,
			@RequestParam("editorValue") String editorValue) {

		try {
			productService.saveSelective(product, editorValue);
			return ResponseEntity.status(HttpStatus.CREATED).body(true);
		} catch (Exception e) {
			e.printStackTrace();
			// 添加发生异常状态 500 返回 false
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

	/**
	 * 分页展示商品列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ResponseEntity<DataTableJSONResponse> getAll(
			@RequestParam(value = "aodata", required = false) String aodata,
			@RequestParam(value = "cid", required = false) Long cid) {

		try {
			String sEcho = "0"; // 计算访问次数
			int iDisplayStart = 0; // 从第几条记录开始
			int iDisplayLength = 10; // 设置默认的每页显示的记录数

			if (aodata == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}

			// 获取页面携带的信息
			JSONArray jsonArray = new JSONArray(aodata);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				if (jsonObject.get("name").equals("sEcho")) {

					sEcho = jsonObject.get("value").toString();
				}

				if (jsonObject.get("name").equals("iDisplayStart")) {
					iDisplayStart = jsonObject.getInt("value");
				}

				if (jsonObject.get("name").equals("iDisplayLength")) {
					iDisplayLength = jsonObject.getInt("value");
				}

			}

			Product product = new Product();

			// cid的作用是什么??
			if (cid != null) {
				product.setCid(cid);
			}

			// 查询符合条件的记录
			List<Product> data = productService.queryBySeletive(product);
			// 查询符合添加的总记录数
			Integer count = productService.queryCountBySelective(product);

			// 分页数据的封装
			if ((count - iDisplayStart) >= iDisplayLength) {
				data = data.subList(iDisplayStart, iDisplayStart
						+ iDisplayLength);
			} else {
				data = data.subList(iDisplayStart, count);
			}

			DataTableJSONResponse dataTableJSONResponse = new DataTableJSONResponse(
					sEcho, count, count, data);
			return ResponseEntity.status(HttpStatus.OK).body(dataTableJSONResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}

	@RequestMapping(value = "/product", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteBatch(@RequestParam("ids") String ids) {
		try {

			if (ids == null || ids.length() == 0) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(false);
			}

			String[] split = ids.split(",");
			List<Object> list = new ArrayList<Object>();

			for (String string : split) {
				list.add(Long.parseLong(string));
			}

			productService.deleteBatch(list);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(true);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(false);
		}
	}

}
