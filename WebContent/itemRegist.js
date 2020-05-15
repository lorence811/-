
/* 商品情報を登録するファンクション */
var registItem = function () {
	// 入力された商品コード
	var inputItemCd = $('#js-input-code').val();
	// 入力された商品名
	var inputItemName = $('#js-input-name').val();
	// 入力された商品名カナ
	var inputItemNameKana = $('#js-input-kana').val();
	// 入力された価格
	var inputSalesPrice = $('#js-input-price').val();
	// 入力された在庫数
	var inputStock = $('#js-input-stock').val();
	// 入力された画像URL
	var inputUrl = $('#js-input-url').val();
	// 入力された商品説明
	var inputDescription = $('#js-input-description').val();
	var requestQuery = {
		itemCd : inputItemCd,
		itemName : inputItemName,
		itemNameKana : inputItemNameKana,
		salesPrice : inputSalesPrice,
		stock : inputStock,
		imageUrl : inputUrl,
		description : inputDescription
	};
	console.log('requestQuery',requestQuery);
	// サーバーにデータを送信する。
	$.ajax({
		type : 'POST',
		dataType:'json',
		url : '/myFirstApp/ItemRegistServlet',
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', json);
			// 登録完了のアラート
			alert('登録が完了しました');
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}


/**
 * 読み込み時の動作
 */
$(document).ready(function() {
	// 登録ボタンを押したときのイベント
	$('#js-regist-button').click(registItem);

});