package com.maaya.azure.example.controller;

import com.maaya.azure.example.form.AdultForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 静的ページ表示コントローラ
 * Created by maaya
 */
@Controller
@RequestMapping("/")
@Slf4j
public class StaticPageController {

    /**
     * 各処理を発動させるための一覧ページ表示
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    String index(Model model) {
        log.debug("indexページ表示");

        model.addAttribute("adultForm", new AdultForm());
        return "index";
    }

}
