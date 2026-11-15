// Javaはフォルダ構成を見てからコンパイルするのではないから、ここで宣言してフォルダの位置を明示する必要がある。
package io.github.kazumawada.mikke;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        int len = args.length;

        if(len != 2){
        System.err.println("usage: mikke <検索ワード> <フォルダ>");
        }
        else{
         list(new File(args[1]));
     }
  } // void Main

  static void list(File dir) {
    // Files.listFiles()を使用してfilesに格納する(ここではファイルとdirが混ざっている。だからこの後にforでその中を探していく必要がある。)
    File[] files = dir.listFiles();

    for(File f : files){
        if(f.isDirectory()){
        list(f); // filesのなかにあるひとつのfをもういっかいFile[] files = dir.listFiles();する。
        System.out.println("📁" + f.getName());
        }
        else{
        System.out.println(" 📄" + f.getPath());
        }
    }

    }

} // Main

// dirかfileかを見分ける方法
// pom.isDirectory()が使えるから、ファイルの文字列自体を識別しなくてもそれを使用すればいける。
