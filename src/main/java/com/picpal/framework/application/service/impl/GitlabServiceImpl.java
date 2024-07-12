package com.picpal.framework.application.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpal.framework.application.service.GitlabService;
import com.picpal.framework.common.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

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
    public void gitlabEventRouter(String event)  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(event);

            printJsonNode(rootNode, "");

            Map<String, Object> nestedMap = jsonNodeToMap(rootNode);
            log.info("Flattened Map: " + nestedMap);

            // Example of accessing specific keys
            String objectKind = (String) nestedMap.get("object_kind");

            switch (objectKind) {
                case "merge_request" -> handleMergeRequestEvent(nestedMap);
                case "note" -> handleCommentEvent(nestedMap);
                case "push" -> emailUtil.sendEmailNotification("Webhooks test", "hello", "picpal@kakao.com");  // push 알림 여부 미정...

                default -> {
                }
            }
        } catch (IOException e) {
            log.error("Error parsing JSON", e);
        }
    }

    /**
     * Merge Request 이벤트를 처리하는 메서드입니다.
     * 이벤트에서 사용자 ID와 이름을 가져오고, 리뷰어들에게 이메일 알림을 보냅니다.
     *
     * @param event GitLab 이벤트 객체
     */
    public void handleMergeRequestEvent(Map<String, Object> event) {
        log.info("##### handleMergeRequestEvent START");

        Map<String, Object> objectAttributes = (Map<String, Object>) event.get("object_attributes");
        String action = (String) objectAttributes.get("action");

        log.info("##### action : {}" , action);
        switch (action) {
            case "open" -> {
            }
//            case "update" -> {}
//            case "approved" -> {}
            case "merge" -> {
            }
//            case "close" -> {}
//            case "reopen" -> {}

            default -> {
            }
        }
        // ##### 이벤트 발생 상황 ( 구분 가능여부 확인 )
        // merge 생성될 때
        // merge 승인할 때
        // merge 닫을 때
        // merge 할 때
        // merge request 에 comment 추가 될 때 ( 작성자 , reviewer 모두의 상황 고려 )
        //   - reviewer가 comment 작성 : MR 요청자에게 발송
        //   - MR 요청자가 comment 작성 : reviewer에게 발송
        //   - reviewer가 다수인 경우 : reviewer 모두 발송
        //   - 3분 내로 재 수정시 발송 : 단순 수정 가능성이 있으니 발송 보류..


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

//        if (event.getAssignees() == null || event.getAssignees().isEmpty()) {
//            log.info("##### No assignees found for merge request created by: {}", userName);
//            return;
//        }
//
//        log.info("##### userName : {}", userName);
//        for (GitlabEventVO.Assignee assignee : event.getAssignees()) {
////            emailUtil.sendEmailNotification(subject, text, reviewerEmail);
//        }
    }

    /**
     * 댓글 이벤트를 처리하는 메서드입니다.
     * 이벤트에서 사용자 이름과 이메일을 가져오고, 이메일 알림을 보냅니다.
     *
     * @param event GitLab 이벤트 객체
     */
    public void handleCommentEvent(Map<String, Object> event) {
        log.info("##### handleCommentEvent START");
//
//        String userName = event.getUser().getName();
//        String email = event.getUser().getEmail();
//
//        String subject = "New Comment by " + userName;
////        String text = "Comment by: " + userName + "\n" + event.getObjectAttributes().getNote();
////        emailUtil.sendEmailNotification(subject, text, email);
    }

    private Map<String, Object> jsonNodeToMap(JsonNode node) {
        Map<String, Object> result = new HashMap<>();
        convertJsonNodeToMap(node, result);
        return result;
    }

    private void convertJsonNodeToMap(JsonNode node, Map<String, Object> map) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                JsonNode value = field.getValue();
                if (value.isObject()) {
                    Map<String, Object> nestedMap = new HashMap<>();
                    convertJsonNodeToMap(value, nestedMap);
                    map.put(field.getKey(), nestedMap);
                } else if (value.isArray()) {
                    List<Object> nestedList = new ArrayList<>();
                    convertJsonNodeToList(value, nestedList);
                    map.put(field.getKey(), nestedList);
                } else {
                    map.put(field.getKey(), value.asText());
                }
            }
        }
    }

    private void convertJsonNodeToList(JsonNode node, List<Object> list) {
        for (JsonNode arrayItem : node) {
            if (arrayItem.isObject()) {
                Map<String, Object> nestedMap = new HashMap<>();
                convertJsonNodeToMap(arrayItem, nestedMap);
                list.add(nestedMap);
            } else if (arrayItem.isArray()) {
                List<Object> nestedList = new ArrayList<>();
                convertJsonNodeToList(arrayItem, nestedList);
                list.add(nestedList);
            } else {
                list.add(arrayItem.asText());
            }
        }
    }


    private void printJsonNode(JsonNode node, String indent) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                log.info(indent + field.getKey() + " : ");
                printJsonNode(field.getValue(), indent + "  ");
            }
        } else if (node.isArray()) {
            for (JsonNode arrayItem : node) {
                printJsonNode(arrayItem, indent + "  ");
            }
        } else {
            log.info(indent + node.asText());
        }
    }

}
