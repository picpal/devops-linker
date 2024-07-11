package com.picpal.framework.application.service;

import com.picpal.framework.application.vo.GitlabEventVO;

public interface GitlabService {
    public void gitlabEventRouter(GitlabEventVO event);
    public void handleMergeRequestEvent(GitlabEventVO event);
    public void handleCommentEvent(GitlabEventVO event);
}
