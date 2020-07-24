package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ZXY
 * @date ：Created in 2020/7/23 21:59
 * @description：    多选删除
 */

@WebServlet("/deleteSelectedServlet")
public class DeleteSelectedServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/jason;charset=utf-8");

        String[] values=req.getParameterValues("id[]");

        UserService userService=new UserService();

        int sum=0;
        for (int i=0;i<values.length;i++){
            int j=Integer.parseInt(values[i]);
            int delete=userService.delete(j);
            sum+=delete;
        }

        Map<String,Object> returnMap=new HashMap<>();

        if (sum==values.length){
            //System.out.println("删除成功");
            returnMap.put("msg",true);

        }else{
            returnMap.put("msg",false);
        }

        //把登录成功的map返回给前端。json      : 便于前端进行处理。
        ObjectMapper objectMapper = new ObjectMapper();
        //就是将returnMap，转换为json字符串
        objectMapper.writeValue(resp.getWriter(),returnMap);

    }

}
