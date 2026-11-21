// Javaはフォルダ構成を見てからコンパイルするのではないから、ここで宣言してフォルダの位置を明示する必要がある。
package io.github.kazumawada.mikke;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Main {
static boolean found = false;
static int countNum = 0;

    public static void main(String[] args)  throws IOException {
        int len = args.length;

        if(len != 2){
        System.err.println("usage: mikke <検索ワード> <フォルダ>");
        System.exit(2); // 終了コード: エラー
        }
        else{
         // listに渡す前に、args[1]に入ってくるものは本当に存在するのか確かめたい。(確かめずに渡すとヌルポになる)
         if(!new File(args[1]).isDirectory()){
             System.err.println("正しいフォルダパスではありません");
             System.exit(2); // 終了コード: エラー
         }
         list(new File(args[1]), args[0]);
         // System.out.println(found);

         if(found == true){
             System.out.println(args[0] + "は、" + countNum + "個見つかりました");
             System.out.println("正常終了");
             System.exit(0); // 終了コード: 正常
         }else{
             System.err.println("0件見つかりました");
             System.exit(1); // 終了コード: 見つからない
         }

     }
  }

  static void list(File dir, String target)  throws IOException {
    // Files.listFiles()を使用してfilesに格納する(ここではファイルとdirが混ざっている。だからこの後にforでその中を探していく必要がある。)
    File[] files = dir.listFiles();

    // 仮引数はfilesだが、lsしたときのフォルダ、ファイルが全部一旦はfilesの中に入ってる。
    // lsで出たフォルダ、ファイル群をループでそれぞれ調査している。
    for(File f : files){
        // Fileクラスの中には、dir or fileを識別するメソッドがすでに用意されているから、それ使うだけで良い。
        if(f.isDirectory()){
        // フォルダだったら、もう一回再帰でlist関数を読んで、ファイルになるまで繰り返させている。
        list(f, target); // filesのなかにあるひとつのfをもういっかいFile[] files = dir.listFiles();する。

        // これデバック用で、コメントアウトしない意味は？結局再帰しているからフォルダ名表示は紛らわしい。
        // System.out.println("📁" + f.getName());
        }
        else{
        // ファイルのpathを表示
        // System.out.println("📄" + f.getPath());
        try{
            // ファイルを一行ずつ読んで、targetを探す。
            List<String> lines = Files.readAllLines(f.toPath());
            int lineCount = 0;
            // 実際にループで一行ずつ探す。countで何行目かを把握。
            for (String line : lines) {
                lineCount++;
                if (line.contains(target)) {
                    countNum++;
                    // 一つでも見つかったかどうかを識別。
                    found = true;
                    // これ上でフルパス出しているからいらないかも。
                    String fileName = f.getName();

                    System.out.println("[📄" + f.getPath() + "]");
                    System.out.println(lineCount + "行目:" + line + "\n");
                }
            }
        } catch (IOException e) {
        System.err.println("cant read this file: " + f.getPath());
        }


        }
    }

    }

} // Main

// dirかfileかを見分ける方法
// pom.isDirectory()が使えるから、ファイルの文字列自体を識別しなくてもそれを使用すればいける。
//
// ファイル内を一文字ずつ読んでくれる。
// jshell> import java.nio.file.*
// jshell> Path p = Path.of("/home/kazumawada/work/mikke/.gitignore")
// jshell> Files.readAllLines(p) // 一行ずつ文字列でかかれている配列で返ってくる・

// MalformedInputException
// 文字列で探しているときに、バイナリで書かれたファイルは読めない。そんなときにこのエラーが出るようになっている。
