package Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Goods;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：ZXY
 * @date ：Created in 2020/5/12 22:18
 * @description：    商品浏览-goodsbrowse
 */

@WebServlet("/goods")
public class GoodsBrowseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        //1.先从数据库中找到 所有商品信息

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        List<Goods> goodsList=new ArrayList<>();


        try{
            String sql="select id,name,introduce,stock,unit,price,discount from goods";
            connection=DBUtil.getConnection(true);
            ps=connection.prepareStatement(sql);

            rs=ps.executeQuery();

            while (rs.next()){
                Goods goods=new Goods();
                goods.setId(rs.getInt("id"));
                goods.setName(rs.getString("name"));
                goods.setIntroduce(rs.getString("introduce"));
                goods.setStock(rs.getInt("stock"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getInt("price"));
                goods.setDiscount(rs.getInt("discount"));

                goodsList.add(goods);
            }

            //System.out.println(goodsList);    //展示后端数据
            Writer writer = resp.getWriter();         //在前端写东西

            //将后端数据 转换为json字符串。 需要依赖
            ObjectMapper objectMapper=new ObjectMapper();
            //将list转换为jason字符串，并将字符串写到流当中。
            objectMapper.writeValue(writer,goodsList);
            //推到前端
            writer.write(writer.toString());



        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }


    }

}
