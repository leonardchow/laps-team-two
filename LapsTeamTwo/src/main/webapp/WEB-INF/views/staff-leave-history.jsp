<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>Employee Leave History</h3>
	<c:if test="${fn:length(lhistory) gt 0}">
	    <div class="table-responsive">
			<table class="table table-hover ">
		<thead>
			<tr>
				
				<th><spring:message code="Leave ID" /></th>
				<th><spring:message code="Leave Type" /></th>
				<th><spring:message code="Start Date" /></th>
				<th><spring:message code="End Date" /></th>
				<th><spring:message code="Status" /></th>
				<th><spring:message code="Details" /></th>
				<th><spring:message code="Update" /></th>
				<th><spring:message code="Cancel/Delete" /></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="leave" items="${lhistory}">
				<tr >
					<td>${leave.leaveId}</td>
					<td>${leave.leaveTypeModel.leaveName}</td>
					<td>${leave.startDate}</td>
					<td>${leave.endDate}</td>
					<td>${leave.status}</td>
					<c:if
						test="${leave.status eq 'PENDING'|| leave.status eq 'UPDATED'}">
						<td><a
							href="${pageContext.request.contextPath}/staff/history/details/${leave.leaveId}.html" class="btn btn-success"><spring:message
									code="details"/></a></td>
						<td><a
							href="${pageContext.request.contextPath}/staff/history/update/${leave.leaveId}.html" class="btn btn-primary"><spring:message
									code="update"/></a></td>
						<td><a
							href="${pageContext.request.contextPath}/staff/history/delete/${leave.leaveId}.html" class="btn btn-danger"><spring:message
									code="delete"/></a></td>
					</c:if>
					<c:if
						test="${leave.status eq 'APPROVED'}">
						<td><a
							href="${pageContext.request.contextPath}/staff/history/details/${leave.leaveId}.html" class="btn btn-success"><spring:message
									code="details"/></a></td>
						<td></td>
						<td><a
							href="${pageContext.request.contextPath}/staff/history/cancel/${leave.leaveId}.html" class="btn btn-danger"><spring:message
									code="cancel"/></a></td>
					</c:if>
					<c:if
						test="${leave.status eq 'REJECTED'|| leave.status eq 'CANCELLED'|| leave.status eq 'DELETED'}">
						<td><a
							href="${pageContext.request.contextPath}/staff/history/details/${leave.leaveId}.html" class="btn btn-success"><spring:message
									code="details"/></a></td>
						<td></td>
						<td></td>
					</c:if>
				 </tr>
			</c:forEach>
			</tbody>
			</table>
		</div>
		</c:if>
</body>
</html>