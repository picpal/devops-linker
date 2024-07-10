package com.picpal.framework.common.utils.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Slf4j
public class ExcelDataService {
    private final SqlSessionFactory sqlSessionFactory;

    public ExcelDataService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void processExcelData(String mapperClass, String methodName, List<Map<String, Object>> dataList) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            Class<?> mapperInterface = Class.forName(mapperClass);
            Object mapper = session.getMapper(mapperInterface);
            Method method = mapperInterface.getMethod(methodName, Map.class);

            int count = 0;
            for (Map<String, Object> data : dataList) {
                method.invoke(mapper, data);
                if (++count % 3000 == 0) {
                    session.commit(); // 3,000건마다 커밋
                    session.clearCache(); // 옵션: 세션 캐시를 정리하여 메모리 사용량 관리
                }
            }
            session.commit(); // 남은 데이터에 대해 커밋
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void uploadExcel() {

        try {
            FileInputStream fis = new FileInputStream("C:/Users/bwc/Desktop/picpal/boilerplate-back/src/main/resources/static/excel/이마트24_EMS300_1구간.xlsx");
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

            processExcelData("com.picpal.framework.sample.mapper.SampleMapper","insertExcelCells",list);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}
