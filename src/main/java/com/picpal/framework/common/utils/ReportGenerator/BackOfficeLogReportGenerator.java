package com.picpal.framework.common.utils.ReportGenerator;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class BackOfficeLogReportGenerator extends HtmlGenerator {


    public String getBackOfficeLogReport(String logData){
        List<Element> elements = new ArrayList<>();

        /**
         * ####### 전달 파라미터 Convert String to JSON Object
         * */
        JSONParser parser = new JSONParser();
        JSONObject jsonObj;
        try {
            jsonObj = (JSONObject) parser.parse(logData);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        /**
         * ####### 점검 보고서 header Template
         * */
        elements.add(createDiv("로그 점검 보고서","","font-size:24px;"));
        elements.add(createBr()); // 줄바꿈
        List<String> headerParams = new ArrayList<>();
        headerParams.add((String) jsonObj.get("serviceName"));
        headerParams.add("2024.03.07");
        headerParams.add((String) jsonObj.get("checkUser"));
        headerParams.add( jsonObj.get("checkStartDate") + " ~ " + jsonObj.get("checkEndDate"));

        String serviceData = "[[{\"text\":\"점검대상\",\"style\":\"background:#ccc;\"},{\"text\":\"pinpay\"}],[{\"text\":\"점검일\",\"style\":\"background:#ccc;\"},{\"text\":\"2024.03.07\"}],[{\"text\":\"점검자\",\"style\":\"background:#ccc;\"},{\"text\":\"bwc123\"}],[{\"text\":\"로그 수집 기간\",\"style\":\"background:#ccc;\"},{\"text\":\"2024.03.07 ~ 2024.03.11\"}]]";
        elements.add(createTable(serviceData,false,headerParams));
        
        /**
         * ####### 점검 보고서 body Template
         * */
        elements.add(createBr());  // 줄바꿈
        elements.add(createDiv("세부 로그 관리 점검 항목","","margin-top:10px; font-size:20px;"));

        /**
         * ####### body Template Parameter 설정
         * */
        List<String> bodyParams = new ArrayList<>();
        JSONArray params = (JSONArray) jsonObj.get("detailParams");
        String[] dlArr = {"param2","param3","param4","param5","param6"};
        List<String> dlItems = new ArrayList<>(Arrays.asList(dlArr));
        int dlIdx = 1;
        for (Object param : params) {
            if(dlItems.contains("param" + dlIdx)){
                String jsonStr = createDl( param.toString()).toString();
                bodyParams.add(jsonStr);
            }else{
                bodyParams.add((String) param);
            }
            dlIdx++;
        }

        String bodyData = "[[{\"text\":\"구분\"},{\"text\":\"세부점검항목\"},{\"text\":\"점검 결과\"}],[{\"text\":\"로깅관리\"},{\"text\":\"로그인/접근 로그 로깅 여부\"},{\"text\":\"로그기록 정상 저장 확인, 접속 리고 필수 항목 상 누락 없음\"}],[{\"text\":\"접근로그 관리\",\"rowspan\":\"3\"},{\"text\":\"로그인 사용자 정보 확인\"},{\"text\":\"정상 접속자 3명 /n - 11111\"}],[{\"text\":\"로그인 접속IP 확인\"},{\"text\":\"특이사항 없음.\"}],[{\"text\":\"로그인/로그아웃 시간 및 정보\"},{\"text\":\"사용자 3명 3월 02일 21시 접속 이력 확인<br />해당 내용 사전 승인 야간작업으로 특이사항 없음\"}],[{\"text\":\"접근 로그 관리\",\"rowspan\":\"2\"},{\"text\":\"관리자 권한 로그인 로그\"},{\"text\":\"관리자 3명 <br />bwc123,bwc234,bwc1256\"}],[{\"text\":\"관리자 작업 수행 로그\"},{\"text\":\"사용자 계정 발급 이력 : 0 건<br />사용자 계정 변경 이력 : 0 건\"}],[{\"text\":\"조회/다운로드 이력 확인\",\"rowspan\":\"4\"},{\"text\":\"월 평균 다운로드 건 수<br />(전체사용자)\"},{\"text\":\"거래내역 파일 다운로드 : 500건\"}],[{\"text\":\"월 평균 조회 건 수<br />(전체사용자)\"},{\"text\":\"개인 정보 조회  : 500건\"}],[{\"text\":\"과다 다운로드 이력(사유)확인\"},{\"text\":\"최다 다운로드 관리자ID : bwc123<br />다운로드 건 수 : 10 건\"}],[{\"text\":\"업무 시간 외 다운로드 이력 확인\"},{\"text\":\"사용자 ID : bwc123,bwc234,bwc35456<br />다운로드 건 수 : 2건<br />다운로드 사유 : 제휴사 쿠폰 업로드 오류로 사용자 구독 정보 다운로드\"}]]";
        elements.add( createTable(bodyData,true,bodyParams));

        /**
         * ####### HTML 생성
         * */
        for (Element element : elements) {
            doc.body().appendChild(element);
        }

        return html();
    }

}

