package com.picpal.framework.common.utils.html;

public class Link implements HtmlElement {
    private final String href;
    private final String text;
    private final String classAttribute;
    private final String style;

    public Link(String href, String text, String classAttribute, String style) {
        this.href = href;
        this.text = text;
        this.classAttribute = classAttribute;
        this.style = style;
    }

    @Override
    public String toHtml() {
        return String.format("<a href=\"%s\" class=\"%s\" style=\"%s\">%s</a>", href, classAttribute, style, text);
    }
}
