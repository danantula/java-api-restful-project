package org.ns.automation.model;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;
import io.cucumber.java.mk_latn.No;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDataPojo {

    private String year;
    private String price;
    @JsonProperty("CPU model")
    private String cpuModel;
    @JsonProperty("Hard disk size")
    private String hardDiskSize;

}
