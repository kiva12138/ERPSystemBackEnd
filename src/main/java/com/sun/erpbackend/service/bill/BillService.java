package com.sun.erpbackend.service.bill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
import com.sun.erpbackend.entity.bill.Bill;
import com.sun.erpbackend.entity.bill.BillOutput;
import com.sun.erpbackend.entity.bill.BillUse;
import com.sun.erpbackend.entity.bill.RefuseBill;
import com.sun.erpbackend.entity.material.Material;
import com.sun.erpbackend.entity.tree.TreeBasic;
import com.sun.erpbackend.repository.bill.BillOutputRepository;
import com.sun.erpbackend.repository.bill.BillReposiroty;
import com.sun.erpbackend.repository.bill.BillUseRepository;
import com.sun.erpbackend.repository.bill.RefuseBillRepository;
import com.sun.erpbackend.repository.material.MaterialRepository;
import com.sun.erpbackend.repository.station.StationRepository;
import com.sun.erpbackend.repository.tree.TreeBasicRepository;
import com.sun.erpbackend.repository.tree.TreeRepository;
import com.sun.erpbackend.response.BillAllDataResponse;
import com.sun.erpbackend.response.BillOverallResponse;
import com.sun.erpbackend.response.bill.BillSearchResponse;
import com.sun.erpbackend.response.bill.SingleBill;
import com.sun.erpbackend.response.bill.SingleBillMaterial;
import com.sun.erpbackend.service.Tree.TreeService;
import com.sun.erpbackend.service.material.MaterialProduceService;
import com.sun.erpbackend.service.material.MaterialService;

@Service
@Secured("ROLE_ADMIN")
@Transactional
public class BillService {
	@Autowired
	BillReposiroty billReposiroty;
	@Autowired
	BillOutputRepository billOutputRepository;
	@Autowired
	BillUseRepository billUseRepository;
	@Autowired
	MaterialRepository materialRepository;
	@Autowired
	MaterialService materialService;
	@Autowired
	MaterialProduceService materialProduceService;
	@Autowired
	StationRepository stationRepository;
	@Autowired
	TreeRepository treeRepository;
	@Autowired
	TreeBasicRepository treeBasicRepository;
	@Autowired
	TreeService treeService;
	@Autowired
	RefuseBillRepository refuseBillRepository;
	
