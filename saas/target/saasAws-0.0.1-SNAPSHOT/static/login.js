function getLoginUrl(){
	return "/saas/api/login";
}

//BUTTON ACTIONS
function doLogin(event){
	//Set the values to update
	var $form = $("#login-form");
	var json = toJson($form);
	var url = getLoginUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		  localStorage.setItem("login_username", response.username);
	   		  localStorage.setItem("login_email", response.email);
	   		  window.location.replace("/saas"); 
	   },
	   error: handleAjaxError
	});

	return false;
}

function init(){
	$('#login_submit').click(doLogin);
}

$(document).ready(init);
