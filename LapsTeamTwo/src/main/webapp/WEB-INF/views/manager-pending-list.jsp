<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<body>
	<h3>
		<spring:message code="testing pending page" />
	</h3>
	<c:if test="${fn:length(leaveList) gt 0}">
		<table style="cellspacing: 2; cellpadding: 2; border: 1;">
			<thead>
				<tr class="listHeading">
					<th>Leave ID</th>
					<th>Staff ID</th>
					<th>Leave Type</th>
					<th>Start Date</th>
					<th>End Date</th>
					<th>DisseminationId</th>
					<th>Status</th>
					<th>Was Updated</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="leave_history" items="${leaveList}">
					<tr class="listRecord">
						<td>${leave_history.leaveId}</td>
						<td>${leave_history.staffMember.name}</td>
						<td>${leave_history.leaveTypeModel.leaveName}</td>
						<td>${leave_history.startDate}</td>
						<td>${leave_history.endDate}</td>
						<td>${leave_history.disseminationMember.name}</td>
						<td>${leave_history.status.toString()}</td>
						<td>${leave_history.wasUpdated}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>