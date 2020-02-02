package com.sun.erpbackend.controller.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.erpbackend.response.GeneralResponse;
import com.sun.erpbackend.response.TransportResponse;
import com.sun.erpbackend.service.material.TransportService;

@CrossOrigin
@RestController
@RequestMapping("/erp/api/transport")
public class TranportController {

	@Autowired
	TransportService transportService;
	
	/**
	 * add new transport of a material
	 * @param mid
	 * @param mount
	 * @param way
	 * @param description
	 * @return 1-Success 2-Internal Error 3-Empty Value 4-Material Not Exist 5-Material Not Enough
	 */
	@PostMapping("/add")
	public GeneralResponse addNewTransport(Integer mid, Integer mount, Integer way, String description) {
		GeneralResponse response = new GeneralResponse();
		if (mid == 0 || (way != 1 && way != -1)) {
			response.setCode(3);
			response.setMessage("Empty Value");
			return response;
		}
		int result = this.transportService.addNewTranposrt(mid, mount, way, description);
		if (result == 2) {
			response.setCode(2);
			response.setMessage("Internal Error");
			return response;
		}
		if (result == 4) {
			response.setCode(4);
			response.setMessage("Material Not Exist");
			return response;
		}
		if (result == 5) {
			response.setCode(5);
			response.setMessage("Material Not Enough");
			return response;
		}
		response.setCode(1);
		response.setMessage("Success");
		return response;
	}
	
	/**
	 * Delete transports records before one day
	 * @param date
	 * @return 3-Empty Value 2-Internal Error 1-Success
	 */
	@PostMapping("/delete")
	public GeneralResponse deleteTranportBeforeDate(String date) {
		GeneralResponse response = new GeneralResponse();
		if(date == "") {
			response.setCode(3);
			response.setMessage("Empty Value");
			return response;
		}
		int result = this.transportService.deleteBefore(date);
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
	 * find transport records by material id
	 * @param mid
	 * @return
	 */
	@GetMapping("/get")
	public TransportResponse findTransportByMid(Integer mid) {
		TransportResponse response = new TransportResponse();
		if(mid == 0) {
			response.setCode(3);
			response.setMessage("Empty Value");
			return response;
		}
		response = this.transportService.findTransportByMid(mid);
		if(response.getCode() == 2) {
			response.setMessage("Internal Error");
			return response;			
		}
		else {
			response.setMessage("Success");
		}
		return response;
	}
	
}
