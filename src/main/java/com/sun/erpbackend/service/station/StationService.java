package com.sun.erpbackend.service.station;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.sun.erpbackend.entity.bill.Bill;
import com.sun.erpbackend.entity.bill.BillUse;
import com.sun.erpbackend.entity.material.MaterialProduce;
import com.sun.erpbackend.entity.station.Station;
import com.sun.erpbackend.repository.bill.BillOutputRepository;
import com.sun.erpbackend.repository.bill.BillReposiroty;
import com.sun.erpbackend.repository.bill.BillUseRepository;
import com.sun.erpbackend.repository.material.MaterialProduceRepository;
import com.sun.erpbackend.repository.station.StationRepository;
import com.sun.erpbackend.response.SingleStationInfoResponse;
import com.sun.erpbackend.response.StationFindResponse;
import com.sun.erpbackend.response.StationOveralResponse;
import com.sun.erpbackend.response.materialcurrent.MaterialCurrentResponse;
import com.sun.erpbackend.response.materialcurrent.SingleMaterialCurrentUse;
import com.sun.erpbackend.response.materialcurrent.SingleStationCurrentData;
import com.sun.erpbackend.response.statisticbystation.MaterialStatisticsByStation;
import com.sun.erpbackend.response.statisticbystation.SingleStationData;
import com.sun.erpbackend.response.statisticbystation.SingleStationMaterial;
import com.sun.erpbackend.service.material.MaterialService;

@Service
@Secured("ROLE_ADMIN")
@Transactional
public class StationService {
	@Autowired
	StationRepository stationRepository;
	@Autowired
	BillReposiroty billReposiroty;
	@Autowired
	BillUseRepository billUseRepository;
	@Autowired
	BillOutputRepository billOutputRepository;
	@Autowired
	MaterialService materialService;
	@Autowired
	MaterialProduceRepository materialProduceRepository;

	public StationOveralResponse getOverallData() {
		StationOveralResponse response = new StationOveralResponse();
		try {
			response.setAll(this.stationRepository.findMountByStatus(0));
			response.setStopped(this.stationRepository.findMountByStatus(2));
			response.setProducing(this.stationRepository.findMountByStatus(1));
			response.setMaintain(this.stationRepository.findMountByStatus(3));
		} catch (Exception e) {
			response.setCode(2);
		}
		response.setCode(1);
		return response;
	}

	public int createNewStaion(String name) {
		Station station = new Station();
		station.setLastmaintain(new Date());
		station.setName(name);
		station.setStatus(3);
		try {
			this.stationRepository.saveAndFlush(station);
		} catch (Exception e) {
			return 2;
		}
		return 1;
	}

	public int editStation(Integer id, String name) {
		if(!this.stationRepository.existsById(id)) {
			return 5;
		}
		Station station = this.stationRepository.findById(id).orElse(null);
		if(station == null) {
			return 2;
		}
		station.setName(name);
		this.stationRepository.saveAndFlush(station);
		return 1;
	}

	/**
	 * 
	 * @param id
	 * @return 1-Success 2-Internal Error 4-BillProducing 5-Id Not Exists
	 */
	public int maintain(Integer id) {
		if(!this.stationRepository.existsById(id)) {
			return 5;
		}
		List<Bill> bills = this.billReposiroty.findProducingBillsByStationId(id);
		if(bills == null) {
			return 2;
		}
		if(bills.size() != 0) {
			return 4;
		}
		Station station = this.stationRepository.findById(id).orElse(null);
		if(station==null) {
			return 2;
		}
		station.setStatus(3);
		this.stationRepository.saveAndFlush(station);
		return 1;
	}

	public int reProduce(Integer id) {
		if(!this.stationRepository.existsById(id)) {
			return 5;
		}
		Station station = this.stationRepository.findById(id).orElse(null);
		if(station==null) {
			return 2;
		}
		station.setStatus(1);
		this.stationRepository.saveAndFlush(station);
		return 1;
	}

	public int stopProduce(Integer id) {
		if(!this.stationRepository.existsById(id)) {
			return 5;
		}
		List<Bill> bills = this.billReposiroty.findProducingBillsByStationId(id);
		if(bills == null) {
			return 2;
		}
		if(bills.size() != 0) {
			return 4;
		}
		Station station = this.stationRepository.findById(id).orElse(null);
		if(station==null) {
			return 2;
		}
		station.setStatus(2);
		this.stationRepository.saveAndFlush(station);
		return 1;
	}

	public int delete(Integer id) {
		if(!this.stationRepository.existsById(id)) {
			return 5;
		}
		List<Bill> bills = this.billReposiroty.findProducingBillsByStationId(id);
		if(bills == null) {
			return 2;
		}else if(bills.size() != 0) {
			return 4;
		}
		this.stationRepository.deleteById(id);
		return 1;
	}
	
