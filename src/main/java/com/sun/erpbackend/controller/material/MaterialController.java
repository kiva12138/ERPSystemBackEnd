package com.sun.erpbackend.controller.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.erpbackend.response.GeneralResponse;
import com.sun.erpbackend.response.MaterialCategorySearchResponse;
import com.sun.erpbackend.response.MaterialMountResponse;
import com.sun.erpbackend.response.threshold.ThresholdResponse;
import com.sun.erpbackend.service.material.MaterialService;

@CrossOrigin
@RestController
@RequestMapping("/erp/api/material")
public class MaterialController {
	
	@Autowired
	MaterialService materialService;
	
	/**
	 * code: 
	 * 	1 correct
	 *  2 内部错误
	 *  3 参数为空
	 * @param id
	 * @param name
	 * @param status
	 * @param page Caution: Start from 0
	 * @param kind
	 * @return
	 */
	@GetMapping("/getcategory")
	public MaterialCategorySearchResponse findMaterialCategory(int id, String name, int status, int kind, int page, int size) {
		MaterialCategorySearchResponse response = new MaterialCategorySearchResponse();
		/*if(id == 0 && name == "" && status == 0 && kind == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
			return response;
		}*/
		response = this.materialService.findMaterialCategory(id, name, status, kind, page, size);
		if(response.getCode() == 2) {
			response.setMessage("内部错误");
			return response;
		}
		response.setMessage("成功");
		return response;
	}
	
	/**
	 * code: 
	 * 	1 correct
	 *  2 内部错误
	 *  3 参数为空
	 * @param id
	 * @param name
	 * @param status
	 * @param page Caution: Start from 0
	 * @param kind
	 * @return
	 */
	@GetMapping("/getcategoryexceptkind")
	public MaterialCategorySearchResponse findMaterialCategoryExceptKind(int id, String name, int status, int kind, int page, int size) {
		MaterialCategorySearchResponse response = new MaterialCategorySearchResponse();
		/*if(id == 0 && name == "" && status == 0 && kind == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
			return response;
		}*/
		response = this.materialService.findMaterialCategoryExceptKind(id, name, status, kind, page, size);
		if(response.getCode() == 2) {
			response.setMessage("内部错误");
			return response;
		}
		response.setMessage("成功");
		return response;
	}
	
	@GetMapping("/getcategorymountexceptstatus")
	public MaterialMountResponse findMaterialCategoryMountExceptStatus(int id, String name, int status, int kind) {
		MaterialMountResponse response = new MaterialMountResponse();
		int result = this.materialService.findMaterialCategoryMountExceptStatus(id, name, status, kind);
		if(result == -1) {
			response.setMessage("内部错误");
			response.setCode(2);
			return response;
		}
		response.setCode(1);
		response.setMount(result);
		response.setMessage("成功");
		return response;
	}
	
	@GetMapping("/getcategorymountexceptstatusandkind")
	public MaterialMountResponse findMaterialCategoryMountExceptStatusAndKind(int id, String name, int status, int kind) {
		MaterialMountResponse response = new MaterialMountResponse();
		int result = this.materialService.findMaterialCategoryMountExceptStatusAndKind(id, name, status, kind);
		if(result == -1) {
			response.setMessage("内部错误");
			response.setCode(2);
			return response;
		}
		response.setCode(1);
		response.setMount(result);
		response.setMessage("成功");
		return response;
	}
	
	@GetMapping("/getthreshold")
	public ThresholdResponse findMaterialThreshold(int id, String name, int status, int kind, int page, int size) {
		ThresholdResponse response = new ThresholdResponse();
		response = this.materialService.findMaterialThreshold(id, name, status, kind, page, size);
		if(response.getCode() == 2) {
			response.setMessage("内部错误");
			return response;
		}
		response.setMessage("成功");
		return response;
	}
	
	/**
	 * Add One New Kind Material
	 * @param name
	 * @param description
	 * @param kind
	 * @return 1-Correct 2-内部错误 3-EmptyValue 
	 */
	@PostMapping("/addcategory")
	public GeneralResponse addMaterialCategory(String name, String description, int kind) {
		GeneralResponse generalResponse = new GeneralResponse();
		if(name=="" && kind == 0) {
			generalResponse.setCode(3);
			generalResponse.setMessage("参数为空");
			return generalResponse;
		}
		int result = this.materialService.addMaterialCategory(name, kind, description);
		if (result == 2) {
			generalResponse.setCode(2);
			generalResponse.setMessage("内部错误");
			return generalResponse;
		}
		generalResponse.setCode(1);
		generalResponse.setMessage("成功");
		return generalResponse;
	}
	
	
	/**
	 * Update One Material
	 * @param id
	 * @param name
	 * @param description
	 * @param kind
	 * @return 1-Correct 2-内部错误 3-参数为空
	 */
	@PostMapping("/updatecategory")
	public GeneralResponse updateMaterialCategory(Integer id, String name, String description, int kind) {
		GeneralResponse generalResponse = new GeneralResponse();
		if(id==0 || (name=="" && kind == 0)) {
			generalResponse.setCode(3);
			generalResponse.setMessage("参数为空");
			return generalResponse;
		}
		int result = this.materialService.updateMaterialCategory(id, name, kind, description);
		if (result == 2) {
			generalResponse.setCode(2);
			generalResponse.setMessage("内部错误");
			return generalResponse;
		}
		generalResponse.setCode(1);
		generalResponse.setMessage("成功");
		return generalResponse;
	}
	
	/**
	 * Delete One Material
	 * @param id 
	 * @return 1-Correct 2-内部错误 3-参数为空
	 */
	@PostMapping("/deletecategory")
	public GeneralResponse deleteMaterialCategory(Integer id) {
		GeneralResponse generalResponse = new GeneralResponse();
		if(id==0) {
			generalResponse.setCode(3);
			generalResponse.setMessage("参数为空");
			return generalResponse;
		}
		int result = this.materialService.deleteMaterialCategory(id);
		if (result == 2) {
			generalResponse.setCode(2);
			generalResponse.setMessage("内部错误");
			return generalResponse;
		}
		generalResponse.setCode(1);
		generalResponse.setMessage("成功");
		return generalResponse;
	}
	
	/**
	 * Update The threshold of one material
	 * @param id
	 * @param warn
	 * @param danger
	 * @return 1-Correct 2-内部错误 3-Invalid Value
	 */
	@PostMapping("/updatethreshold")
	public GeneralResponse updateThreshold(Integer id, Integer warn, Integer danger) {
		GeneralResponse response = new GeneralResponse();
		if(id == 0 || warn <= 0 || danger <= 0 || warn <= danger) {
			response.setCode(3);
			response.setMessage("输入参数为无效值");
		}else {
			int code = this.materialService.updateThreshold(id, warn, danger);
			if(code == 2) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else {
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	/**
	 * Update all thresholds of all material
	 * @param id
	 * @param warn
	 * @param danger
	 * @return 1-Correct 2-内部错误 3-Invalid Value
	 */
	@PostMapping("/updateallthreshold")
	public GeneralResponse updateThreshold(Integer warn, Integer danger) {
		GeneralResponse response = new GeneralResponse();
		if(warn <= 0 || danger <= 0 || warn <= danger) {
			response.setCode(3);
			response.setMessage("输入参数为无效值");
		}else {
			int code = this.materialService.updateAllThreshold(warn, danger);
			if(code == 2) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else {
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
}
