<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table>
	<thead>
		<tr>
			<th>LeaveID</th>
			<th>StaffID</th>
			<th>StaffName</th>
			<th>LeaveType</th>
			<th>Reason</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${ leaves }" var="leave">
			<tr>
				<td>${ leave.leaveId }</td>
				<td>${ leave.staffId }</td>
				<td>${ leave.staffMember.name }</td>
				<td>${ leave.leaveType }</td>
				<td>${ leave.reason }</td>
				<td>${ leave.status }</td>
			</tr>
		</c:forEach>
	</tbody>

</table>

${ staffMember.name }