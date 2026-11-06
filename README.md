# 北海道科学大学 準硬式野球部サイト

Java 21 / Spring Boot / Thymeleaf / MySQL互換DB。

## 月額0円の公開構成

- アプリ: Render Web Service の Free プラン。
- DB: TiDB Cloud Starter の無料枠（MySQL互換）。カードを登録せず、無料枠内で利用します。
- アップロード画像: DBの stored_photos テーブルへ保存。有料ディスク・外部画像サービスは不要です。

Renderは15分アクセスがないと休止し、次のアクセス時に起動を待ちます。
無料枠の上限を超えるとサービスが停止する場合があります。上限と提供条件は各社の管理画面で確認してください。
アカウント共通のRender無料稼働時間は他の無料サービスとも共有します。

## Renderで公開する

1. TiDB CloudでStarterの無料インスタンスを作成し、接続情報を取得します。
2. RenderのNew → BlueprintでこのGitHubリポジトリのrender.yamlを指定します。
3. 次の環境変数を設定して、Freeプランであることを確認しデプロイします。

| 変数 | 内容 |
| --- | --- |
| SPRING_DATASOURCE_URL | jdbc:mysql://接続ホスト:4000/test?sslMode=VERIFY_IDENTITY&characterEncoding=UTF-8&serverTimezone=Asia/Tokyo |
| DB_USERNAME | TiDBが発行した接続ユーザー名 |
| DB_PASSWORD | TiDBの接続パスワード |

パスワードや接続情報をGit・ログ・チャットへ貼らないでください。
管理画面は /login。ユーザー名は admin、初期パスワードはRenderのEnvironmentに自動生成される ADMIN_PASSWORD です。
初期管理者は同名ユーザーが存在しない場合だけ作成されます。環境変数変更では既存パスワードは変わりません。

既存のDBデータはリポジトリに含まれていないため、新規DBは空です。元の記事・部員・試合結果は移行か管理画面からの登録が必要です。
問い合わせはDBに保存して管理画面で確認します。メール通知・送信機能はありません。
画像はJPEG・PNG・GIF、1枚4MB以下に対応し、公開URLから配信します。画像は部員・記事・試合の削除時もDBに残ります。
静的画像は src/main/resources/static/images に含まれます。

## ローカル実行

Java 21を用意し、環境変数 MYSQL_PASSWORD を設定して docker compose up -d でMySQLを起動します。
gradlew.bat bootRun で起動し、http://localhost:8080 にアクセスします。
管理者を初期作成する場合は ADMIN_USERNAME と ADMIN_PASSWORD も設定します。
.envは任意です。公開先は環境変数だけで起動できます。

## 検証

gradlew.bat test bootJar でテスト・ビルドを実行します。
テストはH2のMySQL互換モードで公開ページ、認証、問い合わせ保存、CSRF、画像保存と配信、画像の形式・サイズ制限を確認します。
TiDBの実接続と公開先での起動はデプロイ後にも確認します。
