package com.sun.erpbackend.service.produce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.erpbackend.entity.bill.Bill;
import com.sun.erpbackend.entity.bill.BillOutput;
import com.sun.erpbackend.entity.bill.BillUse;
import com.sun.erpbackend.entity.bill.RefuseBill;
import com.sun.erpbackend.repository.bill.BillOutputRepository;
import com.sun.erpbackend.repository.bill.BillReposiroty;
import com.sun.erpbackend.repository.bill.BillUseRepository;
import com.sun.erpbackend.repository.bill.RefuseBillRepository;
import com.sun.erpbackend.repository.material.MaterialRepository;
import com.sun.erpbackend.repository.station.StationRepository;
import com.sun.erpbackend.response.bill.BillCurrentProduceResponse;
import com.sun.erpbackend.response.bill.SingleBill;
import com.sun.erpbackend.response.bill.SingleBillMaterial;
import com.sun.erpbackend.response.materialcurrent.MaterialCurrentProduceResponse;
import com.sun.erpbackend.response.materialcurrent.MaterialCurrentProduceResponseSingleMaterial;
import com.sun.erpbackend.response.produce.EstimateDataResponse;
import com.sun.erpbackend.response.produce.RefusedProduce;
import com.sun.erpbackend.response.produce.StoppedProduceResponse;
import com.sun.erpbackend.response.statisticbystation.StationProducingResponse;
import com.sun.erpbackend.response.statisticbystation.StationProducingResponseSingle;

@Service
@Secured("ROLE_ADMIN")
@Transactional
public class ProduceService {

	@Autowired
	MaterialRepository materialRepository;
	@Autowired
	BillReposiroty billReposiroty;
	@Autowired
	BillOutputRepository billOutputRepository;
	@Autowired
	StationRepository stationRepository;
	@Autowired
	RefuseBillRepository refuseBillRepository;
	@Autowired
	BillUseRepository billUseRepository;
	
	public MaterialCurrentProduceResponse getWaitingMaterialStatistics() {
		MaterialCurrentProduceResponse response = new MaterialCurrentProduceResponse();
		List<Bill> activeBills = this.billReposiroty.findBillsActive();
		List<Bill> waitingBills = this.billReposiroty.findBillsWaiting();
		if (activeBills == null || waitingBills == null) {
			response.setCode(2);
			return response;
		}
		int activeMount = 0;
		int waitingMount = 0;
		Map<Integer, SingleMaterialStatistic> mapMaterial = new HashMap<Integer, SingleMaterialStatistic>();
		for (Bill bill: activeBills) {
			BillOutput billOutput = this.billOutputRepository.findOutputByBillId(bill.getId());
			activeMount += billOutput.getMount();
		}
		for(Bill bill: waitingBills) {
			BillOutput billOutput = this.billOutputRepository.findByBillId(bill.getId());
			waitingMount += billOutput.getMount();
			if (mapMaterial.containsKey(billOutput.getOutputMaterialId())) {
				SingleMaterialStatistic singleMaterialStatistic = mapMaterial.get(billOutput.getOutputMaterialId());
				singleMaterialStatistic.setMount(singleMaterialStatistic.getMount() + billOutput.getMount());
				if (!singleMaterialStatistic.getBillIds().contains(bill.getId())) {
					singleMaterialStatistic.getBillIds().add(bill.getId());					
				}
				mapMaterial.remove(billOutput.getOutputMaterialId());
				mapMaterial.put(billOutput.getOutputMaterialId(), singleMaterialStatistic);
			} else {
				SingleMaterialStatistic singleMaterialStatistic = new SingleMaterialStatistic();
				singleMaterialStatistic.setMount(billOutput.getMount());
				singleMaterialStatistic.getBillIds().add(bill.getId());
				mapMaterial.put(billOutput.getOutputMaterialId(), singleMaterialStatistic);
			}
		}
		for(Integer key: mapMaterial.keySet()) {
			MaterialCurrentProduceResponseSingleMaterial singleMaterial = new MaterialCurrentProduceResponseSingleMaterial();
			singleMaterial.setId(key);
			singleMaterial.setMount(mapMaterial.get(key).getMount());
			singleMaterial.setName(this.materialRepository.findById(key).get().getName());
			for(Integer billId: mapMaterial.get(key).getBillIds()) {
				singleMaterial.getBillids().add(billId);
			}
			response.getWaitingMaterials().add(singleMaterial);
		}
		response.setActiveMount(activeMount);
		response.setWaitingMount(waitingMount);
		response.setCode(1);
		return response;
	}

