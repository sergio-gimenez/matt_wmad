package web.action;

import entity.Category;
import javax.servlet.http.*;
import model.CategoryModel;
import model.ProductModel;
import web.ViewManager;

public class categoryAction extends Action {

    CategoryModel categoryModel;
    ProductModel productModel;
    
    public categoryAction(CategoryModel categoryModel, ProductModel productModel){
        this.categoryModel = categoryModel;
        this.productModel = productModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        String categoryid = req.getParameter("categoryid");   
        
        Category category = categoryModel.retreiveCategoryById(Integer.parseInt(categoryid));
        
        req.getSession().setAttribute("last_category", category);

        req.setAttribute("products", productModel.retrieveProductsByCategory(category));                
        
        ViewManager.nextView(req, resp, "/view/category.jsp");
        
    }
}
