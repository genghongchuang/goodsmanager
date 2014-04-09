Ext.define('Demo.view.menuTree',{
	extend:'Ext.tree.Panel',
	alias:'widget.menutree',
	border:false,
	hrefTarget:'mainContent',//链接地址的显示区域
	rootVisible: false,//是否显示根节点
	store:'menuStore',//数据集
	bodyStyle:{//菜单样式
	  background:'#ffc',
	  padding:'10px'
	}

});