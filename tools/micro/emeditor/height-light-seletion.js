function isInArray(array, search){
	for (var i = 0; i < array.length; i++) {
        if (array[i] === search) {
            return true;
        }
    }
    return false; 
}

editor.ExecuteCommandByID(4251);// select the whole word
var text = document.selection.Text;

var NG_LIST = ['.', ',', ' ', '-', '&'];
// http://jp.emeditor.com/help/macro/selection/selection_find.htm
// eeFindReplaceCase	大文字と小文字を区別して検索します。
// eeFindReplaceEscSeq	文字列をエスケープ シーケンスで指定します。eeFindReplaceRegExp と組み合わせて指定できません
// ...
if (!isInArray(NG_LIST, text)){
	document.selection.Find(text,eeFindPrevious | eeFindSaveHistory | eeFindReplaceCase | eeFindReplaceOnlyWord); // eeFindNext | eeFindSaveHistory
}


