package com.maaya.azure.example.dto.computerVision;

import lombok.Data;

/**
 * ComputerVision : Analyze AzureDisplay のレスポンスdto.
 * 現段階で表示に必要なデータのdtoしか作成していないので、表示に必要なデータがあればdtoに項目を追加してください
 * Created by 4605851 on 2017/02/06.
 */
@Data
public class AnalyzeImage {
    private Adult adult;
    private String requestId;
    private MetaData metadata;
}
