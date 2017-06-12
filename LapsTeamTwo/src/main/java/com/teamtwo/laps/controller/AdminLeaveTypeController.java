package com.teamtwo.laps.controller;


import java.util.ArrayList;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamtwo.laps.service.LeaveTypeService;



import com.teamtwo.laps.model.LeaveType;;

/**
 * Handles requests for the application staff pages.
 */
@Controller
@RequestMapping(value = "/admin/leavetype")
public class AdminLeaveTypeController {

	@Autowired
	private LeaveTypeService ltService;

	/**
	 * Renders the staff dashboard.
	 */

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView newLeavePage() {
		ModelAndView mav = new ModelAndView("leave-type-new", "leavetype", new LeaveType());
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createNewLeavePage(@ModelAttribute @Valid LeaveType leave, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors())
			return new ModelAndView("leave-type-new");

		ModelAndView mav = new ModelAndView();
		String message = "New leave type " + leave.getLeaveName() + " was successfully created.";

		ltService.createLeaveType(leave);
		mav.setViewName("redirect:/admin/leavetype/list");

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView leaveTypeListPage() {
		ModelAndView mav = new ModelAndView("leave-type-list");
		ArrayList<LeaveType> leaveTypeList = ltService.findAllLeaveType();
		mav.addObject("leaveTypeList", leaveTypeList);
		return mav;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editLeaveTypePage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("leave-type-edit");
		LeaveType leaveType = ltService.findLeaveTypeById(id);
		mav.addObject("leaveType", leaveType);
		return mav;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editRole(@ModelAttribute @Valid LeaveType leave, BindingResult result, @PathVariable Integer id,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors())
			return new ModelAndView("leave-type-edit");

		ModelAndView mav = new ModelAndView("redirect:/admin/leavetype/list");
		String message = "leavetype was successfully updated.";

		ltService.changeLeaveType(leave);

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteRole(@PathVariable Integer id, final RedirectAttributes redirectAttributes)
			 {

		ModelAndView mav = new ModelAndView("redirect:/admin/leavetype/list");
		LeaveType leave = ltService.findLeaveTypeById(id);
		ltService.removeLeaveType(leave);
		String message = "The role " + leave.getLeaveType() + " was successfully deleted.";

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

}
