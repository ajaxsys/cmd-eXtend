/*
 * Usage :1.drag GetPath.js to the ie favorites folder.
 * Usage :2.Select files to get paths
 * Usage :3.click GetPath.js icon will get the path
 */
var i=new ActiveXObject('Shell.Application').Windows().Item().Document.SelectedItems();
var a=new Array();

for(var k=0;k<i.Count;k++){
	WScript.echo(i.Item(k).Path);
}


