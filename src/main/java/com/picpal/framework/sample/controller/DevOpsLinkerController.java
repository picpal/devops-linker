package com.picpal.framework.sample.controller;

import com.picpal.framework.sample.service.DevOpsLinkerService;
import com.picpal.framework.sample.vo.GitlabEventVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Slf4j
@RequestMapping(value = "/devops/")
@RestController
@RequiredArgsConstructor
public class DevOpsLinkerController {
    private static final String API_TOKEN = "glpat-tYyyW1G8L2y9xcRsusrk";
    private static final String GITLAB_API_URL = "https://gitlab.com/api/v4/users/";


    private JavaMailSender mailSender;

    public DevOpsLinkerController(JavaMailSender javaMailSender){
        this.mailSender = javaMailSender;
    }

    private DevOpsLinkerService devOpsLinkerService;

    public DevOpsLinkerController(DevOpsLinkerService devOpsLinkerService) {
        this.devOpsLinkerService = devOpsLinkerService;
    }

    /**
     * GitLab 이벤트를 처리하는 엔드포인트입니다.
     * 이벤트 종류에 따라 적절한 핸들러 메서드를 호출합니다.
     *
     * @param event GitLab 이벤트 객체
     * @throws IOException I/O 예외
     */
    @PostMapping("/gitlab/mail")
    public void handleGitlabEvent(@RequestBody GitlabEventVO event) throws IOException{
        log.info("Gitlab Event : [ {} ]",event.getObjectKind());
        switch (event.getObjectKind()) {
            case "merge_request":
                handleMergeRequestEvent(event);
                break;
            case "note":
                handleCommentEvent(event);
                break;
            case "pipeline":
                handlePipelineEvent(event);
                break;
            default:
                // Handle other events
                break;
        }

    }

    /**
     * Merge Request 이벤트를 처리하는 메서드입니다.
     * 이벤트에서 사용자 ID와 이름을 가져오고, 리뷰어들에게 이메일 알림을 보냅니다.
     *
     * @param event GitLab 이벤트 객체
     */
    private void handleMergeRequestEvent(GitlabEventVO event) {
        String userName = event.getUser().getName();
        int userId = event.getUser().getId();
        log.info("userID : {}", userId);

        if (event.getAssignees() == null || event.getAssignees().isEmpty()) {
            log.info("##### No assignees found for merge request created by: {}", userName);
            return;
        }

        log.info("##### userName : {}" ,userName);
        for (GitlabEventVO.Assignee assignee : event.getAssignees()) {
            String reviewerEmail = assignee.getEmail();
            String reviewerName = assignee.getName();

            String subject = "New Merge Request Review for " + reviewerName;
            String text = "Dear " + reviewerName + ",\n\n"
                    + "A new merge request has been assigned to you by " + userName + ".\n"
                    + "Please review it at your earliest convenience.";


            log.info("reviewerEmail :  {} , reviewerName : {} , subject : {}" , reviewerEmail,reviewerName,subject);
//            sendEmailNotification(subject, text, reviewerEmail);
        }
    }

    /**
     * 댓글 이벤트를 처리하는 메서드입니다.
     * 이벤트에서 사용자 이름과 이메일을 가져오고, 이메일 알림을 보냅니다.
     *
     * @param event GitLab 이벤트 객체
     */
    private void handleCommentEvent(GitlabEventVO event) {
        String userName = event.getUser().getName();
        String email = event.getUser().getEmail();

        String subject = "New Comment by " + userName;
        String text = "Comment by: " + userName + "\n" + event.getObjectAttributes().getNote();
        sendEmailNotification(subject, text, email);
    }

    /**
     * 파이프라인 이벤트를 처리하는 메서드입니다.
     * 파이프라인 상태가 'merge'일 경우 사용자에게 이메일 알림을 보냅니다.
     *
     * @param event GitLab 이벤트 객체
     */
    private void handlePipelineEvent(GitlabEventVO event) {
        if ("merge".equals(event.getObjectAttributes().getStatus())) {
            String userName = event.getUser().getName();
            String email = event.getUser().getEmail();

            String subject = "Merge Completed by " + userName;
            String text = "Merge request completed by: " + userName;
            sendEmailNotification(subject, text, email);
        }
    }

    /**
     * 이메일 알림을 보내는 메서드입니다.
     *
     * @param subject 이메일 제목
     * @param text    이메일 본문
     * @param to      수신자 이메일 주소
     */
    private void sendEmailNotification(String subject, String text, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    
}
