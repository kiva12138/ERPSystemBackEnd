package com.sun.erpbackend.service.Tree;

import java.util.ArrayList;
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
import com.sun.erpbackend.entity.tree.Tree;
import com.sun.erpbackend.entity.tree.TreeBasic;
import com.sun.erpbackend.entity.tree.TreeTimes;
import com.sun.erpbackend.repository.material.MaterialRepository;
import com.sun.erpbackend.repository.tree.TreeBasicRepository;
import com.sun.erpbackend.repository.tree.TreeRepository;
import com.sun.erpbackend.repository.tree.TreeTimesRepository;
import com.sun.erpbackend.response.tree.SingleTree;
import com.sun.erpbackend.response.tree.SingleTreeNeed;
import com.sun.erpbackend.response.tree.SingleTreeWithMount;
import com.sun.erpbackend.response.tree.TreeSearchResponse;
import com.sun.erpbackend.response.tree.TreeSearchResponseWithMount;

@Service
@Secured("ROLE_ADMIN")
@Transactional
public class TreeService {
	@Autowired
	TreeRepository treeRepository;
	@Autowired
	TreeBasicRepository treeBasicRepository;
	@Autowired
	MaterialRepository materialRepository;
	@Autowired
	TreeTimesRepository treeTimesRepository;
	
	/**
	 * 
	 * @param name
	 * @param needid
	 * @param description
	 * @param needs
	 * @return 1-Correct 2-mid not exist 3-Internal Error
	 */
	public int addTree(String name, Integer needid, String description, String[] needs) {
		if(!this.materialRepository.existsById(Integer.valueOf(needid))) {
			return 2;
		}
		for(String s : needs) {
			String[] tempList = s.split("\\*");
			if(!this.materialRepository.existsById(Integer.valueOf(tempList[0]))) {
				return 2;
			}
		}
		Tree tree = new Tree();
		tree.setName(name);
		tree.setDescription(description);
		tree.setOpmount(1);
		tree.setStatus(1);
		tree.setTargetmid(needid);
		tree.setTargetname(this.materialRepository.findById(needid).get().getName());
		StringBuilder builder = new StringBuilder();
		for(String s : needs) {
			String[] tempList = s.split("\\*");
			builder.append(this.materialRepository.findById(Integer.valueOf(tempList[0])).get().getName());
		}
		tree.setNeedbrief(builder.toString());
		Tree saveResulTree = this.treeRepository.saveAndFlush(tree);
		if (saveResulTree == null) {
			return 3;
		}
		List<TreeBasic> treeBasics = new ArrayList<TreeBasic>();
		for(String s : needs) {
			String[] tempList = s.split("\\*");
			TreeBasic tempBasic = new TreeBasic();
			tempBasic.setMid(Integer.valueOf(tempList[0]));
			tempBasic.setMname(this.materialRepository.findById(Integer.valueOf(tempList[0])).get().getName());
			if (tempList.length == 1) {
				tempBasic.setMount(1);
			}else {
				tempBasic.setMount(Integer.valueOf(tempList[1]));
			}
			tempBasic.setTid(saveResulTree.getId());
			treeBasics.add(tempBasic);
		}
		this.treeBasicRepository.saveAll(treeBasics);
		return 1;
	}

	/**
	 * 
	 * @param id
	 * @return 1-Correct
	 */
	public int deleteTree(Integer id) {
		this.treeRepository.deleteById(id);
		this.treeBasicRepository.deleteTreeBasicByTid(id);
		return 1;
	}
	
	/**
	 * 
	 * @param name
	 * @param needid
	 * @param description
	 * @param needs
	 * @return 1-Correct 2-mid not exist 3-Internal Error
	 */
	public int modifyTree(Integer id, String name, Integer needid, Integer status, String description, String[] needs) {
		if(!this.materialRepository.existsById(Integer.valueOf(needid))) {
			return 2;
		}
		for(String s : needs) {
			String[] tempList = s.split("\\*");
			if(!this.materialRepository.existsById(Integer.valueOf(tempList[0]))) {
				return 2;
			}
		}
		Tree tree = this.treeRepository.findById(id).get();
		tree.setName(name);
		tree.setDescription(description);
		tree.setOpmount(1);
		tree.setStatus(status);
		tree.setTargetmid(needid);
		tree.setTargetname(this.materialRepository.findById(needid).get().getName());
		StringBuilder builder = new StringBuilder();
		for(String s : needs) {
			String[] tempList = s.split("\\*");
			builder.append(this.materialRepository.findById(Integer.valueOf(tempList[0])).get().getName());
		}
		tree.setNeedbrief(builder.toString());
		Tree saveResulTree = this.treeRepository.saveAndFlush(tree);
		if (saveResulTree == null) {
			return 3;
		}
		this.treeBasicRepository.deleteTreeBasicByTid(id);
		List<TreeBasic> treeBasics = new ArrayList<TreeBasic>();
		for(String s : needs) {
			String[] tempList = s.split("\\*");
			TreeBasic tempBasic = new TreeBasic();
			tempBasic.setMid(Integer.valueOf(tempList[0]));
			tempBasic.setMname(this.materialRepository.findById(Integer.valueOf(tempList[0])).get().getName());
			if (tempList.length == 1) {
				tempBasic.setMount(1);
			}else {
				tempBasic.setMount(Integer.valueOf(tempList[1]));
			}
			tempBasic.setTid(saveResulTree.getId());
			treeBasics.add(tempBasic);
		}
		this.treeBasicRepository.saveAll(treeBasics);
		return 1;
	}


