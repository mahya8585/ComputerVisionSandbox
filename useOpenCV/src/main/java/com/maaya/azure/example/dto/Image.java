package com.maaya.azure.example.dto;

import lombok.Data;

/**
 * Created by user on 2016/10/07.
 */
@Data
public class Image {
    /** 変更前画像のURL */
    private String before;

    /** 変更後画像のURL */
    private String after;

    public Image(String beforeUrl, String afterUrl) {
        setBefore(beforeUrl);
        setAfter(afterUrl);
    }
}
