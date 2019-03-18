package com.codvision.terminal.common;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class ListResponseEntity<T> {
    private int total = 0;
    private int page = 1;
    private int size = 20;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        if (null != list){
            PageInfo<T> pageInfo = new PageInfo<>(list);
            setPageList(pageInfo);
        }
    }

    /**
     * 使用PageInfo进行分页
     *
     * @param pageInfo
     */
    private void setPageList(PageInfo<T> pageInfo) {
        this.list = pageInfo.getList();
        this.total = (int) pageInfo.getTotal();
        this.page = pageInfo.getPageNum();
        this.size = pageInfo.getSize();
    }
}