	/**
	 * Get The Overall Data Of All Bills
	 * @param time (in format yyyy-MM-dd HH:mm:ss)
	 * @return 1-Success 2-Error
	 */
	public BillOverallResponse findBillOverall(Integer time) {
		BillOverallResponse response = new BillOverallResponse();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -time);
		response.setAll(this.billReposiroty.findAllMountByTimeAndStatus(calendar.getTime(), 0));
		response.setFinished(this.billReposiroty.findAllMountByTimeAndStatus(calendar.getTime(), 7));
		response.setProducing(this.billReposiroty.findAllMountByTimeAndStatus(calendar.getTime(), 4));
		response.setStopping(this.billReposiroty.findAllMountByTimeAndStatus(calendar.getTime(), 6));
		response.setCode(1);
		return response;
	}
	
	/**
	 * Create New Bill
	 * @param name
	 * @param output
	 * @param outputMount
	 * @param materials
	 * @return 1-Success 2-Error 4-Material Not Exist
	 */
	public int createNewBill(String name, int output, int estimateTime, int outputMount, String[] materials, String description) {

		if(!this.materialRepository.existsById(Integer.valueOf(output))) {
			return 4;
		}
		for(String s : materials) {
			String[] tempList = s.split("\\*");
			if(!this.materialRepository.existsById(Integer.valueOf(tempList[0]))) {
				return 4;
			}
		}
		Bill bill = new Bill();
		bill.setName(name);
		bill.setDescription(description);
		bill.setOutputKind(this.materialRepository.findMaterialKindById(output));
		bill.setEstimateTime(estimateTime);
		bill.setStatus(1);
		bill.setCreatedTime(new Date());
		bill.setOutBrief(String.valueOf(output));
		StringBuilder builder = new StringBuilder();
		for(String s : materials) {
			builder.append(s.split("\\*")[0]);
		}
		bill.setUseBrief(builder.toString());
		Bill result = this.billReposiroty.saveAndFlush(bill);
		
		if(result.getId() == 0) {
			return 2;
		}
		BillOutput billOutput = new BillOutput();
		billOutput.setBillId(result.getId());
		billOutput.setHaveOutput(0);
		billOutput.setMount(outputMount);
		billOutput.setOutputMaterialId(output);
		this.billOutputRepository.saveAndFlush(billOutput);
		
		List<BillUse> billUses = new ArrayList<BillUse>();
		for(String s : materials) {
			String[] tempList = s.split("\\*");
			BillUse billUse = new BillUse();
			billUse.setBillId(result.getId());
			billUse.setHaveUsed(0);
			if(tempList.length == 1) {
				billUse.setUseMaterialId(Integer.valueOf(tempList[0]));
				billUse.setMount(1);
			}else {
				billUse.setUseMaterialId(Integer.valueOf(tempList[0]));
				billUse.setMount(Integer.valueOf(tempList[1]));
			}
			builder.append(tempList[0]);
			billUses.add(billUse);
		}
		billUseRepository.saveAll(billUses);
		return 1;
	}
	
	/**
	 * Create New Bill With Tree
	 * @param name
	 * @param output
	 * @param outputMount
	 * @param materials
	 * @return 1-Success 2-Error 4-Material Not Exist 5-TreeNotExist
	 */
	public int createNewBillWithTree(String name, int output, int estimateTime, int outputMount, Integer treeId, String description) {

		if(!this.materialRepository.existsById(Integer.valueOf(output))) {
			return 4;
		}
		if (!this.treeRepository.existsById(treeId)) {
			return 5;
		}
		if(this.treeRepository.findById(treeId).get().getTargetmid()!=output) {
			return 6;
		}
		List<TreeBasic> treeBasics = this.treeBasicRepository.findByTid(treeId);
		for(TreeBasic s : treeBasics) {
			if(!this.materialRepository.existsById(s.getMid())) {
				return 4;
			}
		}
		Bill bill = new Bill();
		bill.setName(name);
		bill.setDescription(description);
		bill.setOutputKind(this.materialRepository.findMaterialKindById(output));
		bill.setEstimateTime(estimateTime);
		bill.setStatus(1);
		bill.setCreatedTime(new Date());
		bill.setOutBrief(String.valueOf(output));
		StringBuilder builder = new StringBuilder();
		for(TreeBasic s : treeBasics) {
			builder.append(s.getMid());
		}
		bill.setUseBrief(builder.toString());
		Bill result = this.billReposiroty.saveAndFlush(bill);
		
		if(result.getId() == 0) {
			return 2;
		}
		BillOutput billOutput = new BillOutput();
		billOutput.setBillId(result.getId());
		billOutput.setHaveOutput(0);
		billOutput.setMount(outputMount);
		billOutput.setOutputMaterialId(output);
		this.billOutputRepository.saveAndFlush(billOutput);
		
		List<BillUse> billUses = new ArrayList<BillUse>();
		for(TreeBasic s : treeBasics) {
			BillUse billUse = new BillUse();
			billUse.setBillId(result.getId());
			billUse.setHaveUsed(0);
			billUse.setUseMaterialId(s.getMid());
			billUse.setMount(s.getMount() * outputMount);
			billUses.add(billUse);
		}
		billUseRepository.saveAll(billUses);
		this.treeService.useTree(treeId);
		return 1;
	}
	
	
	/**
	 * Edit Bill
	 * @param name
	 * @param output
	 * @param outputMount
	 * @param use
	 * @return 1-Success 2-Error
	 */
	public int editBill(Integer id, String name, int output,
			int estimateTime, int outputMount, List<String> use, String description) {
		Bill bill = this.billReposiroty.findById(id).orElse(null);
		if(bill == null) {
			return 2;
		}
		if(!this.materialRepository.existsById(Integer.valueOf(output))) {
			return 4;
		}
		StringBuilder builder = new StringBuilder();
		for(String s : use) {
			String[] tempList = s.split("\\*");
			if(!this.materialRepository.existsById(Integer.valueOf(tempList[0]))) {
				return 4;
			}
			builder.append(tempList[0]);
		}
		bill.setUseBrief(builder.toString());
		bill.setName(name);
		bill.setDescription(description);
		bill.setOutputKind(this.materialRepository.findMaterialKindById(output));
		bill.setEstimateTime(estimateTime);
		bill.setStatus(1);
		bill.setOutBrief(String.valueOf(output));
		Bill result = this.billReposiroty.saveAndFlush(bill);
		
		if(result.getId() == 0) {
			return 2;
		}
		BillOutput billOutput = billOutputRepository.findByBillId(result.getId());
		billOutput.setMount(outputMount);
		billOutput.setOutputMaterialId(output);
		this.billOutputRepository.saveAndFlush(billOutput);

		List<BillUse> billUses = new ArrayList<BillUse>();
		billUseRepository.deleteByBillId(result.getId());
		for(String s : use) {
			String[] tempList = s.split("\\*");
			BillUse billUse = new BillUse();
			billUse.setBillId(result.getId());
			billUse.setHaveUsed(0);
			if(tempList.length == 1) {
				billUse.setUseMaterialId(Integer.valueOf(tempList[0]));
				billUse.setMount(1);
			}else {
				billUse.setUseMaterialId(Integer.valueOf(tempList[0]));
				billUse.setMount(Integer.valueOf(tempList[1]));
			}
			billUses.add(billUse);
		}
		billUseRepository.saveAll(billUses);
		return 1;
	}
	
	
	/**
	 * Edit Bill
	 * @param name
	 * @param output
	 * @param outputMount
	 * @param use
	 * @return 1-Success 2-Error
	 */
	public int editBillWithTree(Integer id, String name, int output,
			int estimateTime, int outputMount, int treeId, String description) {
		Bill bill = this.billReposiroty.findById(id).orElse(null);
		if(bill == null) {
			return 2;
		}
		if(!this.materialRepository.existsById(Integer.valueOf(output))) {
			return 4;
		}
		if (!this.treeRepository.existsById(treeId)) {
			return 5;
		}
		if(this.treeRepository.findById(treeId).get().getTargetmid()!=output) {
			return 6;
		}
		List<TreeBasic> treeBasics = this.treeBasicRepository.findByTid(treeId);
		StringBuilder builder = new StringBuilder();
		for(TreeBasic s : treeBasics) {
			if(!this.materialRepository.existsById(s.getMid())) {
				return 4;
			}
			builder.append(s.getMid());
		}
		bill.setUseBrief(builder.toString());
		bill.setName(name);
		bill.setDescription(description);
		bill.setOutputKind(this.materialRepository.findMaterialKindById(output));
		bill.setEstimateTime(estimateTime);
		bill.setStatus(1);
		bill.setOutBrief(String.valueOf(output));
		Bill result = this.billReposiroty.saveAndFlush(bill);
		
		if(result.getId() == 0) {
			return 2;
		}
		BillOutput billOutput = billOutputRepository.findByBillId(result.getId());
		billOutput.setMount(outputMount);
		billOutput.setOutputMaterialId(output);
		this.billOutputRepository.saveAndFlush(billOutput);

		List<BillUse> billUses = new ArrayList<BillUse>();
		billUseRepository.deleteByBillId(result.getId());
		for(TreeBasic s : treeBasics) {
			BillUse billUse = new BillUse();
			billUse.setBillId(result.getId());
			billUse.setHaveUsed(0);
			billUse.setUseMaterialId(s.getMid());
			billUse.setMount(s.getMount() * outputMount);
			billUses.add(billUse);
		}
		billUseRepository.saveAll(billUses);
		this.treeService.useTree(treeId);
		return 1;
	}
	
	/**
	 * 
	 * @param billId
	 * @param stationId
	 * @return 1-Success 2-Internal ERror 3- billId Not Exists 4-StationId Not Exist 5-Station Not Producing
	 */
	public int distributeBill(Integer billId, Integer stationId) {
		if(!this.stationRepository.existsById(stationId)) {
			return 4;
		}
		if(this.stationRepository.findById(stationId).orElse(null).getStatus() != 1) {
			return 5;
		}
		
		if(this.billReposiroty.existsById(stationId)) {
			return 3;
		}
		Bill bill = this.billReposiroty.findById(billId).orElse(null);
		
		if(bill == null) {
			return 2;
		}
		bill.setStatus(2);
		bill.setStationId(stationId);
		this.billReposiroty.saveAndFlush(bill);
		return 1;
	}
	
	/**
	 * 
	 * @param billId
	 * @return
	 */
	public int deleteBill(Integer billId) {
		if(!this.billReposiroty.existsById(billId)) {
			return 3;
		}
		try {
			this.billReposiroty.deleteById(billId);
			this.billOutputRepository.deleteByBillId(billId);
			this.billUseRepository.deleteByBillId(billId);
			return 1;
		} catch (Exception e) {
			return 2;
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param kind
	 * @param output
	 * @param material
	 * @param stationId
	 * @return code 1-Success 2-Error
	 */
	public BillSearchResponse findBill(Integer id, String name, Integer kind, Integer output,
			Integer material, Integer stationId, Integer page) {
		Sort sort = Sort.by(Direction.DESC, "id");
		Page<Bill> bills = this.billReposiroty.findBill(id, name, kind, output.toString(), material.toString(),
				stationId, PageRequest.of(page, ERPStaticData.billPagination, sort));
		BillSearchResponse response = new BillSearchResponse();
		response.setAllLength(this.billReposiroty.findBillCount(id, name, kind, output.toString(), material.toString(), stationId));
		if(bills == null) {
			response.setCode(2);
			return response;
		}
		for(Bill bill : bills) {
			SingleBill singleBill = new SingleBill();
			singleBill.setId(bill.getId());
			singleBill.setName(bill.getName());
			singleBill.setOutputKind(bill.getOutputKind());
			singleBill.setEstimateTime(bill.getEstimateTime());
			singleBill.setStatus(bill.getStatus());
			singleBill.setDescription(bill.getDescription());
			
			RefuseBill refuseBill = this.refuseBillRepository.findByBillId(bill.getId());
			if (refuseBill != null) {
				singleBill.setRefuseKind(refuseBill.getRefusekind());
			}
			else {
				singleBill.setRefuseKind(0);
			}
			
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
			response.getData().add(singleBill);
		}
		response.setCode(1);
		return response;
	}

	/**
	 * 
	 * @return 1-T 2-F
	 */
	public BillAllDataResponse findAllData() {
		BillAllDataResponse response = new BillAllDataResponse();
		response.setCode(1); 
		Date date;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse("2000-10-10");
		} catch (ParseException e) {
			response.setCode(2);
			e.printStackTrace();
			return response;
		} 
		response.setAll(this.billReposiroty.findAllMountByTimeAndStatus(date, 0));
		response.setCreated(this.billReposiroty.findAllMountByTimeAndStatus(date, 1));
		response.setWaiting(this.billReposiroty.findAllMountByTimeAndStatus(date, 2));
		response.setRefused(this.billReposiroty.findAllMountByTimeAndStatus(date, 3));
		response.setProducing(this.billReposiroty.findAllMountByTimeAndStatus(date, 4));
		response.setOvertime(this.billReposiroty.findAllMountByTimeAndStatus(date, 5));
		response.setStopped(this.billReposiroty.findAllMountByTimeAndStatus(date, 6));
		response.setComplete(this.billReposiroty.findAllMountByTimeAndStatus(date, 7));
		response.setOver(this.billReposiroty.findAllMountByTimeAndStatus(date, 8));
		return response;
	}
	
	/**
	 * 
	 * @param id
	 * @param time
	 * @return
	 */
	public int reDistributeTime(Integer id, Integer time) {
		if(!this.billReposiroty.existsById(id)) {
			return 4;
		}
		Bill bill = this.billReposiroty.findById(id).orElse(null);
		if(bill == null) {
			return 2;
		}
		bill.setEstimateTime(time);
		this.billReposiroty.saveAndFlush(bill);
		
		return 1;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public int stopProduce(Integer id) {
		Bill bill = this.billReposiroty.findById(id).orElse(null);
		if(bill == null) {
			return 2;
		}
		bill.setStatus(6);
		this.billReposiroty.saveAndFlush(bill);
		
		return 1;
	}
	
	public int reDistributeAndStart(Integer id, String[] materials) {
		Bill bill = this.billReposiroty.findById(id).orElse(null);
		if(bill == null) {
			return 2;
		}else {
			
			if(this.stationRepository.findById(bill.getStationId()).orElse(null).getStatus() != 1) {
				return 6;
			}
			
			for(String s : materials) {
				String[] tempList = s.split("\\*");
				if(!this.materialRepository.existsById(Integer.valueOf(tempList[0]))) {
					return 4;
				}
				if(this.materialRepository.findById(Integer.valueOf(tempList[0])).orElse(null).getLeftMount()
						< Integer.valueOf(tempList[1])) {
					return 5;
				}
			}
			
			bill.setStatus(4);
			this.billReposiroty.saveAndFlush(bill);
			
			
			List<BillUse> billUsesOld = this.billUseRepository.findByBillId(id);
			billUsesOld.sort(new Comparator<BillUse>() {
				@Override
				public int compare(BillUse o1, BillUse o2) {
					return o1.getUseMaterialId() - o2.getUseMaterialId();
				}
			});

			
			if(billUsesOld.size() != 0) {
				int left = 0;
				int lastid = billUsesOld.get(0).getUseMaterialId();
				for(BillUse billUse : billUsesOld) {
					if(billUse.getUseMaterialId() != lastid) {
						Material material = this.materialRepository.findById(lastid).orElse(null);
						material.setLeftMount(material.getLeftMount() + left);
						material.setDistributedMount(material.getDistributedMount() - left);
						this.materialRepository.saveAndFlush(material);
						lastid = billUse.getUseMaterialId();
						left = 0;
						if(billUse.getHaveUsed() == 0) {
							left += billUse.getMount();
						}else {
							left -= billUse.getMount();
						}
					}else {
						if(billUse.getHaveUsed() == 0) {
							left += billUse.getMount();
						}else {
							left -= billUse.getMount();
						}
					}
				}
				Material material = this.materialRepository.findById(lastid).orElse(null);
				material.setLeftMount(material.getLeftMount() + left);
				material.setDistributedMount(material.getDistributedMount() - left);
				this.materialRepository.saveAndFlush(material);
				this.billUseRepository.deleteByBillId(id);
			}

			List<BillUse> billUses = new ArrayList<BillUse>();
			for(String s : materials) {
				String[] tempList = s.split("\\*");
				BillUse billUse = new BillUse();
				billUse.setBillId(id);
				billUse.setHaveUsed(0);
				if(tempList.length == 1) {
					billUse.setUseMaterialId(Integer.valueOf(tempList[0]));
					billUse.setMount(1);
				}else {
					billUse.setUseMaterialId(Integer.valueOf(tempList[0]));
					billUse.setMount(Integer.valueOf(tempList[1]));
				}
				billUses.add(billUse);
			}
			billUseRepository.saveAll(billUses);
			
			this.acceptBill(id);
		}
		return 1;
	}
	
	public int reStart(Integer id) {
		if(!this.billReposiroty.existsById(id)) {
			return 4;
		}
		Bill bill = this.billReposiroty.findById(id).orElse(null);
		if(bill == null) {
			return 2;
		}else {
			if(this.stationRepository.findById(bill.getStationId()).orElse(null).getStatus() != 1) {
				return 5;
			}
			bill.setStatus(4);
			this.billReposiroty.saveAndFlush(bill);
		}
		return 1;
	}

	public int recycleAndStop(Integer id) {
		Bill bill = this.billReposiroty.findById(id).orElse(null);
		if(bill == null) {
			return 2;
		}else {
			bill.setStatus(8);
			this.billReposiroty.saveAndFlush(bill);
			List<BillUse> billUses = this.billUseRepository.findByBillId(id);
			billUses.sort(new Comparator<BillUse>() {
				@Override
				public int compare(BillUse o1, BillUse o2) {
					return o1.getUseMaterialId() - o2.getUseMaterialId();
				}
			});
			if(billUses.size() == 0) {
				return 1;
			}
			int left = 0;
			int lastid = billUses.get(0).getUseMaterialId();
			for(BillUse billUse : billUses) {
				if(billUse.getUseMaterialId() != lastid) {
					Material material = this.materialRepository.findById(lastid).orElse(null);
					material.setLeftMount(material.getLeftMount() + left);
					material.setDistributedMount(material.getDistributedMount() - left);
					this.materialRepository.saveAndFlush(material);
					lastid = billUse.getUseMaterialId();
					left = 0;
					if(billUse.getHaveUsed() == 0) {
						left += billUse.getMount();
					}else {
						left -= billUse.getMount();
					}
				}else {
					if(billUse.getHaveUsed() == 0) {
						left += billUse.getMount();
					}else {
						left -= billUse.getMount();
					}
				}
			}

			Material material = this.materialRepository.findById(lastid).orElse(null);
			material.setLeftMount(material.getLeftMount() + left);
			material.setDistributedMount(material.getDistributedMount() - left);
			this.materialRepository.saveAndFlush(material);
			
		} 
		return 1;
	}

	public int acceptBill(Integer billId) {
		if(!this.billReposiroty.existsById(billId)) {
			return 3;
		}
		Bill bill = this.billReposiroty.findById(billId).orElse(null);
		if(bill == null) {
			return 2;
		}else {
			
			if(!this.stationRepository.existsById(bill.getStationId())) {
				return 4;
			}
			if(this.stationRepository.findById(bill.getStationId()).orElse(null).getStatus() != 1) {
				return 5;
			}
			
			List<BillUse> billUses = this.billUseRepository.findByBillId(billId);
			// Check the mount of the materials
			for(BillUse billUse: billUses) {
				Material material = this.materialRepository.findById(billUse.getUseMaterialId()).orElse(null);
				if(material.getLeftMount() < billUse.getMount()) {
					return 6;
				}
				this.materialProduceService.addNewMaterialProduce(material.getId(), billUse.getMount(), -1, bill.getStationId());
			}
			
			bill.setStatus(4);
			bill.setDistributeTime(new Date());
			this.billReposiroty.saveAndFlush(bill);
			
		}
		return 1;
	}

	public int refuseBill(Integer billId, String reason, Integer kind) {
		if(!this.billReposiroty.existsById(billId)) {
			return 3;
		}
		Bill bill = this.billReposiroty.findById(billId).orElse(null);
		if(bill == null) {
			return 2;
		}else {
			bill.setStatus(3);
			bill.setRefuseReason(reason);
			this.billReposiroty.saveAndFlush(bill);
			this.refuseBillRepository.deleteByBillId(bill.getId());
			RefuseBill refuseBill = new RefuseBill();
			refuseBill.setBillid(bill.getId());
			refuseBill.setRefusekind(kind);
			this.refuseBillRepository.saveAndFlush(refuseBill);
		}
		return 1;
	}

	public int completeBill(Integer billId) {
		if(!this.billReposiroty.existsById(billId)) {
			return 3;
		}
		Bill bill = this.billReposiroty.findById(billId).orElse(null);
		if(bill == null) {
			return 2;
		}else {
			bill.setStatus(7);
			bill.setCompleteTime(new Date());
			this.billReposiroty.saveAndFlush(bill);
			
			BillOutput billOutput = this.billOutputRepository.findOutputByBillId(billId);
			BillOutput billHaveOutput = this.billOutputRepository.findHaveOutputByBillId(billId);
			if(billHaveOutput != null && billHaveOutput.getId() != 0) {
				this.materialProduceService.addNewMaterialProduce(billOutput.getOutputMaterialId(),
						billOutput.getMount() - billHaveOutput.getMount(), 1, bill.getStationId());
			} else {
				this.materialProduceService.addNewMaterialProduce(billOutput.getOutputMaterialId(),
						billOutput.getMount(), 1, bill.getStationId());
			}
			
			List<BillUse> billUses = this.billUseRepository.findByBillId(billId);
			billUses.sort(new Comparator<BillUse>() {
				@Override
				public int compare(BillUse o1, BillUse o2) {
					return o1.getUseMaterialId() - o2.getUseMaterialId();
				}
			});
			if(billUses.size() == 0) {
				return 1;
			}
			int left = 0;
			int lastid = billUses.get(0).getUseMaterialId();
			for(BillUse billUse : billUses) {
				if(billUse.getUseMaterialId() != lastid) {
					Material material = this.materialRepository.findById(lastid).orElse(null);
					material.setAllMount(material.getAllMount() - left);
					material.setDistributedMount(material.getDistributedMount() - left);
					this.materialRepository.saveAndFlush(material);
					lastid = billUse.getUseMaterialId();
					left = 0;
					if(billUse.getHaveUsed() == 0) {
						left += billUse.getMount();
					}else {
						left -= billUse.getMount();
					}
				}else {
					if(billUse.getHaveUsed() == 0) {
						left += billUse.getMount();
					}else {
						left -= billUse.getMount();
					}
				}
			}

			Material material = this.materialRepository.findById(lastid).orElse(null);
			material.setAllMount(material.getAllMount() - left);
			material.setDistributedMount(material.getDistributedMount() - left);
		}
		return 1;
	}

	public int stopByStatione(Integer id, String reason, String[] haveUsed, int haveOutput) {

		Bill bill = this.billReposiroty.findById(id).orElse(null);
		if(bill == null) {
			return 2;
		}
		for(String s : haveUsed) {
			String[] tempList = s.split("\\*");
			if(!this.materialRepository.existsById(Integer.valueOf(tempList[0]))) {
				return 4;
			}
		} 
		bill.setStatus(6);
		bill.setStoppedReson(reason);
		bill.setStoppedTime(new Date());
		this.billReposiroty.saveAndFlush(bill);

		BillOutput outputOld = this.billOutputRepository.findHaveOutputByBillId(id);
		this.billOutputRepository.deleteByBillIdHave(id);
		BillOutput output = this.billOutputRepository.findOutputByBillId(id);
		BillOutput billOutput = new BillOutput();
		billOutput.setBillId(id);
		billOutput.setHaveOutput(1);
		if(outputOld != null && outputOld.getId() != 0) {
			billOutput.setMount(haveOutput + outputOld.getMount());
		}else {
			billOutput.setMount(haveOutput);
		}
		billOutput.setOutputMaterialId(output.getOutputMaterialId());
		this.billOutputRepository.saveAndFlush(billOutput);
		this.materialProduceService.addNewMaterialProduce(billOutput.getOutputMaterialId(),
				haveOutput, 1, bill.getStationId());

		

		List<BillUse> billUses = new ArrayList<BillUse>();
		for(String s : haveUsed) {
			String[] tempList = s.split("\\*");
			BillUse billUse = new BillUse();
			billUse.setBillId(id);
			billUse.setHaveUsed(1);
			if(tempList.length == 1) {
				billUse.setUseMaterialId(Integer.valueOf(tempList[0].trim()));
				billUse.setMount(1);
			}else {
				billUse.setUseMaterialId(Integer.valueOf(tempList[0].trim()));
				billUse.setMount(Integer.valueOf(tempList[1]));
			}
			Material material = this.materialRepository.findById(billUse.getUseMaterialId()).orElse(null);
			material.setDistributedMount(material.getDistributedMount() - billUse.getMount());
			material.setAllMount(material.getAllMount() - billUse.getMount());
			this.materialRepository.saveAndFlush(material);
			
			BillUse useOld = this.billUseRepository.findHaveUseByBillIdAndMid(id, billUse.getUseMaterialId());
			if(useOld !=null && useOld.getId() !=null) {
				billUse.setMount(billUse.getMount() + useOld.getMount());
			}
			billUses.add(billUse);
		}
		this.billUseRepository.deleteByBillIdHave(id);
		this.billUseRepository.saveAll(billUses);
		
		return 1;
	}

  public BillSearchResponse findBillWithStatus(Integer id, String name, Integer kind, Integer status, Integer output,
		Integer material, Integer stationId, Integer page) {
	  	Sort sort = Sort.by(Direction.DESC, "id");
		Page<Bill> bills = this.billReposiroty.findBillWithStatus(id, name, kind, output.toString(), material.toString(),
				stationId, status, PageRequest.of(page, ERPStaticData.billPagination, sort));
		BillSearchResponse response = new BillSearchResponse();
		response.setAllLength(this.billReposiroty.findBillCountWithStatus(id, name, kind, 
				output.toString(), material.toString(), stationId, status));
		if(bills == null) {
			response.setCode(2);
			return response;
		}
		for(Bill bill : bills) {
			SingleBill singleBill = new SingleBill();
			singleBill.setId(bill.getId());
			singleBill.setName(bill.getName());
			singleBill.setOutputKind(bill.getOutputKind());
			singleBill.setEstimateTime(bill.getEstimateTime());
			singleBill.setStatus(bill.getStatus());
			singleBill.setRefuseReason(bill.getRefuseReason());
			singleBill.setStation(bill.getStationId());
			singleBill.setDescription(bill.getDescription());
			singleBill.setAcceptedTime(bill.getDistributeTime());
			singleBill.setStoppedReason(bill.getStoppedReson());
			singleBill.setStoppedTime(bill.getStoppedTime());
			singleBill.setCompleteTime(bill.getCompleteTime());
			
			RefuseBill refuseBill = this.refuseBillRepository.findByBillId(bill.getId());
			if (refuseBill != null) {
				singleBill.setRefuseKind(refuseBill.getRefusekind());
			}
			else {
				singleBill.setRefuseKind(0);
			}
			
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
			response.getData().add(singleBill);
		}
		response.setCode(1);
		return response;
	}
	
  public BillSearchResponse findStationBillProducing(Integer id, String name, Integer kind, Integer status, Integer output,
		Integer material, Integer stationId, Integer page) {
	  	Sort sort = Sort.by(Direction.DESC, "id");
		Page<Bill> bills = this.billReposiroty.findStationBillProducing(id, name, kind, output.toString(), material.toString(),
				stationId, status, PageRequest.of(page, ERPStaticData.billPagination, sort));
		BillSearchResponse response = new BillSearchResponse();
		response.setAllLength(this.billReposiroty.findStationBillProducingCount(id, name, kind, 
				output.toString(), material.toString(), stationId, status));
		if(bills == null) {
			response.setCode(2);
			return response;
		}
		for(Bill bill : bills) {
			SingleBill singleBill = new SingleBill();
			singleBill.setId(bill.getId());
			singleBill.setName(bill.getName());
			singleBill.setOutputKind(bill.getOutputKind());
			singleBill.setEstimateTime(bill.getEstimateTime());
			singleBill.setStatus(bill.getStatus());
			singleBill.setRefuseReason(bill.getRefuseReason());
			singleBill.setStation(bill.getStationId());
			singleBill.setDescription(bill.getDescription());
			singleBill.setAcceptedTime(bill.getDistributeTime());
			singleBill.setStoppedReason(bill.getStoppedReson());
			singleBill.setStoppedTime(bill.getStoppedTime());
			singleBill.setCompleteTime(bill.getCompleteTime());
			
			RefuseBill refuseBill = this.refuseBillRepository.findByBillId(bill.getId());
			if (refuseBill != null) {
				singleBill.setRefuseKind(refuseBill.getRefusekind());
			}
			else {
				singleBill.setRefuseKind(0);
			}
			
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
			response.getData().add(singleBill);
		}
		response.setCode(1);
		return response;
	}
	
}
