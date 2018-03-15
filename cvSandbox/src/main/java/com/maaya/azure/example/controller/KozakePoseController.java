package com.maaya.azure.example.controller;

import com.maaya.azure.example.dto.customVision.PredictionResult;
import com.maaya.azure.example.form.KozakeForm;
import com.maaya.azure.example.service.KozakeService;
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
 * その写真がこざけポーズ否かを判定する処理
 * <b>Azure Custom Vision Service利用</b>
 * Created by 4605851 on 2017/06/24.
 */
@Controller
@RequestMapping("/kozake")
@Slf4j
public class KozakePoseController {
    @Autowired
    private KozakeService kozakeService;

    /**
     * 各処理を発動させるための一覧ページ表示
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index(Model model) {
        log.debug("indexページ表示");

        model.addAttribute("kozakeForm", new KozakeForm());
        return "kozakeIndex";
    }

    /**
     * こざけポーズ判定処理
     *
     * @param targetFile
     * @param targetUrl
     * @param model
     * @return
     */
    @RequestMapping(value = "/judgment", method = RequestMethod.POST)
    public String judgeAdult(@RequestParam MultipartFile targetFile, String targetUrl, Model model) {
        //TODO 拡張子チェックなどのバリデーションも作成する必要あり

        try {
            //送付された画像をBLOBへアップロード
            String sourceUrl = kozakeService.makeSourceImageUrl(targetFile, targetUrl);

            //Custom Vision Service を呼び出して画像評価する
            PredictionResult predictionResult = kozakeService.detectKozakePose(sourceUrl);

            //取得結果の整形
            model = kozakeService.makeResultModel(model, predictionResult, sourceUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //表示
        return display(model);
    }

    /**
     * 処理結果表示
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/display")
    public String display(Model model) {
        log.debug("結果表示API");

        return "kozakeResult";
    }
}
