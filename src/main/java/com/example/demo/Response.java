package com.example.demo;

import com.example.demo.error.normal.IError;
import com.example.demo.error.normal.NEError;
import com.example.demo.vo.Page;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhouyan on 17-3-16.
 */
public class Response implements Serializable {

    private static final long serialVersionUID = -6602365878131231511L;
    public static final String DEFAULT_DATA_KEY = "data";
    public static final String DEFAULT_DATAS_KEY = "datas";

    public enum Status {
        SUCCEED, FAILED
    }

    private Status status = Status.SUCCEED;
    private String errorCode;
    private String errorMessage;
    private String extMessage;

    private Long pageIndex;
    private Long pageCount;
    private Long pageSize;
    private Long totalCount;
    @JsonIgnore
    private Map<String, Object> any;

    public static Response success() {
        Response response = new Response();
        return response;
    }


    public static Response success(Object data) {
        return success(DEFAULT_DATA_KEY, data);
    }


    public static Response success(String key, Object data) {
        return success(key, data, null);
    }

    public static Response success(Object data, Page page) {
        return success(DEFAULT_DATAS_KEY, data, page);
    }

    public static Response success(String key, Object data, Page page) {
        Response response = success().put(key, data);
        if (page != null) {
            response.page(page);
        }
        return response;
    }

    public static Response error() {
        return Response.error(NEError.SYSTEM_INTERNAL_ERROR);
    }

    public static Response error(IError error) {
        Response response = new Response();
        response.errorCode = error.getErrorCode();
        response.errorMessage = error.getErrorMessage();
        response.status = Status.FAILED;
        return response;
    }

    public Response put(Object any) {
        if (this.any == null) {
            this.any = new HashMap<>();
        }
        this.any.put(DEFAULT_DATA_KEY, any);
        return this;
    }

    public Response put(String key, Object data) {
        if (data == null) {
            return this;
        }
        if (this.any == null) {
            this.any = new HashMap<>();
        }
        any.put(key, data);
        return this;
    }

    public Response put(Map<String, Object> any) {
        if (this.any == null) {
            this.any = new HashMap<>();
        }
        this.any.putAll(any);
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> anyGetter() {
        return any;
    }

    @JsonAnySetter
    public void anySetter(String name, Object value) {
        if (any == null) {
            any = new HashMap<String, Object>();
        }
        any.put(name, value);
    }

    private Response page(Page page) {
        pageIndex = page.getPageIndex();
        pageCount = page.getPageCount();
        pageSize = page.getPageSize();
        totalCount = page.getTotalCount();
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExtMessage() {
        return extMessage;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
