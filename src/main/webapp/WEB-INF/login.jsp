<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="global.jsp"%>

<html>
<head>
<title>${title}</title>
<meta http-equiv="Cache-Control" content="no-cache" />
<link rel="icon" href="${ctx}/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="${ctx}/js/login/reset.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/login/login.css" type="text/css" />
<%-- <link rel="stylesheet" type="text/css"
	href="${ctx }/js/extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="${ctx }/js/extjs/bootstrap.js"></script>
<script type="text/javascript"
	src="${ctx }/js/extjs/locale/ext-lang-zh_CN.js"></script> --%>
<script type="text/javascript" src="${ctx }/js/login/login.js"></script>
<script type="text/javascript">
//定义验证码控件
Ext.define('CheckCode',{
    extend: 'Ext.form.field.Text', 
    alias: 'widget.checkcode',
    inputTyle:'codefield',
    codeUrl:Ext.BLANK_IMAGE_URL,
    isLoader:true,
     onRender:function(ct,position){
        this.callParent(arguments);
        this.codeEl = ct.createChild({ tag: 'img', src: Ext.BLANK_IMAGE_URL});
        this.codeEl.addCls('x-form-code');
        this.codeEl.on('click', this.loadCodeImg, this);
        
        if (this.isLoader) this.loadCodeImg();
    }, 
    alignErrorIcon: function() {
        this.errorIcon.alignTo(this.codeEl, 'tl-tr', [2, 0]);
    },
    //如果浏览器发现url不变，就认为图片没有改变，就会使用缓存中的图片，而不是重新向服务器请求，所以需要加一个参数，改变url
    loadCodeImg: function() {
        this.codeEl.set({ src: this.codeUrl + '?id=' + Math.random() });
    }
});
function updateCode(){	
	Ext.getCmp("CheckCode").loadCodeImg();
}

Ext.onReady(function() {
	
	 var checkcode = Ext.create('CheckCode',{
		 cls : 'key',		 	
         fieldLabel : '验证码',
         name : 'checkcode',
         id : 'CheckCode',
         allowBlank : false,
         isLoader:true,
         blankText : '验证码不能为空',
         codeUrl: '${ctx}/login/getCode',
         width : 172,
     });
	var form = Ext.create(
					'Ext.form.Panel',
					{
					    id:'form',
						frame:true,
						title:'huo',
						header:false,
						width:348,
						height:170,
						style: {
                           background: '#f9f7f8',
                           padding: '10px'
                        },
                        bodyStyle:'background:#f9f7f8',
						//渲染到页面中的loginForm层中
						renderTo:'loginForm1',
						//是否可以拖动
						draggable:false,
						//使buttons中的button居中显示
						buttonAlign:'center',
						
						fieldDefaults:{
							fieldStyle: 'width:154px;height:28px;',
							//labelStyle:'float: left;font-size: 14px;width: 90px;',
							 labelStyle:'float: left;font-size: 14px;color: #7b7b7b;width: 90px;padding: 0px 5px 0px 0px;text-align: right;',
							//居左
							labelAlign:'center',
							//宽度
							labelWidth:90,
							//anchor: '90%',
							//错误提示显示在一边(side)，还可以配置为under、title、none
							msgTarget: 'side'
						},
						items:[
						       {
						    	   xtype:'textfield',
						    	   fieldLabel:'用户名',
						    	  
						    	   name:'userName',
						    	   //不允许为空
						    	   allowBlank:false,
						    	   blankText:'用户名不能为空',
						    	
						       },
						       {
						    	   xtype:'textfield',
						    	   fieldLabel:'密    码',
						    	   name:'password',
						    	   inputType:'password',
						    	   allowBlank:false,
						    	   blankText:'密码不能为空'
						       },checkcode,
						       
						],
						minButtonWidth :61,
						buttons:[
						         {  
						        	 style:'width:61px',
						        	// text:'登录',
						        	 width:20,
						        	 height:26,
						        	// icon:'${ctx}/js/login/img/login.gif',
						        	
						        	
						        	 cls:'extButton',
						        	 
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
						        						 url:'${ctx}/login/login',
						        						 success:function(form,action){
						        							 //登录成功后的操作，跳转到toIndex.action
						        							 window.location.href = '${ctx}' 
						        						 },
						        						 failure:function(form,action){
						        							 updateCode();
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
						         }/* ,
						         {
						        	 text:'取消',
						        	 width:80,
						        	 height:30,
						        	 handler:function(){
						        		 //点击取消，关闭登录窗口
						        		 var form = this.up('form');
						        		 form.close();
						        	 }
						         } */
						]
					}
			);
	 

});
</script>
<style type="text/css">
#CheckCode{ float:left;}
.x-form-code{width:69px;height:28px;vertical-align:middle;cursor:pointer; float:left; margin-left:7px;}
.extButton {
 width:20px; 
 height:26px;
 background-image:url(${ctx}/js/login/img/login.gif); 
 border:0; 
 padding:0px;
}

#login input {
	float:left;
	width:154px;
	height:28px;
	padding:2px 5px 2px 5px;
}
.ocx_style {
	border: 1px solid #7F9DB9;
	width: 142px;
	height: 22px;
	background-color: white;
	padding: 2px 5px 2px 5px;
}
</style>
</head>
<body scroll="no">
	<div id="main_content">
		<h1>
			<a><img src="${ctx}/js/login/img/logo.jpg" /><span>${title}</span></a>
		</h1>
		<div id="login_box" style="position: relative;height: 400px">
			<div id="login">
				<div id="login_tab">
					<ul>
						<li id="tab1" class="current userLogin" style="cursor: pointer">用户登录</li>
					</ul>
				</div>
				<div id="loginForm1" class="login_tabcontent"></div>
			</div>
		</div>
	</div>
</body>
</html>
