package info.kimiazhu.demo.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.Current;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.kimiazhu.demo.DemoApplication;
import info.kimiazhu.demo.model.AutoincrementSeq;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
//@TransactionConfiguration(defaultRollback = true)
//@Transactional
public class AutoincrementSeqMapperTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoincrementSeqMapperTest.class);
    
    @Autowired
    private AutoincrementSeqMapper autoincrementSeqMapper;
    
    @Test
    public void testInsert() throws Exception {
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                for(int i = 0; i < 100; i++) {
                    AutoincrementSeq autoincrementSeq = new AutoincrementSeq();
                    autoincrementSeqMapper.insert(autoincrementSeq);
//                    LOGGER.info("current id: " + autoincrementSeq.getId() + ", r = " + r);
                }
            }
        };
        
        int runnerCount = 100;
        TestRunnable[] runners = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) { 
            runners[i] = runner; 
        }
        
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(runners);
        long start =  System.currentTimeMillis();
        try {
            mttr.runTestRunnables();
        } catch (Throwable t) {
            LOGGER.error("Error occur", t);
        }
        LOGGER.info("elapse time: " + ((System.currentTimeMillis() - start)/1000.f) + "s");
    }
}
