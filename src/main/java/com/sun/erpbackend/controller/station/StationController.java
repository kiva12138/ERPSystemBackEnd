package com.sun.erpbackend.controller.station;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.erpbackend.response.GeneralResponse;
import com.sun.erpbackend.response.SingleStationInfoResponse;
import com.sun.erpbackend.response.StationFindResponse;
import com.sun.erpbackend.response.StationOveralResponse;
import com.sun.erpbackend.response.materialcurrent.MaterialCurrentResponse;
import com.sun.erpbackend.response.statisticbystation.MaterialStatisticsByStation;
import com.sun.erpbackend.service.station.StationService;

@CrossOrigin
@RestController
@RequestMapping("/erp/api/station")
public class StationController {
	@Autowired
	StationService stationService;
	
	@GetMapping("/getcurrent")
	public MaterialCurrentResponse getMaterialCurrent(Integer mid, String sname, Integer page, Integer size) {
		if(page == null) {
			page = 0;
		}
		MaterialCurrentResponse response = this.stationService.getMaterialCurrent(mid, sname, page, size);
		if(response.getCode() == 1) {
			response.setMessage("成功");
		}else {
			response.setMessage("内部错误");
		}
		return response;
	}
	
	@GetMapping("/getmaterialdata")
	public MaterialStatisticsByStation getMaterailData(Integer id, String name, Integer status,
			Integer time, Integer page) {
		MaterialStatisticsByStation response = new MaterialStatisticsByStation();
		if(id == null) {
			id = 0;
		}
		if(page == null) {
			page = 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -time);
		response = this.stationService.getMaterailData(id, name, status, calendar.getTime(), page);
		if(response.getCode()==2) {
			response.setMessage("内部错误");
		}else {
			response.setMessage("成功");
		}
		return response;
	}
	
	@GetMapping("/overall")
	public StationOveralResponse getOveralData() {
		StationOveralResponse response = new StationOveralResponse();
		response = this.stationService.getOverallData();
		if(response.getCode()==2) {
			response.setMessage("内部错误");
		}else {
			response.setMessage("成功");
		}
		return response;
	}
	
	@PostMapping("/new")
	public GeneralResponse createNewStaion(String name) {
		GeneralResponse response = new GeneralResponse();
		if(name==null || name=="") {
			response.setCode(3);
			response.setMessage("参数为空");
			return response;
		}
		int code = this.stationService.createNewStaion(name);
		if(code==2) {
			response.setCode(2);
			response.setMessage("内部错误");
		}else {
			response.setCode(1);
			response.setMessage("成功");
		}
		return response;
	}
	
	@PostMapping("/edit")
	public GeneralResponse editStaion(Integer id, String name) {
		GeneralResponse response = new GeneralResponse();
		if(name==null || name=="" || id==0) {
			response.setCode(3);
			response.setMessage("参数为空");
			return response;
		}
		int code = this.stationService.editStation(id, name);
		if(code==2) {
			response.setCode(2);
			response.setMessage("内部错误");
		}else if(code==5) {
			response.setCode(5);
			response.setMessage("输入的工位不存在");
		}else{
			response.setCode(1);
			response.setMessage("成功");
		}
		return response;
	}
	
	@PostMapping("/maintain")
	public GeneralResponse maintain(Integer id) {
		GeneralResponse response = new GeneralResponse();
		if(id==null || id==0) {
			response.setCode(3);
			response.setMessage("参数为空");
			return response;
		}
		int code = this.stationService.maintain(id);
		if(code==2) {
			response.setCode(2);
			response.setMessage("内部错误");
		}else if(code==4) {
			response.setCode(4);
			response.setMessage("此工位有工单在生产中");
		}else if(code==5) {
			response.setCode(5);
			response.setMessage("输入的工位不存在");
		}else {
			response.setCode(1);
			response.setMessage("成功");
		}
		return response;
	}
	
	@PostMapping("/reproduce")
	public GeneralResponse reProduce(Integer id) {
		GeneralResponse response = new GeneralResponse();
		if(id==null || id==0) {
			response.setCode(3);
			response.setMessage("参数为空");
			return response;
		}
		int code = this.stationService.reProduce(id);
		if(code==2) {
			response.setCode(2);
			response.setMessage("内部错误");
		}else if(code==5) {
			response.setCode(5);
			response.setMessage("输入的工位不存在s");
		}else {
			response.setCode(1);
			response.setMessage("成功");
		}
		return response;
	}
	
	@PostMapping("/stopproduce")
	public GeneralResponse stopProduce(Integer id) {
		GeneralResponse response = new GeneralResponse();
		if(id==null || id==0) {
			response.setCode(3);
			response.setMessage("参数为空");
			return response;
		}
		int code = this.stationService.stopProduce(id);
		if(code==2) {
			response.setCode(2);
			response.setMessage("内部错误");
		}else if(code==4) {
			response.setCode(4);
			response.setMessage("此工位有工单在生产中");
		}else if(code==5) {
			response.setCode(5);
			response.setMessage("输入的工位不存在s");
		}else {
			response.setCode(1);
			response.setMessage("成功");
		}
		return response;
	}
	
	@PostMapping("/delete")
	public GeneralResponse delete(Integer id) {
		GeneralResponse response = new GeneralResponse();
		if(id==null || id==0) {
			response.setCode(3);
			response.setMessage("参数为空");
			return response;
		}
		int code = this.stationService.delete(id);
		if(code==2) {
			response.setCode(2);
			response.setMessage("内部错误");
		}else if(code==4) {
			response.setCode(4);
			response.setMessage("此工位有工单在生产中");
		}else if(code==5) {
			response.setCode(5);
			response.setMessage("输入的工位不存在s");
		}else {
			response.setCode(1);
			response.setMessage("成功");
		}
		return response;
	}
	
	/**
	 * 
	 * @param id
	 * @return code 1-True 2-False 3-Empty
	 */
	@GetMapping("/staionexist")
	public GeneralResponse stationExist(Integer id) {
		GeneralResponse response = new GeneralResponse();
		if(id == null || id == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			int code = this.stationService.existById(id);
			if(code == 1) {
				response.setCode(1);
				response.setMessage("Yes");
			}else {
				response.setCode(2);
				response.setMessage("No");
			}
		}
		return response;
	}
	
	@GetMapping("/getbyid")
	public SingleStationInfoResponse getById(Integer id) {
		SingleStationInfoResponse response = new SingleStationInfoResponse();
		if(id == null || id == 0) {
			response.setCode(3);
			response.setMessage("参数为空");
		}else {
			response = this.stationService.findById(id);
			if(response.getCode() == 2) {
				response.setCode(2);
				response.setMessage("内部错误");
			}else if(response.getCode() == 4) {
				response.setCode(4);
				response.setMessage("输入的工位不存在");
			}else{
				response.setCode(1);
				response.setMessage("成功");
			}
		}
		return response;
	}
	
	@GetMapping("/find")
	public StationFindResponse findStation(Integer id, String name, Integer status, Integer page, Integer size) {
		StationFindResponse response = new StationFindResponse();
		if(id == 0 && name == "" && status == 0 && page < 0) {
			response.setCode(3);
			response.setMessage("参数为空");
			return response;
		}
		if(name==null) {
			name = "";
		}
		response = this.stationService.findStation(id, name, status, page, size);
		if(response.getCode()==2) {
			response.setMessage("内部错误");
		}else {
			response.setMessage("成功");
		}
		return response;
		
	}
}
