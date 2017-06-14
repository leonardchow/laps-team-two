
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<body>
	<h1>
		<spring:message code="title.selectEmployee" />
	</h1>

	<c:if test="${fn:length(subordinateList) gt 0}">
		<table style="cellspacing: 2; cellpadding: 2; border: 1;">
			<thead>
				<tr class="listHeading">
					<th><spring:message code="fieldLabel.employeeId" /></th>
					<th><spring:message code="fieldLabel.employeeName" /></th>
					<th><spring:message code="fieldLabel.contactInfo" /></th>
					<th><spring:message code="fieldLabel.email" /></th>
					<th><spring:message code="fieldLabel.homeAddress" /></th>
					<th><spring:message code="fieldLabel.designation" /></th>
					<th><spring:message code="fieldLabel.viewEmployee" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="staff_list" items="${subordinateList}">
					<tr class="listRecord">
						<td>${staff_list.staffId}</td>
						<td>${staff_list.name}</td>
						<td>${staff_list.contactNo}</td>
						<td>${staff_list.email}</td>
						<td>${staff_list.homeAddress}</td>
						<td>${staff_list.designation}</td>
						<td><a
							href="${pageContext.request.contextPath}/manager/subordinate/history/${staff_list.staffId}.html"
							class="btn btn-primary">Detail</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>

