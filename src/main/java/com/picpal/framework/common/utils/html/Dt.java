package com.picpal.framework.common.utils.html;

public class Dt implements HtmlElement {
    private final String content;

    public Dt(String content) {
        this.content = content;
    }

    @Override
    public String toHtml() {
        return "<dt>" + content + "</dt>";
    }
}
