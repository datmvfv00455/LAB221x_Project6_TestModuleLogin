<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resources/common/taglib.jsp"%>
 
<!DOCTYPE html>
<html lang="en">

<head>
<title>Member Login</title>

<!-- Custom fonts for this template-->
<link
	href="<c:url value='/resources/templete/home/vendor/fontawesome-free/css/all.min.css'/>"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<link
	href="<c:url value='/resources/templete/home/css/sb-admin-2.min.css'/>"
	rel="stylesheet">
</head>

<!-- Custom styles for this template-->
<link href="<c:url value='/resources/templete/home/css/style.css'/>"
	rel="stylesheet">
</head>

<body class="bg-gradient-primary ">
	<div class="container centered" style="max-width: 700px;">
		<!-- Outer Row -->
		<div class="row justify-content-center">
			<div class="col-12">
				<div class="card o-hidden border-0 shadow-lg my-5">
					<!-- Nested Row within Card Body -->
					<div class="row">
						<div class="col-12">

							<div class="p-5">
								<div class="text-center">
									<h1 class="h4 text-gray-900 mb-4">Member Login</h1>
								</div>


								<form class="login-form user" method="post">
									<div class="form-group ">
										<input type="text" class="form-control form-control-user"
											name="id" id="userId"
											placeholder="UserID" required
											onkeyup="validateUserInput()">
									</div>

									<span class="error-message" id="userId-errorMsg">UserID</span>

									<div class="form-group">
										<input type="password" class="form-control form-control-user"
											id="userPassword" name="password"
											placeholder="Password" required
											onkeyup="validateUserInput()">
									</div>

									<span class="error-message" id="userPassword-errorMsg">Password Error Message</span>

									<div class="form-group">
										<button class="btn btn-primary btn-user btn-block "
											id="btnSubmit" type="submit">Submit</button>

										<button class="btn btn-primary  btn-user btn-block" disabled
											id="btnClear" type="button">Clear</button>
									</div>
								</form>


								<hr>
								<a href="#">Forgot Password</a>
								
							</div>
						</div>

					</div>


				</div>
			</div>
		</div>
	</div>

	<!-- BEGIN FOOTER -->
	<%@include file="/resources/common/footer.jsp"%>


	<script type="text/javascript"
		src="<c:url value="/resources/templete/home/js/jquery.serializejson.js" /> "></script>


	<script src="<c:url value="/resources/templete/home/js/login.js" /> "></script>

	<!-- END FOOTER -->


</body>


</html>