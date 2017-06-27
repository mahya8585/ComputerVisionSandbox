package com.maaya.azure.example.dto.customVision;

import lombok.Data;
import java.util.List;

/**
 * Created by 4605851 on 2017/06/24.
 */
@Data
public class PredictionResult {
    private String Id;
    private String Project;
    private String Iteration;
    private String Created;
    private List<Prediction> Predictions;

}
