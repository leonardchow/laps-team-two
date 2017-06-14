
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<h3>Employee List</h3>
<br>

<c:if test="${fn:length(stafflist) gt 0}">

	<div class="table-responsive">
		
		<input type="text" id="myInput" onkeyup="StaffSearch()"
		placeholder="Search for Staff Name..." title="Type in a name">
		<table id="staffTable" class="table table-hover">
			<thead>
				<tr>
					<th>Staff ID</th>
					<th>Staff Name</th>
					<th>Contact No</th>
					<th>Email</th>
					<th>Home Address</th>
					<th>Designation</th>
					<th>Manager ID</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="employee" items="${stafflist}">
					<tr>
						<td>${employee.staffId}</td>
						<td>${employee.name}</td>
						<td>${employee.contactNo}</td>
						<td>${employee.email}</td>
						<td>${employee.homeAddress}</td>
						<td>${employee.designation}</td>
						<td>${employee.managerId}</td>


						<td><a
							href="${pageContext.request.contextPath}/admin/staff/edit/${employee.staffId}.html"
							class="btn btn-success">Edit</a></td>
						<td><a
							href="${pageContext.request.contextPath}/admin/staff/delete/${employee.staffId}.html"
							class="btn btn-danger">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
	</div>
</c:if>

<a class="btn btn-primary"
	href="${pageContext.request.contextPath}/admin/staff/create">Add
	New Employee</a>
	
	<script>
			function StaffSearch() {
				var input, filter, table, tr, td, i;
				input = document.getElementById("myInput");
				filter = input.value.toUpperCase();
				table = document.getElementById("staffTable");
				tr = table.getElementsByTagName("tr");
				for (i = 0; i < tr.length; i++) {
					td = tr[i].getElementsByTagName("td")[1];
					if (td) {
						if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
							tr[i].style.display = "";
						} else {
							tr[i].style.display = "none";
						}
					}
				}
			}
		</script>