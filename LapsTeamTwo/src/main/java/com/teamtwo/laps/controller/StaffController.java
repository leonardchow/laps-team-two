package com.teamtwo.laps.controller;

import static org.hamcrest.CoreMatchers.startsWith;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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

import com.teamtwo.laps.controller.UserSession;



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
	/*
	@Autowired
	private CourseValidator cValidator;
*/
	@InitBinder("leave")
	private void initLeaveBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		/*binder.addValidators(cValidator);*/

	}

	
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
	
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home/login";

	}
	
	
    //Huitian
	
	@RequestMapping(value = "/history")
	public ModelAndView staffLeaveHistory(HttpSession session) {
		UserSession us = (UserSession) session.getAttribute("USERSESSION");
		ModelAndView mav = new ModelAndView("login");
		if (us.getSessionId() != null) {
			mav = new ModelAndView("/staff-leave-history");
			mav.addObject("lhistory", lService.findAllLeaveOfStaff(us.getEmployee().getStaffId()));
			return mav;
		} 
		return mav;
	}
	
	@RequestMapping(value = "/history/details/{id}", method = RequestMethod.GET)
	public ModelAndView LeaveDetails(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("staff-leave-history-details");
		Leave leave = lService.findLeaveById(id);
		mav.addObject("leave", leave);
		return mav;
	}
	
	@RequestMapping(value = "/history/details/{id}", method = RequestMethod.POST)
	public ModelAndView LeaveDetailsBack(@ModelAttribute @Valid Leave leave, BindingResult result, @PathVariable Integer id,
			final RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("redirect:/staff/history");
		return mav;
	}
	
	@RequestMapping(value = "/history/update/{id}", method = RequestMethod.GET)
	public ModelAndView updateLeaveHistory(@PathVariable Integer id, HttpSession session) {
		UserSession us = (UserSession) session.getAttribute("USERSESSION");
		int loggedInStaffId = us.getUser().getStaffId();
		
		ModelAndView mav = new ModelAndView("staff-leave-history-update");
		//ArrayList<LeaveType> leavetypes = lTypeService.findAllLeaveType();
		ArrayList<StaffMember> staffMembers= (ArrayList<StaffMember>)smService.findAllStaff().stream()
				.filter(staff -> staff.getStaffId() != loggedInStaffId).collect(Collectors.toList());
		
		Leave leave = lService.findLeaveById(id);
		//Date startDate = leave.getStartDate();
		mav.addObject("leave", leave);
		//mav.addObject("leavename", leavetypes);
		mav.addObject("staffMembers", staffMembers);
		//mav.addObject("startDate", startDate);
		return mav;
	}
	
	
	@RequestMapping(value = "/history/update/{id}", method = RequestMethod.POST)
	public ModelAndView updateLeave(@ModelAttribute @Valid Leave leave, BindingResult result, @PathVariable Integer id,
			final RedirectAttributes redirectAttributes, HttpSession session) {
		if (result.hasErrors())
			return new ModelAndView("staff-leave-history-update");
		ModelAndView mav = new ModelAndView();
		String message = "The leave " + leave.getLeaveId() + "has been successfully updated.";
		lService.changeLeave(leave);
		UserSession us = (UserSession) session.getAttribute("USERSESSION");
		leave.setStaffId(us.getEmployee().getStaffId());
		leave.setStatus(LeaveStatus.UPDATED);
		mav.setViewName("redirect:/staff/history");
		lService.changeLeave(leave);
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	@RequestMapping(value = "/history/delete/{id}", method = RequestMethod.GET)
	public ModelAndView DeleteLeave(@PathVariable Integer id, final RedirectAttributes redirectAttributes)
			 {

		ModelAndView mav = new ModelAndView("redirect:/staff/history");
		Leave leave = lService.findLeaveById(id);
		lService.DeleteLeave(leave, LeaveStatus.DELETED);
		String message = "The leave " + leave.getLeaveId() + " has been successfully deleted.";

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	@RequestMapping(value = "/history/cancel/{id}", method = RequestMethod.GET)
	public ModelAndView cancelLeave(@PathVariable Integer id, final RedirectAttributes redirectAttributes)
			 {

		ModelAndView mav = new ModelAndView("redirect:/staff/history");
		Leave leave = lService.findLeaveById(id);
		lService.DeleteLeave(leave, LeaveStatus.CANCELLED);
		String message = "The leave " + leave.getLeaveId() + " has been successfully cancelled.";

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
}
