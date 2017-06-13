package com.teamtwo.laps.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.laps.model.Overtime;
import com.teamtwo.laps.repository.OvertimeRepository;

@Service
public class OvertimeServiceImpl implements OvertimeService {

	@Resource
	private OvertimeRepository repository;
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findOvertimeOfStaff(java.lang.Integer)
	 */
	@Override
	@Transactional
	public ArrayList<Overtime> findOvertimeOfStaff(Integer staffId) {
		return repository.findOvertimeOfStaff(staffId);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findAllOvertime()
	 */
	@Override
	@Transactional
	public ArrayList<Overtime> findAllOvertime() {
		return (ArrayList<Overtime>) repository.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findOvertimeById(java.lang.Integer)
	 */
	@Override
	@Transactional
	public Overtime findOvertimeById(Integer id) {
		return repository.findOne(id);
	}

	@Override
	@Transactional
	public void deleteOvertime(Integer id) {
		repository.delete(id);
	}
	
	@Override
	@Transactional
	public void saveOvertimes(List<Overtime> overtimes) {
		repository.save(overtimes);
//		repository.flush();
	}
	
	@Override
	@Transactional
	public void updateOvertimes(List<Overtime> overtimes) {
//		List<Overtime> toSave = new ArrayList<>();
		for (Overtime overtime : overtimes) {
			Overtime storedOt = repository.findOne(overtime.getId());
			storedOt.setLoggedHours(overtime.getLoggedHours());
			storedOt.setDate(overtime.getDate());
//			toSave.add(storedOt);
			repository.saveAndFlush(storedOt);
		}
		//repository.save(toSave);
//		repository.flush();
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findUnclaimedOvertimeOfStaff(java.lang.Integer)
	 */
	@Override
	public Integer findUnclaimedOvertimeOfStaff(Integer staffId) {
		return repository.findUnclaimedOvertimeOfStaff(staffId);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findLoggedHoursOfStaff(java.lang.Integer)
	 */
	@Override
	public Integer findLoggedHoursOfStaff(Integer staffId) {
		return repository.findLoggedHoursOfStaff(staffId);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findClaimedHoursOfStaff(java.lang.Integer)
	 */
	@Override
	public Integer findClaimedHoursOfStaff(Integer staffId) {
		return repository.findClaimedHoursOfStaff(staffId);
	}
}