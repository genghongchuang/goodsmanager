Ext.define('Demo.view.MainContent', {
	extend : 'Ext.tab.Panel',
	id:'mainContent',
	alias:'widget.mainContent',
	title: '内容',
    layout: 'fit',
    region: 'center',
    collapsible: true,
    contentEl: 'contentIframe' ,//�Ѵ��ڵ�htmlԪ����Ϊ��ʾ����
    tools:[{
        type:'refresh',
        tooltip: 'Refresh form Data',
        // hidden:true,
        handler: function(event, toolEl, panelHeader) {
            // refresh logic
        }
    },
    {
        type:'help',
        tooltip: 'Get Help',
        callback: function(panel, tool, event) {
            // show help here
        }
    }],
	items : [{
		
								title : '新闻动态',
								height : 50,
								iconCls : 'icon-news',
								closable : true ,
								item:[
								  	{
								  		xtype:'button'
								  	}
								  	]
							}, {
								title : '最新通知',
								height : 50,
								closable : true ,
								iconCls : 'icon-notice'
							}, {
								title : '业绩报表',
								height : 50,
								closable : true ,
								iconCls : 'icon-chart'
							}, {
								title : '邮件列表',
								height : 50,
								closable : true ,
								iconCls : 'icon-email-list'
							
				
	}]
	
});