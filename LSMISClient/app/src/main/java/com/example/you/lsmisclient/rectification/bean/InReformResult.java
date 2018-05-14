package com.example.you.lsmisclient.rectification.bean;

import com.example.you.lsmisclient.bean.Result;

/**
 * Created by You on 2018/4/14.
 */

public class InReformResult<T> extends Result<T> {
    private String parentPath;

    public String getParentPath() {
        if(parentPath!=null)
            return parentPath;
        else
            return null;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }
}
