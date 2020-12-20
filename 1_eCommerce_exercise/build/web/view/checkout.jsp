<%-- 
    Document   : checkout
    Created on : Dec 5, 2020, 10:40:24 AM
    Author     : osboxes
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Expires" CONTENT="0">
        <meta http-equiv="Cache-Control" CONTENT="no-cache">
        <meta http-equiv="Pragma" CONTENT="no-cache">
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>eCommerce Sample</title>
    </head>

    <body>
        <form action="https://www.paypal.com/cgi-bin/webscr" method="post">
            <input type="hidden" name="cmd" value="_xclick">
            <input type="hidden" name="business" value="you@youremail.com">
            <input type="hidden" name="item_name" value="Item Name">
            <input type="hidden" name="currency_code" value="EUR">
            
            <%String amount = request.getParameter("amount");%>
            <input type="hidden" name="amount" value=<%=amount%>>
            
            <input type="image" src="http://www.paypal.com/en_US/i/btn/x-click-but01.gif" name="submit" alt="Make payments with PayPal - it's fast, free and secure!">
</form>
    </body>
</html>
