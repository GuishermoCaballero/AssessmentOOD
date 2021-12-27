/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.model.service;

import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.bank.model.dto.TransactionReplyMessage;

import java.util.List;
import org.solent.com504.oodd.bank.model.dto.CreditCard;

/**
 *
 * @author cgallen
 */
public interface PaymentService {

    public TransactionReplyMessage makePayment(CreditCard fromCard,Double amount);

}
