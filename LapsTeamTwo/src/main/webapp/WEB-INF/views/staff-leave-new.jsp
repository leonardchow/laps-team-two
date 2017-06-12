<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="STYLESHEET" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-ui.theme.css" />

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
<script>
	$(document).ready(function() {
		$("#datepicker1").datepicker({
			dateFormat : "dd/mm/yy"
		});
	});
	$(document).ready(function() {
		$("#datepicker2").datepicker({
			dateFormat : "dd/mm/yy"
		});
	});
</script>
<h3>New Leave page</h3>
<form:form method="POST" commandName="leave"
	action="${pageContext.request.contextPath}/staff/leave/created">

	<table>
	
 	 <tr>
			<td>Start Date</td>
			<td><form:input size="16" path="startDate" id="datepicker1" /><form:errors
					path="startDate" cssStyle="color: red;" /></td>
			<td>End Date</td>
			<td><form:input size="16" path="endDate" id="datepicker2" /><form:errors
					path="endDate" cssStyle="color: red;" /></td>
		</tr>	
		<tr>
			<td>LeaveType</td>
			<td colspan="3"><form:input size="40" path="leaveType" /> <form:errors
					path="leaveType" cssStyle="color: red;" /></td>
				
		</tr>
		
		<tr>
			<td>Work Dissemination</td>
			<td colspan="3"><form:input size="40" path="dissemination" /></td>
		</tr>
		
		<tr>
			<td>Dissemination ID</td>
			<td colspan="3"><form:input size="40" path="disseminationId" /></td>
		</tr>
		
	
		<tr>
			<td>Reason</td>
			<td colspan="3"><form:textarea cols="64" rows="5"
					path="reason" /></td>
		</tr>
		


		<tr>
			<td>&nbsp;</td>
			<td colspan="2" align="left"><br></br> <form:button
					type="submit">
					<img
						src="${pageContext.request.contextPath}/image/button_submit.gif"
						alt="" align="middle">
				</form:button>&nbsp; 
				<a href="javascript:history.back();"> <img
					src="${pageContext.request.contextPath}/image/button_cancel.gif"
					alt="" align="middle" border="0">
			</a></td>
		</tr>

	</table>
</form:form>
