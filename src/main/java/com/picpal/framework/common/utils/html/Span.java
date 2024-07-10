package com.picpal.framework.common.utils.html;

public class Span implements HtmlElement {
    private final String content;
    private final String classAttribute;
    private final String style;

    public Span(String content, String classAttribute, String style) {
        this.content = content;
        this.classAttribute = classAttribute;
        this.style = style;
    }

    @Override
    public String toHtml() {
        return String.format("<span class=\"%s\" style=\"%s\">%s</span>", classAttribute, style, content);
    }
}
