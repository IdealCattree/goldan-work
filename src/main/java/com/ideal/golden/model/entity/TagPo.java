package com.ideal.golden.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ideal.golden.common.foreign.anno.ForeignCascade;
import com.ideal.golden.common.foreign.anno.ForeignField;
import com.ideal.golden.common.foreign.anno.ForeignTable;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@TableName("a_tag")
@ForeignTable("a_tag")
public class TagPo {
    @ForeignField(column = "id", cascade = ForeignCascade.DEFAULT)
    private Integer id;
    private String tagName;
}
