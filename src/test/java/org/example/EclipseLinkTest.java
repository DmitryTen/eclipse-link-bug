package org.example;


import lombok.extern.slf4j.Slf4j;
import org.example.config.CommonJpaConfig;
import org.example.dao.TestTableDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CommonJpaConfig.class})
@Slf4j
public class EclipseLinkTest {

    @Autowired
    private TestTableDao dao;


    @Test
    public void buggyBehaviourTest(){
        String SHOULD_NOT_BE_PERSISTED = "flushed_val";
        /**
         * checking that DB is empty and the row flushed_val doesn't exist
         * */
        Assert.assertNull(dao.findByName(SHOULD_NOT_BE_PERSISTED));
        try {
            /**
             * The following dao invocation shouldn't result in persisting some values, since it result with runtime exception.
             * */
            dao.invalidBehaviourTest(SHOULD_NOT_BE_PERSISTED);
            Assert.fail("This line is never invoked");
        } catch (RuntimeException e) {
            log.info("RUNTIME EXCEPTION DURING TRANSACTION! NOTHING SHOULD BE PERSISTED!");
        }

        /**
         * BUT, It actually does. Why so?
         * */
        Assert.assertNotNull(dao.findByName(SHOULD_NOT_BE_PERSISTED));
    }
}
