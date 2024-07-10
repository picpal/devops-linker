package com.picpal.framework.sample.controller;

import com.picpal.framework.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "/api/v1/")
@RestController
@RequiredArgsConstructor
public class SampleContorller {

    private SampleService sampleService;

    public SampleContorller(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    /**
     * Restful
     * GET : 리소스 조회
     * POST : 리소스 생성
     * PUT : 리소스 갱신
     * PATCH : 리소스 일부를 갱신
     * DELETE : 리소스 삭세
     * ALL : 여러개의 HTTP 요청 메서드에 대해 하나로 처리하는 경우 @RequestMapping사용
     *
     * RESTful API를 설계 과정
     * 1. 자원(Resource)을 정의
     * 2. 행위(HTTP Method)를 정의
     * 3. 표현(Representation)을 정의
     * 4. 상태 코드(Status Code)를 정의
     * 5. API 문서화를 정의
     *
     * */

    @GetMapping("/sample")
    public void sample(){


    }
}
