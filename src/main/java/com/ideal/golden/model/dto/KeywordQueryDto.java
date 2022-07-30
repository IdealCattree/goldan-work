package com.ideal.golden.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class KeywordQueryDto extends PageQueryDto {
    private String keyword;
}
