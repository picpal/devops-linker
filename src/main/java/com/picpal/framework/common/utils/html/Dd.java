package com.picpal.framework.common.utils.html;


public class Dd implements HtmlElement {
    private final String content;

    public Dd(String content) {
        this.content = content;
    }

    @Override
    public String toHtml() {
        return "<dd>" + content + "</dd>";
    }
}
