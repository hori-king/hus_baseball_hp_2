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

// 学部と学科のデータ
const departmentData = {
	"工学部" : ["機械工学科", "情報工学科", "電気電子工学科", "建築学科", "都市環境学科"],
	"情報科学部" : ["情報科学科"],
	"薬学部" : ["薬学科"],
	"保健医療学部" : ["看護学科", "理学療法学科", "臨床工学科", "診療放射線学科"],
	"未来デザイン学部" : ["未来デザイン学科", "人間社会学科"]
};

const facultySelect = document.getElementById('faculty');
const departmentSelect = document.getElementById('department');

// 学部セレクトボックスが存在する場合のみ処理を実行
if(facultySelect && departmentSelect){
	// 学部が変更されたときの処理
	facultySelect.addEventListener('change', function(){
		// 現在の学科の選択肢をクリア
		departmentSelect.innerHTML ='<option value=">選択してください</option>';
		// 選択された学部の値を取得
		const selectedFaculty = this.value;
		
		// 対応する学科のリストがあれば、選択肢を生成
		if(selectedFaculty && departmentData[selectedFaculty]){
			departmentData[selectedFaculty].forEach(function(department){
				const option = document.createElement('option');
				option.value = department;
				option.textContent = department;
				departmentSelect.appendChild(option);
			});
		}
	});
	
	// ページロード時に学部が選択されている場合、その学部に対応する学科を表示
	if(facultySelect.value){
		facultySelect.dispatchEvent(new Event('change'));
	}
};