package cn.wuyiz.travel.web.servlet;

import cn.wuyiz.travel.Service.Impl.UserServiceImpl;
import cn.wuyiz.travel.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            boolean flag = userService.active(code);
            //判断是否激活
            if (flag) {
                //激活成功
                response.sendRedirect("activation_successful.html");
            } else {
                response.sendRedirect("activation_fail.html");
            }
            //相应数据到前端页面
            /*response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);*/
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
