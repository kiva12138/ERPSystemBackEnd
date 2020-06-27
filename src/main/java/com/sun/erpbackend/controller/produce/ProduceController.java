package com.sun.erpbackend.controller.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.erpbackend.response.bill.BillCurrentProduceResponse;
import com.sun.erpbackend.response.materialcurrent.MaterialCurrentProduceResponse;
import com.sun.erpbackend.response.produce.EstimateDataResponse;
import com.sun.erpbackend.response.produce.RefusedProduce;
import com.sun.erpbackend.response.produce.StoppedProduceResponse;
import com.sun.erpbackend.response.statisticbystation.StationProducingResponse;
import com.sun.erpbackend.service.produce.ProduceService;

@CrossOrigin
@RestController
@RequestMapping("/erp/api/produce")
public class ProduceController {
	
	@Autowired
	ProduceService produceService;
	
	/**
	 * 
	 * @return 1-Success 2-Internal Error
	 */
	@GetMapping("/getwaitingmaterial")
	MaterialCurrentProduceResponse getWaitingMaterial() {
		MaterialCurrentProduceResponse response = new MaterialCurrentProduceResponse();
		response = this.produceService.getWaitingMaterialStatistics();
		if (response.getCode() == 1) {
			response.setMessage("Success");
		}else {
			response.setMessage("Internal Error");
		}
		return response;
	}
	
	/**
	 * 
	 * @return 1-Success 2-Internal Error
	 */
	@GetMapping("/getwaitingbill")
	BillCurrentProduceResponse getWaitingBill() {
		BillCurrentProduceResponse response = new BillCurrentProduceResponse();
		response = this.produceService.getWaitingBillStatistics();
		if (response.getCode() == 1) {
			response.setMessage("Success");
		}else {
			response.setMessage("Internal Error");
		}
		return response;
	}
	
	/**
	 * 
	 * @return 1-Success 2-Internal Error
	 */
	@GetMapping("/getproducingmaterial")
	MaterialCurrentProduceResponse getProducingMaterial() {
		MaterialCurrentProduceResponse response = new MaterialCurrentProduceResponse();
		response = this.produceService.getProducingMaterialStatistics();
		if (response.getCode() == 1) {
			response.setMessage("Success");
		}else {
			response.setMessage("Internal Error");
		}
		return response;
	}
	
	/**
	 * 
	 * @return 1-Success 2-Internal Error
	 */
	@GetMapping("/getproducingbill")
	BillCurrentProduceResponse getProducingBill() {
		BillCurrentProduceResponse response = new BillCurrentProduceResponse();
		response = this.produceService.getProducingBillStatistics();
		if (response.getCode() == 1) {
			response.setMessage("Success");
		}else {
			response.setMessage("Internal Error");
		}
		return response;
	}
	
	/**
	 * 
	 * @return 1-Success 2-Internal Error
	 */
	@GetMapping("/getproducingstation")
	StationProducingResponse getProducingStation() {
		StationProducingResponse response = new StationProducingResponse();
		response = this.produceService.getProducingStationStatistics();
		if (response.getCode() == 1) {
			response.setMessage("Success");
		}else {
			response.setMessage("Internal Error");
		}
		return response;
	}
	
	/**
	 * 
	 * @return 1-Success 2-Internal Error
	 */
	@GetMapping("/estimatedata")
	EstimateDataResponse getEstimateDataResponse () {
		EstimateDataResponse response = new EstimateDataResponse();
		response = this.produceService.getEstimateData();
		if (response.getCode() == 1) {
			response.setMessage("Success");
		} else {
			response.setMessage("Internal Error");
		}
		return response;
	}
	
	/**
	 * 
	 * @return 1-Success 2-Internal Error 3-Not Good Params
	 */ 
	@GetMapping("/refused")
	RefusedProduce getRefusedProduce (Integer refuseKind) {
		RefusedProduce response  = new RefusedProduce();
		if (refuseKind <=0 || refuseKind >= 5) {
			response.setCode(3);
			response.setMessage("参数错误");
			return response;
		}
		response = this.produceService.findRefusedProduce(refuseKind);
		if (response.getCode() == 2) {
			response.setMessage("Internal Error");
		}else {
			response.setMessage("Success");
		}
		return response;
	}
	
	/**
	 * 
	 * @return 1-Success 2-Internal Error
	 */ 
	@GetMapping("/stopped")
	StoppedProduceResponse getStoppedProduce () {
		StoppedProduceResponse response  = new StoppedProduceResponse();
		response = this.produceService.findStoppedProduce();
		if (response.getCode() == 2) {
			response.setMessage("Internal Error");
		}else {
			response.setMessage("Success");
		}
		return response;
	}
	
}
