package com.yourblogz.clawer.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yourblogz.clawer.Clawer;
import com.yourblogz.clawer.website.CnblogsClawer;
import com.yourblogz.clawer.website.CsdnClawer;

public class ClawerRunner {
    
    private static final Logger log = LoggerFactory.getLogger(ClawerRunner.class);
    private final CsdnClawer csdn = new CsdnClawer("csdn-clawer");
    private final CnblogsClawer cnblogs = new CnblogsClawer("cnblogs-clawer");
    
    private Clawer<?>[] clawers = {
        csdn.setInterval(3000).setDataHandler((bean) -> {
            
        }),
        cnblogs.setInterval(3000).setDataHandler((bean) -> {
            
        })
    };

    public static void main(String[] args) {
        ClawerRunner runner = new ClawerRunner();
        try {
            runner.start();
        } catch (InterruptedException e) {
            log.error("Clawer error.", e);
        }
    }
    
    public void start() throws InterruptedException {
        for (Clawer<?> c : clawers) {
            c.start(); Thread.sleep(3000);
        }
    }
    
    public void shutdown() throws InterruptedException {
        for (Clawer<?> c : clawers) {
            c.shutdown(); c.join();
        }
    }
    
}
