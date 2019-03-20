# Change Log

## 1.4.0 (2019-03-20)
- Kotlinを1.3.21にアップデート、それに伴いコードを微修正

## 1.3.0 (2018-07-18)
- `CombinatorialSequence` の型パラメータを共変に変更
- `CartesianProductGenerator` の変更点
    - `repeat` に巨大な数を指定したときなどでオーバーフローしたとき例外を発生させる
    - パフォーマンスが向上

## 1.2.0 (2018-07-15)
- シーケンス生成のパフォーマンスが向上（ `buildIterator` で生成した `Iterator` を自前で作るようにしたため）

## 1.1.0 (2018-07-14)

- 完全順列を生成するクラス `DerangementGenerator` を追加
- Iterable, Array の拡張関数 `derangements` を追加
- ソースファイルのコピーライトヘッダーを少し変更
- 順列生成のパフォーマンスを少し向上

## 1.0.0 (2018-07-12)
- Maven Central Repository にリリース