	/**
	 * 
	 * @param name
	 * @param targetName
	 * @param needName
	 * @param page
	 * @return 1-Correct 2-Internal Error
	 */
	public TreeSearchResponse findTreeNormal(String name, String targetName, String needName, Integer page) {
		TreeSearchResponse response = new TreeSearchResponse();
		Sort sort = Sort.by(Direction.DESC, "id");
		Page<Tree> resultTrees = this.treeRepository.findTree(name, targetName, needName, PageRequest.of(page, ERPStaticData.treePagination, sort));
		if (resultTrees == null) {
			response.setCode(2);
			return response;
		}
		response.setCode(1);
		response.setAllLength(this.treeRepository.findTreeMount(name, targetName, needName));
		for (Tree tree: resultTrees) {
			SingleTree singleTree = new SingleTree();
			singleTree.setDescription(tree.getDescription());
			singleTree.setId(tree.getId());
			singleTree.setName(tree.getName());
			singleTree.setOpname(tree.getTargetname());
			singleTree.setStatus(tree.getStatus());
			List<TreeBasic> treeBasics = this.treeBasicRepository.findByTid(tree.getId());
			for (TreeBasic singleNeed: treeBasics) {
				SingleTreeNeed singleTreeNeed = new SingleTreeNeed();
				singleTreeNeed.setId(singleNeed.getId());
				singleTreeNeed.setMount(singleNeed.getMount());
				singleTreeNeed.setName(singleNeed.getMname());
				singleTree.getNeed().add(singleTreeNeed);
			}
			response.getData().add(singleTree);
		}
		return response;
	}
	

	/**
	 * 
	 * @param targetmid
	 * @return 1-Correct 2-Internal Error
	 */
	public TreeSearchResponse findTreeByTargetMid(Integer targetmid) {
		TreeSearchResponse response = new TreeSearchResponse();
		List<Tree> resultTrees = this.treeRepository.findTreeByTargetId(targetmid);
		if (resultTrees == null) {
			response.setCode(2);
			return response;
		}
		response.setCode(1);
		response.setAllLength(resultTrees.size());
		for (Tree tree: resultTrees) {
			SingleTree singleTree = new SingleTree();
			singleTree.setDescription(tree.getDescription());
			singleTree.setId(tree.getId());
			singleTree.setName(tree.getName());
			singleTree.setOpname(tree.getTargetname());
			singleTree.setStatus(tree.getStatus());
			List<TreeBasic> treeBasics = this.treeBasicRepository.findByTid(tree.getId());
			for (TreeBasic singleNeed: treeBasics) {
				SingleTreeNeed singleTreeNeed = new SingleTreeNeed();
				singleTreeNeed.setId(singleNeed.getId());
				singleTreeNeed.setMount(singleNeed.getMount());
				singleTreeNeed.setName(singleNeed.getMname());
				singleTree.getNeed().add(singleTreeNeed);
			}
			response.getData().add(singleTree);
		}
		return response;
	}

	/**
	 * 
	 * @param tid
	 * @return 1-Success 2-Error 3-TreeNotExist
	 */
	public int useTree(Integer tid) {
		try {
			if (!this.treeRepository.existsById(tid)) {
				return 3;
			}
			TreeTimes treeTimes = this.treeTimesRepository.findTreeByTid(tid);
			if (treeTimes == null || treeTimes.getTid() == null || treeTimes.getTid() != tid) {
				treeTimes = new TreeTimes();
				treeTimes.setTimes(0);
				treeTimes.setTid(tid);
			}
			treeTimes.setTimes(treeTimes.getTimes() + 1);
			this.treeTimesRepository.saveAndFlush(treeTimes);
			return 1;
		} catch (Exception e) {
			return 2;
		}
	}
	
	/**
	 * 
	 * @param tid
	 * @return
	 */
	public int getUseTreeTimes(Integer tid) {
		try {
			TreeTimes treeTimes = this.treeTimesRepository.findTreeByTid(tid);
			if (treeTimes == null || treeTimes.getTid() == null || treeTimes.getTid() != tid) {
				return 0;
			}
			return treeTimes.getTimes();
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 
	 * @param name
	 * @param targetName
	 * @param needName
	 * @param page
	 * @return 1-Correct 2-Internal Error
	 */
	public TreeSearchResponseWithMount findTreeNormalWithMount(String name, String opname, String needname,
			Integer page) {
		TreeSearchResponseWithMount response = new TreeSearchResponseWithMount();
		Sort sort = Sort.by(Direction.DESC, "id");
		Page<Tree> resultTrees = this.treeRepository.findTree(name, opname, needname, PageRequest.of(page, ERPStaticData.treePagination, sort));
		if (resultTrees == null) {
			response.setCode(2);
			return response;
		}
		response.setCode(1);
		response.setAllLength(this.treeRepository.findTreeMount(name, opname, needname));
		for (Tree tree: resultTrees) {
			SingleTreeWithMount singleTree = new SingleTreeWithMount();
			singleTree.setDescription(tree.getDescription());
			singleTree.setId(tree.getId());
			singleTree.setName(tree.getName());
			singleTree.setOpname(tree.getTargetname());
			singleTree.setStatus(tree.getStatus());
			singleTree.setMount(this.getUseTreeTimes(tree.getId()));
			List<TreeBasic> treeBasics = this.treeBasicRepository.findByTid(tree.getId());
			for (TreeBasic singleNeed: treeBasics) {
				SingleTreeNeed singleTreeNeed = new SingleTreeNeed();
				singleTreeNeed.setId(singleNeed.getId());
				singleTreeNeed.setMount(singleNeed.getMount());
				singleTreeNeed.setName(singleNeed.getMname());
				singleTree.getNeed().add(singleTreeNeed);
			}
			response.getData().add(singleTree);
		}
		return response;
	}
	
}
