<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
// request set in controller
//request.setAttribute("selectedPage", "home");
%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Chekout</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <H1>Shopping cart</H1>
    <table class="table">

        <tr>
            <th>Item Name</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>

        <c:forEach var="item" items="${shoppingCartItems}">

            <tr>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>${item.quantity}</td>
                
           <!--     <td> -->
                    <!-- post avoids url encoded parameters -->
          <!--          <form action="./home" method="post">
                        <input type="hidden" name="itemUUID" value="${item.uuid}">
                        <input type="hidden" name="itemName" value="${item.name}">
                        <input type="hidden" name="action" value="removeItemFromCart">
                        <button type="submit" >Remove Item</button>
                    </form> 
                </td> -->
            </tr>
        </c:forEach>
        <tr>
            <td>TOTAL</td>
            <td>${shoppingcartTotal}</td>
        </tr>
    </table>

    <div class="row">
        <div class="col">

            
        </div>
        <hr>
        <div class="col">


            <form action="checkout" method="post">
                <H1>Payment Details</H1>
                <h3>Enter Your Card Details</h3>
                <div class="mb-2">
                    <label for="name">Name</label>
                    <input id="name" type="text" class="form-control" name="name1" value="test user1">
                </div>
                <div class="mb-2">
                    <label for="enddate">End Date</label>
                    <input id="enddate" type="text" class="form-control" name="endDate1" value="11/21">
                </div>
                <div class="mb-2">
                    <label for="cardnum">Card Number</label>
                    <input id="cardnum" type="text" class="form-control" name="cardnumber1" value="5133880000000012">
                </div>
                <div class="mb-2">
                    <label for="cvv">CVV</label>
                    <input id="cvv" type="text" class="form-control" name="cvv1" value="123">
                </div>
                <div class="mb-2">
                    <label for="issuenum">Issuer Number</label>
                    <input id="issuenum" type="text" class="form-control" name="issueNumber1" value="01">
                </div>
                
                <button type="submit" class="btn btn-primary">Pay ${shoppingcartTotal}</button>
            </form>
        </div>
    </div>

    
    
    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    <form action="./first.jsp" method="post">
                <H1>Shipping and Contact Details</H1>
                <h3>Enter Your Shipping Adress</h3>
                <div class="mb-2">
                    <label for="name">House Number</label>
                    <input id="name" type="text" class="form-control" name="name1" value="${sessionUser.getAddress().getHouseNumber()}">
                </div>
                <div class="mb-2">
                    <label for="enddate">Address Line 1</label>
                    <input id="enddate" type="text" class="form-control" name="" value="${sessionUser}">
                </div>
                <div class="mb-2">
                    <label for="cardnum">Address Line 2</label>
                    <input id="cardnum" type="text" class="form-control" name="cardnumber1" value="${sessionUser.getAddress()}">
                </div>
                <div class="mb-2">
                    <label for="cvv">Country</label>
                    <input id="cvv" type="text" class="form-control" name="cvv1" value="${sessionUser.getAddress().getCountry()}">
                </div>
                <div class="mb-2">
                    <label for="issuenum">City</label>
                    <input id="issuenum" type="text" class="form-control" name="issueNumber1" value="${sessionUser.getAddress().getCity()}">
                </div>
                <div class="mb-2">
                    <label for="enddate">Postcode</label>
                    <input id="enddate" type="text" class="form-control" name="endDate1" value="${sessionUser.getAddress().getPostcode()}">
                </div>
                <div class="mb-2">
                    <label for="cardnum">Mobile</label>
                    <input id="cardnum" type="text" class="form-control" name="cardnumber1" value="${sessionUser.getAddress().getMobile()}">
                </div>
                <div class="mb-2">
                    <label for="cvv">Telephone</label>
                    <input id="cvv" type="text" class="form-control" name="cvv1" value="${sessionUser.getAddress().getTelephone()}">
                </div>
                <div class="mb-2">
                    <label for="issuenum">Country</label>
                    <input id="issuenum" type="text" class="form-control" name="issueNumber1" value="${sessionUser.getAddress().getCountry()}">
                </div>

            </form>

</main>
<jsp:include page="footer.jsp" />
