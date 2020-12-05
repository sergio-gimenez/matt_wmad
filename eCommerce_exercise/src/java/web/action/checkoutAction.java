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
public class checkoutAction extends Action {

    /*
    Additionally, you have to provide the "proceed to chekout" button
    functionality, linking your web-site to a payment platform to simulate the
    last step where the payment is executed for the amount of money buying the
    items added to the shopping cart. 
    Visit this link for instructions on how to do it:
        https://www.paypal.com/cgi-bin/webscr?cmd=p/pdn/howto_checkout-outside
    */
    
    @Override
    public void perform(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String amount = req.getParameter("amount");
        req.setAttribute("amount", amount);
                
        ViewManager.nextView(req, resp, "/view/checkout.jsp");
    }    
}
