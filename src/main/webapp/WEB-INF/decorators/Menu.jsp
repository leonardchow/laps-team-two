q<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<ul class="nav nav-pills nav-stacked">
	<li role="presentation"><a href="#">Manager User</a></li>
	<li role="presentation"><a href="#">Manage Role</a></li>
	<li role="presentation"><spring:url value="/admin/leavetype/list"
			var="ulist" htmlEscape="true" /> <a href="${ulist}"> Manage Leave Type
	</a></li>
	<li role="presentation"><a href="#">Manage Leave Entitlement</a></li>
	<li role="presentation"><a href="#">Manage approval hierarchy</a></li>
	<li role="presentation"><a href="#">Logout</a></li>

</ul>