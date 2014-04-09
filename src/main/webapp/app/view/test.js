Ext.define('Demo.view.Test', {
	extend : 'Ext.tab.Panel',
	id:'test',
	region: 'center',
	alias:'widget.test',
	items: [
	        {
	            title: 'Tab 1',
	            html : 'A simple tab'
	        },
	        {
	            title: 'Tab 2',
	            html : 'Another one'
	        }
	    ],
	    renderTo : Ext.getBody()
});

