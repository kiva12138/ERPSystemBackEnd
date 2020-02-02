package com.sun.erpbackend.service.material;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.erpbackend.config.ERPStaticData;
import com.sun.erpbackend.entity.material.Material;
import com.sun.erpbackend.repository.material.MaterialRepository;
import com.sun.erpbackend.response.MaterialCategorySearchResponse;
import com.sun.erpbackend.response.threshold.SingleMaterialThreshold;
import com.sun.erpbackend.response.threshold.ThresholdResponse;

@Service
@Secured("ROLE_ADMIN")
@Transactional
public class MaterialService {
	@Autowired
	MaterialRepository materialRepository;
	
	public boolean exitsById(Integer id) {
		return this.materialRepository.existsById(id);
	}
	
	public Material findMaterialById(Integer id) {
		return this.materialRepository.findById(id).orElse(null);
	}
	
	/*
	 * Search Material Kind
	 * Input: ID(0), Name(""), Status(0), Kind(0)
	 * Output: ID, Name, All, Left, Distributed, King, Status, Description 
	 * Code: 1-Correct 2-Error
	 */
	public MaterialCategorySearchResponse findMaterialCategory(int id, String name, int status, int kind, int page, int size) {
		MaterialCategorySearchResponse response = new MaterialCategorySearchResponse();
		Sort sort = Sort.by(Direction.DESC, "id");
		Page<Material> result = this.materialRepository.findMaterialCategory(id, name, status, kind,
				PageRequest.of(page, size, sort));
		if (result == null) {
			response.setCode(2);
			return response;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(Material material : result) {
			response.addMaterial(material.getId(), material.getName(),
					material.getAllMount(), material.getDescription(),
					material.getLeftMount(), material.getDistributedMount(),
					material.getKind(), material.getStatus(),
					dateFormat.format(material.getLastTransport()));
		}
		response.setCode(1);
		response.setAllLength(this.materialRepository.findMaterialCategoryCount(id, name, status, kind));
		return response;
	}

	/**
	 * 增加新的物料种类
	 * Return 
	 * 	1-Correct
	 * 	2-Error
	 * @param name
	 * @param kind
	 * @param description
	 * @return
	 */
	public int addMaterialCategory(String name, int kind, String description) {
		Material material = new Material(name, 0, 0, 0, kind, 2, description,
				new Date(), ERPStaticData.defaultMaterialWarnThreshold,
				ERPStaticData.defaultMaterialDangerThreshold);
		Material tempMaterial = this.materialRepository.saveAndFlush(material);
		if (tempMaterial == null) {
			return 2;
		} else if(tempMaterial.getId() == 0) {
			return 2;
		}
		return 1;
	}
	
	/**
	 * Update Material
	 * Return 
	 * 	1-Correct
	 * 	2-Error
	 * @param id
	 * @param name
	 * @param kind
	 * @param description
	 * @return
	 */
	public int updateMaterialCategory(Integer id, String name, int kind, String description) {
		Material material = this.materialRepository.findById(id).orElse(null);
		if(material == null) {
			return 2;
		}
		material.setName(name);
		material.setKind(kind);
		material.setDescription(description);
		Material tempMaterial = this.materialRepository.save(material);
		if (tempMaterial == null) {
			return 2;
		} else if(tempMaterial.getId() == 0) {
			return 2;
		}
		return 1;
	}
	
	/**
	 * Delete One Material
	 * Return 
	 * 	1-Correct
	 * 	2-Error
	 * @param id
	 * @return
	 */
	public int deleteMaterialCategory(Integer id) {
		this.materialRepository.deleteById(id);
		return 1;
	}
	
	/**
	 * Find the name of one material by its id
	 * @param id
	 */
	public String findNameByMaterialId(Integer id) {
		Material material = this.materialRepository.findById(id).orElse(null);
		if (material != null) {
			return material.getName();
		}
		return null;
	}
	
	/**
	 * Find the ids of name by like
	 * @param id
	 * @return
	 */
	public List<Integer> findIdByNameLike(String name) {
		return this.materialRepository.findIdByNameLike(name);
	}
	
	/**
	 * Update Material Mount Just For Accept And Output
	 * @param id
	 * @param mount
	 * @param way 1-in -1-out
	 * @return 1-Correct 2-Error
	 */
	public int updateMaterialMountByProduce(Integer id, Integer mount, Integer way) {
		Material material = this.materialRepository.findById(id).orElse(null);
		if (material == null) {
			return 2;
		}
		if(way == 1) {
			material.setLeftMount(material.getLeftMount() + mount);
			material.setAllMount(material.getAllMount() + mount);
		}else if(way == -1){
			material.setDistributedMount(material.getDistributedMount() + mount);
			material.setLeftMount(material.getLeftMount() - mount);
		}
		Material tempMaterial = this.materialRepository.saveAndFlush(material);
		if (tempMaterial == null) {
			return 2;
		} else if(tempMaterial.getId() == 0) {
			return 2;
		}
		return 1;
	}
	
	/**
	 * Update The Mount Of Material by Transport
	 * @param id
	 * @param mount
	 * @param way
	 * @return 1-Success 2-Error
	 */
	public int updateMaterialMountByTransport(Integer id, Integer mount, Integer way) {
		Material material = this.materialRepository.findById(id).orElse(null);
		if (material == null) {
			return 2;
		}
		if(way == 1) {
			material.setLeftMount(material.getLeftMount() + mount);
			material.setAllMount(material.getAllMount() + mount);
		}else if(way == -1){
			material.setLeftMount(material.getLeftMount() - mount);
			material.setAllMount(material.getAllMount() - mount);
		}
		if(material.getLeftMount() >= material.getWarnThresHold()) {
			material.setStatus(1);
		}else if (material.getLeftMount() >= material.getDangerThredHold()) {
			material.setStatus(3);
		}else{
			material.setStatus(2);
		}
		material.setLastTransport(new Date());
		Material tempMaterial = this.materialRepository.saveAndFlush(material);
		if (tempMaterial == null) {
			return 2;
		} else if(tempMaterial.getId() == 0) {
			return 2;
		}
		return 1;
	}
	
	/**
	 * Update the threshold of one material
	 * @param id
	 * @param warn
	 * @param danger
	 * @return
	 */
	public int updateThreshold(Integer id, Integer warn, Integer danger) {
		int result = this.materialRepository.updateThreshold(id, warn, danger);
		if(result == 1) {
			return 1;
		}else {
			return 2;
		}
	}
	
	
	/**
	 * Update the thresholds all material
	 * @param warn
	 * @param danger
	 * @return
	 */
	public int updateAllThreshold(Integer warn, Integer danger) {
		int result = this.materialRepository.updateAllThreshold(warn, danger);
		if(result >= 1) {
			return 1;
		}
		else {
			return 2;
		}
	}

	public ThresholdResponse findMaterialThreshold(int id, String name, int status, int kind, int page, int size) {
		ThresholdResponse response = new ThresholdResponse();
		Sort sort = Sort.by(Direction.DESC, "id");
		Page<Material> result = this.materialRepository.findMaterialCategory(id, name, status, kind,
				PageRequest.of(page, size, sort));
		if (result == null) {
			response.setCode(2);
			return response;
		}
		for(Material material : result) {
			SingleMaterialThreshold singleMaterialThreshold = new SingleMaterialThreshold();
			singleMaterialThreshold.setId(material.getId());
			singleMaterialThreshold.setName(material.getName());
			singleMaterialThreshold.setStatus(material.getStatus());
			singleMaterialThreshold.setDanger(material.getDangerThredHold());
			singleMaterialThreshold.setWarn(material.getWarnThresHold());
			singleMaterialThreshold.setMount(material.getLeftMount());
			response.getData().add(singleMaterialThreshold);
		}
		response.setCode(1);
		response.setAllLength(this.materialRepository.findMaterialCategoryCount(id, name, status, kind));
		return response;
	}
}
