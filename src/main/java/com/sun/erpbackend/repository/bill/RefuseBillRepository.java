package com.sun.erpbackend.repository.bill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.bill.RefuseBill;

@Repository
public interface RefuseBillRepository extends JpaRepository<RefuseBill, Integer>  {
	@Modifying
	@Query(nativeQuery=true, value="DELETE FROM refusebill WHERE billid=?1")
	void deleteByBillId(@Param("billid")Integer billId);
	
	@Query(nativeQuery=true, value="SELECT * FROM refusebill WHERE billid=?1")
	RefuseBill findByBillId(@Param("billid")Integer billId);
	
	@Query(nativeQuery=true, value="SELECT * FROM refusebill WHERE refusekind=?1")
	List<RefuseBill> findByRefuseKind(@Param("refusekind")Integer refusekind);
}
