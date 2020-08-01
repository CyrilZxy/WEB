package Servlet;

import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;

/**
 * @author ：ZXY
 * @date ：Created in 2020/5/12 22:21
 * @description：    注册
 */

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        System.out.println("注册！");

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        String username=req.getParameter("username");
        String password=req.getParameter("password");

        Connection connection=DBUtil.getConnection(true);
        PreparedStatement ps=null;
        //ResultSet rs=null;

        Writer writer=resp.getWriter();         //在前端写东西


        try {
            String sql = "insert into account(username,password) values(?,?)";
            //connection=DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

//            public static Connection getConn() throws Exception{
//                if(connection == null) {
//                    connection = DriverManager.get.Connection(url, username, password);
//                }
//                return connection;
//            }

            ps = connection.prepareStatement(sql);

            ps.setString(1,username);
            ps.setString(2,password);

            int ret=ps.executeUpdate();
            if (ret==0){            //？？？
                System.out.println("注册失败！");
                writer.write("<h2>注册失败!</h2>"+"<h2>用户名或密码重复请重试...</h2>");
            }else{
                System.out.println("注册成功！");
                writer.write("<h2>注册成功!</h2>"+"<h2>跳转回登录页面...</h2>");

                //resp.sendRedirect("login.html");        //跳转页面
                resp.setHeader("refresh","2;/login.html");      //延时3s跳转
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,null);
        }



    }


}
