package com.sun.erpbackend.repository.material;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.material.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
	
	/**
	 * 多条件模糊查询 可能为空
	 * @param id
	 * @param name
	 * @param status
	 * @param kind
	 * @return
	 */
	@Query(nativeQuery = true,
		value="SELECT * FROM material " +
				"WHERE if(?1!=0,id=?1,1=1) "+
				"and if(?2!='',name like %?2%,1=1) " +
				"and if(?3!=0,status=?3,1=1) "+
				"and if(?4!=0,kind=?4,1=1)")
	Page<Material> findMaterialCategory(@Param("id")int id, @Param("name")String name,
			@Param("status")int status, @Param("kind")int kind, Pageable pageable);
	
	@Query(nativeQuery = true,
			value="SELECT * FROM material " +
					"WHERE if(?1!=0,id=?1,1=1) "+
					"and if(?2!='',name like %?2%,1=1) " +
					"and if(?3!=0,status=?3,1=1) "+
					"and kind != ?4")
	Page<Material> findMaterialCategoryExceptKind(@Param("id")int id, @Param("name")String name,
				@Param("status")int status, @Param("kind")int kind, Pageable pageable);
	
	@Query(nativeQuery = true,
			value="SELECT count(id) FROM material " +
					"WHERE if(?1!=0,id=?1,1=1) "+
					"and if(?2!='',name like %?2%,1=1) " +
					"and if(?3!=0,status=?3,1=1) "+
					"and kind != ?4")
	int findMaterialCategoryMountExceptKind(@Param("id")int id, @Param("name")String name,
				@Param("status")int status, @Param("kind")int kind);
	
	@Query(nativeQuery = true,
			value="SELECT count(id) FROM material " +
					"WHERE if(?1!=0,id=?1,1=1) "+
					"and if(?2!='',name like %?2%,1=1) " +
					"and if(?3!=0,status=?3,1=1) "+
					"and if(?4!=0,kind=?4,1=1)")
	int findMaterialCategoryCount(@Param("id")int id, @Param("name")String name,
			@Param("status")int status, @Param("kind")int kind);
	
	@Query(nativeQuery = true,value="SELECT id FROM material WHERE name like %?1%")
	List<Integer> findIdByNameLike(@Param("name")String name);
	
	@Modifying
	@Query(value="UPDATE Material material " +
			"SET material.warnThresHold=:warn, material.dangerThresHold=:danger " +
			"WHERE material.id=:id")
	int updateThreshold(@Param("id")Integer id, @Param("warn")Integer warn, @Param("danger")Integer danger);
	
	@Modifying
	@Query(value="UPDATE Material material " +
			"SET material.warnThresHold=:warn, material.dangerThresHold=:danger")
	int updateAllThreshold(@Param("warn")Integer warn, @Param("danger")Integer danger);
	
	@Query(nativeQuery=true,value="SELECT kind FROM material WHERE id=?1")
	int findMaterialKindById(@Param("id")Integer id);

	@Query(nativeQuery = true,
			value="SELECT count(id) FROM material " +
					"WHERE if(?1!=0,    id=?1,           1=1) "+
					"and   if(?2!='',   name like %?2%,  1=1) " +
					"and   status!=?3 "+
					"and   if(?4!=0,    kind=?4,         1=1)")
	int findMaterialCategoryMountExceptStatus(int id, String name, int status, int kind);
	
	@Query(nativeQuery = true,
			value="SELECT count(id) FROM material " +
					"WHERE if(?1!=0,    id=?1,           1=1) "+
					"and   if(?2!='',   name like %?2%,  1=1) " +
					"and   status!=?3 "+
					"and   kind != ?3")
	int findMaterialCategoryMountExceptStatusAndKind(int id, String name, int status, int kind);
	
}
