package com.picpal.framework.application.service.impl;

import com.picpal.framework.application.service.GitlabService;
import com.picpal.framework.application.vo.GitlabEventVO;
import com.picpal.framework.common.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitlabServiceImpl implements GitlabService {

    private final EmailUtil emailUtil;

    /**
     * Gitlab 이벤트에 따라 처리 Method를 호출하는 메서드입니다.
     *
     * @param event GitLab 이벤트 객체
     */
    @Override
    public void gitlabEventRouter(GitlabEventVO event)  {
        switch (event.getObjectKind()) {
            case "merge_request":
                handleMergeRequestEvent(event);
                break;
            case "note":
                handleCommentEvent(event);
                break;
            case "push":
                emailUtil.sendEmailNotification("Webhooks test","hello","picpal@kakao.com");
                // push 알림 여부 미정...
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
    public void handleMergeRequestEvent(GitlabEventVO event) {
        log.info("##### handleMergeRequestEvent START");

        String userName = event.getUser().getName();

        // ##### 이벤트 발생 상황 ( 구분 가능여부 확인 )
        // merge 요청할 때
        // merge 승인할 때
        // merge 닫을 때
        // merge 할 때
        // merge request 에 comment 추가 될 때 ( 작성자 , reviewer 모두의 상황 고려 )
        //   - reviewer가 comment 작성 : MR 요청자에게 발송
        //   - MR 요청자가 comment 작성 : reviewer에게 발송
        //   - reviewer가 다수인 경우 : reviewer 모두 발송
        //   - 3분 내로 재 수정시 발송 : 단순 수정 가능성이 있으니 발송 보류..


        Map<String,String> mergeEventParams = parseMergeEventParams(event);

        /*
        * 필요 데이터
        *
        * reviewer의 email
        * MR 요청 내용
        * 요청 MR 번호
        * 요청자
        * MR LINK
        *
        * */

        if (event.getAssignees() == null || event.getAssignees().isEmpty()) {
            log.info("##### No assignees found for merge request created by: {}", userName);
            return;
        }

        log.info("##### userName : {}", userName);
        for (GitlabEventVO.Assignee assignee : event.getAssignees()) {
            String reviewerEmail = assignee.getEmail();
            String reviewerName = assignee.getName();

            String subject = "New Merge Request Review for " + reviewerName;
            String text = "Dear " + reviewerName + ",\n\n"
                    + "A new merge request has been assigned to you by " + userName + ".\n"
                    + "Please review it at your earliest convenience.";


            log.info("reviewerEmail :  {} , reviewerName : {} , subject : {}", reviewerEmail, reviewerName, subject);
            emailUtil.sendEmailNotification(subject, text, reviewerEmail);
        }
    }

    /**
     * 댓글 이벤트를 처리하는 메서드입니다.
     * 이벤트에서 사용자 이름과 이메일을 가져오고, 이메일 알림을 보냅니다.
     *
     * @param event GitLab 이벤트 객체
     */
    public void handleCommentEvent(GitlabEventVO event) {
        log.info("##### handleCommentEvent START");

        String userName = event.getUser().getName();
        String email = event.getUser().getEmail();

        String subject = "New Comment by " + userName;
        String text = "Comment by: " + userName + "\n" + event.getObjectAttributes().getNote();
        emailUtil.sendEmailNotification(subject, text, email);
    }


    /**
     * Merge Request Event에 발생된 Event객체에 대한 정보를 Map 데이터 형태로 파싱합니다
     *
     * @param event GitLab 이벤트 객체
     */
    private Map<String,String> parseMergeEventParams(GitlabEventVO event){

        return null;
    }
}
