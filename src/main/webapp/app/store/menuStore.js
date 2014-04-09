Ext.define('Demo.store.menuStore',{
	extend:'Ext.data.TreeStore',
	defaultRootId:'root',
	requires:'Demo.model.menuModel',
	model:'Demo.model.menuModel',
	//fields:['id','text','url'],
	proxy:{
		type:'ajax',
		url:ctx+'/treeNode/root',
		reader:'json',
		autoLoad:true
	}

});