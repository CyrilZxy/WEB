package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：ZXY
 * @date ：Created in 2020/7/20 19:52
 * @description：    将记录的user信息传给前端update.html显示
 */
@WebServlet("/returnServlet")
public class ReturnServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/jason;charset=utf-8");

        //拿到写在session中的用户 jason传给前端显示
        Object user=req.getSession().getAttribute("updateUser");

        //把登录成功的map发送给前端，json 便于前端处理
        ObjectMapper objectMapper=new ObjectMapper();

        //将updateUser转换为json字符串
        objectMapper.writeValue(resp.getWriter(),user);


    }
}
