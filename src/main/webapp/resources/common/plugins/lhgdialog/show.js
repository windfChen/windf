var flag = "left"; 
function DY_scroll(wraper,prev,next,img,speed,or){ 
var wraper = $(wraper); 
var prev = $(prev); 
var next = $(next); 
var img = $(img).find('ul'); 
var w = img.find('li').innerWidth(true); 
var s = speed; 
next.click(function(){ 
img.animate({'margin-left':-w},function(){ 
img.find('li').eq(0).appendTo(img); 
img.css({'margin-left':0}); 
}); 
flag = "left"; 
}); 
prev.click(function(){ 
img.find('li:last').prependTo(img); 
img.css({'margin-left':-w}); 
img.animate({'margin-left':0}); 
flag = "right"; 
}); 
if (or == true){ 
ad = setInterval(function() { flag == "left" ? next.click() : prev.click()},s*1000); 
wraper.hover(function(){clearInterval(ad);},function(){ad = setInterval(function() {flag == "left" ? next.click() : prev.click()},s*1000);}); 
} 
} 
