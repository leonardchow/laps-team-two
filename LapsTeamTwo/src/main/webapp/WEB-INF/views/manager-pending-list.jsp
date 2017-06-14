<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<body>
	<h3>
		<spring:message code="title.appsForApproval" />
	</h3>
	<c:forEach var="entry" items="${pendinghistory}">
		<c:if test="${fn:length(entry.value) gt 0}">
			<br />
			<spring:message code="fieldLabel.name" /> : <c:out value="${entry.key.name}" />
			<br />
			<table style="cellspacing: 2; cellpadding: 2; border: 1;">
				<tr class="listHeading">
					<th><spring:message code="fieldLabel.leaveId" /></th>
					<th><spring:message code="fieldLabel.reason" /></th>
					<th><spring:message code="fieldLabel.startDate" /></th>
					<th><spring:message code="fieldLabel.endDate" /></th>
					<th><spring:message code="fieldLabel.dissemination" /></th>
					<th><spring:message code="fieldLabel.status" /></th>
					<th><spring:message code="fieldLabel.approve" /></th>
				</tr>
				<c:forEach var="leave" items="${entry.value}">
					<tr class="listRecord">
						<td>${leave.leaveId}</td>
						<td>${leave.reason}</td>
						<td>${leave.startDate}</td>
						<td>${leave.endDate}</td>
						<td>${leave.disseminationMember.name}</td>
						<td>${leave.status}</td>
						<td><c:url
								value="/manager/pending/detail/${leave.leaveId}.html" var="d" />
							<a href="${d}"><spring:message code="detail" /></a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</c:forEach>
</body>
</html>