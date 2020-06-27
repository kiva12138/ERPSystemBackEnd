package com.sun.erpbackend.repository.tree;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.tree.Tree;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Integer>{
	@Query(nativeQuery=true,value="SELECT * FROM tree " +
			"WHERE if(?1!='',  name       like %?1%,  1=1) " +
			"and   if(?2!='',  targetname like %?2%,  1=1) " +
			"and   if(?3!='',  needbrief  like %?3%,  1=1) ")
	Page<Tree> findTree(@Param("name")String name, @Param("targetname")String targetname,
			@Param("needname")String needname,  Pageable pageable);
	
	@Query(nativeQuery=true,value="SELECT * FROM tree " +
			"WHERE targetmid = ?1")
	List<Tree> findTreeByTargetId(@Param("id")Integer targetid);
	
	@Query(nativeQuery=true,value="SELECT count(id) FROM tree " +
			"WHERE if(?1!='',  name       like %?1%,  1=1) " +
			"and   if(?2!='',  targetname like %?2%,  1=1) " +
			"and   if(?3!='',  needbrief  like %?3%,  1=1) ")
	int findTreeMount(@Param("name")String name, @Param("targetname")String targetname,
			@Param("needname")String needname);
	
	
}
