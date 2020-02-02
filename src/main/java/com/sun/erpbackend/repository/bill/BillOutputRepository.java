package com.sun.erpbackend.repository.bill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.bill.BillOutput;

@Repository
public interface BillOutputRepository extends JpaRepository<BillOutput, Integer>{
	BillOutput findByBillId(@Param("billId")Integer billId);
	
	@Query(nativeQuery=true, value="SELECT * FROM billoutput WHERE billid=?1 AND haveop=0")
	BillOutput findOutputByBillId(@Param("billId")Integer billId);
	
	@Query(nativeQuery=true, value="SELECT * FROM billoutput WHERE billid=?1 AND haveop=1")
	BillOutput findHaveOutputByBillId(@Param("billId")Integer billId);
	
	@Modifying
	@Query(nativeQuery=true, value="DELETE FROM billoutput WHERE billid=?1")
	void deleteByBillId(@Param("billId")Integer billId);

	@Modifying
	@Query(nativeQuery=true, value="DELETE FROM billoutput WHERE billid=?1 AND haveop=1")
	void deleteByBillIdHave(@Param("billId")Integer billId);
	
	@Query(nativeQuery=true, value="SELECT * FROM billoutput WHERE haveop=1")
	List<BillOutput> findHaveOutput();
}
