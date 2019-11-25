package cn.wuyiz.hoyoo.web.servlet;

import cn.wuyiz.hoyoo.bean.ResultInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径
        String uri = req.getRequestURI();
        //System.out.println(uri);
        //获取方法名称
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        if (methodName == null || methodName.trim().length() == 0) {
            methodName = "index";  //若为空，后者长度为0，给他一个默认值
        }
        //获取方法对象
        //其中this表示调用者，比如/user/login 则表示userServlet调用父类BaseServlet的service方法，所以this指代UserServlet
        try {
            //如果方法是protected修饰，则使用getDeclaredMethod获取，然后使用method.setAccessible(true)暴力反射，但是非常不建议
            //可以将方法设置为public，这样就只能调用指定的方法，私有方法供内部使用
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 传入封装类对象，将其序列化为json并写回客户端
     *
     * @param obj
     * @param response
     * @throws IOException
     */
    void writeValue(Object obj, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), obj);
    }

    /**
     * 返回一个对象的json串
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    /**
     * 验证码校验
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    boolean checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            return false;
        }
        return true;
    }

    public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        response.getWriter().println("亲，不要捣乱");

        return null;
    }
}
