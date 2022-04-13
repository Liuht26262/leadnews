package com.tanran.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ObjectUtils;

import com.tanran.api.service.ArticleContentService;
import com.tanran.api.service.ArticleHomeService;
import com.tanran.common.constans.ArticleConstans;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleInfoDto;
import com.tanran.model.article.dtos.UserSearchDto;
import com.tanran.model.article.pojos.ApArticleConfig;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章测试类
 * @since 3.0.x 2022/3/18 13:37
 */

@SpringBootTest(classes = ArticleJarApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ArticleJarApplicationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ArticleHomeService articleService;
    @Autowired
    private ArticleContentService articleContentService;
    /*注意引入的包名  com.fasterxml.jackson.databind.ObjectMapper */
    @Autowired
    private ObjectMapper objectMapper;



    @Test
    public void testArticle(){
        ApUser apUser = new ApUser();
        apUser.setId(2104l);
        AppThreadLocalUtils.setUser(apUser);

        RespResult result = articleService.load(null, ArticleConstans.LOADTYPE_LOAD_MORE);
        System.out.println(result.getData());
    }

    /**
     * 使用MockMvc测试请求是否可用
     * @throws Exception
     */
    @Test
    public void getArticleContent() throws Exception {
        ApArticleConfig apArticleConfig = new ApArticleConfig();
        apArticleConfig.setArticleId(1);
        apArticleConfig.setId(1L);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/load_article_info")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(apArticleConfig));
        /*发送请求并输出结果*/
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testLoadArticleBehavior() throws Exception{
        ArticleInfoDto dto = new ArticleInfoDto();
        dto.setArticleId(1);
        dto.setAuthorId(1);
        dto.setEquipmentId(1);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/load_article_behavior");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsBytes(dto));
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void Object(){System.out.println(ObjectUtils.isEmpty(null));}

    @Test
    public void testHotKeywords() throws Exception {
        UserSearchDto dto = new UserSearchDto();
               dto.setHotDate("2022-03-31");
        MockHttpServletRequestBuilder builder =
            MockMvcRequestBuilders.post("/api/v1/article/search/load_hot_keywords")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(dto));
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAssociateWords() throws Exception {
        UserSearchDto dto2 = new UserSearchDto();
        dto2.setPageSize(20);
        dto2.setSearchWords("今日");
        MockHttpServletRequestBuilder builders =
            MockMvcRequestBuilders.post("/api/v1/article/search/associate_search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(dto2));
        mockMvc.perform(builders).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }


}
