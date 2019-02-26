package com.trip.mapper;

import com.trip.dto.ArticleDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {


    public void insertArticle(ArticleDto articleDto);
    public void updateArticle(@Param("param") ArticleDto articleDto);

    public List<ArticleDto> selectArticleByParam(@Param("content") String content,@Param("publishTime") String publishTime);

}
