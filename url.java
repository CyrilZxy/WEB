package Socket.HTTP;

/**
 * @author ：ZXY
 * @date ：Created in 2020/8/6 15:05
 * @description：
 */

public class url {
    public static void main(String[] args) {
        String[] urlList={
                "https://www.nowcoder.com/search?query=%E9%9D%92%E8%9B%99&type=all",
                "https://www.nowcoder.com/tutorial/97/601466fc3ae44f188c57ac2f6eed32f0"};

        for(String url:urlList){
            parseUrl(url);
            System.out.println("---------------");
        }
    }


    /*      协议://
            主机地址
            :端口         ...可省略
            /文件路径
            查询字符      ...可省略
    */
    private static void parseUrl(String url){

        System.out.println("原URL："+url);

        //1.分割协议    "://"
        String[] str1=url.split("://");
        String schema=str1[0];
        System.out.println("1.协议："+schema);
        url=str1[1];
        System.out.println("等待分割："+url);
        System.out.println();

        //2.主机地址    "/"
        int i=url.indexOf("/");
        String host=url.substring(0,i);
/*        String[] str2=url.split("/");
        String host=str2[0];*/



        //3.端口  端口主机相连
        String[] fragments=host.split(":");
        if (fragments.length==1){
            System.out.println("2.主机地址:"+host);
            System.out.println("3.端口默认：8080");
        }else{
            System.out.println("2.主机地址："+fragments[0]);
            System.out.println("3.端口："+fragments[1]);
        }

        url=url.substring(i,url.length());
        System.out.println("等待分割："+url);
        System.out.println();


        //4.文件路径    用？分割
        String[] cutpath=url.split("\\?");
        if(cutpath.length==1){
            System.out.println("4.文件路径："+url);
            System.out.println("5.没有查询字符");
        }else{
            System.out.println("4.文件路径："+cutpath[0]);
            System.out.println("5.查询字符："+cutpath[1]);
        }




        //5.查询字符






    }
}
