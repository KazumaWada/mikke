# mikke

フォルダの中のファイルを全部たどって、検索ワードがある行を表示する CLI です（grep の小さい版）。

## インストール（Homebrew）

```sh
brew install KazumaWada/mikke/mikke
```

Java（openjdk）は brew が一緒に入れます。

## 使い方

```sh
mikke <検索ワード> <フォルダ>
```

例:

```console
$ mikke TODO demo
[📄demo/src/app.txt]
2行目:// TODO: fix this

TODOは、1個見つかりました
正常終了
```

## 終了コード

grep と同じです。

| コード | 意味 |
|---|---|
| 0 | 1件以上見つかった |
| 1 | 見つからなかった |
| 2 | 使い方が間違っている（引数が2つでない） |

## ライセンス

MIT
