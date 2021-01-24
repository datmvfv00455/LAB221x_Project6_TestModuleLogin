<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resources/common/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">

<head>
<title>First time login</title>

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
									<h1 class="h4 text-gray-900 mb-4">First time login</h1>

									UserID: ${UserIDSession}
								</div>

								<form class="fisttime-form" method="post">
									<div class="col-sm-12">Please answer at least one(1) of
										the hint question below. Maximum question is three(3)
										questions.</div>

									<div class="form-group form-row ">
										<div class="col-sm-4">
											<select name="questionList[0][id]" class="form-control">
												<option value="">Select</option>
												<option value="q01">Question 01</option>
												<option value="q02">Question 02</option>
												<option value="q03">Question 03</option>
												<option value="q04">Question 04</option>
												<option value="q05">Question 05</option>
											</select>
										</div>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="answerQuestionFisrst"
												name="questionList[0][answer]" placeholder="Answer"
												onkeyup="validateUserInput()">
										</div>
									</div>

									<div class="form-group form-row ">
										<div class="col-sm-4">
											<select name="questionList[1][id]" class="form-control">
												<option value="">Select</option>
												<option value="q01">Question 01</option>
												<option value="q02">Question 02</option>
												<option value="q03">Question 03</option>
												<option value="q04">Question 04</option>
												<option value="q05">Question 05</option>
											</select>
										</div>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="answerQuestionSecond"
												name="questionList[1][answer]" placeholder="Answer"
												onkeyup="validateUserInput()">
										</div>
									</div>

									<div class="form-group form-row ">
										<div class="col-sm-4">
											<select name="questionList[2][id]" class="form-control">
												<option value="">Select</option>
												<option value="q01">Question 01</option>
												<option value="q02">Question 02</option>
												<option value="q03">Question 03</option>
												<option value="q04">Question 04</option>
												<option value="q05">Question 05</option>
											</select>
										</div>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="answerQuestionThird"
												name="questionList[2][answer]" placeholder="Answer" 
												onkeyup="validateUserInput()">
										</div>
									</div>
									
									<span class="error-message" id="questionList-errorMsg">UserID</span>

									<hr>

									<div class="col-sm-12">Please key in your old password
										and new passwor. The new password must be different fom the
										old password.</div>

									<div class="form-group form-row ">
										<div class="col-sm-4">Old Password</div>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="oldPassword"
												name="oldPassword" placeholder="Old Password" required
												onkeyup="validateUserInput()">
										</div>
									</div>
									<span class="error-message" id="oldPassword-errorMsg">Old Password Error Msg</span>									
									

									<div class="form-group form-row ">
										<div class="col-sm-4">New Password</div>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="newPassword"
												name="newPassword" placeholder="New Password" required
												onkeyup="validateUserInput()">
										</div>
									</div>
									<span class="error-message" id="newPassword-errorMsg">New Password Error Msg</span>									
									

									<div class="form-group form-row ">
										<div class="col-sm-4">Confirm Password</div>
										<div class="col-sm-8">
											<input type="text" class="form-control"
												id="confirmPassword" name="confirmPassword"
												placeholder="Confirm Password" required
												onkeyup="validateUserInput()">
										</div>
									</div>
									<span class="error-message" id="confirmPassword-errorMsg">Confirm Password Error Msg</span>

									<hr>

									<div class="form-row">
										<div class="col-sm-4">
											<button id="btnSubmit" type="submit" disabled  class="btn btn-primary"
												>Submit</button>
										</div>
										<div class="col-sm-4">
											<button id="btnCancel" type="button" class="btn btn-primary">Cancel</button>
										</div>
										<div class="col-sm-4">
											<button id="btnClear" type="button" disabled  class="btn btn-primary"
												>Clear</button>
										</div>
									</div>
								</form>
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


	<script
		src="<c:url value="/resources/templete/home/js/firsttime-login.js" /> "></script>

	<!-- END FOOTER -->


</body>


</html>