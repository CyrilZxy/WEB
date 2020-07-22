package dao;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import entity.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author ：ZXY
 * @date ：Created in 2020/7/13 20:08
 * @description：
 */

public class UserDao {



    //1.登录
    public  User login(User loginUser){
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



    //2.添加用户
    public  int add(User addUser){
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



    //3.删除用户
    public  int delete(int id){
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



    //4.由id查找用户
    public  User find(int id){
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



    //5.更新用户信息
    public  int updateUser(User upUser){
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




    //6.分页查询所有用户信息
    /*  查询当前条件下所有用户
    *   start：开始查询的起始位置
    *   rows：每次查询多少记录
    *   map：name,address,email  可填也可为空    String[]只放了一个字符串
    */
    public  List<User> findByPage(int start,int rows,Map<String,String[]> map){

        String sql="select * from usermessage where 1=1";
        StringBuilder s=new StringBuilder(sql);                 //拼接
        //select * from usermessage where 1=1 and address like ?  and name like ?  limit ?,?

        Set<String> set = map.keySet();                 //name,adress,mail
        List<Object> list =new ArrayList<>();

        for(String key : set){
            String value=map.get(key)[0];
            if(value!=null && !"".equals(value)){                       //有值
                s.append(" and ").append(key).append(" like ? ");       //拼接模糊查询
                list.add("%"+value+"%");
            }
        }

        s.append(" limit ?,? ");
        list.add(start);
        list.add(rows);

        //System.out.println("s: "+ s);
        //System.out.println("list: "+ list);



        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<User> userList = new ArrayList<>();
        try{
            connection=DBUtil.getConnection();
            ps=connection.prepareStatement(s.toString());

            //给sql语句赋值                      SQL语句不确定
            setValues(ps,list.toArray());

            rs=ps.executeQuery();
            while(rs.next()){
                User user=new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setAge(rs.getInt("age"));
                user.setAddress(rs.getString("address"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
                userList.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return userList;
    }


    public static void setValues(PreparedStatement ps,Object... arrays) throws SQLException{
        for (int i = 0; i < arrays.length; i++) {
            ps.setObject(i+1,arrays[i]);
        }
    }




    /** 7.查询共有多少条记录 有多少行
     *  @param   map 包含 name address email
     *  @return
     */
    public  int findAllRecord(Map<String, String[]> map) {

        int count=0;

        String sql = "select count(*) from usermessage where 1=1 "; //and name like ?
        StringBuffer sb = new StringBuffer(sql);

        List<Object> list = new ArrayList<>();
        Set<String> keySet = map.keySet();

        for (String key : keySet) {
            String value = map.get(key)[0];
            if(value != null && !"".equals(value)) {
                sb.append(" and ").append(key).append(" like ? ");
                list.add("%"+value+"%");
            }
        }


        //System.out.println("sql : "+ sb);

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sb.toString());
            //给sql语句赋值
            //String sql = "update usermessage set gender=?,age=?,address=?,qq=?,email=? where id=?";

            setValues(ps,list.toArray());

            rs = ps.executeQuery();

            if (rs.next()) {
                count=rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return count;
    }







}
