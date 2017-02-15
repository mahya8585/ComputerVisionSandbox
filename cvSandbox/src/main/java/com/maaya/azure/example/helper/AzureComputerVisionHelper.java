package com.maaya.azure.example.helper;

import com.google.gson.Gson;
import com.maaya.azure.example.dto.computerVision.AnalyzeImage;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maaya on 2016/12/27.
 */
@Component
@Slf4j
public class AzureComputerVisionHelper {

    //TODO 呼び出したいAPIのURL
    private final static String ANALYZE_URL = "https://westus.api.cognitive.microsoft.com/vision/v1.0/analyze";
    @Value("${azure.computerVision.key}")
    private String API_KEY;

    /**
     * 画像分析する
     * <b>有名人判定パラメータに対応していません。判定したい場合はdetailパラメータにCelebritiesを設定してください</b>
     *
     * @param sourceImgUrl   画像URL
     * @param lang           ComputerVision API で使用できる言語コード(enumで管理しています。現在は英語と中国語のみ)
     * @param visualFeatures ComputerVision APIで使用できるvisualFeature種別(enumで管理しています)
     * @return
     */
    public AnalyzeImage analyzeImage(String sourceImgUrl, Language lang, List<VisualFeature> visualFeatures) throws URISyntaxException, IOException {
        HttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(ANALYZE_URL);

        //getパラメータ作成
        builder = createGetParam(builder, lang, visualFeatures);

        //リクエストヘッダの作成
        URI uri = builder.build();
        HttpPost request = createRequestHeader(uri);

        // Request body
        StringEntity reqEntity = new StringEntity("{\"url\":\"" + sourceImgUrl + "\"}");
        request.setEntity(reqEntity);

        //Response設定
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            //現段階で表示に必要なデータのdtoしか作成していないので、表示に必要なデータがあればdtoに項目を追加してください
            Gson gson = new Gson();
            return gson.fromJson(EntityUtils.toString(entity), AnalyzeImage.class);
        }

        return null;
    }

    /**
     * ComputerVision Analyze APIのGETパラメータ作成
     *
     * @param builder
     * @param lang           ComputerVision API で使用できる言語コード(enumで管理しています。現在は英語と中国語のみ)
     * @param visualFeatures ComputerVision APIで使用できるvisualFeature種別(enumで管理しています)
     * @return
     */
    private URIBuilder createGetParam(URIBuilder builder, Language lang, List<VisualFeature> visualFeatures) {
        builder.setParameter("language", createLanguageParam(lang));
        List<String> features = visualFeatures.stream().map(vf -> createFeatureParam(vf)).collect(Collectors.toList());
        log.debug(String.join(",", features));
        builder.setParameter("visualFeatures", String.join(",", features).replaceAll(",,", ""));

        //有名人判定パラメータに対応していません。判定したい場合はdetailパラメータにCelebritiesを設定してください
        //builder.setParameter("details", "Celebrities");

        return builder;
    }

    /**
     * ComputerVision Analyze API のGリクエストヘッダ作成
     *
     * @param uri
     * @return
     */
    private HttpPost createRequestHeader(URI uri) {
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        request.setHeader("Ocp-Apim-Subscription-Key", API_KEY);

        return request;
    }

    /**
     * 言語enumからパラメータを生成する
     *
     * @param lang
     * @return
     */
    private String createLanguageParam(Language lang) {
        return lang.name().toLowerCase();
    }

    /**
     * フィーチャーの設定を行う
     * コメントアウトされている項目はレスポンスdtoの項目を増やしたら使えるようになります
     *
     * @param feature
     * @return
     * @throws Exception
     */
    private String createFeatureParam(VisualFeature feature) {
        try {
            switch (feature) {
//                case CATEGORIES:
//                    return "Categories";
//                case TAGS:
//                    return "Tags";
//                case DESCRIPTION:
//                    return "Description";
//                case FACES:
//                    return "Faces";
//                case IMAGE_TYPE:
//                    return "ImageType";
//                case COLOR:
//                    return "Color";
                case ADULT:
                    return "Adult";
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            log.error("想定外のフィーチャーが設定されました");
            return "";
        }
    }

    /**
     * 言語種別
     */
    public enum Language {
        EN,
        ZH
    }

    /**
     * VisualFeature種別
     * 追加したら createFeatureParam も追加すること
     * コメントアウトされている項目はレスポンスdtoの項目を増やしたら使えるようになります
     */
    public enum VisualFeature {
        //        CATEGORIES,
//        TAGS,
//        DESCRIPTION,
//        FACES,
//        IMAGE_TYPE,
//        COLOR,
        ADULT
    }
}