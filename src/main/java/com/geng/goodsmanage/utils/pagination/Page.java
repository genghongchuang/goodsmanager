package com.geng.goodsmanage.utils.pagination;

import java.util.Collections;
import java.util.List;


/**
 * 表示分页中的一页。
 * 
 * @author  Zhang Kaitao
 */
public class Page<T> {
    private boolean hasPre;//是否首页
    private boolean hasNext;//是否尾页
    private List<T> items;//当前页包含的记录列表
    private int index;//当前页页码(起始为1)
    private IPageContext<T> context;
    private long count;//查询综述
    
    
   
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public IPageContext<T> getContext() {
        return this.context;
    }

    public void setContext(IPageContext<T> context) {
        this.context = context;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isHasPre() {
        return this.hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public boolean isHasNext() {
        return this.hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<T> getItems() {
        return this.items == null ? Collections.<T>emptyList() : this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
    
}
