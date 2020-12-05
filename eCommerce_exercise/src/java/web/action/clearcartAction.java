/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import cart.ShoppingCart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.ViewManager;

/**
 *
 * @author osboxes
 */
public class clearcartAction extends Action {

    @Override
    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        ShoppingCart cart = (ShoppingCart) req.getSession().getAttribute("cart");
        cart.clear();

        ViewManager.nextView(req, resp, "/view/viewcart.jsp");
    }

}
