<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>货物管理</title>



<script type="text/javascript">
	Ext.require([ 'Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.data.*',
			'Ext.form.*' ]);
	Ext
			.onReady(function() {
				var combostore = Ext.create('Ext.data.Store', {
					fields : [ 'state', 'value' ],
					data : [ {
						"state" : "已完成",
						"value" : "1"
					}, {
						"state" : "未完成",
						"value" : "0"
					} ]
				});
				var classStore = Ext.create('Ext.data.Store', {
					defaultRootId : 'root',
					//requires:'Demo.model.menuModel',
					//model:'Demo.model.menuModel',
					fields : [ 'id', 'className' ],
					proxy : {
						type : 'ajax',
						url : '${ctx}/goods/goodsClass/getGoodsClassInuse/0',
						reader : 'json',
						autoLoad : true
					}
				});
				Ext.QuickTips.init();
				function edit() {
					
					var selRecords = Ext.getCmp("gridPanel")
							.getSelectionModel().getSelection();
					var id, goodsName, goodsNum, brandName, goodsClass, goodsColor, goodsSize, count,returnTime,state;
					if (selRecords.length >= 1) {
						console.log(selRecords[0]);
						id = selRecords[0].get("id");
						goodsId=selRecords[0].get("goodsId");
						goodsNum = selRecords[0].get("goodsNum");
						brandName = selRecords[0].get("brandName");
						goodsName = selRecords[0].get("goodsName");
						//count = selRecords[0].get("count");
						goodsClass = selRecords[0].get("goodsClass");
						goodsColor = selRecords[0].get("goodsColor");
						goodsSize = selRecords[0].get("goodsSize");
						count = selRecords[0].get("count");
						state=selRecords[0].get("state");
						returnTime=selRecords[0].get("returnTime");
						
					} else {
						Ext.MessageBox.alert("提示消息", "您未选中行");
						return false;
					}

					var win = new Ext.Window({
						title : "修改记录窗口",
						width : 500,
						height : 400,
						buttonAlign : 'center',

						items : [ {
							xtype : "form",
							id : "form1",
							height : "200",
							borderStyle : "padding-top:3px",
							frame : true,
							defaultType : "textfield",
							labelAlign : "right",
							labelWidth : 57,
							//padding:[50 ,10],
							defaults : {
								allowBlank : false,
							//width : 200
							},
							items : [ {
								xtype : 'hiddenfield',
								name : 'id',
								value : id
							},{
								xtype : 'hiddenfield',
								name : 'goodsId',
								value : goodsId
							},  {
								fieldLabel : "货号",
								value : goodsNum,
								readOnly : true
							}, {
								fieldLabel : "品牌",
								value : brandName,
								readOnly : true
							}, {
								fieldLabel : "名称",
								value : goodsName,
								readOnly : true
							},{
								fieldLabel : "分类",
								value : goodsClass,
								readOnly : true
							},{
								fieldLabel : "颜色",
								value : goodsColor,
								readOnly : true
							}, {
								fieldLabel : "尺码",
								value : goodsSize,
								readOnly : true
							},{
								xtype : 'combo',
								name : "state",
								fieldLabel : '退货状态',
								hiddenName : 'state',
								id : 'editStateId',
								store : combostore,
								displayField : 'state',
								valueField : 'value',
								triggerAction : "all",
								mode : "local",
								editable : false,
							    allowBlank:false
							},

							{
								fieldLabel : "退货数量",
								xtype:'numberfield',
								minValue:1,
								name : "count",
								id : 'count',
								value : count ,
								allowBlank:false
							}

							]
						} ],//vtype的值:alpha,alphanum,email,url
						buttons : [ {
							xtype : "button",
							text : "确定",
							handler : function() {
								if (!Ext.getCmp("form1").getForm().isValid()) {
									alert("您输入的信息有误,请重新输入...");
									return false;
								}
								Ext.getCmp("form1").getForm().submit({
									clientValidation : true,
									waitMsg : '请稍候',
									waitTitle : '正在提交',
									url : '${ctx}/goods/returnGoods/updateReturnGoods',
									success : function(form, action) {
										Ext.MessageBox.show({
											width : 150,
											title : "更新成功",
											buttons : Ext.MessageBox.OK,
											msg : action.result.msg
										});
										win.close();
										Ext.getCmp("gridPanel").store.reload();
										Ext.getCmp("gridPanel").getSelectionModel().deselectAll();
									},
									failure : function(form, action) {
										Ext.MessageBox.show({
											width : 150,
											title : "更新失败",
											buttons : Ext.MessageBox.OK,
											msg : action.result.msg
										});

									}

								})
							}
						}, {
							xtype : "button",
							text : "取消",
							handler : function() {
								win.close();
							}
						} ]
					}).show();
					Ext.getCmp('editStateId').setValue(state);
				}

				function del() {
					var selRecords = Ext.getCmp("gridPanel")
							.getSelectionModel().getSelection();
					var len = selRecords.length;
					var ids = "";
					if (len == 0) {
						Ext.MessageBox.alert("提示消息", "您未选中行");
						return false;
					}
					Ext.Msg
							.confirm(
									"提示",
									"确定要取消退货吗?",
									function(btn) {
										if (btn == "yes") {
											for ( var i = 0; i < len; i++) {
												if (i == len - 1) {
													ids += selRecords[i]
															.get("id");
												} else {
													ids += selRecords[i]
															.get("id")
															+ ",";
												}
											}
											Ext.Ajax
													.request({
														url : "${ctx}/goods/returnGoods/betchDelReturnGoods/"
																+ ids,
														success : function(
																reponse, option) {
															json = eval("("
																	+ reponse.responseText
																	+ ")");
															Ext.MessageBox
																	.show({
																		width : 150,
																		title : "操作结果",
																		buttons : Ext.MessageBox.OK,
																		msg : json.msg
																	});

															Ext
																	.getCmp("gridPanel").store
																	.reload();
														},
														failure : function() {
															alert("取消失败");
														}

													});
										}
									})
				}
				//var app={};//全局变量  

				Ext.define("ReturnGoods", {
					extend : "Ext.data.Model",
					fields : [
							{
								name : "id"
							},
							{   name:'goodsId',
							    mapping:'goods.id'
							},
							{
								name : "state"
							},
							{
								name : "count"
							},
							{
								name : "goodsName",
								mapping : 'goods.goodsName'
							},
							{
								name : "returnTime",
								type : 'date',
								convert : function(v, record) {
									//将一个long型的time转换为标准的日期对象
									//此时V为一个long型的时间毫秒数
									return Ext.util.Format.date(new Date(v),
											'Y-m-d H:i:s');
								}

							//dateFormat : 'time'
							}, {
								name : "brandName",
								mapping : 'goods.brandName'
							}, {
								name : 'goodsClass',
								mapping : 'goods.goodsClass.className'
							}, {
								name : "goodsColor",
								mapping : 'goods.goodsColor.colorName'

							}, {
								name : "goodsNum",
								mapping : 'goods.goodsNum'
							}, {
								name : "goodsSize",
								mapping : 'goods.goodsSize.size'
							} ]
				});
				var itemsPerPage = 10; //分页大小
				var store = Ext.create("Ext.data.JsonStore", {
					// baseParams: {},
					autoLoad : {
						start : 0,
						limit : itemsPerPage,
						page : 1
					},
					pageSize : itemsPerPage, // 分页大小
					model : "ReturnGoods",
					proxy : {
						type : "ajax",
						url : "${ctx}/goods/returnGoods/getReturnGoods",
						actionMethods : {
							read : 'POST'
						},
						extraParams : {},
						reader : {
							type : "json",
							root : "items",
							totalProperty : 'count'

						}
					}
				});
				var rn = Ext.create('Ext.grid.RowNumberer', {
					header : "编号",

					width : 30,
					css : 'color:blue;'
				});
				var roleCheckPanel = Ext
						.create(
								"Ext.form.Panel",
								{
									bodyPadding : 5,
									layout : 'form',
									frame : true,
									border : true,
									buttonAlign : 'center',
									width : 1120,
									defaults : {
										frame : true,
									},
									items : [ {
										layout : 'column',//第一行
										defaults : {
											frame : true
										},
										items : [ {
											columnWidth : '.15',
											layout : 'form',
											defaults : {

												labelWidth : 80,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side'
											},
											items : [ {
												xtype : 'combo',
												name : "goodsClass.id",
												fieldLabel : '分类',
												emptyText:'请选择',
												store : classStore,
												displayField : 'className',
												valueField : 'id',
												triggerAction : "all",
												mode : "local",
												editable : false,
											//allowBlank:false
											} ]
										}, {
											columnWidth : '.15',
											layout : 'form',
											defaults : {

												labelWidth : 60,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side'
											},
											items : [ {
												xtype : 'textfield',
												name : "goodsNum",
												fieldLabel : '货号'
											//id : 'goo',

											//allowBlank:false
											} ]
										}, {
											columnWidth : '.11',//第一列
											layout : 'form',
											defaults : {

												labelWidth : 60,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side'
											},
											items : [ {
												xtype : 'textfield',
												fieldLabel : '品牌',
												name : 'brandName',
												id : 'prefix'
											} ]
										}, {
											columnWidth : '.15',
											layout : 'form',
											defaults : {

												labelWidth : 60,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side'
											},
											items : [ {
												xtype : 'textfield',
												name : "goodsName",
												fieldLabel : '名称'

											//allowBlank:false
											} ]
										},{
											columnWidth : '.15',
											layout : 'form',
											defaults : {

												labelWidth : 80,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side'
											},
											items : [ {
												xtype : 'combo',
												name : "state",
												hiddenName:'state',
												fieldLabel : '退货状态',
												emptyText:'请选择',
												//id : 'autoCreate',
												store : combostore,
												displayField : 'state',
												valueField : 'value',
												triggerAction : "all",
												mode : "local",
												editable : false,
											//allowBlank:false
											} ]
										},{
						columnWidth : '.145',//第一列
						layout : 'form',
						defaults : {

							labelWidth : 60,
							frame : true,
							border : false,
							labelSeparator : ':',
							labelAlign : 'right',
							msgTarget : 'side'
						},
						items : [ {
							xtype: 'datefield',
					        name: 'startTime',
					        fieldLabel: '创建时间',
					        format: 'Y-m-d',
					        anchor: '100%'
						} ]
					},{
						columnWidth : '.145',//第一列
						layout : 'form',
						defaults : {

							labelWidth : 60,
							frame : true,
							border : false,
							labelSeparator : ':',
							labelAlign : 'right',
							msgTarget : 'side'
						},
						items : [ {
							xtype: 'datefield',
					        name: 'endTime',
					        fieldLabel: '  至',
					        format: 'Y-m-d',
					        anchor: '100%'
						} ]
					} ]
									} ],
									buttons : [
											{
												text : '重置',
												handler : function() {
													this.up('form').getForm()
															.reset();
												}
											},
											{
												text : '查询',
												formBind : true, //only enabled once the form is valid
												disabled : true,
												handler : function() {
													var form = this.up('form')
															.getForm()

													if (form.isValid()) {
														var formValues = form
																.getValues();
														//console.log(formValues['goodsClass.id']);
														if(formValues['goodsClass.id']==''){
														 delete formValues['goodsClass.id'];
														}
														var params = dataGrid.store
																.getProxy().extraParams;
														Ext.apply(params,
																formValues);
														dataGrid.store
																.getProxy().extraParams = params;
														store.currentPage = 1
														dataGrid.store.load({
															params : {
																start : 0,
																limit : 10,
																page : 1
															}
														});
													}
												}
											} ]

								});
				var sm = Ext.create('Ext.selection.CheckboxModel', {
					checkOnly : true
				});
				var pageBar = Ext.create('Ext.PagingToolbar', {
					id : 'pageBar',
					//	dock: 'bottom', //分页 位置  
					afterPageText : '共{0}页',
					beforePageText : '当前页',
					lastText : "尾页",
					nextText : "下一页",
					prevText : "上一页",
					firstText : "首页",
					refreshText : "刷新页面",
					store : store,
					displayInfo : true,
					displayMsg : '显示{0}-{1}条，共{2}条记录',
					emptyMsg : "没有数据",
					items : [ {
						text : "第一行",
						handler : function() {
							var rsm = dataGrid.getSelectionModel(); //得到选择模型
							rsm.select(0);
						}
					}, {
						text : "上一行",
						handler : function() {
							var rsm = dataGrid.getSelectionModel(); //得到选择模型
							if (rsm.isSelected(0)) {
								Ext.MessageBox.alert("警告", "已到达第一行");
							} else {
								rsm.selectPrevious();
							}
						}
					}, {
						text : "下一行",
						handler : function() {
							var length = store.data.length - 1
							var rsm = dataGrid.getSelectionModel(); //得到选择模型
							if (rsm.isSelected(length)) {
								Ext.MessageBox.alert("警告", "已到达最后一行");
							} else {
								rsm.selectNext();
							}
						}
					}, {
						text : "最后一行",
						handler : function() {
							var length = store.data.length - 1
							var rsm = dataGrid.getSelectionModel(); //得到选择模型
							rsm.select(length);
						}
					}, {
						text : "全选",
						handler : function() {
							var rsm = dataGrid.getSelectionModel(); //得到选择模型
							rsm.selectAll();
						}
					}, {
						text : "全不选",
						handler : function() {
							var length = store.data.length - 1
							var rsm = dataGrid.getSelectionModel(); //得到选择模型
							rsm.deselectRange(0, length);
						}
					}, {
						text : "反选",
						handler : function() {
							var rsm = dataGrid.getSelectionModel(); //得到选择模型
							var length = store.data.length - 1;
							for ( var i = length; i >= 0; i--) {
								if (rsm.isSelected(i)) {

									rsm.deselect(i, true);
								} else {

									rsm.select(i, true); //必须保留原来的,否则效果无法显示
								}
							}
						}
					} ]
				});
				var renderer = function(value, metadata, record, rowIndex,
						colIndex, store) {
					metadata.css = 'rowClass';
					alert(rowIndex % 2 != 0);
					if (rowIndex % 2 != 0) {
						metadata.css = 'rowClass';
					}
					return value;
				}
				var dataGrid = Ext.create('Ext.grid.Panel', {
					id : 'gridPanel',
					frame : true,
					store : store,
					selModel : sm,
					columns : [ rn, {
						flex : 7,
						text : '货号',
						dataIndex : 'goodsNum'
					}, {
						flex : 7,
						text : '品牌',
						dataIndex : 'brandName'
					},

					{
						flex : 7,
						text : '名称',
						dataIndex : 'goodsName'
					}, {
						flex : 5,
						text : '分类',
						dataIndex : 'goodsClass'
					}, {
						flex : 5,
						text : '颜色',
						dataIndex : 'goodsColor'
					}, {
						flex : 5,
						text : '尺码',
						dataIndex : 'goodsSize'
					}, {
						flex : 5,
						text : '退货数量',
						dataIndex : 'count'
					}, {
						flex : 5,
						text : '退货时间',
						dataIndex : 'returnTime',
						format : 'd/m/y'
					}, {
						flex : 5,
						text : '退货状态',
						dataIndex : 'state',
						renderer: function(value) {
	       
	        	var display='未完成';
	        	if(value=='1'){
	        		display='已完成'
	        			 return Ext.String.format('<font color=red>{1}</font>', value, display);
	        	}
	        	 return Ext.String.format('{1}', value, display);
	        }
					} ],
					viewConfig : {
						getRowClass : function(record, rowIndex, rowParams,
								store) {
							if (rowIndex % 2 != 0) {
								return 'rowClass';
							}

						}
					},
					tbar : [  {
						text : '修改',
						tooltip : '修改货物',
						iconCls : 'edit',
						handler : edit
					}, '-', {
						text : '取消退货',
						tooltip : '未完成的订单取消退货',
						iconCls : 'remove',
						handler : del
					} ],
					bbar : pageBar,

					buttonAlign : "center",

					buttons : []
				});
				Ext.create('Ext.panel.Panel', {
					frame : true,
					bodyPadding : 10, // Don't want content to crunch against the borders
					width : 1150,
					items : [ roleCheckPanel, dataGrid ],
					renderTo : Ext.getBody()
				});

			});

	/* Ext.require([
	 "Ext.data.*",
	 "Ext.form.*",
	 "Ext.grid.Panel",
	 "Ext.layout.container.Column"
	 ]);
	 Ext.onReady(function() {
	
	 Ext.QuickTips.init();
	 var bd = Ext.getBody();
	
	 // store data(static)
	 var storedata = [
	 ['大唐牛肉无限公司', 1100.00, -0.01,  -0.013,  '2012-02-27'],
	 ['大唐武器无限公司', 1200.00, 0.02,  0.033,  '2012-02-28'],
	 ['大唐资源无限公司', 1300.00, -0.03,  -0.043,  '2012-02-29'],
	 ['大唐人才无限公司', 1400.00, 0.04,  0.053,  '2012-03-27'],
	 ['大唐环境维护无限公司', 1500.00, 0.05,  0.023,  '2012-03-26'],
	 ['大唐矿产无限公司', 1600.00, -0.06,  -0.063,  '2012-03-25'],
	 ['大唐精矿无限公司', 1700.00, 0.07,  0.073,  '2012-03-24'],
	 ['大唐铁矿无限公司', 1800.00, 0.08,  0.083,  '2012-03-23'],
	 ['大唐铝土矿无限公司', 1900.00, 0.09,  0.093,  '2012-03-22'],
	 ['大唐煤资源无限公司', 2000.00, -0.022,  -0.031,  '2013-02-21'],
	 ['大唐天然气无限公司', 1110.00, 0.023,  0.032,  '2012-03-20'],
	 ['大唐太阳能无限公司', 1220.00, -0.024,  -0.033,  '2012-03-19'],
	 ['大唐激光武器无限公司', 1330.00, 0.025,  0.034,  '2012-03-18'],
	 ['大唐汽车无限公司', 1450.00, 0.026,  0.053,  '2012-03-17'],
	 ['大唐卡车配件无限公司', 1660.00, 0.027,  0.036,  '2012-03-16']
	 ];
	
	 // store
	 var dstore = Ext.create('Ext.data.ArrayStore', {
	 fields: [{
	 name: "company"
	 }, {
	 name: "price", type: "float"
	 }, {
	 name: "change", type: "float"
	 }, {
	 name: "pctChange", type: "float"
	 }, {
	 name: "lastChange", type: "date", dateFormat: "Y-m-d"
	 }, 
	 // Rating field dependent upon performance 0=best(A), 2=worst(C);
	 {
	 name: "rating", 
	 type: "int", 
	 convert: function(value, record) {
	 var pct = record.get('pctChange');
	 if(pct<0) return 2;
	 if(pct<1) return 1;
	 return 0;
	 }
	 }],
	 data: storedata
	 });
	
	 // 自定义行内容渲染：
	 function change(val) {
	 if(val > 0) {
	 return '<span style="color: green;">'+ val +'</span>';
	 } else if(val < 0) {
	 return '<span style="color: red;">'+ val +'</span>';
	 }
	
	 return val;
	 }
	 function pctChange(v) {
	 if(v!=0) {
	 return Ext.String.format('<span style="color: {0};">{1}%</span>', v>0?"green":"red", v);
	 } else {
	 return v;
	 }
	 }
	 function rating(v) {
	 if(v==0) return "A";
	 if(v==1) return "B";
	 if(v==2) return "C";
	 }
	
	 bd.createChild({tag: 'h2', html: 'Using a Grid with a Form'});
	
	 // form-grid
	 var gridForm = Ext.create('Ext.form.Panel', {
	 id: "compony-form",
	 renderTo: bd,
	
	 title: "TheCompanyData",
	 frame: true,
	 width: 750,
	 bodyPadding: 5,
	 layout: 'column', // 列布局；
	
	 fieldDefaults: {
	 labelAlign: "left",
	 msgTarget: "side"
	 },
	
	 items: [{    // grid
	 columnWidth: 0.60,
	 xtype: "gridpanel",
	 store: dstore,
	 title: "公司概况",
	
	 columns: [{
	 id: 'company',
	 text: "公司名称",
	 dataIndex: "company",
	 sortable: true,
	 flex: 1        // grid宽度减去固定列宽以后占一份；
	 }, {
	 text: "公司资产",
	 dataIndex: "price",
	 width: 70,
	 sortable: true
	 }, {
	 text: "资产增值量",
	 dataIndex: "change",
	 width: 75,
	 sortable: true,
	 renderer: change
	 }, {
	 text: "资产增值百分比",
	 dataIndex: "pctChange",
	 width: 90,
	 sortable: true,
	 renderer: pctChange
	 }, {
	 text: "更新时间",
	 dataIndex: "lastChange",
	 width: 85,
	 sortable: true,
	 align: "center",
	 renderer: Ext.util.Format.dateRenderer('Y-m-d')
	 }, {
	 text: "等级",
	 dataIndex: "rating",
	 width: 30,
	 sortable: true,
	 align: "center",
	 renderer: rating
	 }],
	 // 监听grid事件：
	 listeners: {
	 selectionchange: function(model, records) {
	 if(records[0]) {    // 加载进form表单中；
	 this.up('form').getForm().loadRecord(records[0]);
	 }
	 }
	 }
	 }, {    // form
	 columnWidth: 0.4,
	 margin: '0 0 0 10',
	 xtype: "fieldset",
	 title: '公司详细信息',
	 defaults: {
	 width: 240,
	 labelWidth: 90,
	 labelAlign: "right"
	 },
	 defaultType: "textfield",
	 items: [{
	 fieldLabel: "公司名称",
	 name: "company"
	 }, {
	 fieldLabel: "公司资产",
	 name: "price"
	 }, {
	 fieldLabel: "资产增值量",
	 name: "change"
	 }, {
	 fieldLabel: "资产增值百分比",
	 name: "pctChange"
	 }, {
	 fieldLabel: "更新时间",
	 name: "lastChange",
	 xtype: "datefield",
	 format: "Y-m-d"
	 }, {
	 fieldLabel: "等级",
	 xtype: "radiogroup",
	 columns: 3,
	 defaults: {
	 name: "rating"
	 },
	 items: [{
	 inputValue: "0",
	 boxLabel: "A"
	 }, {
	 inputValue: "1",
	 boxLabel: "B"
	 }, {
	 inputValue: "2",
	 boxLabel: "C"
	 }]
	 }]
	 }]
	 });
	
	 // 给grid默认选择第一行：
	 gridForm.child('gridpanel').getSelectionModel().select(0);
	 }); */
</script>
</head>
<body>
</body>
</html>