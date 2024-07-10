package com.picpal.framework.common.utils.ReportGenerator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class HtmlGeneratorTest {

    @Test
    void htmlGenerateTest() {
        BackOfficeLogReportGenerator bog = new BackOfficeLogReportGenerator();

        /*
        {
              "serviceName" : "pinpay",
              "checkUser" : "bwc1234",
              "checkStartDate" : "2024.03.07",
              "checkEndDate" : "2024.03.11",
              "detailParams" : [
                    "로그 필수 항목 누락 없음",
                    [
                      {
                        "title":"정상 접속자 : 3명",
                        "list":["bwc123","bwc513123","bwc35513"]
                      }
                    ],
                    [
                      {
                        "title":"비인가 접속 IP ",
                        "list":["1.23.4.5","123.444.223.1"]
                      }
                    ],
                    [
                      {
                        "title":"야간,휴일 접속이력 ",
                        "list":["bwc1234","bwc123400"]
                      }
                    ],
                    [
                      {
                        "title":"관리자 권한 로그인 로그 ",
                        "list":["bwc1234","bwc123400"]
                      }
                    ],
                    [
                      {
                        "title":"관리자 작업 수행 로그 ",
                        "list":["사용자 계정 발급 이력 : 0","사용자 계정 변경 이력 : 0"]
                      }
                    ],
                    "500",
                    "1200",
                    "최다 다운로드 관리자 ID : bwc12345",
                    "최다 다운로드 관리자 ID : bwc123215412",
              ]
        }
        * */

        String params = "{\"serviceName\":\"pinpay\",\"checkUser\":\"bwc1234\",\"checkStartDate\":\"2024.03.07\",\"checkEndDate\":\"2024.03.11\",\"detailParams\":[\"로그 필수 항목 누락 없음\",[{\"title\":\"정상 접속자 : 3명\",\"list\":[\"bwc123\",\"bwc513123\",\"bwc35513\"]}],[{\"title\":\"비인가 접속 IP \",\"list\":[\"1.23.4.5\",\"123.444.223.1\"]}],[{\"title\":\"야간,휴일 접속이력 \",\"list\":[\"bwc1234\",\"bwc123400\"]}],[{\"title\":\"관리자 권한 로그인 로그 \",\"list\":[\"bwc1234\",\"bwc123400\"]}],[{\"title\":\"관리자 작업 수행 로그 \",\"list\":[\"사용자 계정 발급 이력 : 0\",\"사용자 계정 변경 이력 : 0\"]}],\"500\",\"1200\",\"최다 다운로드 관리자 ID : bwc12345\",\"최다 다운로드 관리자 ID : bwc123215412\"]}";
        String result = bog.getBackOfficeLogReport(params);

        log.info(result);
    }
}