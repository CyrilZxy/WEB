package Servlet;

import entity.Goods;
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
 * @date ：Created in 2020/5/12 22:20
 * @description：  更新商品-updategoods.html
 */


@WebServlet("/updategoods")
public class GoodsUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        String name = req.getParameter("name");
        String introduce = req.getParameter("introduce");
        String stock = req.getParameter("stock");
        String unit = req.getParameter("unit");

        String price = req.getParameter("price");                       //89.9
        double doublePrice = Double.valueOf(price);                          //89.9
        int realPrice = new Double(100 * doublePrice).intValue();       //8990

        String discount = req.getParameter("discount");

        String goodsIdString = req.getParameter("goodsID");
        int goodsId = Integer.valueOf(goodsIdString);

        Writer writer = resp.getWriter();         //在前端写东西

        //1.查看是否存在goodsId这个商品，如果有拿到这个商品
        Goods goods = getGoods(goodsId);
        if (goods == null) {
            writer.write("<h2>没有该商品" + goodsId + "</h2>");

        } else {
            //goods 存储的就是需要更新的商品
            goods.setName(name);
            goods.setIntroduce(introduce);
            goods.setStock(Integer.valueOf(stock));
            goods.setUnit(unit);
            goods.setPrice(realPrice);
            goods.setDiscount(Integer.valueOf(discount));

            //将新的数据写回数据库中
            boolean flg = modify(goods);
            if (flg){
                writer.write("<h2>商品更新成功："+goodsId+"号商品</h2>");
                resp.setHeader("refresh","1;/goodsbrowse.html");      //延时1s跳转，goodsbrowse.html
            }else {
                writer.write("<h2>商品更新失败："+goodsId+"号商品</h2>");
            }

        }
    }


    public boolean modify(Goods goods){
        Connection connection = null;
        PreparedStatement ps = null;

        try{
            String sql="update goods set name=?,introduce=?,stock=?,unit=?,price=?,discount=? where id=?";
            connection=DBUtil.getConnection(true);
            ps=connection.prepareStatement(sql);


            ps.setString(1,goods.getName());
            ps.setString(2,goods.getIntroduce());
            ps.setInt(3,goods.getStock());
            ps.setString(4,goods.getUnit());
            ps.setInt(5,goods.getPriceInt());
            ps.setInt(6,goods.getDiscount());
            ps.setInt(7,goods.getId());

            int ret=ps.executeUpdate();
            if (ret==0){
                return false;
            }else{
                return true;
            }


        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,null);
        }

        return false;

    }


    //找到goodsId的信息
    public Goods getGoods(int goodsId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Goods goods = null;

        try {
            String sql = "select * from goods where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1, goodsId);
            rs = ps.executeQuery();
            if (rs.next()) {
                goods = new Goods();
                goods.setId(rs.getInt("id"));
                goods.setName(rs.getString("name"));
                goods.setIntroduce(rs.getString("introduce"));
                goods.setStock(rs.getInt("stock"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getInt("price"));
                goods.setDiscount(rs.getInt("discount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, rs);
        }

        return goods;
    }




}






