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
	function addShopcar(id, count) {
		var win = new Ext.Window({
			title : "购买数量",
			width : 250,
			height : 100,
			iconCls : "icon-shopcar1",
			buttonAlign : 'center',
			items : [ {
				xtype : "form",
				id : "addShopcarForm",
				height : "200",
				borderStyle : "padding-top:3px",
				frame : true,
				defaultType : "textfield",
				labelAlign : "right",
				labelWidth : 57,
				defaults : {
					// allowBlank: true, 
					width : 200,
					msgTarget : 'side'
				},
				items : [ {
					xtype : 'numberfield',
					//id : 'orderCount',
					fieldLabel : '数量',
					name : 'count',
					minValue : '1',
					value : '1',
					maxValue : count
				}, {
					xtype : 'hiddenfield',
					name : 'goodsId',
					value : id,
					columns : 1

				} ]
			} ],
			buttons : [ {
				xtype : "button",
				text : "提交",
				handler : function() {
					console.log(Ext.getCmp("addShopcarForm").getForm());
					if (!Ext.getCmp("addShopcarForm").getForm().isValid()) {

						return false;
					}
					Ext.getCmp("addShopcarForm").getForm().submit({
						clientValidation : true,
						waitMsg : '请稍候',
						waitTitle : '正在添加',
						url : '${ctx}/goods/goodsOrder/addShopcar',
						success : function(form, action) {
							Ext.MessageBox.show({
								width : 150,
								title : "加入成功",
								buttons : Ext.MessageBox.OK,
								msg : action.result.msg
							});
							win.close();
						},
						failure : function(form, action) {
							Ext.MessageBox.show({
								width : 150,
								title : "加入失败",
								buttons : Ext.MessageBox.OK,
								msg : action.result.msg
							});
						}

					});
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
	function deleteOrder(ids){
	var len = ids.length;
	if (len == 0) {
	                Ext.MessageBox.alert("提示消息", "请刷新重试！");
	                return false;
	            }
	                 Ext.Msg.confirm("提示", "确定要删除吗?", function (btn) {
	                if (btn == "yes") {
	                 
	                    Ext.Ajax.request({
	                        url: "${ctx}/goods/goodsOrder/betchDelGoodsOrder/"+ids,
	                        success: function (reponse, option) {
	                        	json=eval("("+reponse.responseText+")");
	                        	console.log(json);
	                        	var msg="删除失败！";
	                        	if(json.success){
	                        	msg="删除成功！";
	                        	}
	                        	 Ext.MessageBox.show({					
                                     width:150,
                                     title:"操作结果",
                                     buttons: Ext.MessageBox.OK,
                                     msg:msg
                                 }) ;
	                 
	                            Ext.getCmp('shopcarGridPanel').getStore().reload();
	                            setTotalPrice();
	                        },
	                        failure: function () {
	                            alert("删除失败");
	                        }

	                    });
	                }
	            });
	}
	function betchDeleteOrder(){
	 var selRecords = Ext.getCmp('shopcarGridPanel').getSelectionModel().getSelection();
	    var len = selRecords.length;
	    var ids = "";
	            if (len == 0) {
	                Ext.MessageBox.alert("提示消息", "您未选中行");
	                return false;
	            }
	            Ext.Msg.confirm("提示", "确定要删除吗?", function (btn) {
	                if (btn == "yes") {
	                    for (var i = 0; i < len; i++) {
	                        if (i == len - 1) { ids += selRecords[i].get("id"); }
	                        else {
	                            ids += selRecords[i].get("id") + ",";
	                        }
	                    }
	                    Ext.Ajax.request({
	                        url: "${ctx}/goods/goodsOrder/betchDelGoodsOrder/"+ids,
	                        success: function (reponse, option) {
	                        	json=eval("("+reponse.responseText+")");
	                        	 Ext.MessageBox.show({					
                                     width:150,
                                     title:"操作结果",
                                     buttons: Ext.MessageBox.OK,
                                     msg:json.msg
                                 }) ;
	                 
	                            Ext.getCmp('shopcarGridPanel').store.reload();
	                            setTotalPrice();
	                        },
	                        failure: function () {
	                            alert("删除失败");
	                        }

	                    });
	                }
	            })

	}
	//设置商品总价格
	function setTotalPrice(){
	 var accountNum=Ext.getCmp('accountNum').getValue();
	 //alert(accountNum);
	 var orderId=Ext.getCmp('orderId').getValue();
	 Ext.Ajax.request({
	 url:'${ctx}/goods/goodsOrder/getTotalPrice',
	 params:{orderId:orderId,accountNum:accountNum},
	 success:function(response){
	 var json=eval("("+ response.responseText+ ")");
	 Ext.getCmp('totalPrice').setValue(json.count);
	// alert(json.count);
	 }
	 });
	}
	//文本框失去焦点改变购物车总价
	function changeTotal(me,e,id){
	  Ext.Msg.confirm('修改应收款','您确定修改应收款吗？',function(button,text){ 
	  if(button=='yes'){
	    Ext.Ajax.request({
	  url:'${ctx}/goods/goodsOrder/updatOrderPrice',
	  params:{orderId:id,price:me.value},
	   success:function(response){
	   var json=eval("("+ response.responseText+ ")");
	     Ext.Msg.show({
          title:'操作结果',
          msg: json.msg,
          buttons: Ext.Msg.YES,
          icon: Ext.Msg.OK
                        });
        Ext.getCmp('shopcarGridPanel').getStore().reload();
        setTotalPrice();
	   
	   }});
	  
	  }
	  	  });
	
	}
	
	
	
	
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
				function clearShopcar() {
					betchDeleteOrder();
				}
				var vip=null;
				if(vip){
				}else{
				Ext.define("GoodsOrder", {
					extend : "Ext.data.Model",
					fields : [ {
						name : "id"
					}, {
						name : "goodsName",
						mapping : 'goods.goodsName'
					}, {
						name : "brandName",
						mapping : 'goods.brandName'
					}, {
						name : "count"
					}, {
						name : "discount",
						mapping : 'goods.discount'
					},{name:'vipDiscount',
					mapping:'goods.vipDiscount'}, {
						name : 'goodsClass',
						mapping : 'goods.goodsClass.className'
					}, {
						name : 'classId',
						mapping : 'goods.goodsClass.id'
					}, {
						name : "goodsColor",
						mapping : 'goods.goodsColor.colorName'

					}, {
						name : 'colorId',
						mapping : 'goods.goodsColor.id'
					}, {
						name : "goodsNum",
						mapping : 'goods.goodsNum'
					}, {
						name : "goodsSize",
						mapping : 'goods.goodsSize.size'
					}, {
						name : 'sizeId',
						mapping : 'goods.goodsSize.id'
					}, {
						name : "orderPrice",
						mapping : 'goods.orderPrice'
					} ,{
					name:'realPrice'
					}]
				});
				}
				
				var goodsOrderStore = Ext.create("Ext.data.JsonStore", {
					// baseParams: {},
					autoLoad : {
						orderId : 0,
						start : 0,
						limit : 5,
						page : 1
					},
					pageSize : 5, // 分页大小
					model : "GoodsOrder",
					proxy : {
						type : "ajax",
						url : "${ctx}/goods/goodsOrder/getGoodsOrder",
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

				function shopcar() {
					var orderSm = Ext.create('Ext.selection.CheckboxModel', {
						checkOnly : true
					});
					var orderPageBar = Ext
							.create(
									'Ext.PagingToolbar',
									{
										id : 'orderPageBar',
										//	dock: 'bottom', //分页 位置  
										afterPageText : '共{0}页',
										beforePageText : '当前页',
										lastText : "尾页",
										nextText : "下一页",
										prevText : "上一页",
										firstText : "首页",
										refreshText : "刷新页面",
										store : goodsOrderStore,
										displayInfo : true,
										displayMsg : '显示{0}-{1}条，共{2}条记录',
										emptyMsg : "没有数据",
										items : [
												{
													text : "全选",
													handler : function() {
														var rsm = Ext
																.getCmp(
																		'shopcarGridPanel')
																.getSelectionModel(); //得到选择模型
														rsm.selectAll();
													}
												},
												{
													text : "全不选",
													handler : function() {
														var length = goodsOrderStore.data.length - 1
														var rsm = Ext
																.getCmp(
																		'shopcarGridPanel')
																.getSelectionModel(); //得到选择模型
														rsm.deselectRange(0,
																length);
													}
												},
												{
													text : "反选",
													handler : function() {
														var rsm = Ext
																.getCmp(
																		'shopcarGridPanel')
																.getSelectionModel(); //得到选择模型
														var length = goodsOrderStore.data.length - 1;
														for ( var i = length; i >= 0; i--) {
															if (rsm
																	.isSelected(i)) {

																rsm.deselect(i,
																		true);
															} else {

																rsm.select(i,
																		true); //必须保留原来的,否则效果无法显示
															}
														}
													}
												},
												 ]
									});

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
							url : '${ctx}/system/signIn/getSignIns',
							reader : 'json',
							autoLoad : true
						}
					});
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
					var orderId;
					jsonStore
							.load({
								callback : function(records, operation, success) {
									itemsInGroup = [];

									//console.log(records);

									for ( var i = 0; i < records.length; i++) {
										//console.log(records[i].data);
										itemsInGroup.push(records[i].data);
									}
									var win = new Ext.Window(
											{
												title : "购物车",
												width : 850,
												height : 400,
												iconCls : "icon-shopcar1",
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
													defaults : {
														 
														width : 850,
														msgTarget : 'side'
													},
													items : [
															{
																//xtype : 'radiogroup',
																id : 'orderId',
																fieldLabel : '订单号',
																name : 'id',
																value : '',
																readOnly : true,
																width : 400,
																columns : 1
															},
															{
																//xtype : 'radiogroup',
																//id : 'accountNum',
																fieldLabel : '会员卡号',
																width : 400,
																//name : 'accountNum',
																columns : 1,
																listeners : {
																	"blur" : function() {
																	me=this;
																	   
																		var vipNum = this
																				.getValue();
																		if(vipNum.length>0){
																											Ext.Ajax
																				.request({
																					url : '${ctx}/vip/getVip',
																					method : 'post',
																					params: {
                                                                                            accountNum: vipNum
                                                                                           },
																					success : function(
																							response,
																							options) {
																							//console.log(response.responseText);
																							
																							if(response.responseText){
																							   vip=Ext.JSON.decode(response.responseText);
																							  me.setValue("("+vip.accountNum+")"+vip.username);
																							  Ext.getCmp('score').show();
																							  Ext.getCmp('accountNum').setValue(vip.accountNum);
																							  var newcolumns = [];
																							  newcolumns.push({flex : 7,text:'货号',dataIndex:'goodsNum'},{flex : 7,text:'品牌',dataIndex:'brandName'},{flex : 7,text:'名称',dataIndex:'goodsName'},{flex : 5,text:'分类',dataIndex:'goodsClass'},{flex : 5,text:'颜色',dataIndex:'goodsColor'},{flex : 5,text:'尺码',dataIndex:'goodsSize'},{flex : 5,text:'尺码',dataIndex:'goodsSize'},{flex : 5,text:'数量',dataIndex:'count'},{flex : 5,text:'定价',dataIndex:'orderPrice'},{flex : 5,text: '折扣', dataIndex: 'vipDiscount' },
																		{
																			flex : 5,
																			text : '应收（元）',
																			dataIndex : 'orderPrice',
																			renderer : function(value, metaData,record,rowIndex,colIndex,store,view) {
																			
					
																				return Ext.String
																						.format(
																								'{0}',
																								record.data['orderPrice']*record.data['vipDiscount']*0.1);
																			}
																		},
																		{
																			flex : 5,
																			text : '实收（元）',
																			dataIndex : 'orderPrice',
																			renderer : function(value, metaData,record,rowIndex,colIndex,store,view) {
																			if(record.data['realPrice']>0){
																					  return Ext.String
																						.format(
																								'<input style="height:17px;width:40px;" type="text" value="{0}" onBlur="changeTotal(this,event,{1});"></input>',
																								record.data['realPrice'],record.data['id']);
																				}else{
																				  return Ext.String
																						.format(
																								'<input style="height:17px;width:40px;" type="text" value="{0}" onBlur="changeTotal(this,event,{1});"></input>',
																								record.data['orderPrice']*record.data['vipDiscount']*0.1,record.data['id']);
																				}
																			
					
																				
																			}
																		},
																		{
																			flex : 5,
																			text : '操作',
																			dataIndex : 'id',
																			renderer : function(
																					value) {
																				return Ext.String
																						.format('<a href="javascript:void(0)" onclick="deleteOrder({0})">删除</a>',value);
																			}});
																							   Ext
															.getCmp('shopcarGridPanel').reconfigure(Ext
															.getCmp('shopcarGridPanel').store, newcolumns);
															//alert(orderId);
																							 /*  Ext
															.getCmp('shopcarGridPanel').store
															.getProxy().extraParams = {
														orderId : orderId
													}; */
													setTotalPrice();
													Ext.getCmp(
															'shopcarGridPanel')
															.getStore()
															.reload();
																							  
																							}else{
																							Ext.getCmp('accountNum').setValue("");
																							setTotalPrice();
																								  var newcolumns = [];
																							  newcolumns.push({flex : 7,text:'货号',dataIndex:'goodsNum'},{flex : 7,text:'品牌',dataIndex:'brandName'},{flex : 7,text:'名称',dataIndex:'goodsName'},{flex : 5,text:'分类',dataIndex:'goodsClass'},{flex : 5,text:'颜色',dataIndex:'goodsColor'},{flex : 5,text:'尺码',dataIndex:'goodsSize'},{flex : 5,text:'尺码',dataIndex:'goodsSize'},{flex : 5,text:'数量',dataIndex:'count'},{flex : 5,text:'定价',dataIndex:'orderPrice'},{flex : 5,text: '折扣', dataIndex: 'vipDiscount' },
																		{
																			flex : 5,
																			text : '应收（元）',
																			dataIndex : 'orderPrice',
																			renderer : function(value, metaData,record,rowIndex,colIndex,store,view) {
																			
					
																				return Ext.String
																						.format(
																								'{0}',
																								record.data['orderPrice']*record.data['discount']*0.1);
																			}
																		},
																		{
																			flex : 5,
																			text : '实收（元）',
																			dataIndex : 'orderPrice',
																			renderer : function(value, metaData,record,rowIndex,colIndex,store,view) {
																			if(record.data['realPrice']>0){
																					  return Ext.String
																						.format(
																								'<input style="height:17px;width:40px;" type="text" value="{0}" onBlur="changeTotal(this,event,{1});"></input>',
																								record.data['realPrice'],record.data['id']);
																				}else{
																				  return Ext.String
																						.format(
																								'<input style="height:17px;width:40px;" type="text" value="{0}" onBlur="changeTotal(this,event,{1});"></input>',
																								record.data['orderPrice']*record.data['discount']*0.1,record.data['id']);
																				}
																			
					
																				
																			}
																		},
																		{
																			flex : 5,
																			text : '操作',
																			dataIndex : 'id',
																			renderer : function(
																					value) {
																				return Ext.String
																						.format('<a href="javascrip:void(0);" onclick="deleteOrder({0})">删除</a>',value);
																			}});
																							Ext
															.getCmp('shopcarGridPanel').reconfigure(Ext
															.getCmp('shopcarGridPanel').store,newcolumns );
																							Ext.getCmp(
															'shopcarGridPanel')
															.getStore()
															.reload();
																							 me.markInvalid("该会员不存在!");
																							
																							
																							
																							}
																							
																							
																							

																					},
																					failure : function(
																							response,
																							options) {
																						Ext.MessageBox
																								.alert(
																										'失败',
																										'请求超时或网络故障,错误编号：'
																												+ response.status);
																					}

																				});
																		
																	}
																		}
									
																}

															},{
																xtype : 'numberfield',
																id : 'score',
																fieldLabel : '积分',
																name : 'score',
																hidden:true,
																value : '',
																readOnly : true,
																width : 400,
																columns : 1
															},
															{
															xtype:'hiddenfield',
															id:'accountNum',
															name:'accountNum'
															},
															{
																width : 200,
																xtype : 'combo',
																name : "user.id",
																fieldLabel : '销售人',
																id : 'sellUser',
																store : treestore,
																displayField : 'text',
																valueField : 'pid',
																triggerAction : "all",
																mode : "local",
																editable : false,
																allowBlank : false
															},{
																//xtype : 'radiogroup',
																id : 'totalPrice',
																fieldLabel : '总计（元）',
																//name : 'id',
																value : '',
																readOnly : true,
																width : 400,
																columns : 1
															},
															{
																xtype : 'grid',
																id : 'shopcarGridPanel',
																frame : true,
																width : 820,
																store : goodsOrderStore,
																selModel : orderSm,
																columns : [
																		{
																			flex : 7,
																			text : '货号',
																			dataIndex : 'goodsNum'
																		},
																		{
																			flex : 7,
																			text : '品牌',
																			dataIndex : 'brandName'
																		},

																		{
																			flex : 7,
																			text : '名称',
																			dataIndex : 'goodsName'
																		},
																		{
																			flex : 5,
																			text : '分类',
																			dataIndex : 'goodsClass'
																		},
																		{
																			flex : 5,
																			text : '颜色',
																			dataIndex : 'goodsColor'
																		},
																		{
																			flex : 5,
																			text : '尺码',
																			dataIndex : 'goodsSize'
																		},
																		{
																			flex : 5,
																			text : '数量',
																			dataIndex : 'count'
																		},
																		{
																			flex : 5,
																			text : '单价（元）',
																			dataIndex : 'orderPrice'
																		},

																		{
																			flex : 5,
																			text : '打折',
																			dataIndex : 'discount'
																		},
																		{
																			flex : 5,
																			text : '应收（元）',
																			dataIndex : 'orderPrice'
																		},
																		{
																			flex : 5,
																			text : '实收（元）',
																			dataIndex : 'orderPrice',
																			renderer : function(
																					value, metaData,record,rowIndex,colIndex,store,view) {
																				if(record.data['realPrice']>0){
																					  return Ext.String
																						.format(
																								'<input style="height:17px;width:40px;" type="text" value="{0}" onBlur="changeTotal(this,event,{1});"></input>',
																								record.data['realPrice'],record.data['id']);
																				}else{
																				  return Ext.String
																						.format(
																								'<input style="height:17px;width:40px;" type="text" value="{0}" onBlur="changeTotal(this,event,{1});"></input>',
																								record.data['orderPrice'],record.data['id']);
																				}
																				
																			}
																		},
																		{
																			flex : 5,
																			text : '操作',
																			dataIndex : 'id',
																			renderer : function(
																					value) {
																				return Ext.String
																						.format('<a href="javascrip:void(0);" onclick="deleteOrder({0})">删除</a>',value);
																			}
																		} ],
																viewConfig : {
																	getRowClass : function(
																			record,
																			rowIndex,
																			rowParams,
																			store) {
																		if (rowIndex % 2 != 0) {
																			return 'rowClass';
																		}

																	}
																},
																tbar : [ {
																	text : '批量删除',
																	tooltip : '批量清空购物车',
																	iconCls : 'remove',
																	handler : clearShopcar
																} ],
																bbar : orderPageBar,

																buttonAlign : "center",

																buttons : []
															}]
												} ],
												buttons : [
														{
															xtype : "button",
															text : "提交",
															handler : function() {
																if (!Ext
																		.getCmp(
																				"form1")
																		.getForm()
																		.isValid()) {

																	return false;
																}
																var orderId=Ext.getCmp("orderId").getValue();
																var accountNum=Ext.getCmp("accountNum").getValue();
																var user=Ext.getCmp("sellUser").getValue();
																//alert(orderId+"---"+accountNum+"--"+user);
													           Ext.Ajax.request({
													           url:'${ctx}/goods/goodsOrder/sell',
													           params:{orderId:orderId,accountNum:accountNum,userId:user},
													           success:function(response,
														options){
														      var json=Ext.JSON.decode(response.responseText);
														      Ext.MessageBox.alert("成功",json.msg);
													             
													           },
													           failure : function(response,
														options) {
													Ext.MessageBox
															.alert(
																	'失败',
																	'请求超时或网络故障,错误编号：'
																			+ response.status);
												}
													           });
																
																win.close();
																Ext.getCmp(
															'gridPanel')
															.getStore()
															.reload();
															//alert(111);
																
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
									
									
									Ext.Ajax
											.request({
												url : '${ctx}/goods/goodsOrder/getOrderId',
												method : 'post',
												success : function(response,
														options) {

													Ext
															.getCmp('orderId')
															.setValue(
																	response.responseText);
													 orderId = response.responseText;
													 setTotalPrice();
													Ext
															.getCmp('shopcarGridPanel').store
															.getProxy().extraParams = {
														orderId : orderId
													};
													Ext.getCmp(
															'shopcarGridPanel')
															.getStore()
															.reload();

												},
												failure : function(response,
														options) {
													Ext.MessageBox
															.alert(
																	'失败',
																	'请求超时或网络故障,错误编号：'
																			+ response.status);
												}

											});

								}
							});

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
						mapping : 'goodsClass.className'
					}, {
						name : 'classId',
						mapping : 'goodsClass.id'
					}, {
						name : "goodsColor",
						mapping : 'goodsColor.colorName'

					}, {
						name : 'colorId',
						mapping : 'goodsColor.id'
					}, {
						name : "goodsNum"
					}, {
						name : "goodsSize",
						mapping : 'goodsSize.size'
					}, {
						name : 'sizeId',
						mapping : 'goodsSize.id'
					}, {
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
						url : "${ctx}/goods/goods/getGoodsForSell",
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
										}, {
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
										}, {
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
				var dataGrid = Ext
						.create(
								'Ext.grid.Panel',
								{
									id : 'gridPanel',
									frame : true,
									store : store,
									selModel : sm,
									columns : [
											rn,
											{
												flex : 7,
												text : '货号',
												dataIndex : 'goodsNum'
											},
											{
												flex : 7,
												text : '品牌',
												dataIndex : 'brandName'
											},

											{
												flex : 7,
												text : '名称',
												dataIndex : 'goodsName'
											},
											{
												flex : 5,
												text : '分类',
												dataIndex : 'goodsClass'
											},
											{
												flex : 5,
												text : '颜色',
												dataIndex : 'goodsColor'
											},
											{
												flex : 5,
												text : '尺码',
												dataIndex : 'goodsSize'
											},
											{
												flex : 5,
												text : '数量',
												dataIndex : 'count'
											},
											{
												flex : 5,
												text : '定价',
												dataIndex : 'orderPrice'
											},
											{
												flex : 5,
												text : '打折',
												dataIndex : 'discount'
											},
											{
												flex : 5,
												text : '会员打折',
												dataIndex : 'vipDiscount'
											},
											{
												flex : 5,
												text : '操作',
												dataIndex : 'id',
												renderer : function(value,
														cellmeta, record,
														rowIndex, columnIndex,
														store) {
													var count = record.data['count'];
													return Ext.String
															.format('<a href=# onClick="addShopcar('
																	+ value
																	+ ','
																	+ count
																	+ ')">加入购物车</a>');
												}
											} ],
									viewConfig : {
										getRowClass : function(record,
												rowIndex, rowParams, store) {
											if (rowIndex % 2 != 0) {
												return 'rowClass';
											}

										}
									},
									tbar : [ {
										text : '购物车',
										tooltip : '购物车',
										iconCls : 'icon-shopcar',
										handler : shopcar

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