package com.example.multidb2;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DBDetail {

    @Valid @NotBlank(message = "datasource url is mandatory")
    private String datasourceUrl;

    @Valid @NotBlank(message = "datasource username is mandatory")
    private String datasourceUsername;

    @Valid @NotBlank(message = "datasource password is mandatory")
    private String datasourcePassword;

    private String schemaName;

    private Map<String,String> hibernateProperties;
}
