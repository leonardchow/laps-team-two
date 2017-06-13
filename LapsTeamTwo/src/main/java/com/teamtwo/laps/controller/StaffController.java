package com.teamtwo.laps.controller;


import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.LeaveType;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.LeaveTypeService;
import com.teamtwo.laps.service.StaffMemberService;






/**
 * Handles requests for the application staff pages.
 */
@Controller
@RequestMapping(value = "/staff")
public class StaffController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private StaffMemberService smService;
	
	@Autowired
	private LeaveService lService;
	
	@Autowired
	private LeaveTypeService lTypeService;
	
	/**
	 * Renders the staff dashboard.
	 */
	@RequestMapping(value = "/dashboard")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "staffDashboard";
	}
	
	@RequestMapping(value = "/view/{staffId}")
	public ModelAndView viewStaffMemberPage(@PathVariable Integer staffId) {
		ModelAndView modelAndView = new ModelAndView("staffMember-view");
		StaffMember staffMember = smService.findStaffById(staffId);
		modelAndView.addObject("staffMember", staffMember);
		return modelAndView;
	}
	
//	@RequestMapping(value = "/leave/{staffId}")
//	public ModelAndView viewStaffLeave(@PathVariable Integer staffId) {
//		ModelAndView modelAndView = new ModelAndView("staffMember-leave-view");
////		ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
//		modelAndView.addObject("leaves", lService.findAllLeaveOfStaff(staffId));
//		return modelAndView;
//	}
	
	@RequestMapping(value = "/leave/create", method = RequestMethod.GET)
	public ModelAndView NewLeavePage() {
		ModelAndView mav = new ModelAndView("staff-leave-new");
		
		List<LeaveType> leaveTypes = lTypeService.findAllLeaveType();
		
		Leave leave = new Leave();
		mav.addObject("leave", leave);
		mav.addObject("leaveTypes", leaveTypes);
		
		return mav;
}
	
	
	
	
@RequestMapping(value = "/leave/created", method = RequestMethod.POST)
public ModelAndView createNewLeave(@ModelAttribute @Valid Leave leave, BindingResult result,
		final RedirectAttributes redirectAttributes, HttpSession session) {



	ModelAndView mav = new ModelAndView("staff-leave-created");
	UserSession us = (UserSession) session.getAttribute("USERSESSION");
	//leave.setEmployeeId(us.getEmployee().getEmployeeId());
	
	leave.setStaffId(us.getUser().getStaffId());
	
	leave.setStatus(LeaveStatus.PENDING);
	

	
	
//	String message = "New leave " + leave.getLeaveId() + " was successfully created.";
	lService.createLeave(leave);

	
	return mav;
}
}


