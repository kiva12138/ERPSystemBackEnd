package com.sun.erpbackend.repository.bill;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.bill.Bill;

@Repository
public interface BillReposiroty extends JpaRepository<Bill, Integer> {
	// Get The count of bills
	@Query(nativeQuery=true, value="SELECT count(id) FROM bill "+
			"WHERE createdtime > ?1 AND if(?2!=0,status=?2,1=1)")
	int findAllMountByTimeAndStatus(@Param("date")Date date, @Param("status")Integer status);
	
	@Query(nativeQuery=true, value="SELECT count(id) FROM bill "+
			"WHERE createdtime > ?1 AND if(?2!=0,status=?2,1=1) AND stationid=?3")
	int findMountByTimeAndStatusAndStationId(@Param("date")Date date, @Param("status")Integer status,
			@Param("stationid")Integer stationId);
	
	@Query(nativeQuery=true, value="SELECT * FROM bill WHERE status=4")
	List<Bill> findProducingBills();
	
	@Query(nativeQuery=true, value="SELECT * FROM bill WHERE status=4 AND stationid=?1")
	List<Bill> findProducingBillsByStationId(@Param("stationid")Integer stationId);
	
	@Query(nativeQuery=true, value="SELECT * FROM bill WHERE stationid=?1")
	List<Bill> findBillsByStationId(@Param("stationid")Integer stationId);
	
	@Query(nativeQuery=true, value="SELECT * FROM bill WHERE stationid=?1 AND (status=4 OR status=5 OR status=6)")
	List<Bill> findBillsByStationIdAndUsingMaterial(@Param("stationid")Integer stationId);
	
	@Query(nativeQuery=true, value="SELECT * FROM bill WHERE stationid=?1 AND distributetime > ?2")
	List<Bill> findBillsByStationIdAndTime(@Param("stationid")Integer stationId, @Param("time")Date time);
	
	@Query(nativeQuery=true,value="SELECT * FROM bill " +
			"WHERE if(?1!=0,  id=?1,              1=1) " +
			"and   if(?2!='', name like %?2%,     1=1) " +
			"and   if(?3!=0,  opkind=?3,          1=1) " +
			"and   if(?6!=0,  stationid=?4,       1=1) " +
			"and   if(?5!=0,  usebrief like %?5%, 1=1) " +
			"and   if(?4!=0,  outbrief like %?4%, 1=1)")
	Page<Bill> findBill(@Param("id")Integer id, @Param("name")String name, @Param("kind")Integer kind,
			@Param("output")String output, @Param("material")String material, @Param("stationId")Integer stationId,
			Pageable pageable);
	
	@Query(nativeQuery=true,value="SELECT count(id) FROM bill " +
			"WHERE if(?1!=0,  id=?1,              1=1) " +
			"and   if(?2!='', name like %?2%,     1=1) " +
			"and   if(?3!=0,  opkind=?3,          1=1) " +
			"and   if(?6!=0,  stationid=?4,       1=1) " +
			"and   if(?5!=0,  usebrief like %?5%, 1=1) " +
			"and   if(?4!=0,  outbrief like %?4%, 1=1)")
	int findBillCount(@Param("id")Integer id, @Param("name")String name, @Param("kind")Integer kind,
			@Param("output")String output, @Param("material")String material, @Param("stationId")Integer stationId);
	
	@Query(nativeQuery=true,value="SELECT * FROM bill " +
			"WHERE if(?1!=0,  id=?1,              1=1) " +
			"and   if(?2!='', name like %?2%,     1=1) " +
			"and   if(?3!=0,  opkind=?3,          1=1) " +
			"and   if(?6!=0,  stationid=?6,       1=1) " +
			"and   if(?5!=0,  usebrief like %?5%, 1=1) " +
			"and   if(?4!=0,  outbrief like %?4%, 1=1) " +
			"and   if(?7!=0,  status=?7,          1=1)")
	Page<Bill> findBillWithStatus(@Param("id")Integer id, @Param("name")String name, @Param("kind")Integer kind,
			@Param("output")String output, @Param("material")String material, 
			@Param("stationId")Integer stationId, @Param("status")Integer status,
			Pageable pageable);
	
	@Query(nativeQuery=true,value="SELECT count(id) FROM bill " +
			"WHERE if(?1!=0,  id=?1,              1=1) " +
			"and   if(?2!='', name like %?2%,     1=1) " +
			"and   if(?3!=0,  opkind=?3,          1=1) " +
			"and   if(?6!=0,  stationid=?6,       1=1) " +
			"and   if(?5!=0,  usebrief like %?5%, 1=1) " +
			"and   if(?4!=0,  outbrief like %?4%, 1=1) " +
			"and   if(?7!=0,  status=?7,          1=1)")
	int findBillCountWithStatus(@Param("id")Integer id, @Param("name")String name, @Param("kind")Integer kind,
			@Param("output")String output, @Param("material")String material, 
			@Param("stationId")Integer stationId, @Param("status")Integer status);
}
