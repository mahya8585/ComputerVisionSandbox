package com.maaya.azure.example.service;

import com.maaya.azure.example.dto.Display;
import com.maaya.azure.example.dto.computerVision.Adult;
import com.maaya.azure.example.dto.computerVision.AnalyzeImage;
import com.maaya.azure.example.helper.AzureComputerVisionHelper;
import com.maaya.azure.example.helper.AzureStorageHelper;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4605851 on 2017/02/03.
 */
@Component
public class AdultJudgmentService {
    @Autowired
    private AzureStorageHelper azureStorageHelper;
    @Autowired
    private AzureComputerVisionHelper azureComputerVisionHelper;

    /**
     * Azureへ画像をアップロードし、画像URLを取得する
     *
     * @param target
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws StorageException
     * @throws URISyntaxException
     */
    public String makeSourceImageUrl(MultipartFile target) throws IOException, InvalidKeyException, StorageException, URISyntaxException {
        final String originalFileName = target.getOriginalFilename();

        //fileをBLOBへアップロード
        azureStorageHelper.upload(target.getInputStream(), originalFileName, target.getBytes().length);

        //URL取得
        return azureStorageHelper.selectBlobUrl(originalFileName);
    }

    /**
     * アダルト検知処理を行う
     * @param targetImageUrl
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public AnalyzeImage detectAdult(String targetImageUrl) throws IOException, URISyntaxException {
        List<AzureComputerVisionHelper.VisualFeature> visualFeatures = new ArrayList<>();
        visualFeatures.add(AzureComputerVisionHelper.VisualFeature.ADULT);

        return azureComputerVisionHelper.analyzeImage(targetImageUrl, AzureComputerVisionHelper.Language.EN, visualFeatures);
    }

    /**
     * 処理結果を表示用モデルに設定する
     *
     * @param model
     * @param analyzeResult
     * @param imgUrl
     * @return
     */
    public Model makeResultModel(Model model, AnalyzeImage analyzeResult, String imgUrl) {
        Display responseDisplay = new Display();
        responseDisplay.setImgUrl(imgUrl);

        Adult analyzeAdultInfo = analyzeResult.getAdult();
        responseDisplay.setAdultScore(analyzeAdultInfo.getAdultScore());
        responseDisplay.setRacyScore(analyzeAdultInfo.getRacyScore());

        //解析結果の判定
        //TODO 精緻にやりたい場合はスコアから判定するべし
        responseDisplay.setResult(makeResultStr(analyzeAdultInfo.getIsAdultContent(), analyzeAdultInfo.getIsRacyContent()));

        model.addAttribute("display", responseDisplay);

        return model;
    }

    /**
     * 表示する結果文言の作成
     * @param isAdult
     * @param isRacy
     * @return
     */
    private String makeResultStr(boolean isAdult, boolean isRacy) {
        if (isAdult) {
            return "この画像は18禁です! 見ちゃダメ!";

        } else if (isRacy) {
            return "この画像はきわどい写真です! 気を付けて!";

        } else {
            return "この画像は健全な写真です。";
        }
    }

}
