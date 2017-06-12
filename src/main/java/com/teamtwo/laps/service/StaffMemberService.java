package com.teamtwo.laps.service;

import java.util.ArrayList;



import com.teamtwo.laps.model.StaffMember;



public interface StaffMemberService {

	ArrayList<StaffMember> findAllStaff();

	StaffMember findStaffById(Integer staffId);

	StaffMember findStaffMemberById(Integer staffId);
	
	ArrayList<StaffMember> findSubordinates(Integer staffId);
	
	//Yin
	ArrayList<StaffMember> showBySubordinateName();
	
	ArrayList<String> getOnlyStaffName();
	
	StaffMember findStaff(Integer sid);

}