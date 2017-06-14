<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<spring:url value="/css/simple.css" var="style" />
<link rel="STYLESHEET" type="text/css" href="${style}" />
<form:form modelAttribute="user" method="POST"
	action="${pageContext.request.contextPath}/home/authenticate">






	<div class="panel panel-primary">
		<div class="panel-heading">Log In</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<table>
						<tr>
							<td>User ID</td>
							<td colspan="3"><form:input path="userId" size="40"
									class="form-control" /></td>
						</tr>
						<tr>
							<td>Password</td>
							<td colspan="3"><form:password path="password" size="40"
									class="form-control" /></td>
						</tr>

						<tr>
							<td></td>
							<td><form:button name="submit" type="submit" value="s"
									class="btn btn-primary">
									Login
								</form:button>
							</td>
						</tr>

					</table>
				</div>

			</div>




		</div>
	</div>






</form:form>
</html>