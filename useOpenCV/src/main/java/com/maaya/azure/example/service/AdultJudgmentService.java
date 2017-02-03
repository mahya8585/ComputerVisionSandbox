package com.maaya.azure.example.service;

import com.maaya.azure.example.dto.Image;
import com.maaya.azure.example.helper.AzureStorageHelper;
import com.microsoft.azure.storage.StorageException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

/**
 * Created by 4605851 on 2017/02/03.
 */
@Component
public class AdultJudgmentService {
    @Autowired
    private AzureStorageHelper azureStorageHelper;

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
     * 処理結果を表示用モデルに設定する
     *
     * @param model
     * @param beforeUri
     * @param afterUri
     * @return
     */
    public Model makeResultModel(Model model, String beforeUri, String afterUri) {
        Image image = new Image(beforeUri, afterUri);
        model.addAttribute("image", image);

        return model;
    }

    /**
     * 実ファイルの作成
     *
     * @param multipartFile
     * @param filePath
     * @throws IOException
     */
    private void convertMultipartToFile(MultipartFile multipartFile, String filePath) throws IOException {
        File source = new File(filePath);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), source);
    }

}
