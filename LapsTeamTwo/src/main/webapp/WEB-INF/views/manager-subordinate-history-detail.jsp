<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<body>
	<h1>
		<spring:message code="title.leaveDetailsHistory" />
	</h1>

	<c:if test="${fn:length(subordinateLeave) gt 0}">

		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Staff ID</th>
						<th>Staff Name</th>
						<th>Contact No</th>
						<th>Email</th>
						<th>Home Address</th>
						<th>Designation</th>
						<th>View For Subordinate Leave History</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="staff_list" items="${subordinateLeave}">
						<tr class="listRecord">
							<td>${staff_list.staffId}</td>
							<td>${staff_list.name}</td>
							<td>${staff_list.contactNo}</td>
							<td>${staff_list.email}</td>
							<td>${staff_list.homeAddress}</td>
							<td>${staff_list.designation}</td>
							<td><a
								href="${pageContext.request.contextPath}/manager/subordinate/LeaveHistory/Details/${staff_list.staffId}.html"
								class="btn btn-success">Detail</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</c:if>
</body>
