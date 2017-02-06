package com.maaya.azure.example.dto.computerVision;

import lombok.Data;

/**
 * Created by 4605851 on 2017/02/06.
 */
@Data
public class AnalyzeImage {
    private Adult adult;
    private String requestId;
    private MetaData metadata;
}
