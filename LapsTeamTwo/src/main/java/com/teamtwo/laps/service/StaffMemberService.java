package com.teamtwo.laps.service;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.laps.model.StaffMember;

public interface StaffMemberService {

	ArrayList<StaffMember> findAllStaff();

	StaffMember findStaffById(Integer staffId);

	StaffMember findStaffMemberById(Integer staffId);

}