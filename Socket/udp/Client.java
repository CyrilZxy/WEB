package Socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author ：ZXY
 * @date ：Created in 2020/8/5 1:46
 * @description：
 */

public class Client {
    public static void main(String[] args) throws IOException {

        Scanner sc=new Scanner(System.in);

        try(DatagramSocket socket=new DatagramSocket()){
            while(true){

                //读取输入
                System.out.println("输入后回车：");
                String input=sc.nextLine();

                //发送请求
                byte[] sendBuffer=input.getBytes("UTF-8");
                //byte[] sendBuffer={9,5,2,7};

                DatagramPacket sendPacket=new DatagramPacket(
                        sendBuffer,0,sendBuffer.length,
                        InetAddress.getByName("127.0.0.1"),
                        9939
                );

                socket.send(sendPacket);


                //接收响应
                byte[] receiveBuffer =new byte[8192];
                DatagramPacket receivePacket=new DatagramPacket(
                        receiveBuffer,0,receiveBuffer.length
                );

                socket.receive(receivePacket);



                //真正接受响应，进行字符集解码
                String response=new String(
                        receiveBuffer,0,receiveBuffer.length
                );
                System.out.printf("FROM服务器$ |%s|%n",response);

            }
        }


    }
}
