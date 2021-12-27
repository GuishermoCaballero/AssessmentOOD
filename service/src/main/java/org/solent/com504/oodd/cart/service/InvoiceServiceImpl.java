/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.service.InvoiceService;
import org.solent.com504.oodd.cart.model.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author cgallen
 */

public class InvoiceServiceImpl implements InvoiceService {

    // note ConcurrentHashMap instead of HashMap if map can be altered while being read
    @Autowired
    InvoiceRepository invoiceRepository;

   

    @Override
    public boolean saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
        return true;
    }

    @Override
    public List<Invoice> getUserInvoices(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
