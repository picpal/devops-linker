package com.picpal.framework.common.utils.html;

public class Li implements HtmlElement {
    private final String content;

    public Li(String content) {
        this.content = content;
    }

    @Override
    public String toHtml() {
        return "<li>" + content + "</li>";
    }
}