	public BillCurrentProduceResponse getWaitingBillStatistics() {
		BillCurrentProduceResponse response = new BillCurrentProduceResponse();
		List<Bill> activeBills = this.billReposiroty.findBillsActive();
		List<Bill> waitingBills = this.billReposiroty.findBillsWaiting();
		if (activeBills == null || waitingBills == null) {
			response.setCode(2);
			return response;
		}
		response.setActiveMount(activeBills.size());
		response.setWaitingMount(waitingBills.size());
		response.setCode(1);
		for(Bill bill: waitingBills) {
			response.getBills().add(bill);
		}
		return response;
	}
	
	public MaterialCurrentProduceResponse getProducingMaterialStatistics() {
		MaterialCurrentProduceResponse response = new MaterialCurrentProduceResponse();
		List<Bill> activeBills = this.billReposiroty.findBillsActive();
		List<Bill> producingBills = this.billReposiroty.findBillsProducing();
		if (activeBills == null || producingBills == null) {
			response.setCode(2);
			return response;
		}
		int activeMount = 0;
		int waitingMount = 0;
		Map<Integer, SingleMaterialStatistic> mapMaterial = new HashMap<Integer, SingleMaterialStatistic>();
		for (Bill bill: activeBills) {
			BillOutput billOutput = this.billOutputRepository.findOutputByBillId(bill.getId());
			activeMount += billOutput.getMount();
		}
		for(Bill bill: producingBills) {
			BillOutput billOutput = this.billOutputRepository.findByBillId(bill.getId());
			waitingMount += billOutput.getMount();
			if (mapMaterial.containsKey(billOutput.getOutputMaterialId())) {
				SingleMaterialStatistic singleMaterialStatistic = mapMaterial.get(billOutput.getOutputMaterialId());
				singleMaterialStatistic.setMount(singleMaterialStatistic.getMount() + billOutput.getMount());
				if (!singleMaterialStatistic.getBillIds().contains(bill.getId())) {
					singleMaterialStatistic.getBillIds().add(bill.getId());					
				}
				mapMaterial.remove(billOutput.getOutputMaterialId());
				mapMaterial.put(billOutput.getOutputMaterialId(), singleMaterialStatistic);
			} else {
				SingleMaterialStatistic singleMaterialStatistic = new SingleMaterialStatistic();
				singleMaterialStatistic.setMount(billOutput.getMount());
				singleMaterialStatistic.getBillIds().add(bill.getId());
				mapMaterial.put(billOutput.getOutputMaterialId(), singleMaterialStatistic);
			}
		}
		for(Integer key: mapMaterial.keySet()) {
			MaterialCurrentProduceResponseSingleMaterial singleMaterial = new MaterialCurrentProduceResponseSingleMaterial();
			singleMaterial.setId(key);
			singleMaterial.setMount(mapMaterial.get(key).getMount());
			singleMaterial.setName(this.materialRepository.findById(key).get().getName());
			for(Integer billId: mapMaterial.get(key).getBillIds()) {
				singleMaterial.getBillids().add(billId);
			}
			response.getWaitingMaterials().add(singleMaterial);
		}
		response.setActiveMount(activeMount);
		response.setWaitingMount(waitingMount);
		response.setCode(1);
		return response;
	}
	
	public BillCurrentProduceResponse getProducingBillStatistics() {
		BillCurrentProduceResponse response = new BillCurrentProduceResponse();
		List<Bill> activeBills = this.billReposiroty.findBillsActive();
		List<Bill> waitingBills = this.billReposiroty.findBillsProducing();
		if (activeBills == null || waitingBills == null) {
			response.setCode(2);
			return response;
		}
		response.setActiveMount(activeBills.size());
		response.setWaitingMount(waitingBills.size());
		response.setCode(1);
		for(Bill bill: waitingBills) {
			response.getBills().add(bill);
		}
		return response;
	}

