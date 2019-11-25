package cn.wuyiz.hoyoo.web.servlet;

import cn.wuyiz.hoyoo.Service.CategoryService;
import cn.wuyiz.hoyoo.Service.Impl.CategoryServiceImpl;
import cn.wuyiz.hoyoo.bean.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    //service
    private CategoryService service = new CategoryServiceImpl();

    /**
     * 查询所有类别名称
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categoryList = service.findAll();
        writeValue(categoryList, response);
    }
}
