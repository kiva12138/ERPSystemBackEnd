package com.sun.erpbackend.service.material;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.erpbackend.config.ERPStaticData;
import com.sun.erpbackend.entity.material.MaterialProduce;
import com.sun.erpbackend.repository.material.MaterialProduceRepository;
import com.sun.erpbackend.response.MaterialStatisticsResposne;

@Service
@Secured("ROLE_ADMIN")
@Transactional
public class MaterialProduceService {
	@Autowired
	MaterialProduceRepository materialProduceRepository;
	@Autowired
	MaterialService materialService;
	
	/**
	 * Add New Material Producing Record
	 * @param mid
	 * @param mount
	 * @param way
	 * @return 1-Correct 2-Error 4-Not exist
	 */
	public int addNewMaterialProduce(Integer mid, Integer mount, Integer way, Integer stationId) {
		if(!this.materialService.exitsById(mid)) {
			return 4;
		}
		MaterialProduce materialProduce = new MaterialProduce();
		materialProduce.setMid(mid);
		materialProduce.setMount(mount);
		materialProduce.setWay(way);
		materialProduce.setTime(new Date());
		materialProduce.setStationId(stationId);
		MaterialProduce result = this.materialProduceRepository.saveAndFlush(materialProduce);
		if(result.getId() == 0) {
			return 2;
		}
		materialService.updateMaterialMountByProduce(mid, mount, way);
		return 1;
	}
	
	/**
	 * Delete By Time
	 * @param date 
	 * @return 1-Correct 2-ERROR
	 */
	public int deleteMaterialProduceBeforeDate(String date) {
		try {
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			this.materialProduceRepository.deleteProduceByTime(date2);
		} catch (ParseException e) {
			e.printStackTrace();
			return 2;
		}
		return 1;
	}
	
	/**
	 * Find Statistics Of One or Some Materials
	 * @param mid
	 * @param name
	 * @param start
	 * @param end
	 * @param size 
	 * @param page 
	 * @return code 1-correct 2-error
	 */
	public MaterialStatisticsResposne findMaterialProduce(Integer mid, String name, Date start, int page, int size) {
		List<MaterialProduce> results;
		Sort sort = Sort.by(Direction.DESC, "id");
		if(mid == null) {
			mid = 0;
		}
		if (mid != 0) {
			results = this.materialProduceRepository.findProduceByMidAndTime(mid, start, 
					PageRequest.of(page, ERPStaticData.billPagination, sort));
		}else {
			results = new ArrayList<MaterialProduce>();
			List<Integer> ids = this.materialService.findIdByNameLike(name);
			for(Integer id: ids) {
				List<MaterialProduce> tempResult = this.materialProduceRepository.findProduceByMidAndTime(id, start, 
						PageRequest.of(page, ERPStaticData.billPagination, sort));
				results.addAll(tempResult);
			}
		}
		MaterialStatisticsResposne resposne = new MaterialStatisticsResposne();
		if(results == null) {
			resposne.setCode(2);
			return resposne;
		}
		results.sort(new Comparator<MaterialProduce>() {
			@Override
			public int compare(MaterialProduce o1, MaterialProduce o2) {
				return o1.getMid() - o2.getMid();
			}
		});
		
		if (results.size() == 0) {
			resposne.setCode(1);
			return resposne;
		}
		
		int currentId = results.get(0).getMid();
		int currentOutput = 0;
		int currentUse = 0;
		for(int i=0; i < results.size(); i++) {
			if(results.get(i).getMid() == currentId) {
				if (results.get(i).getWay() == 1) {
					currentOutput += results.get(i).getMount();
				}else if(results.get(i).getWay() == -1) {
					currentUse += results.get(i).getMount();
				}
			}else {
				resposne.addData(currentId, this.materialService.findNameByMaterialId(currentId), 
						currentOutput, currentUse);
				currentId = results.get(i).getMid();
				currentOutput = 0;
				currentUse = 0;
				i--;
			}
		}
		resposne.addData(currentId, this.materialService.findNameByMaterialId(currentId), 
				currentOutput, currentUse);
		resposne.sortData();
		resposne.setAllLength(this.materialProduceRepository.findMountProduceByMidAndTime(mid, start));
		resposne.setCode(1);
		return resposne;
		
	}
	
	/**
	 * Find Statistics Of One or Some Materials By default (Default days is 15)
	 * @param mid
	 * @param name
	 * @param start
	 * @param end
	 * @return code 1-correct 2-error
	 */
	public MaterialStatisticsResposne findMaterialProduceDefault() {
		Calendar calendar=Calendar.getInstance();
		Date endDate = calendar.getTime();
		calendar.add(Calendar.DATE, -15);
		Date startDate = calendar.getTime();
		List<MaterialProduce> results = this.materialProduceRepository.findProduceDefault(startDate, endDate);
		MaterialStatisticsResposne resposne = new MaterialStatisticsResposne();
		if(results == null) {
			resposne.setCode(2);
			return resposne;
		}
		results.sort(new Comparator<MaterialProduce>() {
			@Override
			public int compare(MaterialProduce o1, MaterialProduce o2) {
				return o1.getMid() - o2.getMid();
			}
		});
		
		if (results.size() == 0) {
			return resposne;
		}
		
		int currentId = results.get(0).getMid();
		int currentOutput = 0;
		int currentUse = 0;
		for(int i=0; i < results.size(); i++) {
			if(results.get(i).getMid() == currentId) {
				if (results.get(i).getWay() == 1) {
					currentOutput += results.get(i).getMount();
				}else if(results.get(i).getWay() == -1) {
					currentUse += results.get(i).getMount();
				}
			}else {
				resposne.addData(currentId, this.materialService.findNameByMaterialId(currentId), 
						currentOutput, currentUse);
				currentId = results.get(i).getMid();
				currentOutput = 0;
				currentUse = 0;
				i--;
			}
		}
		resposne.addData(currentId, this.materialService.findNameByMaterialId(currentId), 
				currentOutput, currentUse);
		
		return resposne;
		
	}
}
