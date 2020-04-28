package why;

/**
 * @author ：ZXY
 * @date ：Created in 2020/4/28 16:13
 * @description：  对比单线程、多线程
 *                  计算Conut次 运行时间
 */

public class Case1 {
    static  final int Count=10;                //计算Count次
    static final int Number=10_0000_0000;       //Number个数字
    //static final int Number=10000;              //Number个数字


    //单线程-----------------------------------------------------------------------------------------
    static long sum(){
        long r=0;
        for (long i=0;i<Number;i++){
            r+=i;
        }
        return r;
    }

    static void 单线程(){
        long b=System.nanoTime();

        for (int i = 0; i <Count ; i++) {
            System.out.println(sum());
        }

        long e=System.nanoTime();
        double s=(e-b)/1000_000_000.0;
        System.out.printf("单线程，运行时间：%f%n",s);       //时间秒
    }

    //多线程-----------------------------------------------------------------------------------------
    static class SumThread extends Thread{              //线程执行run方法
        @Override
        public void run(){
            System.out.println(sum());
        }
    }

    static void 多线程() throws InterruptedException{
        long b=System.nanoTime();

        Thread[] threads=new Thread[Count - 1];         //本身属于一个线程，创建Count-1个新线程
        for (int i = 0; i <Count-1 ; i++) {             //将线程放到就绪队列
            threads[i]=new SumThread();
            threads[i].start();
        }

        System.out.println(sum());      //自身线程计算

        for (int i = 0; i < Count-1; i++) {             //自身线程结束，等待Count-1个线程
            threads[i].join();
        }

        long e=System.nanoTime();
        double s=(e-b)/1000_000_000.0;
        System.out.printf("多线程，运行时间：%f%n",s);       //时间秒
    }


    //---------------------------------------------------------------------------------
    public static void main(String[] args) throws InterruptedException {
        //main就算是个线程
        单线程();
        System.out.println("===========================");
        多线程();
    }

}
