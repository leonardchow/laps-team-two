<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Manager Approve Application Page</title>
</head>
<body>
	<h3>
		<spring:message code="${leave.staffMember.name} "/>
	</h3>

	<table class="table table-hover">
		<tr>
			<th align="right"><spring:message code="Staff Name" /></th>
			<td colspan="3"><c:out value="${leave.staffMember.name}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="Leave ID" /></th>
			<td colspan="3"><c:out value="${leave.leaveId}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="Reason" /></th>
			<td colspan="3"><c:out value="${leave.reason}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="Leave Type" /></th>
			<td colspan="3"><c:out value="${leave.leaveTypeModel.leaveName}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="Start Date" /></th>
			<td><c:out value="${leave.startDate }" /></td>
			<th align="right"><spring:message code="End Date" /></th>
			<td><c:out value="${leave.endDate }" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="Dessemination" /></th>
			<td><c:out value="${leave.dissemination}" /></td>
			<th align="right"><spring:message code="Dessemination ID" /></th>
			<td><c:out value="${leave.disseminationMember.name}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="Contact Information" /></th>
			<td colspan="3"><c:out value="${leave.contactDetails}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="Status" /></th>
			<td colspan="3"><c:out value="${leave.status}" /></td>
		</tr>
	</table>
	
	<form:form method="POST" modelAttribute="approve"
		action="${pageContext.request.contextPath}/manager/pending/edit/${leave.leaveId}.html">
		<table>
			<tr>
				<td><form:radiobutton path="decision" value="APPROVED"
						id="decision" /> <spring:message code="Approve" />
					&nbsp;&nbsp; <form:radiobutton path="decision" value="REJECTED"
						id="decision" /> <spring:message code="Reject" /></td>
			</tr>
			<tr>
				<td><spring:message code="Comment" /><br>
					<form:textarea path="comment" cols="60" rows="4" id="comment" /></td>
			</tr>
		</table>
		<form:button name="submit" type="submit" value="s">
			<img src="${pageContext.request.contextPath}/image/button_submit.gif"
				width="59" height="22" alt="" border="0">
		</form:button>
	</form:form>
</body>
</html>
