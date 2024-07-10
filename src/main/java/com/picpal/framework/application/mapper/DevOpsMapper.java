package com.picpal.framework.sample.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface DevOpsMapper {
    void insertUserRecord(Map<String, Object> record);
    void insertExcelCells(Map<String, Object> record);
}
