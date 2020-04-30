/**
 * @author ：ZXY
 * @date ：Created in 2020/4/29 14:29
 * @description：
 */

public class eg创建线程对象 {

    //线程类 目标类
    private static class MyThread extends Thread{
        @Override
        public void run(){
            //希望线程执行的代码
            for (int i = 0; i < 10 ; i++) {
                System.out.println(i);
            }
        }
    }




    //目标类
    private static class MyRunnable implements Runnable{
        @Override
        public void run(){
            //希望线程执行的代码
            for (int i = 100; i < 110; i++) {
                System.out.println(i);
            }
        }
    }




    public static void main(String[] args) throws InterruptedException {
        Thread a=new MyThread();                    //创建线程对象
        a.start();                                  //加入就绪队列
        a.join();                                   //主线程阻塞，知道t线程执行结束，才接着往下执行
        System.out.println("A 一定结束了");

        Runnable r1=new MyRunnable();                    //目标对象
        Thread b=new Thread(r1);                         //创建线程去运行同一份目标对象
        b.start();
        b.join();
        System.out.println("B 一定结束了");


        Runnable r2=new MyThread();                    //创建Thread对象，Thread实现了Runnable。可看作目标对象
        Thread c=new Thread(r2);
        c.start();
        c.join();
        System.out.println("C 一定结束了");

    }

    public static void 匿名类创建线程对象(){

        //等同于直接创建线程对象
        Thread a=new Thread(){
            @Override
            public void run(){
                //希望线程执行的代码
            }
        };


        //等同于直接创建线程对象
        Thread b=new Thread(new Runnable() {
            @Override
            public void run() {
                //希望线程执行的代码
            }
        });


        // b 的变形 ，lambda 表达式
        Thread c=new Thread(() -> {
            //希望线程执行的代码
        });


    }

}
