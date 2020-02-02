package com.sun.erpbackend.service.material;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.erpbackend.entity.material.Transport;
import com.sun.erpbackend.repository.material.TransportRepository;
import com.sun.erpbackend.response.TransportResponse;

@Service
@Transactional
@Secured("ROLE_ADMIN")
public class TransportService {
	@Autowired
	TransportRepository transportRepository;
	@Autowired
	MaterialService materialService;
	
	/**
	 * New Material Transportation
	 * @param mid
	 * @param mount
	 * @param way
	 * @param description
	 * @return 2-ERROR 1-Correct
	 */
	public int addNewTranposrt(Integer mid, Integer mount,Integer way, String description) {
		if(!this.materialService.exitsById(mid)) {
			return 4;
		}
		if(way == -1 && this.materialService.findMaterialById(mid).getLeftMount() < mount) {
			return 5;
		}
		Transport transport  = new Transport(new Date(), mid, mount, way, description);
		Transport result = this.transportRepository.saveAndFlush(transport);
		if(result == null) {
			return 2;
		}
		if(result.getId() == 0) {
			return 2;
		}
		this.materialService.updateMaterialMountByTransport(mid, mount, way);
		return 1;
	}
	
	/**
	 * Delete Transport Record before date
	 * @param dateString
	 * @return 1-Success 2-Error
	 */
	public int deleteBefore(String dateString) {
		try {
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			this.transportRepository.deleteTransportByTime(date2);
		} catch (ParseException e) {
			e.printStackTrace();
			return 2;
		}
		return 1;
	}
	

	/**
	 * get the transport records by material id
	 * @param mid
	 * @return code 1-correct 2-error
	 */
	public TransportResponse findTransportByMid(Integer mid) {
		List<Transport> results = this.transportRepository.findByMid(mid);
		TransportResponse response = new TransportResponse();
		if(results == null) {
			response.setCode(2);
			return response;
		}
		response.setCode(1);
		response.setData(results);
		return response;
	}
}
