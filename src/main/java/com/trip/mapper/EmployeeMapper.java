package com.trip.mapper;

import com.trip.dto.AccountDto;
import com.trip.dto.EmployeeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {


    public void insertEmployee(EmployeeDto employeeDto);

    public List<EmployeeDto> selectEmployeeList(@Param("employeeName") String employeeName,
                                                @Param("id") Long id);

}