	/**
	 * 
	 * @param id
	 * @return 2-False 1-True
	 */
	public int existById(Integer id) {
		if(this.stationRepository.existsById(id)) {
			return 1;
		}else {
			return 2;
		}
	}

	public SingleStationInfoResponse findById(Integer id) {
		SingleStationInfoResponse response = new SingleStationInfoResponse();
		if(!this.stationRepository.existsById(id)) {
			response.setCode(4);
			return response;
		}
		response.setStation(this.stationRepository.findById(id).orElse(null));
		if(response.getStation() == null) {
			response.setCode(2);
		}
		response.setCode(1);
		return response;
	}

	public StationFindResponse findStation(Integer id, String name, Integer status, Integer page, Integer size) {
		Sort sort = Sort.by(Direction.DESC, "id");
		Page<Station> stations = this.stationRepository.findStation(id, name, status,
				PageRequest.of(page, size, sort));
		StationFindResponse response = new StationFindResponse();
		if(stations == null) {
			response.setCode(2);
			return response;
		}
		List<Station> data = new ArrayList<Station>();
		for(Station station : stations) {
			data.add(station);
		}
		response.setCode(1);
		response.setAllLength(this.stationRepository.findStationCount(id, name, status));
		response.setData(data);
		return response;
	}

	public MaterialStatisticsByStation getMaterailData(Integer id, String name, Integer status, Date time, int page) {
		MaterialStatisticsByStation response = new MaterialStatisticsByStation();
		
		Sort sort = Sort.by(Direction.DESC, "id");
		Page<Station> stations = this.stationRepository.findStation(id, name, status,
				PageRequest.of(page, ERPStaticData.stationStatisticsPagination, sort));
		if(stations == null) {
			response.setCode(2);
			return response;
		}
		
		for(Station station: stations) {
			List<MaterialProduce> materialProduces = this.materialProduceRepository.
				findProduceByStationAndTime(station.getId(), time);
			if(materialProduces == null) {
				response.setCode(2);
				return response;
			}
			if(materialProduces.size() == 0) {
				continue;
			}
			
			HashMap<Integer, Integer> outputs = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> uses = new HashMap<Integer, Integer>();
			for(MaterialProduce materialProduce : materialProduces) {
				if(materialProduce.getWay() == 1) {
					outputs.put(materialProduce.getMid(), 
							materialProduce.getMount() + outputs.getOrDefault(materialProduce.getMid(), 0));
				}else {
					uses.put(materialProduce.getMid(), 
							materialProduce.getMount() + uses.getOrDefault(materialProduce.getMid(), 0));
				}
			}
			SingleStationData singleStationData = new SingleStationData();
			singleStationData.setId(station.getId());
			singleStationData.setName(station.getName());
			singleStationData.setStatus(station.getStatus());
			for (Integer key : outputs.keySet()) {
				 SingleStationMaterial singleStationMaterial = new SingleStationMaterial();
				 singleStationMaterial.setId(key);
				 singleStationMaterial.setMount(outputs.get(key));
				 singleStationMaterial.setName(this.materialService.findNameByMaterialId(key));
				 singleStationData.getOut().add(singleStationMaterial);
			}
			for (Integer key : uses.keySet()) {
				 SingleStationMaterial singleStationMaterial = new SingleStationMaterial();
				 singleStationMaterial.setId(key);
				 singleStationMaterial.setMount(uses.get(key));
				 singleStationMaterial.setName(this.materialService.findNameByMaterialId(key));
				 singleStationData.getUse().add(singleStationMaterial);
			}
			response.getData().add(singleStationData);
		}
		
		
		/*Sort sort = Sort.by(Direction.DESC, "id");
		Page<Station> stations = this.stationRepository.findStation(id, name, status,
				PageRequest.of(page, ERPStaticData.stationStatisticsPagination, sort));
		if(stations == null) {
			response.setCode(2);
			return response;
		}
		
		for(Station station: stations) {
			List<Bill> bills = this.billReposiroty.findBillsByStationIdAndTime(station.getId(), time);
			if(bills == null) {
				response.setCode(2);
				break;
			}
			
			HashMap<Integer, Integer> outputs = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> uses = new HashMap<Integer, Integer>();
			for(Bill bill : bills) {
				BillOutput billOutput = this.billOutputRepository.findHaveOutputByBillId(bill.getId());
				if(billOutput == null) {
					response.setCode(2);
					return response;
				}
				outputs.put(billOutput.getOutputMaterialId(), 
						billOutput.getMount() + outputs.getOrDefault(billOutput.getOutputMaterialId(), 0));

				List<BillUse> billUses = this.billUseRepository.findHaveUseByBillId(bill.getId());
				if(billUses == null) {
					response.setCode(2);
					return response;
				}
				for(BillUse billUse: billUses) {
					uses.put(billUse.getUseMaterialId(), 
						billUse.getMount() + outputs.getOrDefault(billUse.getUseMaterialId(), 0));
				}
			}
			if(outputs.isEmpty() && uses.isEmpty()) {
				continue;
			}
			SingleStationData singleStationData = new SingleStationData();
			singleStationData.setId(station.getId());
			singleStationData.setName(station.getName());
			singleStationData.setStatus(station.getStatus());
			for (Integer key : outputs.keySet()) {
				 SingleStationMaterial singleStationMaterial = new SingleStationMaterial();
				 singleStationMaterial.setId(key);
				 singleStationMaterial.setMount(outputs.get(key));
				 singleStationMaterial.setName(this.materialService.findNameByMaterialId(key));
				 singleStationData.getOut().add(singleStationMaterial);
			}
			for (Integer key : uses.keySet()) {
				 SingleStationMaterial singleStationMaterial = new SingleStationMaterial();
				 singleStationMaterial.setId(key);
				 singleStationMaterial.setMount(outputs.get(key));
				 singleStationMaterial.setName(this.materialService.findNameByMaterialId(key));
				 singleStationData.getUse().add(singleStationMaterial);
			}
			response.getData().add(singleStationData);
		}*/
		
		response.setCode(1);
		return response;
	}

