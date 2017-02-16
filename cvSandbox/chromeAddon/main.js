$(function(){
	//img情報取得
	var list = document.images;
	//console.log(list);

	$.each(list, function(i, val) {
	    //TODO 画像1枚ずつAPIを投げる
	    //TODO 差し替え画像URLが返却されたらhtml.replaceする
        console.log(val.src);
    });

	// ページ内を加工するテスト

//	$('body').html(
//		$('body').html().replace( /!/g, "(ﾟ∀ﾟ)!!!" )
//	);

});