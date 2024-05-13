package com.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JQPageInfo {
    @Setter
    @Getter
    private Integer page;

    private Integer limit;

    private String sidx;

    private String order;

    private Integer offset;

}
