package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;
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
 * @date ：Created in 2020/7/20 18:10
 * @description：    登录
 */

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/jason;charset=utf-8");

        String username=req.getParameter("username");
        String password=req.getParameter("password");

        //System.out.println("username:"+username);
        //System.out.println("password:"+password);

        User loginUser=new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);

        UserService userService=new UserService();
        User user=userService.login(loginUser);

        Map<String,Object> returnmap=new HashMap<>();

        if (user!=null){
            System.out.println("登陆成功："+username);

            //登陆成功的用户写入session中
            req.getSession().setAttribute("user",user);
            returnmap.put("msg",true);

        }else{
            System.out.println("登陆失败："+username);
            returnmap.put("msg",false);
        }

        //把登录成功的map发送给前端，json 便于前端处理
        ObjectMapper objectMapper=new ObjectMapper();

        //将returnmap转换为json字符串
        objectMapper.writeValue(resp.getWriter(),returnmap);




    }

}
