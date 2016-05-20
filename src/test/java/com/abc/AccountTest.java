package com.abc;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by deeshank13 on 5/20/16.
 */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void test_checkAnyTransaction() {
        Date start_dt = DateProvider.getInstance().toDate("05/01/2016");
        Date end_dt = DateProvider.getInstance().toDate("06/01/2016");

        Account acc = new Account(Account.CHECKING);
        acc.deposit(1000,"05/02/2016");
        assertFalse(acc.checkAnyWithdrawlTransaction(start_dt, end_dt));

        acc = new Account(Account.SAVINGS);
        acc.deposit(1000,"06/02/2016");
        assertFalse(acc.checkAnyWithdrawlTransaction(start_dt, end_dt));

        acc = new Account(Account.SAVINGS);
        acc.deposit(1000,"06/02/2016");
        acc.withdraw(100);
        assertTrue(acc.checkAnyWithdrawlTransaction(start_dt, end_dt));
        assertEquals(acc.getAvailableBalance(), 900.0, DOUBLE_DELTA);
    }

    @Test
    public void test_getTransactions() {
        Date start_dt = DateProvider.getInstance().toDate("05/01/2016");
        Date end_dt = DateProvider.getInstance().toDate("05/04/2016");

        Account acc = new Account(Account.SAVINGS);
        acc.deposit(1000,"05/01/2016");
        acc.deposit(1000,"05/02/2016");
        acc.deposit(1000,"05/03/2016");
        acc.deposit(1000,"05/04/2016");
        acc.deposit(1000,"05/05/2016");
        acc.deposit(1000,"05/06/2016");
        acc.deposit(1000,"05/07/2016");
        acc.deposit(1000,"05/08/2016");
        acc.deposit(1000,"05/09/2016");
        acc.deposit(1000,"05/10/2016");

        assertEquals(acc.getAllTransactions(start_dt,end_dt).size(),4);
    }

    @Test
    public void test_MaxiSavingInterestEarned() {

        Account acc = new Account(Account.MAXI_SAVINGS);
        acc.deposit(1000, "05/10/2016");
        acc.deposit(3000, "05/15/2016");
        assertEquals(acc.interestEarned(), 200.0, DOUBLE_DELTA);

        Account acc1 = new Account(Account.MAXI_SAVINGS);
        acc1.deposit(1000, "04/10/2016");
        acc1.deposit(2000, "04/15/2016");
        acc.withdraw(1000);
        assertEquals(acc1.interestEarned(),150.0,DOUBLE_DELTA);

    }

    @Test
    public void test_DailyInterest() {
        assertEquals(Account.dailyInterest(1000, 5, 3), 0.41, DOUBLE_DELTA); //Daily Interest accrued and calculated for first 3 days
        assertEquals(Account.dailyInterest(1000, 5, 365), 50.0, DOUBLE_DELTA); //Daily Interest accrued and calculated for 1 year
    }

    @Test
    public void test_DailyInterestEarned(){
        Account acc = new Account(Account.SAVINGS);
        acc.deposit(1000);
        assertEquals(acc.dailyInterestEarned(),1.0,DOUBLE_DELTA);

        acc = new Account(Account.CHECKING);
        acc.deposit(2000);
        assertEquals(acc.dailyInterestEarned(),2.0,DOUBLE_DELTA);

        acc = new Account(Account.MAXI_SAVINGS);
        acc.deposit(3000);
        assertEquals(acc.dailyInterestEarned(),150.0,DOUBLE_DELTA);

        acc = new Account(Account.MAXI_SAVINGS);
        acc.deposit(3000);
        acc.withdraw(1000);
        assertEquals(acc.dailyInterestEarned(),2.0,DOUBLE_DELTA);
    }
}
