package com.tanran.model.mess.admin;


import com.tanran.model.article.pojos.ApArticleConfig;
import com.tanran.model.article.pojos.ApArticleContent;
import com.tanran.model.article.pojos.ApAuthor;

import lombok.Data;

@Data
public class AutoReviewClNewsSuccess {
    private ApArticleConfig apArticleConfig;
    private ApArticleContent apArticleContent;
    private ApAuthor apAuthor;

}
