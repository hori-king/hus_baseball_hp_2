# 北海道科学大学 準硬式野球部サイト

Java 21 / Spring Boot / Thymeleaf / MySQL 8.4。

## Renderで公開する

1. RenderのNew → BlueprintでこのGitHubリポジトリを指定します。
2. render.yamlによりWebサービスと非公開MySQL、永続ディスク2つを作成します。
3. 料金を確認してデプロイします。無料構成ではありません。
4. WebサービスのURLから公開サイトにアクセスします。
5. 管理画面は /login。ユーザー名は admin、初期パスワードはWebサービスのEnvironmentに自動生成された ADMIN_PASSWORD です。パスワードをGitやログに記録しないでください。

初期管理者は同名ユーザーが存在しない場合だけ作成されます。環境変数を変更しても既存ユーザーのパスワードは変わりません。
既存のMySQLデータはリポジトリに含まれていないため、新規DBは空です。既存記事・部員・試合結果はDBの移行か管理画面からの登録が必要です。
問い合わせはDBに保存して管理画面で確認します。メール通知・メール送信機能はありません。

MySQLのデータは /var/lib/mysql、アップロード画像は /app/uploads の永続ディスクに保存します。
静的画像はリポジトリの src/main/resources/static/images に含まれます。

## ローカル実行

Java 21を用意し、環境変数 MYSQL_PASSWORD を設定して docker compose up -d でMySQLを起動します。
gradlew.bat bootRun で起動し、http://localhost:8080 にアクセスします。
管理者を初期作成する場合は ADMIN_USERNAME と ADMIN_PASSWORD も設定します。
.env は任意です。公開先では環境変数のみで起動できます。

## 検証

gradlew.bat test bootJar でテストとビルドを実行します。
テストはH2のMySQL互換モードで公開ページ、管理画面の認証、問い合わせの保存、CSRFを確認します。
実際のMySQL接続とDockerでの起動は公開先でも確認してください。
