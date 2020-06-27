package com.sun.erpbackend.repository.tree;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.tree.TreeTimes;

@Repository
public interface TreeTimesRepository extends JpaRepository<TreeTimes, Integer> {
	
	@Query(nativeQuery=true,value="SELECT * FROM treetimes WHERE tid = ?1")
	TreeTimes findTreeByTid(@Param("id")Integer tid);
}
