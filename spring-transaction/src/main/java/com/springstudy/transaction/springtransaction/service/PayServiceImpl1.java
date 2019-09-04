package com.springstudy.transaction.springtransaction.service;

import com.springstudy.transaction.springtransaction.dao.AccountInfoDao;
import com.springstudy.transaction.springtransaction.dao.ProductInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Component("payService1")
public class PayServiceImpl1 implements PayService1 {

    @Autowired
    private AccountInfoDao accountInfoDao;

    @Autowired
    private ProductInfoDao productInfoDao;

    @Autowired
    private PayService payService;


    @Transactional
    public void pay(String accountId, double money) {
        //查询余额
        double blance = accountInfoDao.qryBlanceByUserId(accountId);

        //余额不足正常逻辑
        if(new BigDecimal(blance).compareTo(new BigDecimal(money))<0) {
            throw new RuntimeException("余额不足");
        }

       /* //更新库存
        ((PayService) AopContext.currentProxy()).updateProductStore(1);


        System.out.println(1/0);*/

        //更新余额
        int retVal = accountInfoDao.updateAccountBlance(accountId,money);
        // 手动回滚事务
        //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

    @Transactional(propagation =Propagation.REQUIRES_NEW)
    public void updateProductStore(Integer productId) {
        try{
            productInfoDao.updateProductInfo(productId);

        }
        catch (Exception e) {
            throw new RuntimeException("内部异常");
        }
    }


}
