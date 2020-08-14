function getSignupUrl(){
	return "/saas/api/signup";
}

//BUTTON ACTIONS
function addUser(event){
	//Set the values to update
	var $form = $("#signup-form");
	var json = toJson($form);
	var url = getSignupUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		  window.location.replace("/saas/ui/login");
	   },
	   error: handleAjaxError
	});

	return false;
}

function init(){
	$('#signup_submit').click(addUser);
}

$(document).ready(init);