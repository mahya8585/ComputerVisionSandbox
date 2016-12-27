package jp.co.opst.cloud.study.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接続テストクラス
 * Created by maaya
 */
@RestController
@RequestMapping("/")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 疎通確認
     */
    @RequestMapping(method = RequestMethod.GET)
    String test() {
        logger.debug("testメソッド");
        return "hello";
    }

}
