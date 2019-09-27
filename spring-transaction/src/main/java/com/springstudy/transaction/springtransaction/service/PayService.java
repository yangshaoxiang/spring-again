package com.springstudy.transaction.springtransaction.service;


/**
 * Created by smlz on 2019/6/17.
 */


public interface PayService {


    void pay(String accountId, double money);

    void updateProductStore(Integer productId);

    void payWithoutTran(String accountId, double money);
}
