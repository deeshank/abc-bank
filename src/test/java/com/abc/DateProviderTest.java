package com.abc;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by deeshank13 on 5/20/16.
 */
public class DateProviderTest {

    @Test
    public void test_isWithinRange(){

        Date test_dt = DateProvider.getInstance().toDate("01/01/2016");
        Date start_dt = DateProvider.getInstance().toDate("01/01/2015");
        Date end_dt = DateProvider.getInstance().toDate("01/01/2017");
        assertTrue(DateProvider.getInstance().isWithinRange(test_dt,start_dt,end_dt));

        test_dt = DateProvider.getInstance().toDate("01/01/2015");
        start_dt = DateProvider.getInstance().toDate("01/01/2015");
        end_dt = DateProvider.getInstance().toDate("01/01/2017");
        assertTrue(DateProvider.getInstance().isWithinRange(test_dt,start_dt,end_dt));

        test_dt = DateProvider.getInstance().toDate("01/01/2017");
        start_dt = DateProvider.getInstance().toDate("01/01/2015");
        end_dt = DateProvider.getInstance().toDate("01/01/2017");
        assertTrue(DateProvider.getInstance().isWithinRange(test_dt,start_dt,end_dt));

    }


    @Test
    public void test_addDays(){
        Date dt = DateProvider.getInstance().toDate("05/20/2016");
        Date cur_dt = DateProvider.getInstance().addDays(dt,-1);
        assertEquals("05/19/2016",DateProvider.getInstance().toString(cur_dt));

        dt = DateProvider.getInstance().toDate("05/20/2016");
        cur_dt = DateProvider.getInstance().addDays(dt,5);
        assertEquals("05/25/2016",DateProvider.getInstance().toString(cur_dt));
    }
}
