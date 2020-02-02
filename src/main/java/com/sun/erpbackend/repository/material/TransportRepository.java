package com.sun.erpbackend.repository.material;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.material.Transport;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer>{
	
	@Modifying
	@Query(nativeQuery=true, value="DELETE * FROM transport WHERE time < ?1")
	int deleteTransportByTime(Date end);

	List<Transport> findByMid(Integer mid);
}