	public StationProducingResponse getProducingStationStatistics() {
		StationProducingResponse response = new StationProducingResponse();
		List<Bill> activeBills = this.billReposiroty.findBillsActive();
		List<Bill> producingBills = this.billReposiroty.findBillsProducing();
		if (activeBills == null || producingBills == null) {
			response.setCode(2);
			return response;
		}
		response.setActiveMount(activeBills.size());
		response.setProducingMount(producingBills.size());
		Map<Integer, SingleStationStatistic> mapStation = new HashMap<Integer, SingleStationStatistic>();
		for(Bill bill: producingBills) {
			if (mapStation.containsKey(bill.getStationId())) {
				SingleStationStatistic singleStationStatistic = mapStation.get(bill.getStationId());
				singleStationStatistic.getBillIds().add(bill.getId());
				mapStation.remove(bill.getStationId());
				mapStation.put(bill.getStationId(), singleStationStatistic);
			} else {
				SingleStationStatistic singleStationStatistic = new SingleStationStatistic();
				singleStationStatistic.getBillIds().add(bill.getId());
				mapStation.put(bill.getStationId(), singleStationStatistic);
			}
		}
		for(Integer key: mapStation.keySet()) {
			StationProducingResponseSingle singleStation = new StationProducingResponseSingle();
			singleStation.setId(key);
			singleStation.setStatus(this.stationRepository.findById(key).get().getStatus());
			singleStation.setName(this.stationRepository.findById(key).get().getName());
			for(Integer billId: mapStation.get(key).getBillIds()) {
				singleStation.getBillIds().add(billId);
			}
			response.getStations().add(singleStation);
		}
		response.setCode(1);
		return response;
	}

	public EstimateDataResponse getEstimateData() {
		EstimateDataResponse response = new EstimateDataResponse();
		List<Bill> billsSuccess = this.billReposiroty.findBillsSuccess();
		List<Bill> billsOver = this.billReposiroty.findBillsOver();
		List<Bill> billsProducing = this.billReposiroty.findBillsProducing();
		if (billsOver == null || billsOver == null || billsProducing == null) {
			response.setCode(2);
			return response;
		}
		if (billsSuccess.size() + billsOver.size() == 0) {
			response.setSuccessRate(0);
		}else {
			response.setSuccessRate((float)billsSuccess.size() / (float)(billsOver.size() + (float)billsSuccess.size()));
		}
		response.setCurrentBill(billsProducing.size());
		int mount = 0;
		for(Bill bill: billsProducing) {
			BillOutput billOutput  = this.billOutputRepository.findByBillId(bill.getId());
			mount += billOutput.getMount();
		}
		response.setCurrentMaterial(mount);
		response.setCode(1);
		return response;
	}

