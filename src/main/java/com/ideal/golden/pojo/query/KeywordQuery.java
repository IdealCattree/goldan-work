package com.ideal.golden.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class KeywordQuery extends PageQuery {
    private String keyword;
}
