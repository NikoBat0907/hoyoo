package cn.wuyiz.travel.web.servlet;

import cn.wuyiz.travel.Service.Impl.UserServiceImpl;
import cn.wuyiz.travel.Service.UserService;
import cn.wuyiz.travel.bean.ResultInfo;
import cn.wuyiz.travel.bean.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //service
    private UserService service = new UserServiceImpl();

    /**
     * 注册功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        if (!checkCode(request, response)) {
            return;
        }
        //1.获取数据
        Map<String, String[]> parameterMap = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service完成注册
        boolean regist_state = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if (regist_state) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败！此用户已存在。");
        }
        //序列化结果信息
        //设置Json的MIME类型，响应到前端
        writeValue(info, response);
    }

    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        if (!checkCode(request, response)) {
            return;
        }
        //获取提交的表单参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service完成业务操作
        User u = service.login(user.getUsername(), user.getPassword());
        ResultInfo info = new ResultInfo();
        //判断账号密码
        if (u == null) {
            //账户或密码错误
            info.setFlag(false);
            info.setErrorMsg("账号或密码错误，请重新输入！");
        }
        //判断用户状态
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("用户尚未激活，请通过注册邮箱进行激活！");
        }
        //用户存在
        if (u != null && "Y".equals(u.getStatus())) {
            request.getSession().setAttribute("user", u);
            info.setFlag(true);
        }
        //序列化结果信息
        //设置Json的MIME类型，响应到前端
        writeValue(info, response);
    }

    /**
     * 新用户激活
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            //判断是否激活
            if (service.active(code)) {
                //激活成功
                response.sendRedirect(request.getContextPath() + "/activation_successful.html");
            } else {
                response.sendRedirect(request.getContextPath() + "/activation_fail.html");
            }
        }
    }

    /**
     * 退出功能
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 主页显示登录用户姓名
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object user = request.getSession().getAttribute("user");
        //序列化结果信息
        //设置Json的MIME类型，响应到前端
        writeValue(user, response);
    }
}
