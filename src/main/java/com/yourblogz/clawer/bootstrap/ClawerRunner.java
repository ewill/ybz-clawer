package com.yourblogz.clawer.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yourblogz.clawer.Clawer;
import com.yourblogz.clawer.IDataService;
import com.yourblogz.clawer.csdn.CsdnClawer;

public class ClawerRunner {
    
    private static final Logger log = LoggerFactory.getLogger(ClawerRunner.class);
    
    private Clawer[] clawers = {
        new CsdnClawer().setDataService(new IDataService<String>() {
            @Override
            public void dataHandler(String t) {
                
            }
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
        for (Clawer c : clawers) {
            c.start(); Thread.sleep(3000);
        }
    }
    
    public void shutdown() throws InterruptedException {
        for (Clawer c : clawers) {
            c.shutdown(); c.join();
        }
    }
    
}
