package com.maaya.azure.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * html表示用dto
 * Created by user on 2016/10/07.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AzureDisplay {
    /**
     * 変更前画像のURL
     */
    private String imgUrl;

    /**
     * 判定結果文言
     */
    private String result;

    /**
     * アダルト判定スコア
     */
    private Double adultScore;

    /**
     * きわどい判定スコア
     */
    private Double racyScore;


}
