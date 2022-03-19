package com.tanran.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @since 3.0.x 2022/3/18 9:52
 */

@SpringBootApplication
@MapperScan("com.tanran.model.mappers.app")
public class ArticleJarApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleJarApplication.class,args);
    }
}
