package com.maaya.azure.example.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;

/**
 * Created by maaya on 2016/12/27.
 */
@Component
@Slf4j
public class AzureComputerVisionHelper {
    private final static String API_KEY = "【your Computer Vision API KEY】";
    private final static String WIDTH = "200";
    private final static String HEIGHT = "200";
    private final static String SMART_CROPPING = "true";
    private final static String GENERATE_THUMBNAIL_URL = "https://api.projectoxford.ai/vision/v1.0/generateThumbnail";

    /**
     * サムネイル画像を取得する
     * @param sourceImgUrl
     * @return
     */
    public static InputStream createThumbnail(String sourceImgUrl) {
        HttpClient httpclient = HttpClients.createDefault();

        try {
            URIBuilder builder = new URIBuilder(GENERATE_THUMBNAIL_URL);

            builder.setParameter("width", WIDTH);
            builder.setParameter("height", HEIGHT);
            builder.setParameter("smartCropping", SMART_CROPPING);

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            request.setHeader("Ocp-Apim-Subscription-Key", API_KEY);


            // Request body
            StringEntity reqEntity = new StringEntity("{\"url\":\"" + sourceImgUrl + "\"}");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return entity.getContent();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }
}