	public RefusedProduce findRefusedProduce(Integer refuseKind) {
		RefusedProduce response  = new RefusedProduce();
		int refusedCout = this.billReposiroty.findBillsRefusedCout();
		response.setRefusedMount(refusedCout);
		List<RefuseBill> refuseBills = this.refuseBillRepository.findByRefuseKind(refuseKind);
		if (refuseBills == null) {
			response.setCode(2);
			return response;
		}
		for(RefuseBill refuseBill : refuseBills) {
			Bill bill = this.billReposiroty.findById(refuseBill.getBillid()).get();
			if (bill == null) {
				response.setCode(2);
				return response;
			}
			SingleBill singleBill = new SingleBill();
			singleBill.setId(bill.getId());
			singleBill.setName(bill.getName());
			singleBill.setOutputKind(bill.getOutputKind());
			singleBill.setEstimateTime(bill.getEstimateTime());
			singleBill.setStatus(bill.getStatus());
			singleBill.setDescription(bill.getDescription());
			singleBill.setStation(bill.getStationId());
			singleBill.setRefuseKind(refuseBill.getRefusekind());
			singleBill.setRefuseReason(bill.getRefuseReason());

			BillOutput billOutput = this.billOutputRepository.findOutputByBillId(bill.getId());
			singleBill.setOutput(this.materialRepository.findById(billOutput.getOutputMaterialId()).orElse(null).getName());
			singleBill.setOutputMount(billOutput.getMount());
			
			List<BillUse> billUses = this.billUseRepository.findUseByBillId(bill.getId());
			for(BillUse billUse : billUses) {
				SingleBillMaterial singleBillMaterial = new SingleBillMaterial();
				singleBillMaterial.setId(billUse.getUseMaterialId());
				singleBillMaterial.setName(billUse.getMount().toString()+"*"+
						this.materialRepository.findById(billUse.getUseMaterialId()).orElse(null).getName());
				singleBill.getMaterials().add(singleBillMaterial);
			}
			List<BillUse> billHaveUses = this.billUseRepository.findHaveUseByBillId(bill.getId());
			for(BillUse billUse : billHaveUses) {
				SingleBillMaterial singleBillMaterial = new SingleBillMaterial();
				singleBillMaterial.setId(billUse.getUseMaterialId());
				singleBillMaterial.setName(billUse.getMount().toString()+"*"+
						this.materialRepository.findById(billUse.getUseMaterialId()).orElse(null).getName());
				singleBill.getHaveused().add(singleBillMaterial);
			}
			BillOutput billHaveOutput = this.billOutputRepository.findHaveOutputByBillId(bill.getId());
			if(billHaveOutput == null) {
				singleBill.setHaveoutputMount(0);
			}else {
				singleBill.setHaveoutputMount(billHaveOutput.getMount());
			}
			response.getBills().add(singleBill);
		}
		response.setCode(1);
		return response;
	}

	
	public StoppedProduceResponse findStoppedProduce() {
		StoppedProduceResponse response = new StoppedProduceResponse();
		List<Bill> stoppedBills = this.billReposiroty.findBillsStopping();
		if (stoppedBills == null ) {
			response.setCode(2);
			return response;
		}
		response.setStoppedMount(stoppedBills.size());
		for(Bill bill : stoppedBills) {
			if (bill == null) {
				response.setCode(2);
				return response;
			}
			SingleBill singleBill = new SingleBill();
			singleBill.setId(bill.getId());
			singleBill.setName(bill.getName());
			singleBill.setOutputKind(bill.getOutputKind());
			singleBill.setEstimateTime(bill.getEstimateTime());
			singleBill.setStatus(bill.getStatus());
			singleBill.setDescription(bill.getDescription());
			singleBill.setStation(bill.getStationId());

			BillOutput billOutput = this.billOutputRepository.findOutputByBillId(bill.getId());
			singleBill.setOutput(this.materialRepository.findById(billOutput.getOutputMaterialId()).orElse(null).getName());
			singleBill.setOutputMount(billOutput.getMount());
			
			List<BillUse> billUses = this.billUseRepository.findUseByBillId(bill.getId());
			for(BillUse billUse : billUses) {
				SingleBillMaterial singleBillMaterial = new SingleBillMaterial();
				singleBillMaterial.setId(billUse.getUseMaterialId());
				singleBillMaterial.setName(billUse.getMount().toString()+"*"+
						this.materialRepository.findById(billUse.getUseMaterialId()).orElse(null).getName());
				singleBill.getMaterials().add(singleBillMaterial);
			}
			List<BillUse> billHaveUses = this.billUseRepository.findHaveUseByBillId(bill.getId());
			for(BillUse billUse : billHaveUses) {
				SingleBillMaterial singleBillMaterial = new SingleBillMaterial();
				singleBillMaterial.setId(billUse.getUseMaterialId());
				singleBillMaterial.setName(billUse.getMount().toString()+"*"+
						this.materialRepository.findById(billUse.getUseMaterialId()).orElse(null).getName());
				singleBill.getHaveused().add(singleBillMaterial);
			}
			BillOutput billHaveOutput = this.billOutputRepository.findHaveOutputByBillId(bill.getId());
			if(billHaveOutput == null) {
				singleBill.setHaveoutputMount(0);
			}else {
				singleBill.setHaveoutputMount(billHaveOutput.getMount());
			}
			response.getBills().add(singleBill);
		}
		response.setCode(1);
		return response;
	}
	
}
