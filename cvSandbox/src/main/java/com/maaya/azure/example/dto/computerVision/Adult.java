package com.maaya.azure.example.dto.computerVision;

import lombok.Data;

/**
 * Created by 4605851 on 2017/02/06.
 */
@Data
public class Adult {
    private Boolean isAdultContent;
    private Boolean isRacyContent;
    private Double adultScore;
    private Double racyScore;
}
