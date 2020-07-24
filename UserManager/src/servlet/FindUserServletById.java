package servlet;

import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：ZXY
 * @date ：Created in 2020/7/20 19:43
 * @description：    通过id找到用户
 */
@WebServlet("/findUserServlet")

public class FindUserServletById extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/jason;charset=utf-8");

        String idString=req.getParameter("id");
        int id=Integer.parseInt(idString);

        UserService userService=new UserService();

        User user=userService.find(id);

        if (user==null){
            System.out.println("没有找到要修改的对象。");
        }else{
            //记录到session中
            req.getSession().setAttribute("updateUser",user);
            //跳转到update页面 并在此页面中显示原本用户信息
            resp.sendRedirect("/update.html");

        }

    }


}
