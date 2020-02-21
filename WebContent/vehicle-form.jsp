<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Vehicle Application</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: vanilla">
			<div>
				<a href="https://www.javaguides.net" class="navbar-brand"> Vehicle Manage App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Vehicles</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${vehicle != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${vehicle == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${vehicle != null}">
            			Edit Vehicle
            		</c:if>
						<c:if test="${vehicle == null}">
            			Add New Vehicle
            		</c:if>
					</h2>
				</caption>

				<c:if test="${vehicle != null}">
					<input type="hidden" name="id" value="<c:out value='${vehicle.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Year</label> <input type="text"
						value="<c:out value='${vehicle.year}' />" class="form-control"
						name="year" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Make</label> <input type="text"
						value="<c:out value='${vehicle.make}' />" class="form-control"
						name="make">
				</fieldset>

				<fieldset class="form-group">
					<label>Model</label> <input type="text"
						value="<c:out value='${vehicle.model}' />" class="form-control"
						name="model">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
