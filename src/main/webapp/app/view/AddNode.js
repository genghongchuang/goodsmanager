var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define('Demo.view.AddNode', {
	extend : 'Ext.form.Panel',
	alias : 'widget.addnode',
	//	id:'addnode',
	height : 50,
	width : 200,
	title : '添加节点',
	renderTo : "mainContent",
	iconCls : 'icon-news',
	closable : true,
	defaultAlign : 'center',
	buttonAlign : 'center',
	cls : 'form-panel-base',
	bodyCls : 'form-panel-base',
	plain : true,
	bodyStyle : "padding:10px 0 0 10px;margin: 5 5 5 5",
	fieldDefaults : {
		fieldCls : 'field-base',
		//fieldStyle : 'width:50px;height:28px;margin: 0 0 7 0',
		labelCls : 'label-base',

		labelAlign : 'center',
		// 宽度
		labelWidth : 90,
		msgTarget : 'side'
	},
	items : [{
		xtype : 'panel',
		defaultAlign : 'center',
		buttonAlign : 'center',
		frame : true,
		title : '添加节点',
		border : false,
		width : '200',
		items : [

		{
					layout : 'column',
					frame : true,
					border : false,
					items : [{
								columnWidth : .5,
								xtype : 'textfield',
								fieldLabel : '父功能',
								readOnly : true,
								//id:'pid',
								name : 'pname',
								// 不允许为空
								allowBlank : false,
								blankText : '父功能不能为空'
							}, {
								columnWidth : .5,
								xtype : 'textfield',
								fieldLabel : '功能名称',
								afterLabelTextTpl : required,
								name : 'text',
								allowBlank : false,
								blankText : '功能名称不能为空'
							}]
				}, {
					layout : 'column',
					frame : true,
					border : false,
					items : [{
								columnWidth : .5,
								xtype : 'textfield',
								fieldLabel : '功能路径',
								name : 'url'

							}, {
								columnWidth : .5,
								xtype : 'textfield',
								fieldLabel : '节点图标',
								name : 'iconCls'

							}]
				}, {
					xtype : 'hiddenfield',
					name : 'pid'

				}

		],
		buttons : [{
					text : '保存',
					handler : function() {
						//this.up('form').getForm().isValid();
						//获取当前的表单form
						var form = this.up('form').getForm();
						
						//判断否通过了表单验证，如果不能空的为空则不能提交
						if (form.isValid()) {
							form.submit({
										clientValidation : true,
										waitMsg : '请稍候',
										waitTitle : '正在添加',
										url : 'treeNode/addNode',
										success : function(form, action) {
										//	console.log(action);
											Ext.Msg.alert('提示框','保存成功!',function(){
											
												form.findField('text').setValue('');
												console.log(Ext.getCmp('menutree').child());
												menuStore.load();
											});
											// Ext.Msg.show({msg:'添加成功!',buttons:Ext.Msg.YES});
											// this.up('form').getForm().reset();
											//登录成功后的操作，跳转到toIndex.action
											// window.location.href = '${ctx}' 
										},
										failure : function(form, action) {

											Ext.MessageBox.show({
														width : 150,
														title : "登录失败",
														buttons : Ext.MessageBox.OK,
														msg : action.result.msg
													})
										}

									})
						}
					}
				}, {
					text : '重置',
					handler : function() {
						this.up('form').getForm().reset();
					}
				}]
	}]

});