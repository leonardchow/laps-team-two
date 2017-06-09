package com.teamtwo.laps.controller;

import static org.hamcrest.CoreMatchers.startsWith;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	@Autowired
	private StaffMemberService smService;
	
	@Autowired
	private LeaveService lService;
	
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
	
	@RequestMapping(value = "/leave/{staffId}")
	public ModelAndView viewStaffLeave(@PathVariable Integer staffId) {
		ModelAndView modelAndView = new ModelAndView("staffMember-leave-view");
//		ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
		modelAndView.addObject("leaves", lService.findAllLeaveOfStaff(staffId));
		return modelAndView;
	}
	
}
