/*Ext.application({
  name:'myApp',
  launch:function(){    //��ҳ�������ɺ�ִ�еķ���
  	Ext.create("Ext.container.Viewport",{
  		
  	  layout:'fit',
  	  items:[
  	  {
  	  	title:'hello Ext MVC',
  	  	html:'hello!! mvc!!'
  	   }
  	  ]
  		
  	});
  	
  }
});*/
Ext.onReady(function (){
//	Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
	Ext.QuickTips.init();
	Ext.Loader.setConfig({
	  enabled:'true'
	});
	Ext.Ajax.on('requestexception', function(conn, response, options) {
			var msg = '访问系统资源时发生异常<br/>' + '异常状态:' + response.status + '('
					+ response.statusText + ')<br/>' + '异常信息:'
					+ response.responseText
			Ext.Msg.show({
						title : '系统异常',
						msg : msg,
						width : 400,
						icon : Ext.MessageBox.ERROR,
						buttonText : {
							ok : '&nbsp;&nbsp;提交错误报告&nbsp;&nbsp;'
						},
						buttons : Ext.MessageBox.OKCANCEL
					});
		});
Ext.get('loading-msg').update('加载系统组件...');
	Ext.application({
		name:'Demo',//����ռ�
		appFolder:'app',
	//	stores: ['menuStore'],//����ռ��µ�store��controller
		controllers:['demoController'],
		 //�Զ����غ�ʵ��Viewport�ļ�
         autoCreateViewport: true
	});
 //  var store=Ext.create('Demo.store.menuStore');
  // alert(store);

}
)
