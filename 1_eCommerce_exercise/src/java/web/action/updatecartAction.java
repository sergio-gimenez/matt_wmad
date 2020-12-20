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
import model.ProductModel;
import web.ViewManager;

/**
 *
 * @author osboxes
 */
public class updatecartAction extends Action {
    
    ProductModel productModel;
    ShoppingCart cart;
    
    public updatecartAction(ProductModel productModel) {
        this.productModel = productModel;
    }
    
    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        cart = (ShoppingCart) req.getSession().getAttribute("cart");
        
        Integer quantity = Integer.parseInt(req.getParameter("updated_quantity"));
        Integer productId = Integer.parseInt(req.getParameter("updated_product_id"));
        
        Product product = productModel.retrieveProductById(productId);
        cart.update(product, quantity);
        
        req.getSession().setAttribute("cart", cart);
        
        ViewManager.nextView(req, resp, "/view/viewcart.jsp");
    }  
    
    
    
}
