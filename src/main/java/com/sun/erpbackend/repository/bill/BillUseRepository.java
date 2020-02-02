package com.sun.erpbackend.repository.bill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.bill.BillUse;

@Repository
public interface BillUseRepository extends JpaRepository<BillUse, Integer>{
	List<BillUse> findByBillId(@Param("billId")Integer billId);
	
	@Query(nativeQuery=true, value="SELECT * FROM billuse WHERE billid=?1 AND haveused=0")
	List<BillUse> findUseByBillId(@Param("billId")Integer billId);
	
	@Query(nativeQuery=true, value="SELECT * FROM billuse WHERE billid=?1 AND haveused=1 AND usemid=?2")
	BillUse findHaveUseByBillIdAndMid(@Param("billId")Integer billId, @Param("mid")Integer mid);
	
	@Query(nativeQuery=true, value="SELECT * FROM billuse WHERE billid=?1 AND haveused=1")
	List<BillUse> findHaveUseByBillId(@Param("billId")Integer billId);

	@Modifying
	@Query(nativeQuery=true, value="DELETE FROM billuse WHERE billid=?1")
	int deleteByBillId(@Param("billId")Integer billId);

	@Modifying
	@Query(nativeQuery=true, value="DELETE FROM billuse WHERE billid=?1  AND haveused=1")
	int deleteByBillIdHave(@Param("billId")Integer billId);
}
