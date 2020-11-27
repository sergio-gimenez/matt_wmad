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

    public neworderAction(CategoryModel categoryModel, ProductModel productModel) {
        this.productModel = productModel;
        this.categoryModel = categoryModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        String productid = req.getParameter("productid");
        Product product = productModel.retrieveProductById(Integer.parseInt(productid));
        
        ShoppingCart cart;
        if (req.getSession().getAttribute("cart") == null) {
            cart = new ShoppingCart();
        } else {
            cart = (ShoppingCart) req.getSession().getAttribute("cart");
        }

        cart.addItem(product);
        req.getSession().setAttribute("cart", cart);

        System.out.println(cart.getNumberOfItems());

        ViewManager.nextView(req, resp, "/view/category.jsp");
    }
}