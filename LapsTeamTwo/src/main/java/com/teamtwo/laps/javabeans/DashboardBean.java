package com.teamtwo.laps.javabeans;

import java.util.ArrayList;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.teamtwo.laps.controller.UserSession;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.StaffMemberService;

public class DashboardBean {
	
	public static ModelAndView getDashboard(ModelAndView modelAndView, final int DASHBOARD_NUM_TO_SHOW, StaffMember staffMember, ArrayList<Leave> leaves) {
		Integer annualLeaveDays = leaves.stream()
				.filter(a -> a.getLeaveType() == 1
					&& (a.getStatus() == LeaveStatus.APPROVED
					|| a.getStatus() == LeaveStatus.PENDING
					|| a.getStatus() == LeaveStatus.UPDATED))
				.map(Leave::getNumberOfDays).reduce(0, ((a, b) -> a + b));
		Integer annualLeavePending = leaves.stream().filter(a -> a.getLeaveType() == 1 && (a.getStatus() == LeaveStatus.PENDING || a.getStatus() == LeaveStatus.UPDATED)).map(Leave::getNumberOfDays).reduce(0, ((a, b) -> a + b));
		
		Integer medicalLeaveDays = leaves.stream()
				.filter(a -> a.getLeaveType() == 1
				&& (a.getStatus() == LeaveStatus.APPROVED
				|| a.getStatus() == LeaveStatus.PENDING
				|| a.getStatus() == LeaveStatus.UPDATED))
				.map(Leave::getNumberOfDays).reduce(0, ((a, b) -> a + b));
		Integer medicalLeavePending = leaves.stream().filter(a -> a.getLeaveType() == 1 && (a.getStatus() == LeaveStatus.PENDING || a.getStatus() == LeaveStatus.UPDATED)).map(Leave::getNumberOfDays).reduce(0, ((a, b) -> a + b));
		
		
		int leavesToShow = leaves.size() > DASHBOARD_NUM_TO_SHOW ? DASHBOARD_NUM_TO_SHOW : leaves.size();
		
		modelAndView.addObject("staffMember", staffMember);
		modelAndView.addObject("leaves", leaves.subList(0, leavesToShow));
		modelAndView.addObject("totalLeavesNum", leaves.size());
		modelAndView.addObject("annualLeaveDays", annualLeaveDays);
		modelAndView.addObject("annualLeavePending", annualLeavePending);
		modelAndView.addObject("medicalLeaveDays", medicalLeaveDays);
		modelAndView.addObject("medicalLeavePending", medicalLeavePending);
		modelAndView.addObject("numToShow", DASHBOARD_NUM_TO_SHOW);

		return modelAndView;
	}
}
