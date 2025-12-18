package org.ns.automation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponsePojo {
    private String id;
    private String name;
    private DataPojo data;

}
