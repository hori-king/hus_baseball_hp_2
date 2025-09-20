let topButton = document.getElementById("topBtn");

//トップボタンの表示・非表示とスクロール処理
if(topButton){
	window.onscroll = function(){
		// ページがスクロールされたときに実行する処理
		if(document.body.scrollTop > 100 || document.documentElement.scrollTop > 100){
			topButton.style.display = "block";
		}else{
			topButton.style.display = "none";
		}
	}
	
	// ボタンがクリックされたときに実行する処理
	topButton.onclick = function(e){
		e.preventDefault();
		window.scrollTo({
			top: 0,
			behavior: "smooth"
		});
	}
}