	public MaterialCurrentResponse getMaterialCurrent(Integer mid, String sname, Integer page, Integer size) {
		MaterialCurrentResponse response = new MaterialCurrentResponse();
		String midString = "";
		if(mid != null && mid != 0) {
			midString = String.valueOf(mid);
		}
		Sort sort = Sort.by(Direction.DESC, "id");
		Page<Station> stations = this.stationRepository.findStationByStatus(1, PageRequest.of(page, size, sort));
		for(Station station : stations) {
			HashMap<Integer, Integer> using = new HashMap<Integer, Integer>();
			List<Bill> bills = this.billReposiroty.findBillsByStationIdAndUsingMaterial(station.getId());
			if(bills == null) {
				response.setCode(2);
				return response;
			}
			for (Bill bill : bills) {
				if(bill.getUseBrief().contains(midString)){
					// Get mount Of this material
					List<BillUse> uses = this.billUseRepository.findByBillId(bill.getId());
					if(uses == null) {
						response.setCode(2);
						return response;
					}
					for (BillUse billUse : uses) {
						if(String.valueOf(billUse.getUseMaterialId()).contains(midString)) {
							if(billUse.getHaveUsed() == 0) {
								using.put(billUse.getUseMaterialId(), 
										billUse.getMount() + using.getOrDefault(billUse.getUseMaterialId(), 0));
							}else {
								using.put(billUse.getUseMaterialId(), 
									using.getOrDefault(billUse.getUseMaterialId() - billUse.getMount(), 0));
							}
						}
					}
				}
			}
			if(!using.isEmpty()) {
				SingleStationCurrentData singleStationData = new SingleStationCurrentData();
				singleStationData.setId(station.getId());
				singleStationData.setName(station.getName());
				singleStationData.setStatus(station.getStatus());
			    Calendar calendar=Calendar.getInstance();
			    calendar.add(Calendar.DATE, -7);
				int all = this.billReposiroty.findMountByTimeAndStatusAndStationId(calendar.getTime(), 
						0, station.getId());
				int other = 0;
				for(int i=4; i <= 6; i++) {
					other += this.billReposiroty.findMountByTimeAndStatusAndStationId(calendar.getTime(), 
							i, station.getId());
				}
				if(all == 0) {
					singleStationData.setProcess(100);
				}else {
					singleStationData.setProcess(100 * other / all);
				}
				for(Integer key: using.keySet()) {
					if(using.get(key) == 0) {
						continue;
					}
					SingleMaterialCurrentUse singleMaterialCurrentUse = new SingleMaterialCurrentUse();
					singleMaterialCurrentUse.setId(key);
					singleMaterialCurrentUse.setName(this.materialService.findNameByMaterialId(key));
					singleMaterialCurrentUse.setMount(using.get(key));
					singleStationData.getUses().add(singleMaterialCurrentUse);
				}
				response.getData().add(singleStationData);
			}
		}
		response.setCode(1);
		response.setAllLength(this.stationRepository.findMountByStatus(1));
		return response;
	}
	
	
}
