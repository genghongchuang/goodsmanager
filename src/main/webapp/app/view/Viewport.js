// viewport���������������ʾ����ö�����Ⱦ���������body����
Ext.define('Demo.view.Viewport', {
	extend : 'Ext.container.Viewport',
	layout : 'border',
	items : [
	Ext.create("Demo.view.Header"),
	 {
		title : '菜单',
		region : 'west',
		id:'menutree',
		width : 200,
		split : true,
		collapsible: true,
		items:[{
           xtype: 'menutree'
    }]


	},Ext.create('Demo.view.MainContent')
	/*,
		{
		extend : 'Ext.panel.Panel',
		id: 'mainContent',
        title: '内容',
        layout: 'fit',
        region: 'center',
        collapsible: true,
        contentEl: 'contentIframe' //�Ѵ��ڵ�htmlԪ����Ϊ��ʾ����

	}*/
	]
	

})