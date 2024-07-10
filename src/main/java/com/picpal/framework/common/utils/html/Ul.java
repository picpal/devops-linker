package com.picpal.framework.common.utils.html;

import java.util.List;
import java.util.stream.Collectors;

public class Ul implements HtmlElement {
    private final List<HtmlElement> items;

    public Ul(List<HtmlElement> items) {
        this.items = items;
    }

    @Override
    public String toHtml() {
        String listItems = items.stream().map(HtmlElement::toHtml).collect(Collectors.joining());
        return "<ul>" + listItems + "</ul>";
    }
}
