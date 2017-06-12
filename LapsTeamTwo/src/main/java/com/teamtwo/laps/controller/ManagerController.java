package com.teamtwo.laps.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

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

import com.teamtwo.laps.javabeans.Approve;
import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.LeaveType;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.StaffMemberService;

/**
 * Handles requests for the application staff pages.
 */
@Controller
@RequestMapping(value = "/manager")
public class ManagerController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private LeaveService lService;

	@Autowired
	private StaffMemberService smService;

	/**
	 * Renders the staff dashboard.
	 */
	@RequestMapping(value = "/dashboard")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "staffDashboard";
	}

	@RequestMapping(value = "/pending/{staffId}")
	public ModelAndView viewPendingPage(@PathVariable Integer staffId) {
		ModelAndView modelAndView = new ModelAndView("manager-view-pending");
		StaffMember manager = smService.findStaffById(staffId);
		modelAndView.addObject("manager", manager);
		modelAndView.addObject("approve", new Approve());
		return modelAndView;
	}

	@RequestMapping(value = "/pending/detail/{leaveId}")
	public ModelAndView approveApplicationPage(@PathVariable Integer leaveId) {
		ModelAndView modelAndView = new ModelAndView("manager-approve");
		Leave leave = lService.findLeaveById(leaveId);
		modelAndView.addObject("leave", leave);
		modelAndView.addObject("approve", new Approve());
		return modelAndView;
	}

	@RequestMapping(value = "/pending/edit/{leaveId}", method = RequestMethod.POST)
	public ModelAndView approveOrRejectCourse(@ModelAttribute("approve") Approve approve, BindingResult result,
			@PathVariable Integer leaveId, HttpSession session, final RedirectAttributes redirectAttributes) {
		if (result.hasErrors())
			return new ModelAndView("manager-approve");
		Leave leave = lService.findLeaveById(leaveId);
		if (approve.getDecision().equalsIgnoreCase("approved")) {
			leave.setStatus(LeaveStatus.APPROVED);
		} else {
			leave.setStatus(LeaveStatus.REJECTED);
		}
		leave.setComment(approve.getComment());
		System.out.println(leave.toString());
		lService.changeLeave(leave);
		ModelAndView mav = new ModelAndView("redirect:/manager/pending");
		String message = "Course was successfully updated.";
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	// Yin
	@RequestMapping(value = "/leave/Subordinate", method = RequestMethod.GET)
	public ModelAndView viewSubordinateListForLeaveApproval() {
		ModelAndView mav = new ModelAndView("manager-subordinate-list");
		List<StaffMember> subordinateList = smService.showBySubordinateName();
		mav.addObject("subordinateList", subordinateList);
		return mav;
	}

	@RequestMapping(value = "/leave/Subordinate/approve", method = RequestMethod.GET)
	public ModelAndView viewLeaveForDetails() {
		ModelAndView mav = new ModelAndView("manager-approve-leavelist");
		mav.addObject("leaveList", "#######");
		return mav;
	}

	@RequestMapping(value = "/subordinate/LeaveHistory", method = RequestMethod.GET)
	public ModelAndView viewSubordinateLeaveHistory() {
		ModelAndView mav = new ModelAndView("manager-subordinate-leave-history");
		List<StaffMember> subordinateLeave = smService.showBySubordinateName();
		mav.addObject("subordinateLeave", subordinateLeave);
		return mav;
	}

	@RequestMapping(value = "/subordinate/LeaveHistory/Details/{sid}", method = RequestMethod.GET)
	public ModelAndView viewSubordinateLeaveHistoryDeatils(@PathVariable int sid) {
		ModelAndView mav = new ModelAndView("manager-subordinate-leave-history-detail");

		StaffMember staffMember = smService.findStaff(sid);
		mav.addObject("staffMember", staffMember);

		List<Leave> leaveHistoryList = lService.findPendingLeaves(staffMember.getStaffId());
		mav.addObject("leaveHistoryList", leaveHistoryList);
		return mav;
	}
}
