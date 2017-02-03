package com.maaya.azure.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by 4605851 on 2017/02/03.
 */
@Data
public class CommonConfig {
    //Azure account name
    @Value("${azure.storage.account.name}")
    private String accountName;
    //Azure account key
    @Value("${azure.storage.account.key}")
    private String accountKey;
    //コンテナー名
    @Value("${azure.storage.container}")
    private String container;

    //変更前作業ファイル
    @Value("${cv.tempfile.name}")
    private String tempFileName;
}
