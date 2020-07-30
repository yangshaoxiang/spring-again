package com.springstudy.transaction.springtransaction.service;

import com.springstudy.transaction.springtransaction.dao.AccountInfoDao;
import com.springstudy.transaction.springtransaction.dao.ProductInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Component("payService")
public class PayServiceImpl implements PayService {

    @Autowired
    private AccountInfoDao accountInfoDao;

    @Autowired
    private ProductInfoDao productInfoDao;

    @Autowired
    private PayService1 payService1;



    @Transactional
    public void pay(String accountId, double money) {
        System.out.println("进入方法。。。。");
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
        } catch (Exception e) {
            throw new RuntimeException("内部异常");
        }
    }


    @Override
    public void payWithoutTran(String accountId, double money) {
        System.out.println("进入 service 方法。。。。");
        //查询余额
        double blance = accountInfoDao.qryBlanceByUserId(accountId);

        //余额不足正常逻辑
        if(new BigDecimal(blance).compareTo(new BigDecimal(money))<0) {
            throw new RuntimeException("余额不足");
        }

        //更新余额
        int retVal = accountInfoDao.updateAccountBlance(accountId,money);

        pay(accountId,money);
        int a = 1/0;
    }

    public static void main(String[] args) {
        String ss = "0123,";
        StringBuilder sb = new StringBuilder(ss);
        String substring = sb.substring(0, sb.length() - 1);
        System.out.println(substring);
        System.out.println(sb);
    }
}
