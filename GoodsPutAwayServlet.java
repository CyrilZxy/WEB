package Servlet;

import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ：ZXY
 * @date ：Created in 2020/5/12 22:19
 * @description：    上架商品-inbound
 */

@WebServlet("/inbound")
public class GoodsPutAwayServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        String name = req.getParameter("name");
        String introduce = req.getParameter("introduce");
        String stock = req.getParameter("stock");
        String unit = req.getParameter("unit");

        String price = req.getParameter("price");                       //89.9
        double doublePrice=Double.valueOf(price);                          //89.9
        int realPrice=new Double(100*doublePrice).intValue();       //8990

        String discount = req.getParameter("discount");


        Connection connection = null;
        PreparedStatement ps = null;
        //ResultSet rs = null;

        Writer writer = resp.getWriter();         //在前端写东西


        try{
            String sql="insert into goods(name,introduce,stock,unit,price,discount)values(?,?,?,?,?,?)";
            connection=DBUtil.getConnection(true);
            ps=connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,introduce);
            ps.setInt(3,Integer.valueOf(stock));
            ps.setString(4,unit);
            ps.setInt(5,realPrice);
            ps.setInt(6,Integer.valueOf(discount));


            int ret=ps.executeUpdate();

            if (ret==0){
                writer.write("<h2>商品上架失败</h2>");
            }else {
                writer.write("<h2>商品上架成功</h2>"+"<h2>自动返回系统菜单...</h2>");
                resp.setHeader("refresh","2;/index.html");      //延时2s跳转，index.html 系统菜单
            }


        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,null);
        }


        }





}
