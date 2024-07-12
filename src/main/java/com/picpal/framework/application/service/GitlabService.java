package com.picpal.framework.application.service;

import java.util.Map;

public interface GitlabService {
    public void gitlabEventRouter(String event);
    public void handleMergeRequestEvent(Map<String, Object> event);
    public void handleCommentEvent(Map<String, Object> event);
}
