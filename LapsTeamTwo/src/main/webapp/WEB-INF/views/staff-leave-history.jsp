<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<body>
	<h3>
		Employee Leave History
	</h3>
<%-- 	<c:if test="${fn:length(lhistory) gt 0}"> --%>
		<table class="table table-hover">
			<tr >
			
			</tr>
			<c:forEach var="leave" items="${lhistory}">
				<tr >
					<td>${leave.leaveId}</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<c:if
						test="${leave.status eq 'SUBMITTED' || leave.status eq 'UPDATED' }">
						<td><a
							href="${pageContext.request.contextPath}/staff/leave/edit/${course.courseId}.html"><spring:message
									code="fieldLabel.update" /></a></td>
						<td><a
							href="${pageContext.request.contextPath}/staff/leave/withdraw/${course.courseId}.html"><spring:message
									code="fieldLabel.withdraw" /></a></td>
					</c:if>
					<c:if
						test="${course.status eq 'WITHDRAWN' || course.status eq 'APPROVED' ||course.status eq 'REJECTED' }">
						<td></td>
						<td></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
<%-- 	</c:if> --%>
</body>
</html>