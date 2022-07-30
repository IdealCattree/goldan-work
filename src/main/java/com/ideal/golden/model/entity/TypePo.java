package com.ideal.golden.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * @作者 Ideal
 * @时间 2022-07-25 21:09
 * @类说明
 */
@Data
@Accessors(chain = true)
@Component
@TableName("a_type")
public class TypePo {
    private Integer id;
    private String typeName;
}
