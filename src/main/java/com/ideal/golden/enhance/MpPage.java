package com.ideal.golden.enhance;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ideal.golden.pojo.query.PageQuery;

public class MpPage <T> extends Page<T> {

    public MpPage(PageQuery query) {
        super(query.getPageNumber(), query.getPageSize());
    }

    /**
     * 查询完之后把数据填充到query对象里，更新数据
     * @param query 查询对象
     */
    public void updateQuery(PageQuery query) {
        query.setData(getRecords());
        query.setTotal(getTotal());
        query.setPages(getPages());
        query.setPageNumber(getCurrent());
        query.setPageSize(getSize());
    }

}
