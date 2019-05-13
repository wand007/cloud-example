package com.cloud.example.client;

import com.cloud.example.base.BaseClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-04-18
 */

@Slf4j
@RestController
@RequestMapping(value = "/sso")
public class ThreadClient extends BaseClient {

    public static void main(String[] args) {
        label:
        for (int i = 0; i < 10; i++) {
            outer:
            for (int j = 0; j < 10; j++) {
                bgm:
                for (int m = 0; m < 10; m++) {
                    if (m > 6) {
                        System.out.println(i + "++" + j + "++" + m);
                        break   outer ;
                    }
                    System.out.println(i + "--" + j + "--" + m);
                }
            }
        }
    }
}
