package util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ：ZXY
 * @date ：Created in 2020/5/12 21:32
 * @description：链接数据库
 */

public class DBUtil {
    private static final String URL="jdbc:mysql://localhost:3306/cash?useSSL=false";
    private static final String USERNAME="root";
    private static final String PASSWORD="123456";

    private static volatile DataSource DATASORCE;

    //数据库连接池
    private  static DataSource getDATASORCE(){
        if(DATASORCE==null){
            synchronized (DBUtil.class){
                if(DATASORCE==null){
                    DATASORCE=new MysqlDataSource();
                    ((MysqlDataSource)DATASORCE).setUrl(URL);
                    ((MysqlDataSource)DATASORCE).setUser(USERNAME);
                    ((MysqlDataSource)DATASORCE).setPassword(PASSWORD);
                }
            }
        }
        return DATASORCE;
    }


    public static Connection getConnection(boolean autocommit){

        try{
            Connection connection=getDATASORCE().getConnection();
            connection.setAutoCommit(autocommit);
            return connection;
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }



    public static void close(Connection con, PreparedStatement ps, ResultSet rs){

        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }




