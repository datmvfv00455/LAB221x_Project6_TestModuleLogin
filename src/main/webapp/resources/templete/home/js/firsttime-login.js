$(document).ready(function () {

	hideFormSelectOption();

	btnCancelClick();

	btnClearClick();

	fisttimeFormSubmit();

});

const isEmpty = str => !str.trim().length;

function validateUserInput() {

	var isEmptyAnswerQuestionFirst = isEmpty($('#answerQuestionFisrst').val());
	var isEmptyAnswerQuestionSecond = isEmpty($('#answerQuestionSecond').val());
	var isEmptyAnswerQuestionThird = isEmpty($('#answerQuestionThird').val());
	var isEmptyOldPassword = isEmpty($('#oldPassword').val());
	var isEmptyNewPassword = isEmpty($('#newPassword').val());
	var isEmptyConfirmNewPassword = isEmpty($('#confirmPassword').val());

	var isEmptyAnswer = isEmptyAnswerQuestionFirst
		&& isEmptyAnswerQuestionSecond
		&& isEmptyAnswerQuestionThird;

	if (isEmptyAnswer
		|| isEmptyOldPassword
		|| isEmptyNewPassword
		|| isEmptyConfirmNewPassword) {
		$('#btnClear').removeAttr("disabled");		
		$('#btnSubmit').attr("disabled", true);
	} else {
		$('#btnSubmit').removeAttr("disabled");			
	}
}



function fisttimeFormSubmit() {
	$('.fisttime-form').submit(function (e) {
		e.preventDefault();

		let dataObj = $(this).serializeJSON();
		console.log(dataObj);

		var questionArr = [];
		Object.keys(dataObj.questionList).forEach(key => {
			let value = dataObj.questionList[key];
			let answerId = value.id;
			let answerInput = value.answer;
			if (answerId !== "" && answerInput !== "") {
				let question = {
					id: value.id,
					answer: value.answer
				};
				questionArr.push(question);
			}
		});

		dataObj.questionList = questionArr;

		var jsonData = JSON.stringify(dataObj);

		console.log(jsonData);

		$.ajax({
			type: "POST",
			url: "fisttime-login",
			contentType: "application/json",
			dataType: "json",
			data: jsonData,
			success: function (response) {
				console.log(response);

				if (!response.valid) {
					 displayErrorFields(response.errorFields);
				} else {
					$("body").fadeOut(1000, function () {
						window.location = response.redirectUrl;
					});		
					
					$("container").hide();
				//	$("body").fadeIn(1000).delay(15000);			 				 
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


function btnClearClick() {
 

	$("#btnClear").click(function () {
		$('#answerQuestionFisrst').val("");
		$('#answerQuestionSecond').val("");
		$('#answerQuestionThird').val("");
		$('#oldPassword').val("");
		$('#newPassword').val("");
		$('#confirmPassword').val("");
		$('#btnClear').attr("disabled", true);
		$('#btnSubmit').attr("disabled", true);
	});
}


function btnCancelClick() {

	$("#btnCancel").click(function () {

	/*	let dataObj = $('.fisttime-form').serializeJSON();
		console.log(dataObj);

		var questionArr = [];
		Object.keys(dataObj.questionList).forEach(key => {
			let value = dataObj.questionList[key];
			let answerId = value.id;
			let answerInput = value.answer;

			console.log(answerId)
			console.log(answerInput)
 

			if (answerId !== "" && answerInput !== "") {
				let question = {
					id: value.id,
					answer: value.answer
				};
				questionArr.push(question);
			}
		});
		dataObj.questionList = questionArr;

		var jsonData = JSON.stringify(dataObj);

		console.log(jsonData);*/
		
		 window.location = "logout";

	});

}


function hideFormSelectOption() {

	let selecter = 'select';

	$(selecter).on('change', function (event) {
		// restore previously selected value
		var prevValue = $(this).data('previous');
		$(selecter).not(this).find('option[value="' + prevValue + '"]').show();
		// hide option selected
		var value = $(this).val();
		// update previously selected data
		$(this).data('previous', value);
		if (value !== "") {
			$(selecter).not(this).find('option[value="' + value + '"]').hide();
		}
	});
}

function inputTagError(id, msg) {
	console.log(id + " : " + msg);

	$(".alert-message").remove();

	if (msg == "OK") {
		$(id).children(".error-message").remove();
		$(id).children("input").removeAttr("style");
		$(id).children("input").focusin(function () {
			$(this).css("box-shadow", "0 0 0 .2rem rgba(78, 115, 223, .25)");
		});
	} else {
		if (!$(id).children("div").is(":visible")) {
			var html = $('<div>')
				.addClass("error-message")
				.append($('<i>').addClass("fas fa-exclamation-circle"))
				.append(" " + msg);

			$(id).append(html);
		}

		$(id).find('input')
			.attr("border-color", "red")

			.focusin(function () {
				$(this).css("box-shadow", "0 0 0 .1rem red");
			}).focusout(function () {
				$(this).css("box-shadow", "");
			});

	}
}