Ext.define('Demo.view.menuTree',{
	extend:'Ext.tree.Panel',
	alias:'widget.menutree',
	border:false,
	hrefTarget:'mainContent',//���ӵ�ַ����ʾ����
	rootVisible: false,//�Ƿ���ʾ���ڵ�
	store:'menuStore',//���ݼ�
	bodyStyle:{//�˵���ʽ
	  background:'#ffc',
	  padding:'10px'
	}

});