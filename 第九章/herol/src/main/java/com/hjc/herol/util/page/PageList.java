package com.hjc.herol.util.page;

import java.util.List;

/**
 * 翻页列表
 */
public class PageList implements java.io.Serializable {

    private List objectList;
    private Page page;
    private static final long serialVersionUID = 1L;

    public PageList(Page page) {
        PageContext pageContext = PageContext.getContext();
        pageContext.setPage(page);
        pageContext.setPagination(true);
        this.page = pageContext;
    }


    public List getObjectList() {
        return objectList;
    }

    public void setObjectList(List objectList) {
        this.objectList = objectList;
    }

    public Page getPage() {
        return page;
    }

}