<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h1>Subordinate List History Detail Page 2</h1>

<form:form method="POST" modelAttribute="staffMember"
	action="${pageContext.request.contextPath}/manager/subordinate/LeaveHistory/Details/${staffMember.staffId}.html">

	<h2>Staff Name: ${staffMember.name}</h2>
	<c:if test="${fn:length(leaveHistoryList) gt 0}">
		<table class="table table-hover">
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
					<th>details</th>
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
						<td>${leave_history.wasUpdated}</td>
						<td><a
							href="${pageContext.request.contextPath}/manager/subordinate/history//detail/${leave_history.leaveId}.html">Detail</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</form:form>
