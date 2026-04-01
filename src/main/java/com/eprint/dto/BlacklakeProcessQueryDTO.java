package com.eprint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlacklakeProcessQueryDTO {

    private String processCode;
    private String processName;
    private String createdAtGte;
    private String createdAtLte;
    private String updatedAtGte;
    private String updatedAtLte;
    private PageDTO page;

    @Data
    public static class PageDTO {
        private Integer pageNum = 1;
        private Integer pageSize = 10;
    }
}
