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
	

	<table class="table table-striped">
		<tr>
			<th ><spring:message code="Staff Name" /></th>
			<td ><c:out value="${leave.staffMember.name}" /></td>
		</tr>
		<tr>
			<th ><spring:message code="Leave ID" /></th>
			<td ><c:out value="${leave.leaveId}" /></td>
		</tr>
		<tr>
			<th ><spring:message code="Reason" /></th>
			<td ><c:out value="${leave.reason}" /></td>
		</tr>
		<tr>
			<th ><spring:message code="Leave Type" /></th>
			<td ><c:out value="${leave.leaveTypeModel.leaveName}" /></td>
		</tr>
		<tr>
			<th ><spring:message code="Start Date" /></th>
			<td><c:out value="${leave.startDate }" /></td>
			<th ><spring:message code="End Date" /></th>
			<td><c:out value="${leave.endDate }" /></td>
		</tr>
		<tr>
			<th ><spring:message code="Dessemination" /></th>
			<td><c:out value="${leave.dissemination}" /></td>
			<th ><spring:message code="Dessemination ID" /></th>
			<td><c:out value="${leave.disseminationMember.name}" /></td>
		</tr>
		<tr>
			<th ><spring:message code="Contact Information" /></th>
			<td ><c:out value="${leave.contactDetails}" /></td>
		</tr>
		<tr>
			<th ><spring:message code="Status" /></th>
			<td ><c:out value="${leave.status}" /></td>
		</tr>
	</table>
	
	<form:form method="POST" modelAttribute="approve"
		action="${pageContext.request.contextPath}/manager/pending/edit/${leave.leaveId}.html">
		<table class="table table-hover">
			<tr>
				<td><form:radiobutton path="decision" value="APPROVED"
						id="decision" /> <spring:message code="Approve" />
					<form:radiobutton path="decision" value="REJECTED"
						id="decision" /> <spring:message code="Reject" /></td>
			</tr>
			<tr>
				<td><spring:message code="Comment" /><br>
					<form:textarea path="comment" cols="60" rows="4" id="comment" /></td>
			</tr>
		</table>
		<form:button name="submit" type="submit"  class="btn btn-primary">
			Submit
		</form:button>
	</form:form>
</body>
</html>
