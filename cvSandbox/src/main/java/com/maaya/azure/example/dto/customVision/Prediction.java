package com.maaya.azure.example.dto.customVision;

import lombok.Data;

/**
 * Created by 4605851 on 2017/06/24.
 */
@Data
public class Prediction {
    private String TagId;
    private String Tag;
    private Double Probability;

}
