package com.maaya.azure.example.controller;

import com.maaya.azure.example.dto.computerVision.AnalyzeImage;
import com.maaya.azure.example.service.AdultJudgmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * その写真が卑猥か否かを判定する処理
 * Created by 4605851 on 2017/01/31.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class RestAPIController {
    @Autowired
    AdultJudgmentService adultJudgmentService;

    /**
     * アダルト写真判定処理
     *
     * @param targetUrl
     * @return
     */
    @RequestMapping(value = "/azure/adult", method = RequestMethod.GET)
    public String azureJudgeAdult(@RequestParam String targetUrl) {
        //TODO Azure Comuputer Visionはファイルサイズが4MBまでなので、ファイルサイズのチェック処理を追加する
        //TODO 拡張子チェックなどのバリデーションも作成する必要あり

        String responseJson = null;
        try {
            //アップロードしたファイルからComputer Vision結果を取得する
            AnalyzeImage adultDetection = adultJudgmentService.detectAdult(targetUrl);

            //取得結果の整形
            responseJson = adultJudgmentService.makeApiResponse(adultDetection, targetUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //表示
        return responseJson;
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

        return "resultAdultJudgment";
    }
}
