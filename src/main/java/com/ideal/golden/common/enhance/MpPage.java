package com.ideal.golden.common.enhance;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ideal.golden.model.dto.PageQueryDto;

public class MpPage <T> extends Page<T> {

    public MpPage(PageQueryDto query) {
        super(query.getPageNumber(), query.getPageSize());
    }

    /**
     * 查询完之后把数据填充到query对象里，更新数据
     * @param query 查询对象
     */
    public void updateQuery(PageQueryDto query) {
        query.setData(getRecords());
        query.setTotal(getTotal());
        query.setPages(getPages());
        query.setPageNumber((int) getCurrent());
        query.setPageSize((int) getSize());
    }

}
