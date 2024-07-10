package com.picpal.framework.common.utils.html;

import java.util.List;
import java.util.stream.Collectors;

public class Table implements HtmlElement {
    private final List<TableRow> rows;

    public Table(List<TableRow> rows) {
        this.rows = rows;
    }

    @Override
    public String toHtml() {
        String rowHtml = rows.stream().map(TableRow::toHtml).collect(Collectors.joining());
        return String.format("<table>%s</table>", rowHtml);
    }
}

