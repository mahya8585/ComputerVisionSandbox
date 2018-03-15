package com.maaya.azure.example.helper;

import com.google.gson.Gson;
import com.maaya.azure.example.dto.customVision.PredictionResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by maaya on 2017/06/24.
 */
@Component
@Slf4j
public class AzureCustomVisionHelper {

    @Value("${azure.customVision.prediction.key}")
    private String PREDICTION_KEY;
    //画像URLを使った画像解析URL
    @Value("${azure.customVision.prediction.url}")
    private String PREDICTION_URL;

    /**
     * 画像分析する
     *
     * @param sourceImgUrl
     * @return
     */
    public PredictionResult analyzeImage(String sourceImgUrl) throws URISyntaxException, IOException {
        HttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(PREDICTION_URL);

        URI uri = builder.build();
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        request.setHeader("Prediction-Key", PREDICTION_KEY);

        // Request body
        StringEntity reqEntity = new StringEntity("{\"url\":\"" + sourceImgUrl + "\"}");
        request.setEntity(reqEntity);

        //Response設定
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            //現段階で表示に必要なデータのdtoしか作成していないので、表示に必要なデータがあればdtoに項目を追加してください
            Gson gson = new Gson();
            return gson.fromJson(EntityUtils.toString(entity), PredictionResult.class);
        }

        return null;
    }
}