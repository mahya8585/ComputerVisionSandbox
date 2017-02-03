package jp.co.opst.cloud.study.domain.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by maaya on 2016/12/19.
 */
@Component
@Slf4j
public class DisplayHostHelper {
    public String createIp () {
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = ia.getHostAddress();       //IPアドレス

        log.debug("IP : " + ip);

        return ip;
    }
}
