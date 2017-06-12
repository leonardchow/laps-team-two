package com.teamtwo.laps.controller;

import static org.hamcrest.CoreMatchers.startsWith;

import java.awt.Dialog.ModalExclusionType;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.StaffMemberService;



/**
 * Handles requests for the application staff pages.
 */
@Controller
@RequestMapping(value = "/staff")
public class StaffController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final int DASHBOARD_NUM_TO_SHOW = 3;
	
	@Autowired
	private StaffMemberService smService;
	
	@Autowired
	private LeaveService lService;
	
	/**
	 * Renders the staff dashboard.
	 */
	@RequestMapping(value = "/dashboard")
	public ModelAndView home(HttpSession session) {
		int userid = 1;
		
		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");
		
		int staffId = userSession.getEmployee().getStaffId();
		
		StaffMember staffMember = smService.findStaffById(staffId);
		ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
		ModelAndView modelAndView = new ModelAndView("staffDashboard");
		
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

//		leaves.get(1).getStartDate().getDate()
		logger.info("Rendering dashboard for user {}.", userid);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/view/{staffId}")
	public ModelAndView viewStaffMemberPage(@PathVariable Integer staffId) {
		ModelAndView modelAndView = new ModelAndView("staffMember-view");
		StaffMember staffMember = smService.findStaffById(staffId);
		modelAndView.addObject("staffMember", staffMember);
		return modelAndView;
	}
	
	@RequestMapping(value = "/leave/{staffId}")
	public ModelAndView viewStaffLeave(@PathVariable Integer staffId) {
		ModelAndView modelAndView = new ModelAndView("staffMember-leave-view");
//		ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
		modelAndView.addObject("leaves", lService.findAllLeaveOfStaff(staffId));
		return modelAndView;
	}
	
	@RequestMapping(value = "/history")
	public ModelAndView employeeCourseHistory(HttpSession session) {
		UserSession us = (UserSession) session.getAttribute("USERSESSION");
		ModelAndView mav = new ModelAndView("login");
		if (us.getSessionId() != null) {
			mav = new ModelAndView("/staff-leave-history");
			mav.addObject("lhistory", lService.findLeaveById(us.getUser().getStaffId()));
			return mav;
		}
		return mav;

	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home/login";

	}
	
	
	
}
