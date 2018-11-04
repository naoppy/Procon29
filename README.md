# Procon29
プロコン29競技部門のビジュアライザとソルバーです。
![Overview](https://raw.githubusercontent.com/naoppy/Procon29/master/docs/ReadmeResources/overview.png?token=AbSzmU40ifUi9u3zfw5Oe57tgj84O5aUks5b55JuwA%3D%3D)

## Developer
naoppy(@naoppyj)

ecasdqina(@fyba_, @ecasdqina)

ピッチぃー(@d4wnin9)

## Contents
- [Requirements](#Requirements)
- [Building](#building)
- [Usage and examples](#usage-and-examples)
- [API documentation](#api-documentation)
- [Development and contributing](#Development-and-contributing)

## Requirements
JavaFX2

## Building
`/resource/`フォルダ、`/src/`フォルダ、`/test/`フォルダ以下の全てのファイルにパスを通してください

## Usage and examples
実行、開発にはIntellij IDEAを使用することを推奨します。

### ・簡単な動かし方
`src/procon29/akashi/Main`をコンパイルして実行する

QRコードをコンソールに入力、このとき改行文字ではなく`[A-z]`が入力終わりを表すことに注意！

舞台に対して右か左か、ターン数を入力するとゲームが始まる。

![run1](https://raw.githubusercontent.com/naoppy/Procon29/master/docs/ReadmeResources/howToRun1.png?token=AbSzmauASnmkecSckEXPD-Ar5kprDfNwks5b55LqwA%3D%3D)

GUIが起動する。GUI上のグリッドをクリックすることで敵の位置を入力する。
敵の位置を間違えてクリックした場合、もう一度クリックすることで取り消すことができる。

敵の位置を入力できたら、左のSolveボタンを押すと位置が確定され、ゲームのメインループが始まる。

1. 自動でSolverが走り、味方のプレイヤー2人の動きが矢印で表示される。
![run2](https://raw.githubusercontent.com/naoppy/Procon29/master/docs/ReadmeResources/howToRun2.png?token=AbSzmRvbW5354PGVT0_8UrQK-oNAY2uKks5b55MGwA%3D%3D)

2. 敵の動く方向を入力する。
設定したいプレイヤー(主に敵プレイヤーDだが、味方プレイヤーも一応設定できる)をクリックする。
その後、動く方向をクリックする。(8近傍と同じ場所の9箇所が選べる)。
すると、行動に応じて右側に矢印がでる。Sはstay(待機)のS。
![run3](https://raw.githubusercontent.com/naoppy/Procon29/master/docs/ReadmeResources/howToRun3.png?token=AbSzmRXyAaznyRcfIBqlNc1Nd450HJF2ks5b55M3wA%3D%3D)

3. Solveボタンを押す。全プレイヤーの行動を入力し終えていた場合盤面とターンが進む
![run4](https://raw.githubusercontent.com/naoppy/Procon29/master/docs/ReadmeResources/howToRun4.png?token=AbSzmaAman6S7PYwmYTru8t9LqoJi0jIks5b55NawA%3D%3D)

以下、繰り返し～

### ・QRコードを使わずに盤面をランダムに生成する
[ソース](src/procon29/akashi/GameBoard.java)
の約30行目

`public ScoreMaker maker = new ScoreFromQR(QRImporter.inputCode());`
を

`public ScoreMaker maker = new ScoreFromRandom(9, 10, true, false, 70);`

などに変える。
コンストラクタに渡すパラメータは
[ソース](src/procon29/akashi/scores/ScoreFromRandom.java)
と
[APIドキュメント](https://naoppy.github.io/Procon29/procon29/akashi/scores/ScoreFromRandom.html)
を参照。

Random生成の場合、敵プレイヤーの位置は最初から入力されているので、起動してすぐSolveボタンを押せばよい。

### ・Solverを変える、新しくSolverを作る
[ソース](src/procon29/akashi/GameBoard.java)
の約55行目

`private Solver solver = new DoNothingSolver();`
の右側を

` = new RandomeSelect();`

などに変える。

既にあるSolverは
[ソース](src/procon29/akashi/solver)
と
[APIドキュメント](https://naoppy.github.io/Procon29/procon29/akashi/solver/package-summary.html)
を参照。

新しくSolverを作る場合、Solverインターフェースを継承して作ることになる。

## API documentation
[API documents](https://naoppy.github.io/Procon29/)

## Development and contributing
Feel free to send pull requests and raise issues.
