var windowResize = function(){
	document.documentElement.style.fontSize = document.documentElement.clientWidth >750?(100+ 'px'):(document.documentElement.clientWidth / 7.5 + 'px');
}
window.onresize = windowResize;
//$(document).ready(function(){ 
//
//}); 
windowResize();