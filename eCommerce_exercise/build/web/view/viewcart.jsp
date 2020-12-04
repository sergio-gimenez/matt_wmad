<%@ page import="entity.Category" %>
<%@ page import="entity.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="cart.ShoppingCart" %>
<%@ page import="cart.ShoppingCartItem" %>

<head>
    <meta http-equiv="Expires" CONTENT="0">
    <meta http-equiv="Cache-Control" CONTENT="no-cache">
    <meta http-equiv="Pragma" CONTENT="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>eCommerce Sample</title>
</head>

<body>  
    <%    
        ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
    %>     
    
    <table width="50%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">

        <tr> <font size="2" face="Verdana"> 
        <th> Product  </th>
        <th> Description </th>
        <th> Price </th>
        <th> Photo </th>
        <th> Action </th>
    </tr>


    <%
        List<ShoppingCartItem> items = (List<ShoppingCartItem>) cart.getItems();

        for (ShoppingCartItem item : items) {
            Product product = item.getProduct();
    %>

    <tr> 
        <td width="14%" valign="center" align="middle"> 
            <%=product.getName()%> </td>

        <td width="14%" valign="center" align="middle"> 
            <%=product.getDescription()%> </td>

        <td width="14%" valign="center" align="middle"> 
            <%=product.getPrice() + " euros"%> </td>

        <td width="14%" valign="center" align="middle">
            <a href="category.do?categoryid=<%= product.getId()%>">
                <img src="img/products/<%=product.getName()%>.png"
                     alt="<%=product.getName()%>">
            </a>
        </td>

        <td width="14%" valign="center" align="middle">            
        </td>
        <% }%>
        </font> 
    </tr>
</table>

Total price: <%=cart.getTotal()%>



</body>
