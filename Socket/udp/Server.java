package Socket.udp;



import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author ：ZXY
 * @date ：Created in 2020/8/5 1:07
 * @description：
 */


public class Server {
    public static void main(String[] args) throws IOException{
        //1.创建Server的Socket
        //  内部进行本地ip+port的绑定
        try(DatagramSocket socket=new DatagramSocket(9939)){
            while(true){
                //3.处理一个请求
                action(socket);
            }
        }

    }

    private static void action(DatagramSocket socket) throws IOException{
        //1.接受请求
            //1.1 准备一个字节数组，用来存放读到的数据
        byte[] receiveBuffer=new byte[8192];
            //1.2 把buffer封装成datagram
        DatagramPacket receivePacket=new DatagramPacket(receiveBuffer,0,8192);
            //1.3 读取请求
        socket.receive(receivePacket);
            //1.4 从receive中返回，意味有人给我发送请求
        /*
        System.out.println(
                Arrays.toString(
                        Arrays.copyOfRange(receiveBuffer,0,receivePacket.getLength() )
                )
        );*/

        //需将byte[]中的数据进行，字符集解码
        String request=new String(receiveBuffer,0,receiveBuffer.length);
        System.out.println("收到请求："+request);



        //2.提供服务/资源
        // version 1：回显服务—echo服务!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // 响应=请求
            //String response=request;

        // version 2：字典查询服务!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // 请求英文，响应中文+例句
        String response=translate(request);


        // version 3：轮盘聊天
            // 给我发送过请求的ip+prot，会记录
            //再有人发送新的请求时，随机选择一个记录发送回去
            //不保证对方在线，不保证能收到
        //randomTalk(socket,request,receivePacket.getAddress(),receivePacket.getPort());



        //3.发送响应    VERSION 2
       byte[] sendBuffer=response.getBytes("UTF-8");
            DatagramPacket sendPacket=new DatagramPacket(
                sendBuffer,0,sendBuffer.length,
                receivePacket.getAddress(),     //地址
                receivePacket.getPort()         //端口
            );
        socket.send(sendPacket);


    }



    //version 3
/*    private static class Remote{
        private InetAddress address;
        private int port;

        private Remote(InetAddress address, int port) {
            this.address = address;
            this.port = port;
        }
    }

    //所有曾经给我发消息的客户端信息—远端
    private static List<Remote> remoteList=new ArrayList<>();
    private static Random random=new Random();

    private static void randomTalk ( DatagramSocket socket,String request, InetAddress address,int port)throws IOException{

        System.out.printf("已有%d个客户端发送了消息%n",remoteList.size());


        if(remoteList.size()>0) {
            //随机一个下标，决定这个消息发给谁
            int rIndex = random.nextInt(remoteList.size());
            Remote remote = remoteList.get(rIndex);
            System.out.printf("此次随机结果发送给%d下标的客户",rIndex);

            //发送消息
            byte[] sendBuffer = request.getBytes("UTF-8");
            DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer, 0, sendBuffer.length,
                    remote.address,     //地址
                    remote.port         //端口
            );
            socket.send(sendPacket);
        }

        //把自己加入remoteList
        remoteList.add(new Remote(address,port));
    }*/










    //version 2
    private  static Map<String,Result> dictionary=new TreeMap<>();
    static{
        dictionary. put("mask", new Result("n. 面具;口罩;掩饰","The") );
        dictionary. put("cat", new Result("n. 猫","The cat") );
        dictionary. put("dog", new Result("n. 狗","The dog") );
    }
    private static class Result{
        String chinese;
        String sentence;
        //内部类的方法即便私有，外部类也有权限访问
        private Result(String chinese,String sentence){
            this.chinese=chinese;
            this.sentence=sentence;
        }
    }
    private static String translate(String english){
        //最简单的翻译形式—提前保存一份字典
        Result result=dictionary.get(english);
        if(result==null){
            return "不支持的单词";
        }
        return String.format("%s%n%s%n",result.chinese,result.sentence);
    }




}
