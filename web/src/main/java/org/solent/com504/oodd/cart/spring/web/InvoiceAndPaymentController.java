package org.solent.com504.oodd.cart.spring.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.model.service.ShoppingService;
import org.solent.com504.oodd.cart.web.WebObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.solent.com504.oodd.bank.model.dto.CreditCard;

import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;

@Controller
@RequestMapping("/")
public class InvoiceAndPaymentController {

    final static Logger LOG = LogManager.getLogger(InvoiceAndPaymentController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShoppingService shoppingService = null;
    
    @Autowired
    ShoppingService paymentService = null;
    
    

    // note that scope is session in configuration
    // so the shopping cart is unique for each web session
    @Autowired
    ShoppingCart shoppingCart = null;

    @Autowired
    InvoiceRepository invoiceRepository;

    private User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            sessionUser = new User();
            sessionUser.setUsername("anonymous");
            sessionUser.setUserRole(UserRole.ANONYMOUS);
            session.setAttribute("sessionUser", sessionUser);
        }
        return sessionUser;
    }

    //CHECKOUT 
    @RequestMapping(value = "/checkout", method = {RequestMethod.GET})
    public String checkout(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "home");

        String message = "";
        String errorMessage = "";

        if (!sessionUser.getUserRole().equals(UserRole.CUSTOMER)) {

            errorMessage = "You must log in before proceed to checkout";
            model.addAttribute("errorMessage", errorMessage);

            return "login";
        } else {

            List<ShoppingItem> availableItems = shoppingService.getAvailableItems();

            List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

            Double shoppingcartTotal = shoppingCart.getTotal();

            // populate model with values
            model.addAttribute("availableItems", availableItems);
            model.addAttribute("shoppingCartItems", shoppingCartItems);
            model.addAttribute("shoppingcartTotal", shoppingcartTotal);
            model.addAttribute("message", message);
            model.addAttribute("errorMessage", errorMessage);

            return "checkout";

        }

    }

    @RequestMapping(value = "/checkout", method = {RequestMethod.POST})
    public String checkoutPayment(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,
            @RequestParam(name = "endDate1", required = false) String endDate1,
            @RequestParam(name = "cardnumber1", required = false) String cardnumber1,
            @RequestParam(name = "cvv1", required = false) String cvv1,
            @RequestParam(name = "issueNumber1", required = false) String issueNumber1,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "home");

        String message = "";
        String errorMessage = "";

        if (!sessionUser.getUserRole().equals(UserRole.CUSTOMER)) {

            errorMessage = "You must log in before proceed to pay";
            model.addAttribute("errorMessage", errorMessage);

            return "login";
        } else {

            List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();
            Double shoppingcartTotal = shoppingCart.getTotal();
            


            //CardFrom
            String name1 = "test user1";

            CreditCard cardFrom = new CreditCard();

            cardFrom.setName(name1);
            cardFrom.setEndDate(endDate1);
            cardFrom.setCardnumber(cardnumber1);
            cardFrom.setCvv(cvv1);
            cardFrom.setIssueNumber(issueNumber1);
            
            
            //Amount
            Double amount = shoppingcartTotal;

            

//PRINCIPIO PAGO BANCO 
            /*    String bankUrl = "http://com528bank.ukwest.cloudapp.azure.com:8080/rest/";

            BankRestClient client = new BankRestClientImpl(bankUrl);

            TransactionReplyMessage reply = null;

            CreditCard cardFrom = null;
            CreditCard cardTo = null;

            //CardFrom
            String name1 = "test user1";

            cardFrom = new CreditCard();

            cardFrom.setName(name1);
            cardFrom.setEndDate(endDate1);
            cardFrom.setCardnumber(cardnumber1);
            cardFrom.setCvv(cvv1);
            cardFrom.setIssueNumber(issueNumber1);
            //Card To
            String name2 = "test user2";
            String endDate2 = "11/21";
            String cardnumber2 = "4285860000000021";
            String cvv2 = "123";
            String issueNumber2 = "01";

            cardTo = new CreditCard();
            cardTo.setName(name2);
            cardTo.setEndDate(endDate2);
            cardTo.setCardnumber(cardnumber2);
            cardTo.setCvv(cvv2);
            cardTo.setIssueNumber(issueNumber2);
            //Amount
            Double amount = shoppingcartTotal;

            reply = client.transferMoney(cardFrom, cardTo, amount);

            message = reply.toString();    */
//FIN PAGO BANCO
            //SAVE THE INVOICE
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());

            Invoice invoice = new Invoice();
            invoice.setDateOfPurchase(date);
            invoice.setAmountDue(amount);
            invoice.setPurchasedItems(shoppingCart.getShoppingCartItems());
            invoice.setPurchaser(sessionUser);

            invoice = invoiceRepository.save(invoice);

            errorMessage = invoice.toString();
//desde 140 va al servicio
            //    conection conection = new conection();
            //  conection.connectDatabase();
            // populate model with values
            model.addAttribute("invoice", invoice);
            model.addAttribute("shoppingCartItems", shoppingCartItems);
            model.addAttribute("shoppingcartTotal", shoppingcartTotal);
            model.addAttribute("message", message);
            model.addAttribute("errorMessage", errorMessage);

            return "home";

        }

    }

    //CHECKOUT END
    //SHOW ORDERS
    @RequestMapping(value = "/orders", method = {RequestMethod.GET})
    public String orders(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,
            Model model,
            HttpSession session) {

        String message = "";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!sessionUser.getUserRole().equals(UserRole.CUSTOMER)) {

            errorMessage = "You must log in to see your orders";
            model.addAttribute("errorMessage", errorMessage);

            return "login";
        } else {

            //List<Invoice> orderList = invoiceRepository.findAll();
            List<Invoice> orderList = invoiceRepository.findAll();

            model.addAttribute("invoiceListSize", orderList.size());
            model.addAttribute("invoiceList", orderList);
            model.addAttribute("selectedPage", "orders");
            return "orders";
        }

    }

    /*
     * Default exception handler, catches all exceptions, redirects to friendly
     * error page. Does not catch request mapping errors
     */
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model,
            HttpServletRequest request
    ) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        final String strStackTrace = sw.toString(); // stack trace as a string
        String urlStr = "not defined";
        if (request != null) {
            StringBuffer url = request.getRequestURL();
            urlStr = url.toString();
        }
        model.addAttribute("requestUrl", urlStr);
        model.addAttribute("strStackTrace", strStackTrace);
        model.addAttribute("exception", e);
        //logger.error(strStackTrace); // send to logger first
        return "error"; // default friendly exception message for sessionUser
    }

}
