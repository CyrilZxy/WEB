package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ZXY
 * @date ：Created in 2020/7/23 21:04
 * @description：
 */

@WebServlet("/updateServlet")
public class UpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/jason;charset=utf-8");

        //获取信息显示
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String ageString = req.getParameter("age");
        int age = Integer.parseInt(ageString);
        String address = req.getParameter("address");
        String qq = req.getParameter("qq");
        String email = req.getParameter("email");

        Object us=req.getSession().getAttribute("updateUser");    //拿到session中的对象
        User user=(User) us;    //强制转换

        User updateUser=new User();     //获取session对象的信息 赋值给update
        updateUser.setId(user.getId());
        updateUser.setName(name);               //姓名不可更改
        updateUser.setGender(gender);
        updateUser.setAge(age);
        updateUser.setAddress(address);
        updateUser.setQq(qq);
        updateUser.setEmail(email);

        UserService userService=new UserService();
        int ret=userService.updateUser(updateUser);

        Map<String,Object> returnMap=new HashMap<>();
        if (ret==1){
            returnMap.put("msg",true);
        }else{
            returnMap.put("msg",false);
        }


        //把登录成功的map发送给前端，json 便于前端处理
        ObjectMapper objectMapper=new ObjectMapper();

        //将returnMap转换为json字符串
        objectMapper.writeValue(resp.getWriter(),returnMap);


    }


}
