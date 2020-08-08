package Socket.tcp;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ：ZXY
 * @date ：Created in 2020/8/5 22:23
 * @description：
 */

public class Client {

    public static void main(String[] args) throws IOException,InterruptedException{

/*        String request="你好世界";
        if(args.length>0){
            request=args[0];        //只取第一个
        }*/

        Socket socket=new Socket("127.0.0.1",9898);

        Scanner console=new Scanner(System.in);
        System.out.print("请输入请求>");
        String request=console.nextLine();


        //明确等待时间
        //TimeUnit.SECONDS.sleep(20);

        //
        OutputStream os=socket.getOutputStream();
        PrintWriter writer=new PrintWriter(
                new OutputStreamWriter(os,"UTF-8")
        );
        writer.println(request);
        //writer.println("你好世界");
        writer.flush();

        //
        InputStream is=socket.getInputStream();
        Scanner scanner=new Scanner(is,"UTF-8");
        String response=scanner.nextLine();
        System.out.println(response);

        socket.close();

    }
}
