package com.picpal.framework.common.utils.html;

public class Div implements HtmlElement {
    private final String content;
    private final String classAttribute;
    private final String style;

    public Div(String content, String classAttribute, String style) {
        this.content = content;
        this.classAttribute = classAttribute;
        this.style = style;
    }

    @Override
    public String toHtml() {
        return String.format("<div class=\"%s\" style=\"%s\">%s</div>", classAttribute, style, content);
    }
}
