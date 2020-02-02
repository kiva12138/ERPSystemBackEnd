package com.sun.erpbackend.config;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sun.erpbackend.entity.bill.Bill;
import com.sun.erpbackend.repository.bill.BillReposiroty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduledService {
	
	@Autowired
	BillReposiroty billRepository; 
	
    @Scheduled(cron = "0 * * * *")
    public void scheduled(){
    	List<Bill> bills = this.billRepository.findProducingBills();
    	for(Bill bill: bills) {
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(bill.getDistributeTime());
    		calendar.add(Calendar.HOUR, bill.getEstimateTime());
    		
    		Calendar nowCalendar = Calendar.getInstance();
    		nowCalendar.setTime(new Date());
    		if(calendar.before(nowCalendar)){
    			bill.setStatus(5);
    		}
    		this.billRepository.save(bill);
    	}
    }
}