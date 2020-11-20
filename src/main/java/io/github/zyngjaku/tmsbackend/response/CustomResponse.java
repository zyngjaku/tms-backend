package io.github.zyngjaku.tmsbackend.response;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CustomResponse {
    @Expose
    private int page;
    @Expose
    private int perPage;
    @Expose
    private int total;
    @Expose
    private List<?> data;

    public CustomResponse(int page, int perPage, List<?> data) {
        setPage(page);
        setPerPage(perPage);
        setData(data);
    }

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }
    public void setData(List<?> data) {
        this.total = data.size();
        this.data = data;
    }
}
