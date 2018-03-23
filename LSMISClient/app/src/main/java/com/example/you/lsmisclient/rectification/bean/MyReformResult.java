package com.example.you.lsmisclient.rectification.bean;

import com.example.you.lsmisclient.bean.Result;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by You on 2018/2/28.
 */

/**
 * totalCount	int	总的数据的行数
 *   pageNum	int	当前的页码
 */
public class MyReformResult<T> extends Result<T> {
    int totalCount;
    int pageNum;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
