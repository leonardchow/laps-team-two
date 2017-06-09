package com.teamtwo.laps.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.repository.StaffMemberRepository;

@Service
public class StaffMemberServiceImpl implements StaffMemberService {
	@Resource
	private StaffMemberRepository repository;
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.StaffMemberService#findAllStaff()
	 */
	@Override
	@Transactional
	public ArrayList<StaffMember> findAllStaff() { 
		return (ArrayList<StaffMember>) repository.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.StaffMemberService#findStaffById(java.lang.Integer)
	 */
	@Override
	@Transactional
	public StaffMember findStaffById(Integer staffId) { 
		return repository.findOne(staffId);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.StaffMemberService#findStaffMemberById(java.lang.Integer)
	 */
	@Override
	@Transactional
	public StaffMember findStaffMemberById(Integer staffId) { 
		return repository.findStaffMemberById(staffId);
	}
}
