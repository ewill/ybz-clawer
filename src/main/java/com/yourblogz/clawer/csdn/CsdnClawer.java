package com.yourblogz.clawer.csdn;

import com.yourblogz.clawer.Clawer;
import com.yourblogz.clawer.IDataService;

public class CsdnClawer extends Clawer {

    @Override
    public void run() {
        closed = false;
        while (!closed) {
            
        }
    }

    @Override
    public <V> CsdnClawer setDataService(IDataService<V> service) {
        service.dataHandler(null);
        return this;
    }

}
