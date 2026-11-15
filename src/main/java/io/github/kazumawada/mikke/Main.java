// Javaはフォルダ構成を見てからコンパイルするのではないから、ここで宣言してフォルダの位置を明示する必要がある。
package io.github.kazumawada.mikke;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Main {
static boolean found = false;

    public static void main(String[] args)  throws IOException {
        int len = args.length;

        if(len != 2){
        System.err.println("usage: mikke <検索ワード> <フォルダ>");
        System.exit(2); // 終了コード: エラー
        }
        else{
         list(new File(args[1]), args[0]);
         // 終了コードとして
         System.out.println(found);
         if(found == true){
             System.out.println("正常終了");
             System.exit(0); // 終了コード: 正常
         }else{
             System.out.println("0件見つかりました");
             System.exit(1); // 終了コード: 見つからない
         }

     }
  } // void Main

  static void list(File dir, String target)  throws IOException {
    // Files.listFiles()を使用してfilesに格納する(ここではファイルとdirが混ざっている。だからこの後にforでその中を探していく必要がある。)
    File[] files = dir.listFiles();

    for(File f : files){
        if(f.isDirectory()){
        list(f, target); // filesのなかにあるひとつのfをもういっかいFile[] files = dir.listFiles();する。
        System.out.println("📁" + f.getName());
        }
        else{
        System.out.println("📄" + f.getPath());
        try{
            List<String> lines = Files.readAllLines(f.toPath());
            int count = 0;
            for (String line : lines) {
                count++;
                if (line.contains(target)) {
                    // 一つでも見つかったかどうかを識別。
                    found = true;
                    // これ上でフルパス出しているからいらないかも。
                    String fileName = f.getName();
                    System.out.println(fileName + " < " + count + "行目" + "'" + line + "'" + "\n");
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
