Ext.define('Demo.model.menuModel',{
	extend:'Ext.data.Model',
	fields:[
		{name:'id',type:'int'},
		{name:'pid',type:'int'},
		{name:'text',type:'string'},
		//�������ͱ���Ҫ��Ĭ��ֵ
		{name:'leaf',type:'boolean',defaultValue:false},
		{name:'url',type:'string'}
		]
   
});