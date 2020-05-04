package web05;

public class 不同线程下的状态_01 {
    private static void print() {
        Thread current = Thread.currentThread();

        // id分辨出哪个线程打的
        System.out.println(current.getId() + ":" + current.getName());
        System.out.println(current.getId() + ":" + current.getPriority());
        System.out.println(current.getId() + ":" + current.getState());
        System.out.println(current.getId() + ":" + current.isDaemon());
        System.out.println(current.getId() + ":" + current.isAlive());
        System.out.println(current.getId() + ":" + current.isInterrupted());
    }

    //随时间片调度，打印 !!!!!!!!!!!!!!!!!!!   (也可能主线程全部打印完，打印子线程)

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            print();                    // 在子线程中打印
        });
        t.start();

        print();                        // 在主线程中打印
    }
}
