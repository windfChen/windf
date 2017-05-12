/************************墙*start**********************************/
function Wall(size_x, size_y, center_x, center_y){
	this.size_x = size_x;	// 能展示的屏幕的宽
	this.size_y = size_y;	// 能展示的屏幕的高
	this.center_x = center_x;	// 起始中心点的坐标
	this.center_y = center_y;	// 起始中心点的坐标
	
	this.size = size_x * size_y;
	this.blocks = [];
	this.currentShowBlock = undefined;
	this.div = undefined;
	this.siteMap = [];
	
	return this;
}
Wall.prototype = {
	img_start: BASE_PATH + '/resources/model/wall/images/background/20160207_',
	blockLoadPath: BASE_PATH + '',
	bind: function($div) {
		this.div = $div;
		return this;
	},
	start: function() {
		var start_x = this.center_x - (Math.floor(this.size_x/2) - (1 - this.size_x%2));
		var start_y = this.center_y - (Math.floor(this.size_y/2) - (1 - this.size_y%2));
		var end_x = this.center_x + (Math.floor(this.size_x/2));
		var end_y = this.center_y + (Math.floor(this.size_y/2));
		this.loadBlocks(start_x, start_y, end_x, end_y);
		return this;
	},
	loadBlocks: function(start_x, start_y, end_x, end_y) {
		var obj = this;
		$.ajax({
			async:false,
			url: BASE_PATH + '/wall/getBlocksByRange',
			type: "GET",
			dataType: 'json',
			data: {startX:start_x ,startY:start_y ,endX:end_x ,endY:end_y },
			timeout: 5000,
			success: function (json) {
				if(json.success=='Y'){
					var blocks = json.data;
					for (var i = 0; i < blocks.length; i++) {
						var block = blocks[i];
						if (obj.siteMap[block.x] === undefined) {
							obj.siteMap[block.x] = [];
						}
						obj.siteMap[block.x][block.y] = block;
					}
				} else {
					alert(json.top)
				}
			}
		});

		for (var i = start_x; i <= end_x; i++) {
			for(var j = start_y; j <= end_y; j++) {
				var s = new Seat(i, j, this.img_start + i + '_' + j + '.gif');
				var o = new User();
				if (this.siteMap[i] !== undefined && this.siteMap[i][j] !== undefined && this.siteMap[i][j].owner != 'null') {
					o.username = this.siteMap[i][j].owner.username;
				}
				var b = new Block(s, o);
				if (this.blocks[i] === undefined) {
					this.blocks[i] = [];
				}
				this.blocks[i][j] = b;
				this.div.append(b.getHTML(this));
			}
		}
	},
	getCurrentShowBlock: function(){
		var result = this.currentShowBlock;
		if (result === undefined) {
			result = new Block();
		}
		return result;
	},
	getBlock: function(x, y) {
		var resutl = blocks[x][y];
		if (resutl === undefined) {
			resutl = new Block();
		}
		return resutl;
	},
	show: function(block) {
		if (this.getCurrentShowBlock() === block) {
			return ;
		}
		
		if (this.getCurrentShowBlock().exists()) {
			this.getCurrentShowBlock().hide();
		}
		
		this.currentShowBlock = block;
		if (this.currentShowBlock.exists()) {
			this.currentShowBlock.show();
		}
		
	}
}
/************************墙*end**********************************/