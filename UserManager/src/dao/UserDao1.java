package dao;

import entiy.User;
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


    public static void main1(String[] args) {
        User user=new User();
        user.setName("zhangfei");

    }

    //1.
    public User login(User user){
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
            String sql="";
            connection=DBUtil.getConnection();
            ps=connection.prepareStatement(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
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
                    "valus (?,?,?,?,?,?,?,?);";

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

            int ret=ps.executeUpdate();
            return ret;


        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }

        return 0;   //添加失败
    }


    //3.删除用户
    public static int delect(int id){
        Connection connection=null;
        PreparedStatement ps=null;
        //ResultSet rs=null;

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
    public static void main(String[] args) {
        int ret=delect(6);
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
            String sql="select * from usermessage where id=?";

            connection=DBUtil.getConnection();
            ps=connection.prepareStatement(sql);
            ps.setInt(1,id);

            rs =ps.executeQuery();
            if(rs.next()){
                user=new User();

            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }


    }



    //5.更新用户信息
    public static  int updataUser(User upUser){

    }

}
