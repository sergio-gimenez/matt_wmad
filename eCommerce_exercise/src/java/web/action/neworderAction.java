/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import cart.ShoppingCart;

import entity.Product;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CategoryModel;
import model.ProductModel;
import web.ViewManager;

/**
 *
 * @author osboxes
 */
public class neworderAction extends Action {

    ProductModel productModel;
    CategoryModel categoryModel;
    ShoppingCart shoppingCart;
    Product product;

    public neworderAction(CategoryModel categoryModel, ProductModel productModel) {
        this.productModel = productModel;
        this.categoryModel = categoryModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession current_session = req.getSession();

        // Check if there was already a shopping cart in the session scope.
        // Otherwise create a new one.
        ShoppingCart cart = (ShoppingCart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
        }
        
        String productid = req.getParameter("productid");
        product = productModel.retrieveProductById(Integer.parseInt(productid));
        cart.addItem(product);
        
        // Set the cart for the current session scope
        current_session.setAttribute("cart", cart);
        
        // Pass all the necessar attributes to the request in order to propoerly
        // load the view.
        req.setAttribute("categoryid", product.getCategory().getId());
        req.setAttribute("categoryId", product.getCategory().getId());
        req.setAttribute("category", product.getCategory());
        req.setAttribute("categories", categoryModel.retrieveAll());
        req.setAttribute("products", productModel.retrieveProductsByCategory(product.getCategory()));       
               
        ViewManager.nextView(req, resp, "/view/category.jsp");
    }

}
