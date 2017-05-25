/************************位置*start**********************************/
function Seat(x, y, img) {
	this.x = x;
	this.y = y;
	this.img = img;
}
Seat.prototype = {
	toString: function() {
		return this.x + "_" + this.y;
	}
}
/************************位置*start**********************************/