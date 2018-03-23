package com.example.you.lsmisclient.check.bean;

import com.example.you.lsmisclient.treeview.Element;

/**
 * Created by You on 2018/3/19.
 */

public class CheckItemElement extends Element {

    private CheckItem checkItem;

    public CheckItemElement(String contentText, int level, int id, int parendId, boolean hasChildren, boolean isExpanded) {
        super(contentText, level, id, parendId, hasChildren, isExpanded);
    }

    public CheckItemElement(String contentText, int level, int id, int parendId, boolean hasChildren, boolean isExpanded, CheckItem checkItem) {
        super(contentText, level, id, parendId, hasChildren, isExpanded);
        this.checkItem = checkItem;
    }

    public CheckItem getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(CheckItem checkItem) {
        this.checkItem = checkItem;
    }
}
