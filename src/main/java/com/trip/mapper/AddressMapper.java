package com.trip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.trip.dto.AddressDto;

public interface AddressMapper {


    public void insertAddress(AddressDto addressDto);

    public List<AddressDto> selectAddressList(@Param("addressType") String addressType,
                                               @Param("addressDetail") String addressDetail);

}
