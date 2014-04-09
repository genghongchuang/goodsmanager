Ext.define('Demo.model.menuModel',{
	extend:'Ext.data.Model',
	fields:[
		{name:'id',type:'int'},
		{name:'pid',type:'int'},
		{name:'text',type:'string'},
		//布尔类型必须要设默认值
		{name:'leaf',type:'boolean',defaultValue:false},
		{name:'url',type:'string'}
		]
   
});