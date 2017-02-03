package jp.co.opst.cloud.study.domain.service;

import jp.co.opst.cloud.study.domain.model.dto.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by maaya on 2016/12/12.
 */
@Component
@Slf4j
public class DemoService {

    /**
     * ①Controllerからautowiredで呼び出す方法のサンプルメソッド
     * ②htmlデモ表示をするためのデータ作成メソッド
     *
     * @return デモ表示用画像リストURL
     */
    public List<Image> makeDemoData() {
      log.debug("autowairedのサンプル用メソッド");

      List<Image> results = new ArrayList<>();

      for(int cnt = 1; cnt <= 8; cnt++) {
          Image img = new Image("images/fulls/0" + cnt + ".jpg", "images/thumbs/0" + cnt + ".jpg");
          results.add(img);
      }

        return results;
    }

}
