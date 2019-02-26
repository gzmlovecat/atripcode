package com.trip.mapper;

import com.trip.dto.EmployeeDto;
import com.trip.dto.ProductTypeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductTypeMapper {


    public void insertProductType(ProductTypeDto productTypeDto);

    public List<ProductTypeDto> selectProductTypeList(@Param("productTypeName") String productTypeName);

}
