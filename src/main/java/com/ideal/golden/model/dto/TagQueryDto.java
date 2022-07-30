package com.ideal.golden.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TagQueryDto extends PageQueryDto {
    private String ids;
}
