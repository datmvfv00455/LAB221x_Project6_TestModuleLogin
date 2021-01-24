$(document).ready(function () {
	loginFormSubmit();

	validateUserInput();

	$("#btnClear").click(function () {
		$('#userId').val("");
		$('#userPassword').val("");
		$('#btnClear').attr("disabled", true);
		$('#btnSubmit').attr("disabled", true);
	});
});

function loginFormSubmit() {

	$('.login-form').submit(function (e) {
		e.preventDefault();
		let dataObj = $(this).serializeJSON();
		
		console.log(JSON.stringify(dataObj));
		
		$.ajax({
			type: "POST",
			url: "login",
			contentType: "application/json",
			dataType: "json",
			data: JSON.stringify(dataObj),
			success: function (response) {							
				console.log(response); 

				if (!response.valid) {	
					displayErrorFields(response.errorFields);			
				}else{							 
					$("body").fadeOut(1000, function () {
						window.location = response.redirectUrl;
					});		
					
					$("container").hide();
					//$("body").fadeIn(1000).delay(15000);			 				 
				}
				
				if (response.messege != null) {
					insertErrorMsg(response.messege);
					displayErrorFields(response.errorFields);	
				} else {
					$(".alert-message").hide();					
				}
			}
		});
	});
}


function displayErrorFields(errorFields){
	if(errorFields != null){
		for (let error of errorFields) { 
			console.log(error.field + ": " + error.message);	
			let errorMsgSpan = '#' + error.field+'-errorMsg';
			if(error.field != null){								
				$(errorMsgSpan).text(error.message).css("display", "block");
			}else{
				$(errorMsgSpan).css("display", "none");
			}												
		}	
	}else{
		$(".error-message").css("display", "none");
	}	
}

function validateUserInput() {

	var isEmptyUserID = isEmpty($('#userId').val());
	var isEmptyUserPassword = isEmpty($('#userPassword').val());

	if (isEmptyUserID || isEmptyUserPassword) {
		$('#btnClear').attr("disabled", true);
		$('#btnSubmit').attr("disabled", true);
	} else {
		$('#btnClear').removeAttr("disabled");
		$('#btnSubmit').removeAttr("disabled");
	}

}

const isEmpty = str => !str.trim().length;

function insertErrorMsg(msg) {
	if (!$(".alert-message").is(":visible")) {
		var html = $("<div>").addClass("alert alert-block alert-danger alert-message")		 
			.append(msg);
		$(html).insertBefore($(".login-form"));
	}else{		
		$(".alert-message").text(msg);
	}	
}


