package com.picpal.framework.common.utils.html;

import java.util.List;
import java.util.stream.Collectors;

public class TableRow implements HtmlElement {
    private final List<TableCell> cells;

    public TableRow(List<TableCell> cells) {
        this.cells = cells;
    }

    @Override
    public String toHtml() {
        return "<tr>" + cells.stream().map(TableCell::toHtml).collect(Collectors.joining()) + "</tr>";
    }
}

