Ext.define('Demo.controller.demoController',{
	extend:'Ext.app.Controller',
	//��Viewport.js��ӵ�������
	views:['Viewport','menuTree','contextMenu','AddNode','EditNode','MainContent','Header'],
    stores:['menuStore'],
   //  models:['menuModel'],
    //ͨ��init������������ͼ�¼���������ͼ��������Ľ���
	init: function() {
		Ext.get('loading-msg').update('加载菜单资源...');
		this.control({
			'menutree': {
				itemclick: this.chagePage ,//����������¼�����������Ӧ����
				itemcontextmenu:this.contextMenu //��������Ҽ��¼�
			}
			
		});
		Ext.get('loading-msg').update('加载完成.');
		Ext.Function.defer(function() {
					Ext.get('loading-tip').remove();
				}, 1000);
		var headerTime=Ext.getCmp('headerTime');
	//	console.log(headerTime);
		var task ={
				run:function(){
					//alert(111);
					headerTime.setText(Ext.util.Format.date(new Date(),'Y年m月d日 A g:i:s'));	
				},
				interval:1000
			}
		Ext.TaskManager.start(task);
	},
	chagePage: function(view, rec, item, index, e) {
		
		var url=rec.get('url');
		
		var title=rec.get('text');
		
		//alert(url);
		Ext.getDom('contentIframe').src=url;
		Ext.getCmp('mainContent').removeAll();
		Ext.getCmp('mainContent').setTitle(title);
		//alert('success');
	},
	contextMenu:function(tree, record, item, index, e, eOpts){
	//	console.log(record.data.id);
		var me=this;
		e.preventDefault();
		e.stopEvent();
		var menuView=Ext.getCmp('contextMenu');//排除contextMenu已存在导致显示异常
		if(menuView!=null){
			menuView.close();
		}
		
		//��ֹ�����Ĭ���Ҽ��¼�
	//	console.log(menuView);
		 menuView=Ext.widget('contextmenu');
	//	console.log(menuView);
		Ext.each(menuView.items.items,function(item){
		//	console.log(item);
		 item.on('click',me.clickHandler,me,record.data);
		});
		//menuView.items.items[0].on('click',this.clickHandler,me);
		menuView.showAt(e.getXY());
		
	},

	clickHandler:function(item,me,data){
		//console.log(data);
		var action=item.action;
		//console.log(action);
		var content=Ext.getCmp('mainContent');
		if(action=='add'){
			//console.log(Ext.getCmp('menutree').child());
			//if()
			var addNode=Ext.widget('addnode');
			//console.log(addNode);
		//设值父节点的名称
		addNode.getForm().findField('pname').setValue(data.text);
		addNode.getForm().findField('pid').setValue(data.id);
		//Ext.getCmp('pid').setValue(data.text);	
		//console.log(Ext.getCmp('pid').setValue("aa"));	
		var tab=content.add(addNode);
		content.setActiveTab(tab);
		var menuView=Ext.getCmp('contextMenu');
		menuView.close();
		//console.log(menuView);
		}else if(action=='edit'){
			
			//Ext.Msg.alert("更新");
		var editNode=Ext.widget('editnode');
		console.log(11);
			//console.log(addNode);
		//设值父节点的名称
		editNode.getForm().findField('text').setValue(data.text);
		editNode.getForm().findField('url').setValue(data.url);
		editNode.getForm().findField('iconCls').setValue(data.iconCls);
		editNode.getForm().findField('id').setValue(data.id);
		//Ext.getCmp('pid').setValue(data.text);	
		//console.log(Ext.getCmp('pid').setValue("aa"));	
		var tab=content.add(editNode);
		content.setActiveTab(tab);
		var menuView=Ext.getCmp('contextMenu');
		menuView.close();
		}else if(action=='del'){
			Ext.Msg.alert("删除");
			  Ext.Ajax.request({
	                url: 'treeNode/deleteNode',
	                
	                params: { id:data.id  },
	                method: 'POST',
	                success: function (response, options) {
	                	var json = eval('(' + response.responseText + ')');
	                	
	                    Ext.MessageBox.alert("操作结果", json.msg);
	                },
	                failure: function (response, options) {
	                    Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
	                }
	            });
			
			var menuView=Ext.getCmp('contextMenu');
			menuView.close();
			
		}else if(action=='cancle'){
			var menuView=Ext.getCmp('contextMenu');
			menuView.close();
		}
		
		
		
	}
	
   // models:['menuModel']
    

});