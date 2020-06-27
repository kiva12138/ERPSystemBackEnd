package com.sun.erpbackend.repository.material;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.material.MaterialProduce;

/**
 * The Repository Of Material Producing Records
 * @author 夜流歌
 *
 */
@Repository
public interface MaterialProduceRepository extends JpaRepository<MaterialProduce, Integer>{
	
	@Modifying
	@Query(nativeQuery=true, value="SELECT * FROM materialproduce " +
			"WHERE  time BETWEEN ?1 AND ?2")
	List<MaterialProduce> findProduceDefault(@Param("start")Date start, @Param("end")Date end);
	
	@Query(nativeQuery=true, value="SELECT * FROM materialproduce " +
			"WHERE mid=?1 AND time > ?2")
	List<MaterialProduce> findProduceByMidAndTime(@Param("mid")Integer mid, @Param("start")Date start, Pageable pageable);

	@Query(nativeQuery=true, value="SELECT count(id) FROM materialproduce " +
			"WHERE mid=?1 AND time > ?2 ")
	int findMountProduceByMidAndTime(@Param("mid")Integer mid, @Param("start")Date start);
	
	@Modifying
	@Query(nativeQuery=true, value="SELECT * FROM materialproduce " +
			"WHERE stationid=?1 AND time > ?2")
	List<MaterialProduce> findProduceByStationAndTime(@Param("stationid")Integer stationid, @Param("start")Date start);
	
	@Modifying
	@Query(nativeQuery=true, value="DELETE * FROM materialproduce " +
			"WHERE time < ?1")
	int deleteProduceByTime(@Param("end")Date end);
	
	@Query(nativeQuery=true, value="SELECT * FROM materialproduce " +
			"WHERE if(?1!=0,mid=?1,1=1) " +
			"AND if(?2!=0,stationid=?2,1=1) " +
			"AND if(?3!=0,way=?3,1=1) " +
			"AND time > ?4 AND time < ?5")
	List<MaterialProduce> findProduceBySearch(@Param("id")Integer id, @Param("stationid")Integer stationid,
			@Param("kind")Integer kind, @Param("start")Date startDate, @Param("end")Date endDate, Pageable pageable);
	
	@Query(nativeQuery=true, value="SELECT count(id) FROM materialproduce " +
			"WHERE if(?1!=0,mid=?1,1=1) " +
			"AND if(?2!=0,stationid=?2,1=1) " +
			"AND if(?3!=0,way=?3,1=1) " +
			"AND time > ?4 AND time < ?5")
	int findProduceMountBySearch(@Param("id")Integer id, @Param("stationid")Integer stationid,
			@Param("kind")Integer kind, @Param("start")Date startDate, @Param("end")Date endDate);

}
