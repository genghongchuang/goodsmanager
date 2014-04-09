var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define('Demo.view.AddNode', {
			extend : 'Ext.form.Panel',
			alias : 'widget.addnode',
			//height : 50,
			title : '添加节点',
			//split : true,
			//	collapsible : true,
			renderTo : "mainContent",
			//iconCls : 'icon-news',
			closable : true,
			      labelWidth : 65,
       labelAlign : "center",
             layout : "form", // 整个大的表单是form布局
                 width : 650,
       autoHeight : true,
            frame : true,
			items : [{ // 行1
				layout : "column", // 从左往右的布局
				items : [{
							columnWidth : .3, // 该列有整行中所占百分比
							layout : "form", // 从上往下的布局
							items : [{
										xtype : "textfield",
										fieldLabel : "姓",
										width : 120
									}]
						}, {
							columnWidth : .3,
							layout : "form",
							items : [{
										xtype : "textfield",
										fieldLabel : "名",
										width : 120
									}]
						}, {
							columnWidth : .3,
							layout : "form",
							items : [{
										xtype : "textfield",
										fieldLabel : "英文名",
										width : 120
									}]
						}]
			}, {	// 行2
						layout : "column",
						items : [{
									columnWidth : .5,
									layout : "form",
									items : [{
												xtype : "textfield",
												fieldLabel : "座右铭1",
												width : 220
											}]
								}, {
									columnWidth : .5,
									layout : "form",
									items : [{
												xtype : "textfield",
												fieldLabel : "座右铭2",
												width : 220
											}]
								}]
					}, {// 行3
						layout : "form",
						items : [{
									xtype : "textfield",
									fieldLabel : "奖励",
									width : 500
								}, {
									xtype : "textfield",
									fieldLabel : "处罚",
									width : 500
								}]
					}, {// 行4
						layout : "column",
						items : [{
									layout : "form",
									columnWidth : 0.2,
									items : [{
												xtype : "textfield",
												fieldLabel : "电影最爱",
												width : 50
											}]
								}, {
									layout : "form",
									columnWidth : 0.2,
									items : [{
												xtype : "textfield",
												fieldLabel : "音乐最爱",
												width : 50
											}]
								}, {
									layout : "form",
									columnWidth : 0.2,
									items : [{
												xtype : "textfield",
												fieldLabel : "明星最爱",
												width : 50
											}]
								}, {
									layout : "form",
									columnWidth : 0.2,
									items : [{
												xtype : "textfield",
												fieldLabel : "运动最爱",
												width : 50
											}]
								}]
					}, {// 行5
						layout : "form",
						items : [{
									xtype : "htmleditor",
									fieldLabel : "获奖文章",
									enableLists : false,
									enableSourceEdit : false,
									height : 150
								}]
					}],
			buttonAlign : "center",
			buttons : [{
						text : "提交"
					}, {
						text : "重置"
					}]

		});