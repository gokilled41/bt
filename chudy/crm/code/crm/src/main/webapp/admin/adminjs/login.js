$(document).ready(function() {
  initLoginUser();
});
function initLoginUser() {
  var u = $("#login-user").text();
  if (!isEmpty(u)) {
    $("#login-userName").prop('readonly',true);
    $("#login-password").focus();
  } else {
    $("#login-userName").focus();
  }
}

document.onkeydown = function(e) {
  var theEvent = e || window.event;
  var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
  if (code == 13) {
    $("#login_btn").focus();
    document.all('login_btn').click();
    return false;
  }
};
