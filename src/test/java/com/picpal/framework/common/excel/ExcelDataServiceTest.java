package com.picpal.framework.common.excel;

import com.picpal.framework.common.utils.excel.ExcelDataService;
import com.picpal.framework.common.utils.excel.ExcelReader;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static org.mockito.Mockito.mock;


@SpringBootTest
class ExcelDataServiceTest {
    private SqlSessionFactory sqlSessionFactory;
    private ExcelDataService excelDataService;



    @Test
    void testProcessExcelData() {
        // 테스트용 데이터 생성
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("C:/Users/bwc/Desktop/picpal/boilerplate-back/src/main/resources/static/excel/이마트24_EMS300_1구간.xlsx");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Map<String, Object>> dataList =  ExcelReader.readExcelFile(fis);

        List<Map<String,Object>> list = new LinkedList<>();

        for (Map<String, Object> stringObjectMap : dataList) {
            Map<String,Object> data = new HashMap<>();
            data.put("col1" , stringObjectMap.get("유효기간구간순번"));
            data.put("col2" , stringObjectMap.get("일련번호"));
            data.put("col3" , stringObjectMap.get("쿠폰핀번호"));
            data.put("col4" , stringObjectMap.get("업체코드"));
            data.put("col5" , stringObjectMap.get("상품코드"));
            data.put("col6" , stringObjectMap.get("유효종료일자"));
            data.put("col7" , stringObjectMap.get("유효시작일자"));

            list.add(data);
        }
        // 테스트 실행
        excelDataService.processExcelData("com.picpal.framework.sample.mapper.SampleMapper", "insertExcelCells", list);

        // 예외 없이 정상적으로 실행되면 테스트 통과
    }

}
