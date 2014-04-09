function signIn(){
	Ext.define('Demo.model.menuModel',{
		extend:'Ext.data.Model',
		fields:[
			{name:'id',type:'int'},
			{name:'pid',type:'int'},
			{name:'text',type:'string'},
			//�������ͱ���Ҫ��Ĭ��ֵ
			{name:'leaf',type:'boolean',defaultValue:false},
			{name:'url',type:'string'},
			{name:'iconCls',type:'string'}
			]
	   
	});
	var treestore = Ext.create('Ext.data.TreeStore', {
		defaultRootId:'root',
		requires:'Demo.model.menuModel',
		model:'Demo.model.menuModel',
		//fields:['id','text','url'],
		proxy:{
			type:'ajax',
			url:ctx+'/system/signIn/getSignIns',
			reader:'json',
			autoLoad:true
		}
	});
//	Ext.getCmp('mainContent').removeAll();
	/*var signInpPanel=Ext.create('Ext.panel.Panel', {
	    title: '签到',
	    iconCls : 'icon-news',
	    closable : true ,
	    width: 200,
	    items: [{
	        xtype: 'datefield',
	        fieldLabel: 'Start date'
	    }, {
	        xtype: 'datefield',
	        fieldLabel: 'End date'
	    }],
	    renderTo: Ext.getDom('mainContent')
	});*/
	var win=Ext.getCmp('accwin');
	//console.log(win);
	if(win==null){
		 win = Ext.create('Ext.window.Window',{
	         id: 'accwin',
	         title: '签到',
	         width:250,
	         height:400,
	         x:Ext.getBody().getWidth()-255,
	         y:155,
	         iconCls: 'accordion',
	         shim:false,
	         animCollapse:false,
	         constrainHeader:true,

	         tbar:[{
	             tooltip:{title:'重连', text:'长时间未刷新，可能会断开连接'},
	             iconCls:'icon-sys-config'
	         },'-',{
	             tooltip:'签到',
	             iconCls:'user-add',
	             handler:function(){
	            	 Ext.getCmp('signForm').show();
	             }
	         },' ',{
	             tooltip:'下班',
	             iconCls:'icon-status-away',
	             handler:function(){
	            	 Ext.getCmp('exitForm').show();
	             }
	         }],

	         layout:'accordion',
	         border:false,
	         layoutConfig: {
	             animate:false
	         },

	         items: [{
	        	 id:'signForm',
	        	 xtype:'form',
	        	 title:'签到登录',
	        	 autoScroll:true,
	        	 closable:true,
	        	 closeAction:'hide',
	        	 bodyPadding: 5, 
	        //	 height:10,
	        	 buttonAlign:'center',
	        	 hidden:true,
	        	 fieldDefaults:{
						labelAlign:'center',
						//宽度
						labelWidth:90,
						//anchor: '90%',
						//错误提示显示在一边(side)，还可以配置为under、title、none
						msgTarget: 'side'
					},
	        	 items:[{
	        		   xtype:'textfield',
			    	   fieldLabel:'用户名',		    	  
			    	   name:'userName',
			    	   allowBlank:false,
			    	   blankText:'用户名不能为空'
	        	 },{
	        		 xtype:'textfield',
			    	   fieldLabel:'密    码',
			    	   name:'password',
			    	   inputType:'password',
			    	   allowBlank:false,
			    	   blankText:'密码不能为空'
	        	 }],
	        	 
	        	 buttons:[
					         {  				    
					        	 text:'签到',
	        	                 handler:function(){
					        		 //获取当前的表单form
					        		 var form = this.up('form').getForm();
					        		 //判断否通过了表单验证，如果不能空的为空则不能提交
					        		 if(form.isValid()){				
					        			 form.submit(
					        					 {
					        						 clientValidation:true,
					        						 waitMsg:'请稍候',
					        						 waitTitle:'正在验证登录',
					        						 url:'system/signIn/toSignIn',
					        						 success:function(form,action){
					        							 Ext.MessageBox.show({					
					                                         width:150,
					                                         title:"签到成功",
					                                         buttons: Ext.MessageBox.OK,
					                                         msg:action.result.msg
					                                     })
					                                     form.reset();
					        							 var tree=Ext.getCmp('im-tree');
					        	                         tree.store.reload();

					        							 //登录成功后的操作，跳转到toIndex.action
					        							 //window.location.href = '${ctx}' 
					        						 },
					        						 failure:function(form,action){
					        							// updateCode();
					        							 Ext.MessageBox.show({					
					                                         width:150,
					                                         title:"登录失败",
					                                         buttons: Ext.MessageBox.OK,
					                                         msg:action.result.msg
					                                     })
					        						 }
					        					 		
					        					 }
					        			 )
					        		 }
					        	 }
					         }
	         ]
	             },
	             {
	            	 id:'exitForm',
	            	 xtype:'form',
	            	 title:'下班',
	            	 autoScroll:true,
	            	 closable:true,
	            	 closeAction:'hide',
	            	 bodyPadding: 5, 
	            //	 height:10,
	            	 buttonAlign:'center',
	            	 hidden:true,
	            	 fieldDefaults:{
	    					labelAlign:'center',
	    					//宽度
	    					labelWidth:90,
	    					//anchor: '90%',
	    					//错误提示显示在一边(side)，还可以配置为under、title、none
	    					msgTarget: 'side'
	    				},
	            	 items:[{
	            		   xtype:'textfield',
	    		    	   fieldLabel:'用户名',		    	  
	    		    	   name:'userName',
	    		    	   allowBlank:false,
	    		    	   blankText:'用户名不能为空'
	            	 },{
	            		 xtype:'textfield',
	    		    	   fieldLabel:'密    码',
	    		    	   name:'password',
	    		    	   inputType:'password',
	    		    	   allowBlank:false,
	    		    	   blankText:'密码不能为空'
	            	 }],
	            	 
	            	 buttons:[
	    				         {  				    
	    				        	 text:'下班',
	            	                 handler:function(){
	    				        		 //获取当前的表单form
	    				        		 var form = this.up('form').getForm();
	    				        		 //判断否通过了表单验证，如果不能空的为空则不能提交
	    				        		 if(form.isValid()){				
	    				        			 form.submit(
	    				        					 {
	    				        						 clientValidation:true,
	    				        						 waitMsg:'请稍候',
	    				        						 waitTitle:'正在验证登录',
	    				        						 url:'system/signIn/toSignOut',
	    				        						 success:function(form,action){
	    				        							 Ext.MessageBox.show({					
	    				                                         width:150,
	    				                                         title:"下班",
	    				                                         buttons: Ext.MessageBox.OK,
	    				                                         msg:action.result.msg
	    				                                     })
	    				                                     form.reset();
	    				        							 var tree=Ext.getCmp('im-tree');
	    				        	                         tree.store.reload();

	    				        							 //登录成功后的操作，跳转到toIndex.action
	    				        							 //window.location.href = '${ctx}' 
	    				        						 },
	    				        						 failure:function(form,action){
	    				        							// updateCode();
	    				        							 Ext.MessageBox.show({					
	    				                                         width:150,
	    				                                         title:"提交失败",
	    				                                         buttons: Ext.MessageBox.OK,
	    				                                         msg:action.result.msg
	    				                                     })
	    				        						 }
	    				        					 		
	    				        					 }
	    				        			 )
	    				        		 }
	    				        	 }
	    				         }
	             ]
	                 },
	             new Ext.tree.TreePanel({
	                 id:'im-tree',
	                 title: '在线',
	                 rootVisible:false,
	                 lines:false,
	                 autoScroll:true,
	                 store:treestore,
	                 tools:[{
	                     type:'refresh',
	                     tooltip:'刷新',
	                     handler: function(event, toolEl, panelHeader) {
	                         var tree=Ext.getCmp('im-tree');
	                         tree.store.reload();
	                     }
	                 }],
	                 //dataUrl: 'get-users.json',
	                /* root: {
	                     nodeType: 'async',
	                     text: 'Online',
	                     expanded: true
	                 }*/
	             }), {
	                 title: '请假',
	                 html:'<p>Something useful would be in here.</p>',
	                 autoScroll:true
	             },{
	                 title: '营业额',
	                 html : '<p>Something useful would be in here.</p>'
	             },{
	                 title: '个人设置',
	                 html : '<p>Something useful would be in here.</p>'
	             }
	         ],
	        // renderTo: Ext.getDom('mainContent')
	     });
	}
	
	 win.show();
 
	//Ext.getCmp('mainContent').add(signInpPanel);
	//Ext.getCmp('mainContent').setActiveTab(signInpPanel);
		
	}
Ext.define('Demo.view.Header', {
	extend : 'Ext.panel.Panel',
	alias:'widget.myheader',
	height : 100,
	html : '业务基础平台',
	region : 'north',
	split : true,
	collapsible: true,
	bbar : [{
				iconCls : 'icon-user',
				text : username
			}, '-', {
				id:'headerTime',
				text : Ext.Date.format(new Date(), 'Y年m月d日')
			},'-',{
				iconCls : 'icon-userSignIn',
				text:'签到',
				handler:signIn
			}, '->', {
				text : '退出',
				iconCls : 'icon-logout'
			}],
	bodyStyle : 'backgroud-color:#99bbe8;line-height : 50px;padding-left:20px;' +
			'font-size:22px;color:#000000;font-family:黑体;font-weight:bolder;'+ 
			'background: -webkit-gradient(linear, left top, left bottom, ' +
			'color-stop(0%, rgba(153,187, 232, 0.4) ),' +
			'color-stop(50%, rgba(153, 187, 232, 0.8) ),' +
			'color-stop(0%, rgba(153, 187, 232, 0.4) ) );' ,
	initComponent : function(){
		this.callParent();
	}
});