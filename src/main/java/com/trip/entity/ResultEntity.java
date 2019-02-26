package com.trip.entity;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultEntity<T> {
    private ResultEnum resultEnum;
    private T data;
    private Long totalPageSize;
    private Long totalPageNum;

    public ResultEntity(ResultEnum resultEnum){
        this.resultEnum = resultEnum;
    }

    public ResultEntity(ResultEnum resultEnum,T data){
        this.resultEnum = resultEnum;
        this.data = data;
    }


    @Override
    public String toString(){
        Map map = new HashMap();
        map.put("resultCode",this.resultEnum.getResultCode());
        map.put("resultMsg",this.resultEnum.getResultMsg());
        map.put("totalPageSize", this.totalPageSize);
        map.put("totalPageNum", this.totalPageNum);
        if( this.data != null ){
            map.put("data",this.data);
        }
        Gson gson = new Gson();
        return gson.toJson(map);
    }


    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTotalPageSize() {
		return totalPageSize;
	}

	public void setTotalPageSize(Long totalPageSize) {
		this.totalPageSize = totalPageSize;
	}

	public Long getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(Long totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public static void main(String[] args){
        Map  map = new HashMap();
        map.put("a","");
        map.put("b","2");

        Map  map2 = new HashMap();
        map2.put("a1","11");
        map2.put("b1","21");

        List<Map> list = Arrays.asList(map,map2);
        ResultEntity entity = new ResultEntity(ResultEnum.SUCCESS,list);
        entity.setTotalPageNum(5L);
        entity.setTotalPageSize(30L);
        System.out.println(entity.toString());
        entity = new ResultEntity(ResultEnum.DUPLICATE_DATA, "");
        System.out.println(entity.toString());

    }
}
