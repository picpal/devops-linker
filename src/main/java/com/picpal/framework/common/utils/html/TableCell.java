package com.picpal.framework.common.utils.html;

public class TableCell implements HtmlElement {
    private final String content;
    private final Integer rowspan;
    private final Integer colspan;
    private final boolean isHeader;

    public TableCell(String content, Integer rowspan, Integer colspan, boolean isHeader) {
        this.content = content;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.isHeader = isHeader;
    }

    @Override
    public String toHtml() {
        String tag = isHeader ? "th" : "td";
        String attrs = "";
        if (rowspan != null && rowspan > 1) {
            attrs += " rowspan=\"" + rowspan + "\"";
        }
        if (colspan != null && colspan > 1) {
            attrs += " colspan=\"" + colspan + "\"";
        }
        return String.format("<%s%s>%s</%s>", tag, attrs, content, tag);
    }
}
