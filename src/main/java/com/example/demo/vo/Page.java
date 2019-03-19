package com.example.demo.vo;


import com.example.demo.error.normal.NEError;
import com.example.demo.error.normal.NEException;

public class Page {
    //总条数
    private Long totalCount = 0l;

    //请求页数
    private Long pageIndex = 0l;

    //每页请求数
    private Long pageSize = 0l;

    public Page() {
        super();
        this.pageIndex = 1l;
        this.pageSize = 10l;
    }

    public Page(Long pageIndex) {
        this();
        pageIndex = pageIndex == null ? 1 : pageIndex;
        this.pageSize = 10l;
        this.pageIndex = pageIndex;
    }

    public Page(Long pageIndex, Long pageSize) {
        super();

        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 1 : pageSize;

        if (pageIndex <= 0 || pageSize <= 0) {
            throw new NEException(NEError.PAGER_PARAMETER_IS_NOT_CORRECT);
        }
        if (pageSize > 1000) {
            throw new NEException(NEError.PAGER_PARAMETER_IS_NOT_CORRECT, "Page Size too large, limit:1000");
        }
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Long getPageSize() {
        return pageSize = pageSize < 1 ? 1 : pageSize;
    }

    public void setPageSize(Long pageSize) {

        pageSize = pageSize == null ? 1 : pageSize;

        if (pageSize <= 0) {
            throw new NEException(NEError.PAGER_PARAMETER_IS_NOT_CORRECT);
        }
        if (pageSize > 1000) {
            throw new NEException(NEError.PAGER_PARAMETER_IS_NOT_CORRECT, "Page Size too large");
        }
        this.pageSize = pageSize;
    }

    public Long getPageIndex() {
        return pageIndex < 1 ? 1 : pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        pageIndex = pageIndex == null ? 1 : pageIndex;

        if (pageIndex <= 0) {
            throw new NEException(NEError.PAGER_PARAMETER_IS_NOT_CORRECT);
        }
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
