$(function(){
	//img情報取得
	var list = document.images;
	console.log(list);
	$.each(list, function(i, val) {
        console.log(val.src);
    });

	// ページ内を加工するテスト

//	$('body').html(
//		$('body').html().replace( /ー/g, "━━━(ﾟ∀ﾟ)━━━" )
//	);

});