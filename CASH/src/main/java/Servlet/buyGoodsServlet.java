package Servlet;

import common.OrderStatus;
import entity.Goods;
import entity.Order;
import entity.OrderItem;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author ：ZXY
 * @date ：Created in 2020/5/12 22:18
 * @description：    支付购买-buyGoodsServlet
 */



@WebServlet("/buyGoodsServlet")
public class buyGoodsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        Writer writer = resp.getWriter();         //在前端写东西

        HttpSession session=req.getSession();
        Order order = (Order) session.getAttribute("order");

        List<Goods> goodsList = (List<Goods>) session.getAttribute("goodsList");

        //将订单内的数据写入数据库当中
        order.setOrder_status(OrderStatus.OK);

        //DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyy-MM-dd");
        //order.setFinish_time(LocalDateTime.now().format(formatter));
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss"
        );
        String finishTime = format.format(date);
        order.setFinish_time(finishTime);



        boolean flg= commitOrder(order);       //订单提交 成功 | 失败
        if (flg){

            //更新库存 goods表
            for (Goods goods:goodsList) {       //更新goodlist的商品库存
                boolean isUpdate=updateAfterPay(goods,goods.getBuyGoodsNum());
                if (isUpdate){
                    System.out.println("更新库存成功");
                }else{
                    System.out.println("更新库存失败");
                    return;
                }
            }

        }else{
            //插入失败
            System.out.println("插入失败");
            return;
        }

        resp.sendRedirect("buyGoodsSuccess.html");

    }






    public boolean updateAfterPay(Goods goods,int buyGoodsNum){
        Connection connection=null;
        PreparedStatement ps=null;

        try{
            String sql="update `goods` set stock=? where id=?";
            connection=DBUtil.getConnection(true);
            ps=connection.prepareStatement(sql);

            ps.setInt(1,goods.getStock()-buyGoodsNum);
            ps.setInt(2,goods.getId());

            if (ps.executeUpdate()==0){
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,null);
        }

        return true;
    }







    public boolean commitOrder(Order order){
        Connection connection=null;
        PreparedStatement ps=null;

        try{
            String insertOrderSql="insert into `order` (id,account_id,account_name," +
                    "create_time,finish_time," +
                    "actual_amount,total_money,order_status)"
                    +"values(?,?,?,?,?,?,?,?)";
            connection=DBUtil.getConnection(false);
            ps=connection.prepareStatement(insertOrderSql);
            ps.setString(1,order.getId());
            ps.setInt(2,order.getAccount_id());
            ps.setString(3,order.getAccount_name());
            ps.setString(4,order.getCreate_time());
            ps.setString(5,order.getFinish_time());
            ps.setInt(6,order.getActualAmountInt());
            ps.setInt(7,order.getTotalMoneyInt());
            ps.setInt(8,order.getOrder_status().getFlg());



            int ret= ps.executeUpdate();
            if (ret == 0){
                throw new RuntimeException("插入订单失败！");
            }


            String insertOrderItemSql="insert into `order_item` (order_id,goods_id,goods_name," +
                    "goods_introduce,goods_num,goods_unit,goods_price,goods_discount)"
                    +"values (?,?,?,?,?,?,?,?)";

            ps=connection.prepareStatement(insertOrderItemSql);
            for (OrderItem orderItem:order.getOrderItemList()) {
                //ps.setString(1,orderItem.getId());
                ps.setString(1,orderItem.getOrder_id());
                ps.setInt(2,orderItem.getGoods_id());
                ps.setString(3,orderItem.getGoods_name());
                ps.setString(4,orderItem.getGoods_introduce());
                ps.setInt(5,orderItem.getGoods_num());
                ps.setString(6,orderItem.getGoods_unit());
                ps.setInt(7,orderItem.getGoodsPriceInt());
                ps.setInt(8,orderItem.getGoods_discount());
                ps.addBatch();      //批量插入 缓存
            }


            int[] effect=ps.executeBatch();      //批量的插入
            for (int i:effect) {
                if (i==0){
                    throw  new RuntimeException("插入订单失败！");
                }
            }
            connection.commit();        //批量插入无异常，手动提交


        }catch(Exception e){
            e.printStackTrace();
            //判断连接是否断开
            if (connection!=null){
                try{
                    connection.rollback();
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
            return false;


        }finally {
            DBUtil.close(connection,ps,null);
        }

        return true;
    }

}