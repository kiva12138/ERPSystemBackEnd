package com.sun.erpbackend.controller.tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.erpbackend.response.GeneralResponse;
import com.sun.erpbackend.response.tree.TreeSearchResponse;
import com.sun.erpbackend.response.tree.TreeSearchResponseWithMount;
import com.sun.erpbackend.service.Tree.TreeService;

@CrossOrigin
@RestController
@RequestMapping("/erp/api/tree")
public class TreeController {
	@Autowired
	TreeService treeService;
	
	/**
	 * 
	 * @param params
	 * @return 1-Correct 2-Empty 3-Internal Error 4-Not Exist
	 */
	@PostMapping("/createnew")
	public GeneralResponse addTree(@RequestBody NewTreeParams params) {
		GeneralResponse response  = new GeneralResponse();
		if (params.getName()=="" || params.getTargetmid()==0) {
			response.setCode(2);
			response.setMessage("Empty Value");
			return response;
		}
		int result = this.treeService.addTree(params.getName(), params.getTargetmid(),
				params.getDescription(), params.getNeeds());
		if (result == 2) {
			response.setCode(4);
			response.setMessage("Material ID not Exits");
			return response;
		}
		if (result == 3) {
			response.setCode(3);
			response.setMessage("Internal Error");
			return response;
		}
		response.setCode(1);
		response.setMessage("Success");
		return response;
	}

	
	/**
	 * 
	 * @param id
	 * @return 1-Success 2-NotExist 3-Empty 4-Internal Error
	 */
	@PostMapping("/delete")
	public GeneralResponse deleteTree(Integer id) {
		GeneralResponse response = new GeneralResponse();
		if (id == 0) {
			response.setCode(3);
			response.setMessage("Success");
			return response;
		}
		this.treeService.deleteTree(id);
		response.setCode(1);
		response.setMessage("Success");
		return response;
	}

	/**
	 * 
	 * @param params
	 * @return 1-Correct 2-Empty 3-Internal Error 4-Not Exist
	 */
	@PostMapping("/edit")
	public GeneralResponse editTree(@RequestBody EditTreeParams params) {
		GeneralResponse response  = new GeneralResponse();
		if (params.getName()=="" || params.getTargetmid()==0 || params.getStatus() == 0) {
			response.setCode(2);
			response.setMessage("Empty Value");
			return response;
		}
		int result = this.treeService.modifyTree(params.getId(), params.getName(), params.getTargetmid(),
				params.getStatus(), params.getDescription(), params.getNeeds());
		if (result == 2) {
			response.setCode(4);
			response.setMessage("Material ID not Exits");
			return response;
		}
		if (result == 3) {
			response.setCode(3);
			response.setMessage("Internal Error");
			return response;
		}
		response.setCode(1);
		response.setMessage("Success");
		return response;
	}

	/**
	 * 
	 * @param name
	 * @param opname
	 * @param needname
	 * @return 1-Success 2-Error
	 */ 
	@GetMapping("/get")
	public TreeSearchResponse findTree(String name, String opname, String needname, Integer page) {
		TreeSearchResponse response = this.treeService.findTreeNormal(name, opname, needname, page);
		if (response.getCode() == 2) {
			response.setMessage("Internal Error");
		}
		else {
			response.setMessage("Success");
		}
		return response;
	}
	
	/**
	 * 
	 * @param name
	 * @param opname
	 * @param needname
	 * @return 1-Success 2-Error
	 */ 
	@GetMapping("/getwithmount")
	public TreeSearchResponseWithMount findTreeWithMount(String name, String opname, String needname, Integer page) {
		TreeSearchResponseWithMount response = this.treeService.findTreeNormalWithMount(name, opname, needname, page);
		if (response.getCode() == 2) {
			response.setMessage("Internal Error");
		}
		else {
			response.setMessage("Success");
		}
		return response;
	}
	
	/**
	 * 
	 * @param name
	 * @param opname
	 * @param needname
	 * @return 1-Success 2-Error
	 */ 
	@GetMapping("/getbymid")
	public TreeSearchResponse findTreeByMid(Integer mid) {
		TreeSearchResponse response = this.treeService.findTreeByTargetMid(mid);
		if (response.getCode() == 2) {
			response.setMessage("Internal Error");
		}
		else {
			response.setMessage("Success");
		}
		return response;
	}
}
