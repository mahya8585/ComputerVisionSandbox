package com.maaya.azure.example.controller;

import com.maaya.azure.example.dto.Image;
import com.maaya.azure.example.helper.AzureComputerVisionHelper;
import com.maaya.azure.example.helper.AzureStorageHelper;
import com.maaya.azure.example.service.BwImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 静的ファイルをアップロードして、グレースケールにする処理
 * Created by 4605851 on 2017/01/31.
 */
@Controller
@RequestMapping("/bwImage")
@Slf4j
public class BWImageController {

    @Autowired
    private BwImageService bwImageService;
//    @Autowired
//    private AzureComputerVisionHelper azureComputerVisionHelper;


    /**
     * 画像グレースケール化処理
     *
     * @param bwTarget
     * @param model
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createBWImage(@RequestParam MultipartFile bwTarget, Model model) {
        //TODO Azure Comuputer Visionはファイルサイズが4MBまでなので、ファイルサイズのチェック処理を追加する
        //TODO 拡張子チェックなどのバリデーションも作成する必要あり

        try {
            //元画像URL取得
            String  beforeUrl = bwImageService.makeSourceImageUrl(bwTarget);

            //グレースケール画像取得
            //TODO multipartをfile化
            String afterUrl = bwImageService.makeBWImageUrl(beforeUrl, bwTarget.getOriginalFilename());

            //表示用Modelの作成
            model = bwImageService.makeResultModel(model, beforeUrl, afterUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //表示
        return displayBWImage(model);
    }

    /**
     * 処理結果表示
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/display")
    public String displayBWImage(Model model) {
        log.debug("結果表示API");

        return "resultBwImage";
    }
}
