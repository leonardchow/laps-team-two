package com.teamtwo.laps.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.repository.LeaveRepository;

@Service
public class LeaveServiceImpl implements LeaveService {
	@Resource
	private LeaveRepository repository;
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.LeaveService#findAllLeaveOfStaff(java.lang.Integer)
	 */
	@Override
	@Transactional
	public ArrayList<Leave> findAllLeaveOfStaff(Integer staffId) {
		return repository.findAllLeaveOfStaff(staffId);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.LeaveService#findLeaveByType(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional
	public ArrayList<Leave> findLeaveByType(Integer staffId, Integer leaveType) {
		return repository.findLeaveByType(leaveType, staffId);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.LeaveService#findLeaveById(java.lang.Integer)
	 */
	@Override
	@Transactional
	public Leave findLeaveById(Integer leaveId) {
		return repository.findOne(leaveId);
	}

	@Override
<<<<<<< HEAD
	public Leave createLeave(Leave leave) {
=======
	public Leave changeLeave(Leave leave) {
>>>>>>> branch 'master' of https://github.com/leonardchow/laps-team-two.git
		// TODO Auto-generated method stub
		return repository.saveAndFlush(leave);
	}
}
