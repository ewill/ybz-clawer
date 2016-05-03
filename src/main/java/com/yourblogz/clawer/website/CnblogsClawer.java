package com.yourblogz.clawer.website;

import org.jsoup.nodes.Document;

import com.yourblogz.clawer.Clawer;
import com.yourblogz.clawer.bean.CnblogsBean;

public class CnblogsClawer extends Clawer<CnblogsBean> {

    public CnblogsClawer(String name) {
        super(name);
        addSites("http://www.cnblogs.com/mvc/AggSite/PostList.aspx?PageIndex=1",
                 "http://www.cnblogs.com/mvc/AggSite/PostList.aspx?PageIndex=2",
                 "http://www.cnblogs.com/mvc/AggSite/PostList.aspx?PageIndex=3");
        addRules("^http://www.cnblogs.com/([\\w\\d]+)/p/([\\d]+)\\.html$");
    }

    @Override
    protected void page(Document page) {
        System.out.println(page.title());
    }

}
