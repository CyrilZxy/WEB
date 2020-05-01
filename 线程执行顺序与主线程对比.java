/**
 * @author ：ZXY
 * @date ：Created in 2020/4/29 13:11
 *
 * @description：    线程执行顺序与主线程对比
 *                    输出结果：大概率是  main-->a-->b  ，其他情况都有可能！！！！！！！！！！
 *                             大概率CPU最先分配给主线程main，主线程结束在分配给其他线程
 *                             优先级只是建议，不是强制！！！
 */

public class 线程执行顺序与主线程对比 {

    private static class A extends Thread{
        @Override
        public void run(){
            System.out.println("我是A");
        }
    }

    private static class B extends Thread{
        @Override
        public void run(){
            System.out.println("我是B");
        }
    }

    public static void main(String[] args) {
        A a=new A();
        B b=new B();

        //a、b 放入就绪队列中
        a.start();
        b.start();
        System.out.println("我是main");
    }

}
