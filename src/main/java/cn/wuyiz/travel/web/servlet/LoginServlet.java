package cn.wuyiz.travel.web.servlet;

import cn.wuyiz.travel.Service.Impl.UserServiceImpl;
import cn.wuyiz.travel.Service.UserService;
import cn.wuyiz.travel.bean.ResultInfo;
import cn.wuyiz.travel.bean.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        String code = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        ////保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //判断用户输入的验证码是否匹配
        if (code == null || !checkcode_server.equalsIgnoreCase(code)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误，请重新输入！");
            //将错误信息对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //设置响应信息
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
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
        UserService service = new UserServiceImpl();
        User u = service.login(user);
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

        }
        //序列化对象
        ObjectMapper mapper = new ObjectMapper();
        //设置Json的MIME类型，响应到前端
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
