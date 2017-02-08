package com.maaya.azure.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 4605851 on 2017/02/08.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String sourceImgUrl;
    private String resultImgUrl;
    private Double adultScore;
    private Double racyScore;
}
