package com.sun.erpbackend.controller.bill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.erpbackend.response.BillAllDataResponse;
import com.sun.erpbackend.response.BillOverallResponse;
import com.sun.erpbackend.response.GeneralResponse;
import com.sun.erpbackend.response.bill.BillSearchResponse;
import com.sun.erpbackend.service.bill.BillService;

@CrossOrigin
@RestController
@RequestMapping("/erp/api/bill")
public class BillController {
	@Autowired
	BillService billService;
	
	/**
	 * Get The Overall data of bills
	 * @param time
	 * @return code 1-成功 2-Error 3-Empty
	 */ 
	@GetMapping("/overall")
	public BillOverallResponse findBillOverall(Integer time) {
		BillOverallResponse response = new BillOverallResponse();
		if(time == null) {
			response.setCode(3);
			response.setMessage("输入的参数为空");
		}else {
			response = this.billService.findBillOverall(time);
			if(response.getCode() == 2) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else{
				response.setCode(1);
				response.setMessage("成功");
			};
		}
		return response;
	}
	
	/**
	 * Create New Bill
	 * @param name
	 * @param output
	 * @param outputMount
	 * @param estimateTime
	 * @param materials
	 * @param description
	 * @return
	 */
	@PostMapping("/createnew")
	public GeneralResponse newBill(@RequestBody NewBillParam param) {
		GeneralResponse response = new GeneralResponse();
		if(param.name=="" || param.output == 0 || param.outputMount < 1 || param.estimateTime < 0 || param.materials==null) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code = this.billService.createNewBill(param.name, param.output, 
					param.estimateTime, param.outputMount, param.materials, param.description);
			if(code == 2) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else if(code == 4){
				response.setCode(4);
				response.setMessage("输入的物料不存在");
			}else {
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	/**
	 * Edit Bill
	 * @param name
	 * @param output
	 * @param outputMount
	 * @param estimateTime
	 * @param materials
	 * @param description
	 * @return
	 */
	@PostMapping("/edit")
	public GeneralResponse editBill(@RequestBody EditBillParam param) {
		GeneralResponse response = new GeneralResponse();
		if(param.id == 0 || param.name=="" || param.output == 0 ||
			param.outputMount < 1 || param.estimateTime < 0 || param.materials==null) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code = this.billService.editBill(param.id,param.name, param.output,
					param.estimateTime, param.outputMount,param. materials, param.description);
			if(code == 2) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else if(code == 4){
				response.setCode(4);
				response.setMessage("物料不存在");
			}else {
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	/**
	 * 
	 * @param billId
	 * @param stationId
	 * @return 5-station Not Exits
	 */
	@PostMapping("/distribute")
	public GeneralResponse destributeBill(Integer billId, Integer stationId) {
		GeneralResponse response = new GeneralResponse();
		if(billId == 0 || stationId == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code=this.billService.distributeBill(billId, stationId);
			if(code == 2 ) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else if(code == 4) {
				response.setCode(5);
				response.setMessage("无效的工位编号");
			}else if(code == 5) {
				response.setCode(6);
				response.setMessage("工位没有在运行");
			}else if(code == 3) {
				response.setCode(4);
				response.setMessage("无效的工单编号");
			}else{
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	/**
	 * 
	 * @param billId
	 * @return 1-成功 2-内部错误 3-Invalid Id 4-Mount Not enough
	 */
	@PostMapping("/accept")
	public GeneralResponse acceptBill(Integer billId) {
		GeneralResponse response = new GeneralResponse();
		if(billId == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code=this.billService.acceptBill(billId);
			if(code == 2 ) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else if(code == 3) {
				response.setCode(4);
				response.setMessage("无效的工单编号");
			}else if(code == 4) {
				response.setCode(5);
				response.setMessage("无效的工位编号");
			}else if(code == 5) {
				response.setCode(6);
				response.setMessage("工位没有在运行");
			}else if(code == 6) {
				response.setCode(7);
				response.setMessage("物料数量不足");
			}else{
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	@PostMapping("/refuse")
	public GeneralResponse refuseBill(Integer billId, String reason) {
		GeneralResponse response = new GeneralResponse();
		if(billId == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code=this.billService.refuseBill(billId, reason);
			if(code == 2 ) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else if(code == 3) {
				response.setCode(4);
				response.setMessage("无效的工单编号");
			}else{
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	@PostMapping("/complete")
	public GeneralResponse completeBill(Integer billId) {
		GeneralResponse response = new GeneralResponse();
		if(billId == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code=this.billService.completeBill(billId);
			if(code == 2 ) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else if(code == 3) {
				response.setCode(4);
				response.setMessage("无效的工单编号");
			}else{
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	@PostMapping("/delete")
	public GeneralResponse deleteBill(Integer billId) {
		GeneralResponse response = new GeneralResponse();
		if(billId == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code=this.billService.deleteBill(billId);
			if(code == 2 ) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else if(code == 3 ){
				response.setCode(4);
				response.setMessage("无效的工单编号");
			}else{
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param kind
	 * @param output
	 * @param material
	 * @param stationId
	 * @return code 1-成功 2-Error 3-参数为空
	 */
	@GetMapping("/find")
	public BillSearchResponse findBill(Integer id, String name, Integer kind,
			Integer output, Integer material, Integer stationId, Integer page) {
		BillSearchResponse response = new BillSearchResponse();
		if(id == 0 && name == "" && kind == 0 && output == 0 && material==0 && stationId == 0 && page < 0) {
			response.setCode(3);
			response.setMessage("参数为空");
			return response;
		}
		if(name==null) {
			name = "";
		}
		response = this.billService.findBill(id, name, kind, output, material, stationId, page);
		if(response.getCode()==2) {
			response.setMessage("内部错误");
		}else {
			response.setMessage("成功");
		}
		return response;
	}
	
	@GetMapping("/findwithstatus")
	public BillSearchResponse findBillWithStatus(Integer id, String name, Integer kind, Integer status,
			Integer output, Integer material, Integer stationId, Integer page) {
		BillSearchResponse response = new BillSearchResponse();
		if(name==null) {
			name = "";
		}
		response = this.billService.findBillWithStatus(id, name, kind, status, output, material, stationId, page);
		if(response.getCode()==2) {
			response.setMessage("内部错误");
		}else {
			response.setMessage("成功");
		}
		return response;
	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/alldata")
	public BillAllDataResponse findAllData() {
		BillAllDataResponse response = this.billService.findAllData();
		if(response.getCode() == 2) {
			response.setMessage("内部错误");
		}else {
			response.setMessage("成功");
		}
		return response;
	}
	
	@GetMapping("/redistributetime")
	public GeneralResponse reDistributeTime(Integer id, Integer time) {
		GeneralResponse response = new GeneralResponse();
		if(id == 0 || time <= 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}
		int code = this.billService.reDistributeTime(id, time);
		if(code == 2) {
			response.setCode(2);
			response.setMessage("内部错误");
		}else if(code == 4) {
			response.setCode(4);
			response.setMessage("内部错误");
		}else{
			response.setCode(1);
			response.setMessage("成功");
		}
		return response;
	}
	
	@GetMapping("/stopproduce")
	public GeneralResponse stopProduce(Integer id) {
		GeneralResponse response = new GeneralResponse();
		if(id == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}
		int code = this.billService.stopProduce(id);
		if(code == 2) {
			response.setCode(2);
			response.setMessage("内部错误");
		}else {
			response.setCode(1);
			response.setMessage("成功");
		}
		return response;
	}
	
	@PostMapping("/stopbystation")
	public GeneralResponse stopByStatione(@RequestBody StopByStationParam param) {
		GeneralResponse response = new GeneralResponse();
		if(param.id == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}
		int code = this.billService.stopByStatione(param.id, param.reason, param.haveUsed, param.haveOutput);
		if(code == 2) {
			response.setCode(2);
			response.setMessage("内部错误");
		}else if(code == 4) {
			response.setCode(4);
			response.setMessage("输入的物料不存在");
		}else {
			response.setCode(1);
			response.setMessage("成功");
		}
		return response;
	}
	
	@PostMapping("/redistributeandstart")
	public GeneralResponse reDistributeAndStart(@RequestBody RedistributeParam param) {
		GeneralResponse response = new GeneralResponse();
		if(param.id == 0 || param.materials==null) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code = this.billService.reDistributeAndStart(param.id, param.materials);
			if(code == 2) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else if(code == 4){
				response.setCode(4);
				response.setMessage("输入的物料不存在");
			}else if(code == 5){
				response.setCode(5);
				response.setMessage("物料数量不足");
			}else if(code == 6){
				response.setCode(6);
				response.setMessage("工位没有在运行");
			}else {
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	@PostMapping("/restart")
	public GeneralResponse reStart(Integer id) {
		GeneralResponse response = new GeneralResponse();
		if(id == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code = this.billService.reStart(id);
			if(code == 2) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else if(code == 4) {
				response.setCode(4);
				response.setMessage("工单编号无效");
			}else if(code == 5) {
				response.setCode(4);
				response.setMessage("工位没有在运行");
			}else{
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	@PostMapping("/recycleandstop")
	public GeneralResponse recycleAndStop(Integer id) {
		GeneralResponse response = new GeneralResponse();
		if(id == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code = this.billService.recycleAndStop(id);
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
