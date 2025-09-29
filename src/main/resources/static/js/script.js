// --- トップへ戻るボタンの処理 ---

// id="topBtn"の要素を取得
let topButton = document.getElementById("topBtn");

// topBtnというIDを持つボタンが存在する場合のみ、処理を実行
if (topButton) {
	// ▼▼▼ スクロールされたときの処理 ▼▼▼
	window.onscroll = function() {
		// ページ上部から100px以上スクロールされたらボタンを表示
		if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
			topButton.style.display = "block";
		} else {
			topButton.style.display = "none";
		}
	}

	// ▼▼▼ クリックされたときの処理 ▼▼▼
	topButton.onclick = function(e) {
		e.preventDefault(); // aタグのデフォルトの動きを止める
		window.scrollTo({
			top: 0,
			behavior: "smooth" // スムーズにスクロール
		});
	}
}


// --- 学部・学科 連動ドロップダウンの処理 ---

// 学部と学科のデータ
const departmentsData = {
    "保健医療学部": ["看護学科", "臨床工学科", "診療放射線学科", "臨床検査学科", "義肢装具学科", "理学療法学科", "作業療法学科"],
    "未来デザイン学部": ["メディアデザイン学科", "人間社会学科"],
    "工学部": ["機械工学科", "電気電子工学科", "情報通信工学科", "建築学科", "都市環境学科"],
    "薬学部": ["薬学科"]
};

const facultySelect = document.getElementById('faculty');
const departmentSelect = document.getElementById('department');

// 学部と学科のセレクトボックスが存在する場合のみ処理を実行
if (facultySelect && departmentSelect) {
    // 学部が変更されたときの処理
    facultySelect.addEventListener('change', function() {
        // 現在の学科の選択肢を一旦リセット
        departmentSelect.innerHTML = '<option value="">選択してください</option>';
        
        // 選択された学部の値を取得
        const selectedFaculty = this.value;

        // 対応する学科のリストがあれば、選択肢を生成して追加
        if (selectedFaculty && departmentsData[selectedFaculty]) {
            departmentsData[selectedFaculty].forEach(function(department) {
                const option = document.createElement('option');
                option.value = department;
                option.textContent = department;
                departmentSelect.appendChild(option);
            });
        }
    });

    // ページ読み込み時に、もし学部がすでに選択されていたら（編集画面の場合）、
    // 上記の'change'イベントを手動で発生させて、学科の選択肢を初期表示する
    if (facultySelect.value) {
        facultySelect.dispatchEvent(new Event('change'));
    }
}