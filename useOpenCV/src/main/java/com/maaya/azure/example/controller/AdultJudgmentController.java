package com.maaya.azure.example.controller;

import com.maaya.azure.example.service.AdultJudgmentService;
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
 * その写真が卑猥か否かを判定する処理
 * Created by 4605851 on 2017/01/31.
 */
@Controller
@RequestMapping("/adult")
@Slf4j
public class AdultJudgmentController {
    @Autowired
    private AdultJudgmentService adultJudgmentService;

    /**
     * アダルト写真判定処理
     *
     * @param adultTarget
     * @param model
     * @return
     */
    @RequestMapping(value = "/judgment", method = RequestMethod.POST)
    public String judgeAdult(@RequestParam MultipartFile adultTarget, Model model) {
        //TODO Azure Comuputer Visionはファイルサイズが4MBまでなので、ファイルサイズのチェック処理を追加する
        //TODO 拡張子チェックなどのバリデーションも作成する必要あり

        try {
            //送付された画像をBLOBへアップロード
            String sourceUrl = adultJudgmentService.makeSourceImageUrl(adultTarget);

            //TODO アップロードしたファイルからComputer Vision結果を取得する

            //TODO 取得結果の整形


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

        return "resultAdultJudgment";
    }
}
