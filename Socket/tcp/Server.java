package Socket.tcp;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author ：ZXY
 * @date ：Created in 2020/8/5 21:39
 * @description：
 *
 *          TCP：   可靠的
 *                  有连接的
 *                  发送/接收数据时，感受到的是数据流的形式
 */

public class Server {


    private static class ServiceMan extends Thread{
        private final Socket  socket;

        ServiceMan(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run(){
            try {

                //获取输入流
                InputStream is = socket.getInputStream();
                //封装成scanner
                Scanner scanner = new Scanner(is, "UTF-8");
                //使用\r\n 进行分割的方式，读取一个请求
                String request = scanner.nextLine();
                System.out.println("收到请求：" + request);

                //业务处理
                String response = request;

                //发送响应,也要使用\r\n 跟在后面，进行分割
                OutputStream os = socket.getOutputStream();
                //封装成PrintWrite
                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(os, "UTF-8")
                );

                //发送响应
                writer.println(response);
                writer.flush();

                socket.close();
            }catch(IOException e){
                e.printStackTrace();
            }

        }


    }



    public static void main(String[] args) throws IOException {
        //1.开一家店
        ServerSocket serverSocket=new ServerSocket(9898);       //ServerSocket建立连接  一个

        //2.循环处理业务
        //主线程建立连接，只负责接待
        while(true){
            Socket socket=serverSocket.accept();        //通信Socket  多个

            //业务过程，交给线程处理（或建立线程池）
            new ServiceMan(socket).start();
        }


    }
}
