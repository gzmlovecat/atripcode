package com.trip.mapper;

import com.trip.dto.TripTypeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TripTypeMapper {


    public void insertTripType(TripTypeDto productTypeDto);

    public List<TripTypeDto> selectTripTypeList(@Param("tripTypeName") String tripTypeName);

}
