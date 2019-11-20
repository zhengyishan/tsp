package com.dyy.tsp.common.entity;

import com.dyy.tsp.common.exception.IError;
import java.io.Serializable;
import java.util.List;

/**
 * created by dyy
 * @param <T>
 */
@SuppressWarnings("all")
public class Response<T> implements Serializable {
    private Status status;
    private String errorCode;
    private String errorMessage;
    private String extMessage;
    private Long pageIndex;
    private Long pageCount;
    private Long pageSize;
    private Long totalCount;
    private T data;
    private List<T> datas;

    public Response() {
        this.status = Status.SUCCEED;
    }

    public static Response success() {
        Response response = new Response();
        return response;
    }

    public static<T> Response success(T t) {
        Response response = new Response();
        response.setData(t);
        return response;
    }

    public static<T> Response success(List<T> list) {
        Response response = new Response();
        response.setDatas(list);
        return response;
    }

    public static<T> Response success(List<T> list, Page page) {
        Response response = new Response();
        response.setDatas(list);
        response.setPageCount(page.getPageCount());
        response.setPageIndex(page.getPageIndex());
        response.setTotalCount(page.getTotalCount());
        response.setPageSize(page.getPageSize());
        return response;
    }

    public static Response error() {
        Response response = new Response();
        response.status = Status.FAILED;
        return response;
    }

    public static Response error(IError error) {
        Response response = new Response();
        response.errorCode = error.getErrorCode();
        response.errorMessage = error.getErrorMessage();
        response.status = Status.FAILED;
        return response;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static enum Status {
        SUCCEED,
        FAILED;

        private Status() {
        }
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

}
