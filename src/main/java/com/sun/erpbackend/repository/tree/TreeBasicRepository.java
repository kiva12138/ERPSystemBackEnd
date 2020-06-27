package com.sun.erpbackend.repository.tree;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.tree.TreeBasic;

@Repository
public interface TreeBasicRepository extends JpaRepository<TreeBasic, Integer> {
	@Modifying
	@Query(nativeQuery=true, value="DELETE FROM treebasic " +
			"WHERE tid = ?1")
	void deleteTreeBasicByTid(@Param("tid")Integer tid);
	
	List<TreeBasic> findByTid(Integer tid);
}
