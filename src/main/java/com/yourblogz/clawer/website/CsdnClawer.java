package com.yourblogz.clawer.website;

import org.jsoup.nodes.Document;

import com.yourblogz.clawer.Clawer;
import com.yourblogz.clawer.bean.CsdnBean;

public class CsdnClawer extends Clawer<CsdnBean> {
    
    private CsdnBean csdnBean = null;
    
    public CsdnClawer(String name) {
        super(name);
        addSites("http://blog.csdn.net/",
                 "http://blog.csdn.net/?page=2",
                 "http://blog.csdn.net/?page=3");
        addRules("^http://blog\\.csdn\\.net/([\\w\\d]+)/article/details/[\\d]+$");
    }

    @Override
    protected void page(Document page) {
        csdnBean = new CsdnBean();
        service.data(csdnBean);
    }

}
