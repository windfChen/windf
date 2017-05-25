$(function () {
	
	var w = 0;
    $(this).mousemove(function (e) {
		
        w = $("#dock").offset().top;
        var mouseY = parseInt(e.pageY);
        if (mouseY < w+90 && mouseY > w) {
            var mouseX = parseInt(e.pageX);
            $("#dock img").each(function () {
                var obj = $(this);
                var objWidth = obj.css("width");
                //获取图片中心水平坐标 
                var objX = parseInt(obj.offset().left) + parseInt(objWidth.substr(0, objWidth.length - 2)) / 2;
                var x = Math.abs(objX - mouseX);
                if (x < 75) {
                    var w = 70 - ((20 * x * x) / (75 * 75));
                    if (w <= 50) {
                        w = 50 + "px";
                    }
                    else {
                        w = w + "px";
                    }
                    obj.css("width", w).css("height", w);
                    obj.css("padding-bottom", (20 - ((78 * x * x) / (75 * 75))) + "px");
					$(this).attr('src', $(this).attr('src_c'));
                } else {
                    obj.css("width", "50px").css("height", "50px");
                    obj.css("padding-bottom", "0");
					$(this).attr('src', $(this).attr('src_old'));
                }
            });
        } else {
            $("#dock img").each(function () {
                $(this).css("width", "50px").css("height", "50px");
                $(this).css("padding-bottom", "0");
				$(this).attr('src', $(this).attr('src_old'));
            });
        }
    });
	
	$("#dock img").each(function () {
		$(this).attr('src_old', $(this).attr('src'));
	});
	
	$("#dock img").click(function () {
		if (CURRENT_SELECT_BLOCK !== undefined) {
			var selectBlock = CURRENT_SELECT_BLOCK;
			CURRENT_SELECT_BLOCK = undefined;
			selectBlock.show('background');
		}
	});
}); 



$(function(){
	if (CURRENT_USER_USERNAME !== '') {
		ShowDock();
	}
});

function ShowDock() {
	$('#dock').show();
}

