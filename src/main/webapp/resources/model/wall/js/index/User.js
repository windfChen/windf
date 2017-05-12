/************************用户*start**********************************/
function User(username) {
	this.username = username;
}
User.prototype = {
	exists: function() {
		return this.username !== undefined;
	}
}
/************************用户*end**********************************/