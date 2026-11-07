// Javaはフォルダ構成を見てからコンパイルするのではないから、ここで宣言してフォルダの位置を明示する必要がある。
package io.github.kazumawada.mikke;

public class Main {

    public static void main(String[] args) {
        int len = args.length;
        if(len != 2){
        System.err.println("usage: mikke <検索ワード> <フォルダ>");
        }
        else{
         System.out.println(args[0] + " " + args[1]);
        };
        return;
    }

}
