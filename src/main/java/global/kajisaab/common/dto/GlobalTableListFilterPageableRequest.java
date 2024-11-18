package global.kajisaab.common.dto;

import global.kajisaab.core.usecase.UseCaseRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Introspected
public class GlobalTableListFilterPageableRequest implements UseCaseRequest {

    private String searchParam;
    private String sortBy;
    private String sortOrder;
    private int page;
    private int pageSize;

    public GlobalTableListFilterPageableRequest(String searchParam, String sortBy, String sortOrder, Integer page, Integer pageSize) {
        this.searchParam = searchParam;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
