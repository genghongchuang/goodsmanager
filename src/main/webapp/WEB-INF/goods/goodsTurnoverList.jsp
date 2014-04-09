<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>营业额</title>
<script type="text/javascript">
	Ext.require([ 'Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.data.*',
			'Ext.form.*' ]);
	Ext.require('Ext.chart.*');
	Ext.require([ 'Ext.Window', 'Ext.fx.target.Sprite',
			'Ext.layout.container.Fit', 'Ext.window.MessageBox' ]);
	Ext
			.onReady(function() {
				Ext.QuickTips.init();
				//var app={};//全局变量  
				Ext.define('Demo.model.menuModel', {
					extend : 'Ext.data.Model',
					fields : [ {
						name : 'id',
						type : 'int'
					}, {
						name : 'pid',
						type : 'int'
					}, {
						name : 'text',
						type : 'string'
					},
					//�������ͱ���Ҫ��Ĭ�ֵ
					{
						name : 'leaf',
						type : 'boolean',
						defaultValue : false
					}, {
						name : 'url',
						type : 'string'
					}, {
						name : 'iconCls',
						type : 'string'
					} ]

				});
				var treestore = Ext.create('Ext.data.Store', {
					defaultRootId : 'root',
					requires : 'Demo.model.menuModel',
					model : 'Demo.model.menuModel',
					//fields:['id','text','url'],
					proxy : {
						type : 'ajax',
						url : '${ctx}/system/signIn/getAllUser',
						reader : 'json',
						autoLoad : true
					}
				});

				Ext
						.define(
								"GoodsOrder",
								{
									extend : "Ext.data.Model",
									fields : [
											{
												name : 'id',
											},
											{
												name : "orderId",
												mapping : 'order.id'
											},
											{
												name : "createTime",
												mapping : 'order.createTime',
												type : 'date',
												convert : function(v, record) {
													//将一个long型的time转换为标准的日期对象
													//此时V为一个long型的时间毫秒数
													if (v) {
														return Ext.util.Format
																.date(new Date(
																		v),
																		'Y-m-d H:i:s');
													}

												}
											},
											{
												name : "goodsNum",
												mapping : 'goods.goodsNum'
											},
											{
												name : "status",
												mapping : 'order.status'
											},
											{
												name : "user",
												mapping : 'order.user.userFullName'
											},
											{
												name : "vip",
												mapping : 'order.vip.accountNum'
											},
											{
												name : 'count',
												mapping : 'count'
											},
											{
												name : 'inPrice',
												mapping : 'goods.inPrice',
												type : 'int'
											},
											{
												name : 'inPriceCount',
												type : 'int',
												convert : function(v, record) {
													if (record.data['id']) {
														return parseFloat(
																record.data['count']
																		* record.data['inPrice'])
																.toFixed(1);
													}
													return v;

												}
											},
											{
												name : 'realPrice',
												type : 'int',
												convert : function(v, record) {
													if (record.data['id']) {
														return parseFloat(
																record.data['count']
																		* v)
																.toFixed(1);
													}
													return v;
												}
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
					model : "GoodsOrder",
					proxy : {
						type : "ajax",
						url : "${ctx}/goods/goodsOrder/getOrderTurnover",
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
									width : 970,
									defaults : {
										frame : true,
									},
									items : [ {
										layout : 'column',//第一行
										defaults : {
											frame : true
										},
										items : [ {
											columnWidth : '.5',//第一列
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
												fieldLabel : '订单号',
												name : 'orderId',
												id : 'orderId'
											} ]
										}, {
											columnWidth : '.5',
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
												xtype : 'combo',
												name : "userId",
												fieldLabel : '销售人',
												id : 'sellUser',
												store : treestore,
												displayField : 'text',
												valueField : 'pid',
												triggerAction : "all",
												mode : "local",
												editable : false

											} ]
										}, {
											columnWidth : '.5',
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
												xtype : 'datefield',
												name : 'startTime',
												fieldLabel : '提交时间',
												format : 'Y-m-d',
												anchor : '100%'
											} ]
										}, {
											columnWidth : '.5',
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
												xtype : 'datefield',
												name : 'endTime',
												fieldLabel : '至',
												format : 'Y-m-d',
												anchor : '100%'
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
					emptyMsg : "没有数据"
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
				var dataGrid = Ext
						.create(
								'Ext.grid.Panel',
								{
									id : 'gridPanel',
									frame : true,
									store : store,
									//selModel : sm,
									columns : [
											rn,
											{
												flex : 5,
												text : '订单号',
												dataIndex : 'orderId'
											},
											{
												flex : 4,
												text : '销售人',
												dataIndex : 'user'
											},
											{
												flex : 6,
												text : '提交时间',
												dataIndex : 'createTime'
											},
											{
												flex : 5,
												text : '会员',
												dataIndex : 'vip'
											},
											{
												flex : 5,
												text : '货号',
												dataIndex : 'goodsNum'
											},
											{
												flex : 5,
												text : '数量',
												dataIndex : 'count'
											},
											{
												flex : 5,
												text : '进价（元）',
												dataIndex : 'inPriceCount'
											},
											{
												flex : 5,
												text : '出价（元）',
												dataIndex : 'realPrice'
											},
											{
												flex : 5,
												text : '进出净额（元）',
												renderer : function(value,
														metaData, record,
														rowIndex, colIndex,
														store, view) {

													var min = parseFloat(
															record.data['realPrice']
																	- record.data['inPriceCount'])
															.toFixed(1);
													return Ext.String.format(
															'{0}', min);
												}
											} ],
									viewConfig : {
										getRowClass : function(record,
												rowIndex, rowParams, store) {
											if (rowIndex % 2 != 0) {
												//return 'rowClass';
											}

										}
									},
									tbar : [ {
										text : '图表',
										tooltip : '查看图表',
										iconCls : 'icon-chart',
										handler : showChart

									} ],
									bbar : pageBar,

									buttonAlign : "center",

									buttons : []
								});
				function GridSum(grid) {
					var realPriceSum = 0;
					var inPriceSum = 0;
					var countSum = 0;
					grid.store.each(function(record) {
						countSum += Number(record.data.count);

						inPriceSum += Number(record.data.inPriceCount);
						realPriceSum += Number(record.data.realPrice);
					});
					//alert(inPriceSum);
					var n = grid.getStore().getCount();// 获得总行数
					var p = Ext.create('GoodsOrder', {
						id : '',
						orderId : '合计：',
						inPriceCount : inPriceSum,
						count : countSum,
						inPrice : '1',

						realPrice : realPriceSum
					});
					if (n > 0) {
						grid.store.insert(n, p);// 插入到最后一行 
					}

				}
				dataGrid.getStore().on('load', function() {
					GridSum(dataGrid);//
					var cols = [ 1, 2, 3, 4 ];
					mergeCells(dataGrid, cols);
				});
				Ext.create('Ext.panel.Panel', {
					frame : true,
					bodyPadding : 10, // Don't want content to crunch against the borders
					width : 1100,
					items : [ roleCheckPanel, dataGrid ],
					renderTo : Ext.getBody()
				});
				function showChart() {
					Ext
							.define(
									"MonthOrderTurnover",
									{
										extend : "Ext.data.Model",
										fields : [
												{
													name : 'inPriceSum',
												},
												{
													name : "realPriceSum",

												},
												{
													name : "createTime",
												},
												{
													name : 'profitPriceSum',
													convert : function(v,
															record) {
														return parseFloat(
																record.data['realPriceSum']
																		- record.data['inPriceSum'])
																.toFixed(1);
													}

												}, {
													name : 'userId'
												}, {
													name : 'userFullName'
												}]
									});

					var turnoverStore = Ext.create("Ext.data.JsonStore", {
						model : "MonthOrderTurnover",
						proxy : {
							type : "ajax",
							url : "${ctx}/goods/goodsOrder/getMonthTurnover",
							reader : {
								type : "json"

							}
						},
						autoLoad : true
					});
					var chart = Ext
							.create(
									'Ext.chart.Chart',
									{
										style : 'background:#fff',
										animate : true,
										shadow : true,
										store : turnoverStore,
										legend : {
											position : 'right'
										},
										axes : [ {
											type : 'Numeric',
											position : 'left',
											//fields : [ 'inpriceSum' ],
											/* label : {
												renderer : Ext.util.Format
														.numberRenderer('0,0')
											}, */
											title : '月统计(元)',
											grid : true,
											minimum : 0,
											minorTickSteps : 5,
											majorTickSteps : 9,
											maximum : 2000
										}, {
											type : 'Category',
											position : 'bottom',
											fields : [ 'createTime' ],
											title : '月数'
										} ],
										series : [ {
											type : 'column',
											axis : 'left',
											highlight : true,
											stacked : false,
											tips : {
												trackMouse : true,
												width : 240,
												height : 28,
												renderer : function(storeItem,
														item) {
													if (item.yField == 'inPriceSum') {
														this
																.setTitle(storeItem
																		.get('createTime')
																		+ '的成本: '
																		+ storeItem
																				.get('inPriceSum')
																		+ '元');
													} else if (item.yField == 'realPriceSum') {
														this
																.setTitle(storeItem
																		.get('createTime')
																		+ '的出售: '
																		+ storeItem
																				.get('realPriceSum')
																		+ '元');
													} else if (item.yField == 'profitPriceSum') {
														this
																.setTitle(storeItem
																		.get('createTime')
																		+ '的盈利: '
																		+ storeItem
																				.get('profitPriceSum')
																		+ '元');
													}

												}
											},
											label : {
												display : 'insideEnd',
												'text-anchor' : 'middle',
												field : [ 'inPriceSum',
														'realPriceSum',
														'profitPriceSum' ],
												renderer : Ext.util.Format
														.numberRenderer('0.00'),
												orientation : 'vertical',
												color : '#333'
											},
											xField : 'createTime',
											yField : [ 'inPriceSum',
													'realPriceSum',
													'profitPriceSum' ],
											title : [ '成本总额', '售出总额', '盈利总额' ]

										} ]

									});
					window.loadTask = new Ext.util.DelayedTask();
					var win = Ext
							.create(
									'Ext.window.Window',
									{
										title : '销售额统计',
										height : 400,
										width : 600,
										layout : 'fit',
										maximizable : true,
										tbar : [
												{

													text : '保存图片',
													handler : function() {
														Ext.MessageBox
																.confirm(
																		'确定下载',
																		'你确定将这个报表保存为图片?',
																		function(
																				choice) {
																			if (choice == 'yes') {
																				chart
																						.save({
																							type : 'image/png'
																						});
																			}
																		});
													}

												},
												{
													text : '重新加载',
													handler : function() {
														// Add a short delay to prevent fast sequential clicks
														window.loadTask
																.delay(
																		100,
																		function() {
																			turnoverStore
																					.reload();
																		});
													}
												},
												{
													text : '月数据',
													handler : function() {
														chart.axes.items[0].title = '月统计(元)';
														chart.axes.items[1].title = '月数';
														turnoverStore.proxy.url = '${ctx}/goods/goodsOrder/getMonthTurnover';
														turnoverStore.reload();
													}
												},
												{
													text : '年数据',
													handler : function() {
														chart.axes.items[0].title = '年统计(元)';
														chart.axes.items[1].title = '年数';
														turnoverStore.proxy.url = '${ctx}/goods/goodsOrder/getYearTurnover';
														turnoverStore.reload();
													}
												}/* ,
												{
													text : '售货员盈利数据',
													handler : function() {
													    turnoverStore.proxy.url = '${ctx}/goods/goodsOrder/getUserYearTurnover';
														turnoverStore.reload();
														chart.axes.items[0].title = '年统计(元)';
														chart.axes.items[1].title = '年数';
														chart.series.items[0].yField=['userId','profitPriceSum'];
														var createTime=null;
														var i=0;
														var userTurnovers=[];
														var userTurnover=Ext.JSON.decode('{}');
														turnoverStore.each(function(record) {
														if(record.get('userFullName')){
														 var createTime1=record.get('createTime');
														   var userFullName=record.get('userFullName');  
														   var profitPriceSum=record.get('profitPriceSum');
														 if(record.get('createTime')==createTime){
													       
														   userTurnover={"createTime":createTime1};
														   userTurnover[userFullName]=profitPriceSum;
														   var e=Ext.apply(userTurnovers[userTurnovers.length-1],userTurnover);
														   userTurnovers[userTurnovers.length-1]=e;
														 }else{
														   createTime=record.get('createTime');
														  if(!userTurnover.length||userTurnover.length==0){
														   userTurnover={"createTime":createTime1};
														   userTurnover[userFullName]=profitPriceSum;
														  }								
														   userTurnovers.push(userTurnover) ; 
														   userTurnover=Ext.JSON.decode('{}');
                                                          }
														 
														}
                                                         });
                                                         var names=new Array();
                                                         for(var userTurnover in userTurnover){
                                                         if(userTurnover)
                                                         }
                                                         console.log(userTurnovers);
														

														// chart.series.items[1].title='年数';
														//console.log(chart.series.items[0].yField);
													}
												}  */],
										items : chart
									//{
									//id : 'chartCmp',
									//xtype : 'chart',

									//}
									});
					win.show();

				}

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