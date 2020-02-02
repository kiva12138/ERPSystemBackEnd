package com.sun.erpbackend.repository.station;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.station.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer>{
	
	@Query(nativeQuery=true, value="SELECT count(id) FROM station "+
			"WHERE if(?1!=0,status=?1,1=1)")
	int findMountByStatus(@Param("status")Integer status);

	@Query(nativeQuery=true,value="SELECT * FROM station " +
			"WHERE if(?1!=0,  id=?1,              1=1) " +
			"and   if(?2!='', name like %?2%,     1=1) " +
			"and   if(?3!=0,  status=?3,          1=1) ")
	Page<Station> findStation(@Param("id")Integer id, @Param("name")String name, 
			@Param("status")Integer status, Pageable pageable);
	
	@Query(nativeQuery=true,value="SELECT * FROM station WHERE status=?1")
	Page<Station> findStationByStatus(@Param("status")Integer status, Pageable pageable);
	
	@Query(nativeQuery=true,value="SELECT count(id) FROM station " +
			"WHERE if(?1!=0,  id=?1,              1=1) " +
			"and   if(?2!='', name like %?2%,     1=1) " +
			"and   if(?3!=0,  status=?3,          1=1) ")
	int findStationCount(@Param("id")Integer id, @Param("name")String name, 
			@Param("status")Integer status);
	
		
}