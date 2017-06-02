function pageJumpInputFocus(obj) {
	$(obj).parent().addClass('pagin-jump-focus').removeClass('pagin-jump');
}
function pageJumpInputBlur(obj) {
	if ($(obj).val().length == 0) {
		$(obj).parent().addClass('pagin-jump').removeClass('pagin-jump-focus');
	}
}
function pageJumpInputKeyUp(obj) {
	if (event.keyCode == "13") {
		$('#pagin_jump_btn').click();
	}
}
function pageJump(pageIndex, currentIndex, indexTotal) {
	if (!pageIndex) {
		return false;
	}
	if (pageIndex < 1) {
		pageIndex = 1;
	}
	if (pageIndex > indexTotal) {
		pageIndex = indexTotal;
	}
	
	if (currentIndex == pageIndex) {
		return false;
	}
	
	onPageJump(pageIndex);
}