package servlet;

import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：ZXY
 * @date ：Created in 2020/7/23 21:50
 * @description：    单个根据id删除
 */
@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/jason;charset=utf-8");

        String idString=req.getParameter("id");
        int id=Integer.parseInt(idString);

        UserService userService=new UserService();

        int ret=userService.delete(id);
        if (ret==1) {           //刷新
            resp.getWriter().write("<h2删除成功！>" + "</h2>");
            resp.sendRedirect("/list.html");
        }else {
            //System.out.println("删除失败。");
            resp.getWriter().write("<h2删除失败！>" + "</h2>");
        }

    }

}
