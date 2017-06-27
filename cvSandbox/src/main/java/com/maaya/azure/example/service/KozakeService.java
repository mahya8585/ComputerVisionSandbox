package com.maaya.azure.example.service;

import com.maaya.azure.example.dto.KozakeDisplay;
import com.maaya.azure.example.dto.customVision.Prediction;
import com.maaya.azure.example.dto.customVision.PredictionResult;
import com.maaya.azure.example.helper.AzureCustomVisionHelper;
import com.maaya.azure.example.helper.AzureStorageHelper;
import com.microsoft.azure.storage.StorageException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.stream.Collectors;


/**
 * Created by 4605851 on 2017/02/03.
 */
@Component
public class KozakeService {
    @Autowired
    private AzureStorageHelper azureStorageHelper;
    @Autowired
    private AzureCustomVisionHelper azureCustomVisionHelper;


    /**
     * Azureへ画像をアップロードし、画像URLを取得する
     *
     * @param target
     * @param targetUrl
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws StorageException
     * @throws URISyntaxException
     */
    public String makeSourceImageUrl(MultipartFile target, String targetUrl) throws IOException, InvalidKeyException, StorageException, URISyntaxException {
        //URLが設定されていなかった場合はファイルアップロードを行う
        if (StringUtils.isEmpty(targetUrl)) {
            final String originalFileName = target.getOriginalFilename();
            //fileをBLOBへアップロード
            azureStorageHelper.upload(target.getInputStream(), originalFileName, target.getBytes().length);

            //URL取得
            return azureStorageHelper.selectBlobUrl(originalFileName);

        } else {
            return targetUrl;
        }
    }

    /**
     * こざけポーズ検知処理を行う
     * @param targetImageUrl
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public PredictionResult detectKozakePose(String targetImageUrl) throws IOException, URISyntaxException {

        return azureCustomVisionHelper.analyzeImage(targetImageUrl);
    }

    /**
     * 処理結果を表示用モデルに設定する
     *
     * @param model
     * @param predictionResult
     * @param imgUrl
     * @return
     */
    public Model makeResultModel(Model model, PredictionResult predictionResult, String imgUrl) {
        KozakeDisplay display = new KozakeDisplay();
        display.setImgUrl(imgUrl);

        Prediction prediction = predictionResult.getPredictions().stream()
                .filter(p -> p.getTag().equals("こざけポーズ")).collect(Collectors.toList()).get(0);

        //解析結果の判定
        //TODO 精緻にやりたい場合はスコアから判定するべし
        Double score = prediction.getProbability() * 100;
        display.setKozakeScore(String.valueOf(score) + "%");

        model.addAttribute("display", display);
        return model;
    }


}
