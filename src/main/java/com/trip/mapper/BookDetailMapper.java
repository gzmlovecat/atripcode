package com.trip.mapper;

import com.trip.dto.BookDetailDto;
import com.trip.dto.OrderDto;
import com.trip.entity.OrderQueryEntity;
import org.apache.ibatis.annotations.Param;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

public interface BookDetailMapper {


    public void insertBookDetail(BookDetailDto param);

    public List<BookDetailDto> selectBookDetailList(@Param("param") BookDetailDto bookDetailDto);

    public Map selectBookDetailVersion(@Param("orderId") Long orderId);
    public List<Map> selectRefundHistory(@Param("orderId") Long orderId);

    public void updateBookDetailVersion(@Param("param") BookDetailDto bookDetailDto);

}
