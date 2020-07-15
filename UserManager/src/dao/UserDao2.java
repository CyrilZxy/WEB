package dao;

import entity.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ：ZXY
 * @date ：Created in 2020/7/13 20:08
 * @description：
 */

public class UserDao {



    //1.登录
    public static User login(User loginUser){
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        User user =null;

        try{
            String sql="select * from usermessage " +
                    "where username=? and password=?;";

            connection=DBUtil.getConnection();
            ps=connection.prepareStatement(sql);
            ps.setString(1,loginUser.getUsername());
            ps.setString(2,loginUser.getPassword());
            rs=ps.executeQuery();

            if (rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setAge(rs.getInt("age"));
                user.setAddress(rs.getString("address"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return user;
    }
    //测试登录
    public static void main1(String[] args) {
        User user=new User();
        user.setUsername("zhangxinyue");
        user.setPassword("123");
        if(login(user)==null){
            System.out.println("登录失败");
        }else{
            System.out.println("登录成功");
        }
    }



    //2.添加用户
    public static int add(User addUser){
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
            String sql="insert into usermessage(" +
                    "name, username, password, gender, " +
                    "age, address, qq, email)" +
                    "values (?,?,?,?,?,?,?,?);";

            connection=DBUtil.getConnection();
            ps=connection.prepareStatement(sql);
            ps.setString(1,addUser.getName());
            ps.setString(2,addUser.getUsername());
            ps.setString(3,addUser.getPassword());
            ps.setString(4,addUser.getGender());
            ps.setInt(5,addUser.getAge());
            ps.setString(6,addUser.getAddress());
            ps.setString(7,addUser.getQq());
            ps.setString(8,addUser.getEmail());

            int ret=ps.executeUpdate();     //update返回int值
            return ret;

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }

        return 0;   //添加失败
    }
    //测试添加用户
    public static void main2(String[] args) {
        User user=new User();
        user.setName("琦玉");
        user.setUsername("qiyu");
        user.setPassword("123");
        user.setGender("男");
        user.setAge(20);
        user.setAddress("日本");
        user.setQq("000");
        user.setEmail("000@qq.com");
        int ret=add(user);
        if(ret==0){
            System.out.println("添加失败");
        }else{
            System.out.println("添加成功");
        }
    }




    //3.删除用户
    public static int delect(int id){
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
            String sql="delete from usermessage where id=?;";
            connection=DBUtil.getConnection();
            ps=connection.prepareStatement(sql);
            ps.setInt(1,id);

            int ret=ps.executeUpdate();
            return ret;
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,null);
        }

        return 0;   //添加失败
    }
    //测试删除用户
    public static void main3(String[] args) {
        int ret=delect(2);
        if (ret==0){
            System.out.println("删除失败");
        }else {
            System.out.println("删除成功");
        }
    }




    //4.由id查找用户
    public static User find(int id){
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        User user=null;

        try{
            String sql="select * from usermessage where id=?;";
            connection=DBUtil.getConnection();
            ps=connection.prepareStatement(sql);
            ps.setInt(1,id);

            rs =ps.executeQuery();      //有内容next不为空
            if(rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setAge(rs.getInt("age"));
                user.setAddress(rs.getString("address"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }

        return user;
    }
    //测试根据id查找用户
    public static void main4(String[] args) {
        User user=find(8);
        System.out.println(user);
    }




    //5.更新用户信息
    public static int updataUser(User upUser){
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            String sql="update usermessage " +
                    "set gender=?,age=?,address=?,qq=?,email=?" +
                    "where id=?;";
            connection=DBUtil.getConnection();
            ps=connection.prepareStatement(sql);
            ps.setString(1,upUser.getGender());
            ps.setInt(2,upUser.getAge());
            ps.setString(3,upUser.getAddress());
            ps.setString(4,upUser.getQq());
            ps.setString(5,upUser.getEmail());
            ps.setInt(6,upUser.getId());

            int ret=ps.executeUpdate();
            return ret;

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;       //更新失败
    }
    //测试更新用户信息
    public static void main(String[] args) {
        User user=new User();
        user.setId(7);
        //user.setName("波奇");       不可更新
        //user.setUsername("boqi");
        //user.setPassword("123");
        user.setGender("男");
        user.setAge(12);
        user.setAddress("日本");
        user.setQq("666");
        user.setEmail("666@qq.com");

        int ret=updataUser(user);
        if (ret==0){
            System.out.println("更新失败");
        }else{
            System.out.println("更新成功");
        }
    }




}
