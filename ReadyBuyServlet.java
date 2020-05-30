package Servlet;

import common.OrderStatus;
import entity.Account;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：ZXY
 * @date ：Created in 2020/5/12 22:20
 * @description：    准备订单
 */


@WebServlet("/pay")
public class ReadyBuyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        String goodsIDandNum = req.getParameter("goodsIDandNum");
        //1-8,3-2       用，分割

        //货物较多，货物需要List进行保存
        List<Goods> goodsList=new ArrayList<>();

        String[] strings1=goodsIDandNum.split(",");
        for (String s1:strings1){
            //System.out.println(s1);
            String[] strings2=s1.split("-");

            //查找货物是否存在
            Goods goods=getGoods(Integer.valueOf(strings2[0]));
            if (goods!=null){
                goods.setBuyGoodsNum(Integer.valueOf(strings2[1]));
                goodsList.add(goods);
            }
        }
        //System.out.println(goodsList);            //查看获得数据
        Writer writer = resp.getWriter();         //在前端写东西


        //创建订单
        Order order=new Order();
        order.setId(String.valueOf(System.currentTimeMillis()));

        HttpSession session=req.getSession();
        Account account=(Account) session.getAttribute("user");
        order.setAccount_id(account.getId());
        order.setAccount_name(account.getUsername());

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyy-MM-dd");
        order.setCreate_time(LocalDateTime.now().format(formatter));
        //SimpleDateFormat

        int totalMoney=0;
        int actualMoney=0;
        for (Goods goods:goodsList){
            //每一个商品实际就是一个订单项
            OrderItem orderItem=new OrderItem();
            orderItem.setOrder_id(order.getId());
            orderItem.setGoods_id(goods.getId());
            orderItem.setGoods_name(goods.getName());
            orderItem.setGoods_introduce(goods.getIntroduce());
            orderItem.setGoods_num(goods.getBuyGoodsNum());
            orderItem.setGoods_unit(goods.getUnit());
            orderItem.setGoods_price(goods.getPriceInt());
            orderItem.setGoods_discount(goods.getDiscount());

            order.orderItemList.add(orderItem);

            //当前商品多少钱
            int currentMoney=goods.getBuyGoodsNum()*goods.getPriceInt();
            //总共
            totalMoney+=currentMoney;
            //实际=当前商品*折扣
            actualMoney+=currentMoney*goods.getDiscount()/100;
        }

        order.setTotal_money(totalMoney);
        order.setActual_amount(actualMoney);
        order.setOrder_status(OrderStatus.PLAYING);

        //System.out.println(order);                      //后台观察订单表
        writer.write("<h2>订单详情如下：</h2>");

        HttpSession session2 = req.getSession();
        session2.setAttribute("order",order);

        HttpSession session3 = req.getSession();
        session3.setAttribute("goodsList",goodsList);


        //如果是跳转到另一个网页的话，对应的数据不好拿到，所以在这里直接进行打印网页
        //通过响应体对前端传入数据。
        resp.getWriter().println("<html>");
        resp.getWriter().println("<p>"+"【用户名称】:"+order.getAccount_name()+"</p>");
        resp.getWriter().println("<p>"+"【订单编号】:"+order.getId()+"</p>");
        resp.getWriter().println("<p>"+"【订单状态】:"+order.getOrder_statusDesc()+"</p>");
        resp.getWriter().println("<p>"+"【创建时间】:"+order.getCreate_time()+"</p>");

        resp.getWriter().println("<p>"+"编号  "+"名称   "+"数量  "+"单位  "+"单价（元）   "+"</p>");
        resp.getWriter().println("<ol>");
        for (OrderItem orderItem  : order.orderItemList) {
            resp.getWriter().println("<li>" + orderItem.getGoods_name() +" " + orderItem.getGoods_num()+ " "+
                    orderItem.getGoods_unit()+" " + orderItem.getGoodsPrice()+"</li>");
        }
        resp.getWriter().println("</ol>");
        resp.getWriter().println("<p>"+"【总金额】:"+order.getTotal_money() +"</p>");
        resp.getWriter().println("<p>"+"【优惠金额】:"+order.getDiscount() +"</p>");
        resp.getWriter().println("<p>"+"【应支付金额】:"+order.getActual_amount() +"</p>");
        //这个标签<a href = > 只会以get方式请求，所以buyGoodsServlet的 doGet方法

        //resp.getWriter().println("<a href=\"buyGoodsServlet\">确认</a>");
        //resp.getWriter().println("<a href= \"index.html\">取消</a>");
        resp.getWriter().println("<form action=\"buyGoodsServlet\" method=\"post\"><button type=\"submit\">确认订单</button></form>");
        resp.getWriter().println("<form action=\"index.html\" method=\"post\"><button type=\"submit\">取消订单</button></form>");

        resp.getWriter().println("</html>");



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
