package com.picpal.framework.application.controller;

import com.picpal.framework.application.service.GitlabService;
import com.picpal.framework.application.vo.GitlabEventVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping(value = "/devops/")
@RestController
@RequiredArgsConstructor
public class DevOpsLinkerController {

    private final GitlabService gitlabService;

    /**
     * GitLab 이벤트를 처리하는 엔드포인트입니다.
     *
     * @param event GitLab 이벤트 객체
     */
    @PostMapping("/gitlab/mail")
    public void handleGitlabEvent(@RequestBody GitlabEventVO event) {
        log.info("##### Gitlab Event : [ {} ]", event.getObjectKind());
        gitlabService.gitlabEventRouter(event);
    }
}
