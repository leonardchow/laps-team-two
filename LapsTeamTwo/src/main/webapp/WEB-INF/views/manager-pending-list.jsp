<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

	<h3>
		<spring:message code="Leaves for Approval" />
	</h3>
	<c:forEach var="entry" items="${pendinghistory}">
		<c:if test="${fn:length(entry.value) gt 0}">
			<br />
			<spring:message code="name" /> : <c:out value="${entry.key.name}" />
			<br />
			<table class="table table-hover" >
				<tr class="listHeading">
					<th><spring:message code="reference" /></th>
					<th><spring:message code="courseName" /></th>
					<th><spring:message code="startDate" /></th>
					<th><spring:message code="endDate" /></th>
					<th><spring:message code="fees" /></th>
					<th><spring:message code="status" /></th>
					<th><spring:message code="courseDetails" /></th>
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


