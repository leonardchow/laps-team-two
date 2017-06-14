<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<body>
	<h1><spring:message code="title.leaveDetailsHistory" /></h1>

	<form:form method="POST" modelAttribute="staffMember"
		action="${pageContext.request.contextPath}/manager/subordinate/LeaveHistory/Details/${staffMember.staffId}.html">

		<h2>Staff Name: ${staffMember.name}</h2>
		<c:if test="${fn:length(leaveHistoryList) gt 0}">
			<table style="cellspacing: 2; cellpadding: 2; border: 1;">
				<thead>
					<tr class="listHeading">
						<th><spring:message code="fieldLabel.leaveId"/></th>
						<th><spring:message code="fieldLabel.employeeId"/>Staff ID</th>
						<th><spring:message code="fieldLabel.leaveType"/>Leave Type</th>
						<th><spring:message code="fieldLabel.startDate"/>Start Date</th>
						<th><spring:message code="fieldLabel.endDate"/>End Date</th>
						<th><spring:message code="fieldLabel.dissemination"/>DisseminationId</th>
						<th><spring:message code="fieldLabel.status"/>Status</th>
						<th><spring:message code="title.leaveDetails"/>details</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="leave_history" items="${leaveHistoryList}">
						<tr class="listRecord">
							<td>${leave_history.leaveId}</td>
							<td>${leave_history.staffMember.name}</td>
							<td>${leave_history.leaveTypeModel.leaveName}</td>
							<td>${leave_history.startDate}</td>
							<td>${leave_history.endDate}</td>
							<td>${leave_history.disseminationMember.name}</td>
							<td>${leave_history.status.toString()}</td>
							<td><a
								href="${pageContext.request.contextPath}/manager/subordinate/history//detail/${leave_history.leaveId}.html">Detail</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</form:form>
</body>
