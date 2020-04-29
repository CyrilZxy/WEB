package why;

import java.util.Scanner;

/**
 * @author ：ZXY
 * @date ：Created in 2020/4/28 18:47
 * @description：  必须使用多线程的场合
 */

public class Case2 {

    //单线程-------------------------------------------------------------------
    static long filb(int n){
        if (n < 2) {
            return n;
        }
        return filb(n-1)+filb(n-2);
    }

    //多线程-------------------------------------------------------------------
    static class FilbThread extends Thread{
        int n;
        FilbThread(int n){
            this.n=n;
        }

        @Override
        public void run(){                                      //新建线程执行run方法
            System.out.printf("filb(%d)=%d%n",n,filb(n));
        }
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.print("请输入要计算的数：");
            int n=sc.nextInt();

            //System.out.printf("filb(%d)=%d%n",n,filb(n));       //单线程

            new FilbThread(n).start();                           //多线程
        }
    }

}
