function isEmpty(src) { //判断为空
  if(("undefined" == typeof src)  || (src == null) || ($.trim(src) == "") ) {
    return true;
  }
  return false;
}
function notEmpty(src) {//判断不为空
  return !isEmpty(src);
}
