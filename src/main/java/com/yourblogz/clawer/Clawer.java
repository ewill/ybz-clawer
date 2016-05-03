package com.yourblogz.clawer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yourblogz.clawer.kit.HttpKit;

public abstract class Clawer<T> extends Thread {
    
    protected final String name;
    protected volatile boolean closed = true;
    protected IDataHandler<T> service = null;
    protected final List<String> sites = new ArrayList<>();
    protected final List<String> rules = new ArrayList<>();
    protected Logger log = LoggerFactory.getLogger(Clawer.class);
    protected Pattern pattern;
    protected long interval;
    
    protected abstract void page(Document page);
    
    public Clawer(String name) {
        this.name = name;
    }
    
    public Clawer<T> setInterval(long interval) {
        this.interval = interval;
        return this;
    }
    
    public final Clawer<T> setDataHandler(IDataHandler<T> service) {
        this.service = service;
        return this;
    }
    
    public final void shutdown() {
        closed = true;
    }
    
    protected final Clawer<T> addRules(String... args) {
        rules.addAll(Arrays.asList(args));
        return this;
    }
    
    protected final Clawer<T> addSites(String... args) {
        sites.addAll(Arrays.asList(args));
        return this;
    }
    
    @Override
    public final void run() {
        if (rules.size() > 0) {
            pattern = Pattern.compile(buildRules());
        } else {
            pattern = null;
        }
        
        closed = false;
        while (!closed) {
            handle();
        }
    }
    
    protected void handle() {
        try {
            Set<String> filterSet = new LinkedHashSet<>();
            for (String site : sites) {
                Document doc = HttpKit.httpGet(site);
                Elements els = doc.select("a");
                if (CollectionUtils.isNotEmpty(els)) {
                    for (Element e : els) { filterSet.add(e.absUrl("href")); }
                    for (String url : filterSet) {
                        if (StringUtils.isNotBlank(url)) {
                            if (pattern != null && !pattern.matcher(url).matches()) {
                                continue;
                            }
                            page(HttpKit.httpGet(url));
                        }
                    }
                }
                filterSet.clear();
                sleep(interval);
            }
        } catch (Exception e) {
            log.error("Catch page fail.", e);
        }
    }
    
    private final String buildRules() {
        StringBuilder sb = new StringBuilder();
        for (String rule : rules) {
            if (sb.length() == 0) {
                sb.append("(").append(rule).append(")");
            } else {
                sb.append("|").append("(").append(rule).append(")");
            }
        }
        return sb.toString();
    }
}
