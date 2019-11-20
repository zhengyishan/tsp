package com.dyy.tsp.common.entity;

public class Page {
    //总条数
    private Long totalCount;

    //请求页数
    private Long pageIndex;

    //每页请求数
    private Long pageSize;

    public Page() {
        super();
        this.pageIndex = 1L;
        this.pageSize = 10L;
    }

    public Page(Long pageIndex) {
        this();
        pageIndex = pageIndex == null ? 1 : pageIndex;
        this.pageSize = 10L;
        this.pageIndex = pageIndex;
    }

    public Page(Long pageIndex, Long pageSize) {
        super();

        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 10L : pageSize;

        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Long getPageSize() {
        return pageSize = pageSize < 1 ? 10l : pageSize;
    }

    public void setPageSize(Long pageSize) {

        pageSize = pageSize == null ? 10l : pageSize;

        this.pageSize = pageSize;
    }

    public Long getPageIndex() {
        return pageIndex < 1 ? 1 : pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        pageIndex = pageIndex == null ? 1 : pageIndex;

        this.pageIndex = pageIndex;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getPageCount() {
        if (totalCount % pageSize > 0) {
            return totalCount / pageSize + 1;
        } else {
            return totalCount / pageSize;
        }
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalCount=" + totalCount +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }
}
