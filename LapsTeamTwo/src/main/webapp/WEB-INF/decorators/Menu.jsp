<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${sessionScope.USERSESSION.sessionId ne null}">

<ul class="nav nav-pills nav-stacked">

	<c:choose>
		<c:when test="${sessionScope.USERSESSION.user.isAdmin eq true }">

			<li role="presentation"><spring:url value="/admin/user/list"
					var="ulist" htmlEscape="true" /><a href="${ulist}">Manage User</a></li>

			<li role="presentation"><spring:url value="/admin/leavetype/list"
					var="ulist" htmlEscape="true" /><a href="${ulist}">Manage
					Leave Type</a></li>

			<li role="presentation"><spring:url value="/admin/staff/list"
					var="ulist" htmlEscape="true" /><a href="${ulist}">Manage
					Employee</a></li>
			<li role="presentation"><spring:url value="/admin/leave/list"
					var="ulist" htmlEscape="true" /><a href="${ulist}">Manage
					Leave Entitlement</a></li>


		</c:when>
		<c:when test="${sessionScope.USERSESSION.user.isManager eq true }">
			<li role="presentation"><spring:url value="/admin/user/list"
					var="ulist" htmlEscape="true" /><a href="${ulist}">View Leave
					Application For Approval</a></li>

			<li role="presentation"><spring:url value="/admin/user/list"
					var="ulist" htmlEscape="true" /><a href="${ulist}">Subordinate
					Leave History</a></li>


		</c:when>
		<c:when test="${sessionScope.USERSESSION.user.isStaff eq true }">
			<li role="presentation"><spring:url value="/admin/user/list"
					var="ulist" htmlEscape="true" /><a href="${ulist}">Apply Leave</a></li>

			<li role="presentation"><spring:url value="/admin/user/list"
					var="ulist" htmlEscape="true" /><a href="${ulist}">View Leave
					History</a></li>


		</c:when>
	</c:choose>
	<li role="presentation"><spring:url value="/staff/logout"
			var="logout" htmlEscape="true" /> <a href="${logout}"> Logout </a></li>

</ul>

</c:if>