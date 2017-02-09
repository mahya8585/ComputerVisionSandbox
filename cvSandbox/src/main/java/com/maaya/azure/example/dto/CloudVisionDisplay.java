package com.maaya.azure.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 4605851 on 2017/02/09.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloudVisionDisplay {
    /**
     * 変更前画像のURL
     */
    private String imgUrl;

    /**
     * 判定結果文言
     */
    private String result;

    /**
     * 18禁レベル
     */
    private String adult;
    /**
     * なりすまし・偽装レベル
     */
    private String spoof;
    /**
     * 医用画像レベル
     */
    private String medical;

    /**
     * 暴力的画像レベル
     */
    private String violence;
}
