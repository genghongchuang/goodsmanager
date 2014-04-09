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
	function getGoodsNumPre(date) {
		dy = date.getFullYear();
		dm = date.getMonth() + 1;
		dd = date.getDate();
		dh = date.getHours();
		dmm = date.getMinutes();
		dss = date.getSeconds();
		return dy + "" + dm + "" + dd + "" + dh + "" + dmm + "" + dss;
	}
	var autoPreFlag = 0;
	var goodsNumPre = "";
	Ext
			.onReady(function() {
				// var da = new Date();
				// dy = da.getFullYear();

				// console.log(goodsNumPre);
				var combostore = Ext.create('Ext.data.Store', {
					fields : [ 'inuse', 'value' ],
					data : [ {
						"inuse" : "是",
						"value" : "1"
					}, {
						"inuse" : "否",
						"value" : "0"
					} ]
				});
				var classStore = Ext.create('Ext.data.Store', {
					defaultRootId:'root',
					//requires:'Demo.model.menuModel',
					//model:'Demo.model.menuModel',
					fields:['id','className'],
					proxy:{
						type:'ajax',
						url:'${ctx}/goods/goodsClass/getGoodsClassInuse/0',
						reader:'json',
						autoLoad:true
					}
				});
				Ext.QuickTips.init();
				function apply() {
					var jsonStore = Ext.create('Ext.data.JsonStore', {
						storeId : 'radio',
						fields : [ 'boxLabel', 'name', 'inputValue' ],
						proxy : {
							type : 'ajax',
							url : '${ctx}/goods/goodsNumRule/getRadios',
							reader : {
								type : 'json',
								// root: 'groupitems',
								totalProperty : 'total'
							},
							baseParams : {
								operation : 'showall'
							},
						},
					});
					jsonStore
							.load({
								callback : function(records, operation, success) {
									itemsInGroup = [];

									console.log(records);

									for (var i = 0; i < records.length; i++) {
										console.log(records[i].data);
										itemsInGroup.push(records[i].data);
									}

									console.log(itemsInGroup);
									var win = new Ext.Window(
											{
												title : "应用货号生成规则",
												width : 347,
												height : 250,
												iconCls : "center_icon",
												buttonAlign : 'center',
												items : [ {
													xtype : "form",
													id : "form1",
													height : "250",
													borderStyle : "padding-top:3px",
													frame : true,
													defaultType : "textfield",
													labelAlign : "right",
													labelWidth : 57,
													defaults : {
														// allowBlank: true, 
														width : 300,
														msgTarget : 'side'
													},
													items : [ {
														xtype : 'radiogroup',
														id : 'prefixRadio',
														fieldLabel : '设置货号前缀',
														columns : 1,
														vertical : true,
														items : itemsInGroup
													} ]
												} ],
												buttons : [
														{
															xtype : "button",
															text : "确定",
															handler : function() {
																if (!Ext
																		.getCmp(
																				"form1")
																		.getForm()
																		.isValid()) {

																	return false;
																}
																var prefix = Ext
																		.getCmp(
																				"prefixRadio")
																		.getValue().rg;
																if (prefix == "") {
																	autoPreFlag = 1;
																} else {
																	autoPreFlag = 0;
																	goodsNumPre = prefix;
																}
																Ext.MessageBox
																		.show({
																			width : 150,
																			title : "设置成功",
																			buttons : Ext.MessageBox.OK,
																			msg : '设置成功'
																		});
																win.close();
																//console.log(Ext.getCmp("prefixRadio").getValue().rg);

															}
														},
														{
															xtype : "button",
															text : "取消",
															handler : function() {
																win.close();
															}
														} ]
											});
									win.show();

								}
							});

				}
				function add() {
					var addFlag=1;
					var sucCmp=new Array();
					var classStore = Ext.create('Ext.data.Store', {
						defaultRootId:'root',
						//requires:'Demo.model.menuModel',
						//model:'Demo.model.menuModel',
						fields:['id','className'],
						proxy:{
							type:'ajax',
							url:'${ctx}/goods/goodsClass/getGoodsClassInuse/1',
							reader:'json',
							autoLoad:true
						}
					});
					var colorStore = Ext.create('Ext.data.Store', {
						defaultRootId:'root',
						//requires:'Demo.model.menuModel',
						//model:'Demo.model.menuModel',
						fields:['id','colorName'],
						proxy:{
							type:'ajax',
							url:'${ctx}/goods/goodsColor/getGoodsColorInuse',
							reader:'json',
							autoLoad:true
						}
					});
					var sizeStore = Ext.create('Ext.data.Store', {
						defaultRootId:'root',
						//requires:'Demo.model.menuModel',
						//model:'Demo.model.menuModel',
						fields:['id','size'],
						proxy:{
							type:'ajax',
							url:'${ctx}/goods/goodsSize/getGoodsSizeInuse',
							reader:'json',
							autoLoad:true
						}
					});
					var id='0';
					if (autoPreFlag == 1) {
						goodsNumPre = getGoodsNumPre(new Date());
					}
					console.log(goodsNumPre);
					
					var win = new Ext.Window(
							{
								title : "添加货物",
								width : 1150,
								height : 500,
								iconCls : "center_icon",
								buttonAlign : 'center',
								autoScroll :true,
								listeners:{
									beforeclose:function(panel, eOpts){
									 Ext.getCmp("gridPanel").store.reload();
								}
							   },
								
								items : [ {
									xtype:'form',
									bodyPadding : 5,
									layout : 'form',
									id:'addForm',
									frame : true,
									border : true,
									buttonAlign : 'center',
									width : 1150,
									defaults : {
										frame : true,
										allowBlank: false
									},
									items : [{
										layout:'column',
										id:id+"*col",
										defaults : {
											frame : true,
											border:false
											
										},
										items:[{
											columnWidth : '.4',
											layout : 'form',
											defaults : {

												labelWidth : 80,
												//frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype:'textfield',
												fieldLabel : "货号",
												name : "goodsNum",
												id : 'goodsNum',
												value : goodsNumPre
											}]
											
										},{
											columnWidth : '.4',
											layout : 'form',
											defaults : {

												labelWidth : 80,
												//frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype:'textfield',
												fieldLabel : "品牌",
												name : "brandName",
												id : 'brandName'
											}]
											
										}]
										} ,{
										layout : 'column',//第一行
										id:id+'*copyColumn',
										alias:'copyColumn',
										defaults : {
											frame : true
										},
										items : [ {
											columnWidth : '.15',
											layout : 'form',
											defaults : {

												labelWidth : 40,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype : 'textfield',
												name : "goodsName",
												id:id+"*goodsName",
												fieldLabel : '名称'						
											} ]
										}, {
											columnWidth : '.1',//第一列
											layout : 'form',
											defaults : {

												labelWidth : 30,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype : 'combo',
												name : "goodsClass.id",
												fieldLabel : '分类',
												id : 'classId'+id,
												store : classStore,
												displayField : 'className',
												valueField : 'id',
												triggerAction : "all",
												mode : "local",
												editable : false,
											//allowBlank:false
											}]
										}, {
											columnWidth : '.1',
											layout : 'form',
											defaults : {

												labelWidth : 30,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype : 'combo',
												name : "goodsColor.id",
												fieldLabel : '颜色',
												id : 'colorId'+id,
												store : colorStore,
												displayField : 'colorName',
												valueField : 'id',
												triggerAction : "all",
												mode : "local",
												editable : false,
											//allowBlank:false
											} ]
										}, {
											columnWidth : '.1',
											layout : 'form',
											defaults : {

												labelWidth : 30,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype : 'combo',
												name : "goodsSize.id",
												fieldLabel : '尺码',
												id : 'sizeId'+id,
												store : sizeStore,
												displayField : 'size',
												valueField : 'id',
												triggerAction : "all",
												mode : "local",
												editable : false,
											//allowBlank:false
											} ]
										}, {
											columnWidth : '.1',
											layout : 'form',
											defaults : {

												labelWidth : 30,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype: 'numberfield',
										        anchor: '100%',
												name : "count",
												fieldLabel : '数量',
												minValue: 0
											} ]
										} , {
											columnWidth : '.1',
											layout : 'form',
											defaults : {

												labelWidth : 30,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype: 'numberfield',
										        anchor: '100%',
												name : "inPrice",
												fieldLabel : '进价',
												minValue: 0											
											} ]
										} ,{
											columnWidth : '.1',
											layout : 'form',
											defaults : {

												labelWidth : 30,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype: 'numberfield',
										        anchor: '100%',
												name : "orderPrice",
												fieldLabel : '定价',
												minValue: 0											
											}  ]
										},{
											columnWidth : '.08',
											layout : 'form',
											defaults : {

												labelWidth : 30,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'right',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype: 'numberfield',
										        anchor: '100%',
												name : "discount",
												fieldLabel : '折扣',
												maxValue: 10,
												value:10,
												minValue: 0											
											} ]
										} ,  {
											columnWidth : '.11',
											layout : 'form',
											defaults : {

												labelWidth : 55,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'left',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype: 'numberfield',
										        anchor: '100%',
												name : "vipDiscount",
												fieldLabel : '会员折扣'	,
												maxValue: 10,
												value:10,
												minValue: 0										
											} ]
										} ,
										{
											columnWidth : '.03',
											layout : 'form',
											defaults : {

												labelWidth : 55,
												frame : true,
												border : false,
												labelSeparator : ':',
												labelAlign : 'left',
												msgTarget : 'side',
												allowBlank:false
											},
											items : [ {
												xtype:'button',
												//text : '添加',
												tooltip : '添加一行',
												iconCls : 'add',
										       handler:function(){
										    	    id=id+1;
										    	   
										    	   var addForm=Ext.getCmp('addForm');
										    	   var copyColumn=Ext.getCmp('copyColumn');
										    	   var copy=new Ext.Panel({
														layout : 'column',//第一行
														id:id+"*copyColumn",
														frame:true,
														width:1150,
														//alias:'copyColumn',
														defaults : {
															frame : true
														},
														items : [ {
															columnWidth : '.149',
															layout : 'form',
															defaults : {

																labelWidth : 40,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'right',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype : 'textfield',
																name : "goodsName",
																id:id+"*goodsName",
																fieldLabel : '名称'						
															} ]
														}, {
															columnWidth : '.099',//第一列
															layout : 'form',
															defaults : {

																labelWidth : 30,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'right',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype : 'combo',
																name : "goodsClass.id",
																fieldLabel : '分类',
																id : 'classId'+id,
																store : classStore,
																displayField : 'className',
																valueField : 'id',
																triggerAction : "all",
																mode : "local",
																editable : false,
															//allowBlank:false
															}]
														}, {
															columnWidth : '.099',
															layout : 'form',
															defaults : {

																labelWidth : 30,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'right',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype : 'combo',
																name : "goodsColor.id",
																fieldLabel : '颜色',
																id : 'colorId'+id,
																store : colorStore,
																displayField : 'colorName',
																valueField : 'id',
																triggerAction : "all",
																mode : "local",
																editable : false,
															//allowBlank:false
															} ]
														}, {
															columnWidth : '.099',
															layout : 'form',
															defaults : {

																labelWidth : 30,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'right',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype : 'combo',
																name : "goodsSize.id",
																fieldLabel : '尺码',
																id : 'sizeId'+id,
																store : sizeStore,
																displayField : 'size',
																valueField : 'id',
																triggerAction : "all",
																mode : "local",
																editable : false,
															//allowBlank:false
															} ]
														}, {
															columnWidth : '.099',
															layout : 'form',
															defaults : {

																labelWidth : 30,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'right',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype: 'numberfield',
														        anchor: '100%',
																name : "count",
																fieldLabel : '数量',
																minValue:0
															} ]
														} , {
															columnWidth : '.099',
															layout : 'form',
															defaults : {

																labelWidth : 30,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'right',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype: 'numberfield',
														        anchor: '100%',
																name : "inPrice",
																fieldLabel : '进价',
																minValue:0
															} ]
														} ,{
															columnWidth : '.099',
															layout : 'form',
															defaults : {

																labelWidth : 30,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'right',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype: 'numberfield',
														        anchor: '100%',
																name : "orderPrice",
																fieldLabel : '定价',
																minValue:0
															}  ]
														},{
															columnWidth : '.079',
															layout : 'form',
															defaults : {

																labelWidth : 30,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'right',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype: 'numberfield',
														        anchor: '100%',
																name : "discount",
																fieldLabel : '折扣',
																maxValue:10,
																value:10,
																minValue:0
															} ]
														} ,  {
															columnWidth : '.109',
															layout : 'form',
															defaults : {

																labelWidth : 55,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'left',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype: 'numberfield',
														        anchor: '100%',
																name : "vipDiscount",
																fieldLabel : '会员折扣'	,
																maxValue:10,
																value:10,
																minValue:0
															} ]
														} ,
														{
															columnWidth : '.029',
															layout : 'form',
															defaults : {

																labelWidth : 55,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'left',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype: 'button',
																value:id,
																//text : '删除',
																tooltip : '删除改行',
																iconCls : 'remove',
		
														       handler:function(obj){
														    	   
														    	   var delId=obj.value;
														    	  
														    	   var delCmp=Ext.getCmp(delId+"*copyColumn");
														    	   var addForm=Ext.getCmp('addForm');
														    	   addForm.remove(delCmp,true);
														    	   
														       }									
															} ]
														}]
													});
										    	   addForm.add(copy);
										       }									
											} ]
										}]
									} ]

								} ],
								buttons : [
										{
											xtype : "button",
											text : "确定",
											handler : function() {
												
												
												//var sucCmp=new Array();
												if(addFlag!=1){
													return false;
												}
												addFlag=0;
												if (!Ext
														.getCmp(
																"addForm")
														.getForm()
														.isValid()) {

													return false;
												}
												
												 var goodsNum=Ext.getCmp('goodsNum').getValue();
												 var brandName=Ext.getCmp('brandName').getValue();
												// console.log(goodsNum+brandName);
												 var goodsNames=Ext.query('*[name=goodsName]');
												 console.log(goodsNames);
												 var classIds=Ext.query('*[name=goodsClass.id]');
												 //console.log(classIds);
												 var colorIds=Ext.query('*[name=goodsColor.id]');
												 //console.log(colorIds);
												 var sizeIds=Ext.query('*[name=goodsSize.id]');
												// console.log(sizeIds);
												 var counts=Ext.query('*[name=count]');
												 //console.log(counts);
												 var inPrices=Ext.query('*[name=inPrice]');
												// console.log(inPrices);
												 var orderPrices=Ext.query('*[name=orderPrice]');
												// console.log(orderPrices);
												 var discounts=Ext.query('*[name=discount]');
												// console.log(discounts);
												 var vipDiscounts=Ext.query('*[name=vipDiscount]');
												//console.log(vipDiscounts);				
												for(var i=1;i<goodsNames.length;i++){
													var submitFlag=true;
													var goodsName=goodsNames[i].value;
													var nameId=goodsNames[i].id;
													console.log(nameId);
													nameId=nameId.match(/\d+/g);
													 console.log('classId'+nameId);
                                                    for(var j=0;j<sucCmp.length;j++){
														if(nameId==sucCmp[j]){
															submitFlag=false;
														}
													}
											      
													var classId=Ext.getCmp('classId'+nameId).getValue();
													
													//console.log();
													var colorId=Ext.getCmp('colorId'+nameId).getValue();
													var sizeId=Ext.getCmp('sizeId'+nameId).getValue();
													var count=counts[i-1].value;
													var inPrice=inPrices[i-1].value;
													var orderPrice=orderPrices[i-1].value;
													var discount=discounts[i-1].value;
													var vipDiscount=vipDiscounts[i-1].value;
													columnId=nameId+"*copyColumn";

										        	var column=Ext.getCmp(columnId);
							
										        	if(submitFlag){
										        		column.add({
															columnWidth : '.03',
															layout : 'form',
															id:nameId+'*state',
															defaults : {

																labelWidth : 55,
																frame : true,
																border : false,
																labelSeparator : ':',
																labelAlign : 'left',
																msgTarget : 'side',
																allowBlank:false
															},
															items : [ {
																xtype: 'button',
																id:nameId+"*stateButton",
																tooltip : '提交状态',
																iconCls : 'icon-refresh'				
															} ]
														});

														Ext.Ajax.request({
														    url: '${ctx}/goods/goods/addGoods',
														    method:'post',
														    defaultHeaders: {
									                            'Content-Type': 'application/json; charset=iso-8859-1'
									                        },
									                        params: {
														        goodsNum: goodsNum,
														        brandName:brandName,
														        goodsName:goodsName,
														        inPrice:inPrice,
														         classId:classId,
														          colorId:colorId,
														        sizeId:sizeId,
														        count:count,
														        inPrice:inPrice,
														        orderPrice:orderPrice,
														        discount:discount,
														        vipDiscount:vipDiscount,
														        nameId:nameId
														    }, 
														    success: function(response){
														    	var result=Ext.JSON.decode(response.responseText);
														        var text = response.responseText;
														        if(result.success==true){
														        	buttonId=result.nameId+"*stateButton";
														        	var buttonCmp=Ext.getCmp(buttonId);
														        	buttonCmp.setIconCls('icon-yes');
														        	sucCmp.push(result.nameId);
														        }
														    }
														});
										        	}
													
													
											
												}
												addFlag=1;

											}
										}, {
											xtype : "button",
											text : "取消",
											handler : function() {
												win.close();
											}
										} ]
							});
					win.show();
				}

				function edit() {
				  var classStore = Ext.create('Ext.data.Store', {
						defaultRootId:'root',
						//requires:'Demo.model.menuModel',
						//model:'Demo.model.menuModel',
						fields:['id','className'],
						proxy:{
							type:'ajax',
							url:'${ctx}/goods/goodsClass/getGoodsClassInuse/1',
							reader:'json',
							autoLoad:true
						}
					});
					
					var colorStore = Ext.create('Ext.data.Store', {
						defaultRootId:'root',
						//requires:'Demo.model.menuModel',
						//model:'Demo.model.menuModel',
						fields:['id','colorName'],
						proxy:{
							type:'ajax',
							url:'${ctx}/goods/goodsColor/getGoodsColorInuse',
							reader:'json',
							autoLoad:true
						}
					});
					var sizeStore = Ext.create('Ext.data.Store', {
						defaultRootId:'root',
						//requires:'Demo.model.menuModel',
						//model:'Demo.model.menuModel',
						fields:['id','size'],
						proxy:{
							type:'ajax',
							url:'${ctx}/goods/goodsSize/getGoodsSizeInuse',
							reader:'json',
							autoLoad:true,
							
						}
					});

					var selRecords = Ext.getCmp("gridPanel")
							.getSelectionModel().getSelection();
					var id, goodsName, goodsNum, brandName,goodsClass,goodsColor,goodsSize,count,inPrice,orderPrice,discount,vipDiscounnt;
					if (selRecords.length >= 1) {
					console.log(selRecords[0]);
						id = selRecords[0].get("id");
						goodsNum = selRecords[0].get("goodsNum");
						brandName = selRecords[0].get("brandName");
						goodsName=selRecords[0].get("goodsName");
						inUse = selRecords[0].get("inUse");
						goodsClass= selRecords[0].get("goodsClass");
						goodsColor= selRecords[0].get("goodsColor");
						goodsSize= selRecords[0].get("goodsSize");
						count= selRecords[0].get("count");
						inPrice= selRecords[0].get("inPrice");
						orderPrice= selRecords[0].get("orderPrice");
						discount= selRecords[0].get("discount");
						vipDiscount= selRecords[0].get("vipDiscount");
						classId=selRecords[0].get("classId");
						colorId=selRecords[0].get("colorId");
						sizeId=selRecords[0].get("sizeId");
					} else {
						Ext.MessageBox.alert("提示消息", "您未选中行");
						return false;
					}

					var win = new Ext.Window(
							{
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
									items : [
											 {
												xtype : 'hiddenfield',
												name : 'id',
												value : id
											}, {
												fieldLabel : "货号",
												name : "goodsNum",
												id : 'goodsNum',
												value : goodsNum,
												readOnly:true
											},
											{
												fieldLabel : "品牌",
												name : "brandName",
												id : 'brandName',
												value : brandName
											},
											{
												fieldLabel : "名称",
												name : "goodsName",
												id : 'goodsName',
												value : goodsName
											},
											{
																xtype : 'combo',
																name : "goodsClass.id",
																fieldLabel : '分类',
																hiddenName:'goodsClass.id',
																id : 'editClassId',
																//value:'XXX',
																store : classStore,
																displayField : 'className',
																valueField : 'id',
																triggerAction : "all",
																mode : "local",
																editable : false,
															//allowBlank:false
															},
											{
																xtype : 'combo',
																name : "goodsColor.id",
																fieldLabel : '颜色',
																hiddenName:'goodsColor.id',
																id : 'editColorId',
																store : colorStore,
																displayField : 'colorName',
																valueField : 'id',
																triggerAction : "all",
																mode : "local",
																editable : false,
															//allowBlank:false
															},
															{
																xtype : 'combo',
																name : "goodsSize.id",
																fieldLabel : '尺码',
																hiddenName:'goodsSize.id',
																id : 'editSizeId',
																store : sizeStore,
																value:'',
																displayField : 'size',
																valueField : 'id',
																triggerAction : "all",
																mode : "local",
																editable : false,
															//allowBlank:false
															},
															
											{
												fieldLabel : "数量",
												name : "count",
												id : 'count',
												value : count
											},
											{
												fieldLabel : "进价",
												name : "inPrice",
												id : 'inPrice',
												value : inPrice
											},
											{
												fieldLabel : "定价",
												name : "orderPrice",
												id : 'orderPrice',
												value :orderPrice
											},
											{
												fieldLabel : "打折",
												name : "discount",
												id : 'discount',
												value :discount
											},
											{
												fieldLabel : "会员打折",
												name : "vipDiscount",
												id : 'vipDiscount',
												value :vipDiscount
											}

									]
								} ],//vtype的值:alpha,alphanum,email,url
								buttons : [
										{
											xtype : "button",
											text : "确定",
											handler : function() {
												if (!Ext.getCmp("form1")
														.getForm().isValid()) {
													alert("您输入的信息有误,请重新输入...");
													return false;
												}
												Ext
														.getCmp("form1")
														.getForm()
														.submit(
																{
																	clientValidation : true,
																	waitMsg : '请稍候',
																	waitTitle : '正在提交',
																	url : '${ctx}/goods/goods/updateGoods',
																	success : function(
																			form,
																			action) {
																		Ext.MessageBox
																				.show({
																					width : 150,
																					title : "更新成功",
																					buttons : Ext.MessageBox.OK,
																					msg : action.result.msg
																				});
																		win
																				.close();
																		Ext
																				.getCmp("gridPanel").store
																				.reload();
																		 Ext.getCmp("gridPanel").getSelectionModel().deselectAll();
																	},
																	failure : function(
																			form,
																			action) {
																		Ext.MessageBox
																				.show({
																					width : 150,
																					title : "更新失败",
																					buttons : Ext.MessageBox.OK,
																					msg : action.result.msg
																				});
																		// console.log(win.ownerCt);

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
							//console.log(goodsClass);
							classStore.addListener('load', function() {
                               
                              Ext.getCmp('editClassId').setValue(classId);
                              }
                              );
                              classStore.load();
                              sizeStore.addListener('load', function() {
                               
                              Ext.getCmp('editSizeId').setValue(sizeId);
                              }
                              );
                              sizeStore.load();
                              colorStore.addListener('load', function() {
                               
                              Ext.getCmp('editColorId').setValue(colorId);
                              }
                              );
                              colorStore.load();
                              
							//Ext.getCmp('editClassId').setValue("1");
							/* Ext.getCmp('editSizeId').setValue(goodsSize);
							Ext.getCmp('editColorId').setValue(goodsColor);
							console.log(Ext.getCmp('editClassId'));
							Ext.getCmp('editClassId').hiddenValue = goodsClass; */
							//Ext.getCmp('editColorId').hiddenField.value = "goodsColor.id";
							
							//Ext.getCmp('editSizeId').hiddenField.value = "goodsSize.id";
					/* if (autoCreate == '1') {
						Ext.getCmp('prefix_').hide();
					} */

				}
				function returnGoods(){
				//alert(11);
				var selRecords = Ext.getCmp("gridPanel")
							.getSelectionModel().getSelection();
					var id, goodsName, goodsNum, brandName,goodsClass,goodsColor,goodsSize,count,inPrice,orderPrice,discount,vipDiscounnt;
					if (selRecords.length == 1) {
						id = selRecords[0].get("id");
						goodsNum = selRecords[0].get("goodsNum");
						brandName = selRecords[0].get("brandName");
						goodsName=selRecords[0].get("goodsName");
						inUse = selRecords[0].get("inUse");
						goodsClass= selRecords[0].get("goodsClass");
						goodsColor= selRecords[0].get("goodsColor");
						goodsSize= selRecords[0].get("goodsSize");
						count= selRecords[0].get("count");
						inPrice= selRecords[0].get("inPrice");
						orderPrice= selRecords[0].get("orderPrice");
						discount= selRecords[0].get("discount");
						vipDiscount= selRecords[0].get("vipDiscount");
						classId=selRecords[0].get("classId");
						colorId=selRecords[0].get("colorId");
						sizeId=selRecords[0].get("sizeId");
					}else if(selRecords.length > 1){
					    Ext.MessageBox.alert("提示消息", "只能选中一行执行该操作");
						return false;
					}else {
						Ext.MessageBox.alert("提示消息", "您未选中行");
						return false;
					}
					var win = new Ext.Window(
							{
								title : "退货窗口",
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
									items : [
											 {
												xtype : 'hiddenfield',
												name : 'goods.id',
												value : id
											}, {
												fieldLabel : "货号",
												//name : "goodsNum",
												//id : 'goodsNum',
												value : goodsNum,
												readOnly:true
											},
											{
												fieldLabel : "品牌",
												//name : "brandName",
												//id : 'brandName',
												value : brandName,
												readOnly:true
											},
											{
												fieldLabel : "名称",
												//name : "goodsName",
												//id : 'goodsName',
												value : goodsName,
												readOnly:true
											},{
												fieldLabel : "分类",
												//name : "goodsName",
												//id : 'goodsName',
												value : goodsClass,
												readOnly:true
											},{
												fieldLabel : "颜色",
												//name : "goodsName",
												//id : 'goodsName',
												value : goodsColor,
												readOnly:true
											},{
												fieldLabel : "尺码",
												//name : "goodsName",
												//id : 'goodsName',
												value : goodsSize,
												readOnly:true
											},{
												fieldLabel : "总量",
												//name : "goodsName",
												//id : 'goodsName',
												value : count,
												readOnly:true
											},{
											  xtype:'numberfield',
											  fieldLabel : "退货数量",
												name : "count",
												id : 'count',
												value : count,
												maxValue:count,
												minValue:1
											}
									]
								} ],//vtype的值:alpha,alphanum,email,url
								buttons : [
										{
											xtype : "button",
											text : "确定",
											handler : function() {
												if (!Ext.getCmp("form1")
														.getForm().isValid()) {
													alert("您输入的信息有误,请重新输入...");
													return false;
												}
												Ext
														.getCmp("form1")
														.getForm()
														.submit(
																{
																	clientValidation : true,
																	waitMsg : '请稍候',
																	waitTitle : '正在提交',
																	method:'post',
																	url : '${ctx}/goods/returnGoods/addReturnGoods',
																	success : function(
																			form,
																			action) {
																		Ext.MessageBox
																				.show({
																					width : 150,
																					title : "更新成功",
																					buttons : Ext.MessageBox.OK,
																					msg : action.result.msg
																				});
																		win
																				.close();
																		Ext
																				.getCmp("gridPanel").store
																				.reload();
																	    Ext.getCmp("gridPanel").getSelectionModel().deselectAll();
																	},
																	failure : function(
																			form,
																			action) {
																		Ext.MessageBox
																				.show({
																					width : 150,
																					title : "更新失败",
																					buttons : Ext.MessageBox.OK,
																					msg : action.result.msg
																				});
																		// console.log(win.ownerCt);

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
									"确定要删除吗?",
									function(btn) {
										if (btn == "yes") {
											for (var i = 0; i < len; i++) {
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
														url : "${ctx}/goods/goods/betchDelGoods/"
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
															alert("删除失败");
														}

													});
										}
									})
				}
				//var app={};//全局变量  

				Ext.define("Goods", {
					extend : "Ext.data.Model",
					fields : [ {
						name : "id"
					}, {
						name : "goodsName"
					}, {
						name : "addTime"
					}, {
						name : "brandName"
					}, {
						name : "count"
					}, {
						name : "discount"
					}, {
						name : 'goodsClass',
						mapping:'goodsClass.className'
					}, {
					    name:'classId',
					    mapping:'goodsClass.id'
					},
					{
						name : "goodsColor",
						mapping:'goodsColor.colorName'
						
					},{
					   name:'colorId',
					   mapping:'goodsColor.id'
					}, {
						name : "goodsNum"
					}, {
						name : "goodsSize",
						mapping:'goodsSize.size'
					}, {
					    name:'sizeId',
					    mapping:'goodsSize.id'
					},{
						name : "inPrice"
					}, {
						name : "orderPrice"
					}, {
						name : "outPrice"
					}, {
						name : "sellTime"
					}, {
						name : "vip"
					}, {
						name : "vipDiscount"
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
					model : "Goods",
					proxy : {
						type : "ajax",
						url : "${ctx}/goods/goods/getGoods",
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
											columnWidth : '.25',
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
												name : "classId",
												fieldLabel : '分类',
												//id : 'autoCreate',
												store : classStore,
												displayField : 'className',
												valueField : 'id',
												triggerAction : "all",
												mode : "local",
												editable : false,
											//allowBlank:false
											} ]
										},{
											columnWidth : '.25',
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
											columnWidth : '.25',//第一列
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
										},  {
											columnWidth : '.25',
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
							for (var i = length; i >= 0; i--) {
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
						text : '数量',
						dataIndex : 'count'
					}, {
						flex : 5,
						text : '进价(单价)',
						dataIndex : 'inPrice'
					}, {
						flex : 5,
						text : '定价',
						dataIndex : 'orderPrice'
					}, {
						flex : 5,
						text : '打折',
						dataIndex : 'discount'
					}, {
						flex : 5,
						text : '会员打折',
						dataIndex : 'vipDiscount'
					} ],
					viewConfig : {
						getRowClass : function(record, rowIndex, rowParams,
								store) {
							if (rowIndex % 2 != 0) {
								return 'rowClass';
							}

						}
					},
					tbar : [ {
						text : '应用',
						tooltip : '应用货号生成规则',
						iconCls : 'icon-activity',
						handler : apply

					}, {
						text : '添加',
						tooltip : '添加货物',
						iconCls : 'add',
						handler : add

					}, '-', {
						text : '修改',
						tooltip : '修改货物',
						iconCls : 'edit',
						handler : edit
					},'-',{
					    text : '退货',
						tooltip : '退货',
						iconCls : 'icon-cancel',
						handler : returnGoods
					}, '-', {
						text : '删除',
						tooltip : '删除货物',
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