package service;

import dao.UserDao;
import entity.PageBean;
import entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author ：ZXY
 * @date ：Created in 2020/7/20 17:33
 * @description：操作dao层
 *                高内聚低耦合
 */

public class UserService {


    //1.登录方法
    public User login(User loginUser){
        UserDao userDao=new UserDao();
        User user=userDao.login(loginUser);
        return user;
    }

    //2.添加方法
    public int add(User addUser) {
        UserDao userDao=new UserDao();
        int i = userDao.add(addUser);
        return i;
    }

    //3.删除方法
    public int delete(int id) {
        UserDao userDao=new UserDao();
        int i = userDao.delete(id);
        return i;
    }

    //4.根据id查询
    public User find(int id) {
        UserDao userDao=new UserDao();
        User user = userDao.find(id);
        return user;
    }

    //5.更新方法
    public int updateUser(User updateUser) {
        UserDao userDao=new UserDao();
        int i = userDao.updateUser(updateUser);
        return i;
    }



    /**PageBean 抽象信息，提取数据信息。
     * 合并：分页查询所有用户信息、查询共有多少条记录
     *      private int totalCount;     //总记录数  findAllRecord
     *      private int totalPage;      //总页码   totalCount%rows
     *      private List<T> list;       //每页中的数据    findByPage
     *      private int currentPage;    //当前页码 已知的
     *      private int rows;            //每页记录数 5
     *  map 包含 name address email
     *  根据map条件进行查询
     *
     */
    public PageBean<User> findAllByPage(int currentPage, int rows, Map<String, String[]> map) {
        PageBean<User> pageBean=new PageBean<>();
        UserDao userDao=new UserDao();
        int totalCount=userDao.findAllRecord(map);

        int totalPage=0;
        if (totalCount%rows!=0){        //页数+1
            totalPage=totalCount/rows+1;
        }else{
            totalPage=totalCount/rows;
        }
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);

        int start=(currentPage-1)*rows;      //计算开始行
        List<User> userList= userDao.findByPage(start,rows,map);
        pageBean.setList(userList);

        pageBean.setCurrentPage(currentPage);
        pageBean.setRows(rows);

        return pageBean;
    }





}
