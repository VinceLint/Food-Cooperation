package cn.scau.springcloud.domain.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class Query implements Serializable {

    private static final Integer DEFAULT_PAGE_SIZE = 50;

    /**
     * 是否需要查询包含软删除的数据
     */
    private Boolean withTrashed;

    /**
     * 当前页码，从1开始
     */
    private Integer curPage = 1;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 是否需要查询符合条件的记录总条数，用于分页
     */
    private Boolean withCount;

    /**
     * OrderBy子句
     */
    private String orderBy;

    /**
     * 获取当前页，默认从1开始
     */
    public Integer getCurPage() {
        if (this.curPage == null || this.curPage < 1) {
            return 1;
        }
        return this.curPage;
    }

    /**
     * limit子句第一个参数
     */
    public Integer getStart() {
        return (this.getCurPage() - 1) * getPageSize();
    }

    /**
     * 获取每页大小，默认最大返回50
     */
    public Integer getPageSize() {
        if (this.pageSize == null || this.pageSize < 1) {
            this.setPageSize(DEFAULT_PAGE_SIZE);
            return DEFAULT_PAGE_SIZE;
        }
        return this.pageSize;
    }

}
