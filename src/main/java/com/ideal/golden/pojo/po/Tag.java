package com.ideal.golden.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@TableName("a_tag")
public class Tag {
    private int id;
    private String tagName;
}
