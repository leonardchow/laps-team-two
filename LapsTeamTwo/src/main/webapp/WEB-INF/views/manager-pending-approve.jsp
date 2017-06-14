<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>
	<spring:message code="title.leaveDetails" />
</title>
</head>
<body>
	<h3>
		${leave.staffMember.name}'s <spring:message code="fieldLabel.leave" /> ${leave.leaveId}
	</h3>

	<table>
		<tr>
			<th align="right"><spring:message code="fieldLabel.employeeName" /></th>
			<td colspan="3"><c:out value="${leave.staffMember.name}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="fieldLabel.leaveId" /></th>
			<td colspan="3"><c:out value="${leave.leaveId}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="fieldLabel.reason" /></th>
			<td colspan="3"><c:out value="${leave.reason}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="fieldLabel.leaveType" /></th>
			<td colspan="3"><c:out value="${leave.leaveTypeModel.leaveName}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="fieldLabel.startDate" /></th>
			<td><c:out value="${leave.startDate }" /></td>
			<th align="right"><spring:message code="fieldLabel.endDate" /></th>
			<td><c:out value="${leave.endDate }" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="fieldLabel.dissemTask" /></th>
			<td><c:out value="${leave.dissemination}" /></td>
			<th align="right"><spring:message code="fieldLabel.dissemination" /></th>
			<td><c:out value="${leave.disseminationMember.name}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="fieldLabel.contactInfo" /></th>
			<td colspan="3"><c:out value="${leave.contactDetails}" /></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="fieldLabel.status" /></th>
			<td colspan="3"><c:out value="${leave.status}" /></td>
		</tr>
	</table>

	<form:form method="POST" modelAttribute="approve"
		action="${pageContext.request.contextPath}/manager/pending/edit/${leave.leaveId}.html">
		<table>
			<tr>
				<td><form:radiobutton path="decision" value="APPROVED"
						id="decision" /> <spring:message code="caption.approve" /> &nbsp;&nbsp;
					<form:radiobutton path="decision" value="REJECTED" id="decision" />
					<spring:message code="caption.reject" /></td>
			</tr>
			<tr>
				<td><spring:message code="fieldLabel.actionComment" /><br> <form:textarea
						path="comment" cols="60" rows="4" id="comment" /></td>
			</tr>
		</table>
		<form:button name="button" type="submit" value="submit">
			<img src="${pageContext.request.contextPath}/image/button_submit.gif"
				width="59" height="22" alt="" border="0">
		</form:button>
	</form:form>
	<form:form method="POST" modelAttribute="approve"
		action="${pageContext.request.contextPath}/manager/pending/cancel/${leave.leaveId}.html">
		<form:button name="button" type="submit" value="submit">
			<img src="${pageContext.request.contextPath}/image/button_cancel.gif"
				width="59" height="22" alt="" border="0">
		</form:button>
	</form:form>

</body>
</html>
