package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.PageBean;
import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ZXY
 * @date ：Created in 2020/7/20 18:55
 * @description：        查找
 */

@WebServlet("/findByPageServlet")
public class FindByPageServlet extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                req.setCharacterEncoding("utf-8");
                resp.setContentType("application/jason;charset=utf-8");

                String currentPage=req.getParameter("currentPage");
                String rows=req.getParameter("rows");

                Map<String,String[]> parMap=req.getParameterMap();

                Map<String,String[]> map= new HashMap<>(parMap);
                map.remove("currentPage");
                map.remove("rows");

                System.out.println("=========================");
                for (Map.Entry<String,String[]> entry:parMap.entrySet()){
                        System.out.println("key:"+entry.getKey()+"value"+Arrays.toString(entry.getValue()));
                }


                int curtpage=Integer.parseInt(currentPage);
                int rowsint=Integer.parseInt(rows);
                UserService userService=new UserService();
                PageBean<User> pageBean=userService.findAllByPage(curtpage,rowsint,map);

                //把登录成功的map发送给前端，json 便于前端处理
                ObjectMapper objectMapper=new ObjectMapper();

                //将returnmap转换为json字符串
                objectMapper.writeValue(resp.getWriter(),pageBean);



                /*
                try {
                        parMap.remove("currentPage");
                        parMap.remove("rows");
                }catch(Exception e){
                        System.out.println("=========================");
                        e.printStackTrace();
                }*/


        }

}
