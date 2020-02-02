package com.sun.erpbackend.controller.material;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.erpbackend.response.GeneralResponse;
import com.sun.erpbackend.response.MaterialStatisticsResposne;
import com.sun.erpbackend.service.material.MaterialProduceService;

@CrossOrigin
@RestController
@RequestMapping("/erp/api/materialproduce")
public class MaterialProduceController {
	@Autowired
	MaterialProduceService materialProduceService;
	
	/**
	 * new produce
	 * @param mid
	 * @param mount
	 * @param way
	 * @return code 1-correct 2-internal error 3-empty 4-mid not exist
	 */
	@PostMapping("/add")
	public GeneralResponse addNewMaterialProduce(Integer id, Integer mount, Integer way, Integer stationId) {
		GeneralResponse response = new GeneralResponse();
		if (id == 0 || (way != 1 && way != -1)) {
			response.setCode(3);
			response.setMessage("Empty Value");
			return response;
		}
		int result = this.materialProduceService.addNewMaterialProduce(id, mount, way, stationId);
		if (result == 2) {
			response.setCode(2);
			response.setMessage("Internal Error");
			return response;
		}
		
		if (result == 4) {
			response.setCode(4);
			response.setMessage("Material Not Exists");
			return response;
		}

		response.setCode(1);
		response.setMessage("Success");
		return response;
	}
	
	/**
	 * delete material before the exact date
	 * @param date
	 * @return code 1-correct 2-internal error 3-empty
	 */
	@PostMapping("/delete")
	public GeneralResponse deleteMaterialProduceBeforeDate(String date) {
		GeneralResponse response = new GeneralResponse();
		if(date == "") {
			response.setCode(3);
			response.setMessage("Empty Value");
			return response;
		}
		int result = this.materialProduceService.deleteMaterialProduceBeforeDate(date);
		if (result == 2) {
			response.setCode(2);
			response.setMessage("Internal Error");
			return response;
		}

		response.setCode(1);
		response.setMessage("Success");
		return response;
		
	}
	
	/**
	 * Search the statistics Of One material Or Some
	 * @param id
	 * @param name
	 * @param date
	 * @return code 1-correct 2-internal error 3-empty
	 */
	@GetMapping("/getbydate")
	public MaterialStatisticsResposne findMaterialStatisticsResposne(Integer id, String name, Integer date, int page, int size) {
		MaterialStatisticsResposne resposne = new MaterialStatisticsResposne();
		if(date == 0) {
			date = 30;
		}
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE, -date);
		Date startDate = calendar.getTime();
		resposne = this.materialProduceService.findMaterialProduce(id, name, startDate, page, size);
		if(resposne.getCode() == 2) {
			resposne.setMessage("Internal Error");
		}else {
			resposne.setMessage("Success");
		}
		return resposne;
	}
	
	/**
	 * Find Statistics By default
	 * @return
	 */
	@GetMapping("/getdefault")
	public MaterialStatisticsResposne findMaterialStatisticsResposneByDefalut() {
		MaterialStatisticsResposne resposne = new MaterialStatisticsResposne();
		resposne = this.materialProduceService.findMaterialProduceDefault();
		if(resposne.getCode() == 2) {
			resposne.setMessage("Internal Error");
		}else {
			resposne.setMessage("Success");
		}
		return resposne;
	}
}
