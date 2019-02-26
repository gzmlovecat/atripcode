package com.trip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.trip.dto.SupplierDto;

public interface SupplierMapper {


    public void insertSupplier(SupplierDto account);

    public List<SupplierDto> selectSupplierByParam(@Param("supplierName") String supplierName);

    public int selectSupplierByCount(SupplierDto account);
}
