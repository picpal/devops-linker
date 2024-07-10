package com.picpal.framework.common.utils.html;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
class HtmlElementGenerateTest {
    @Test
    public void generate(){

        String[] headerCells = {"Header1", "Header2", "Header3", "Header4"};
        List<TableCell> row_00 = Arrays.stream(headerCells)
                .map(header -> new TableCell(header, null, null,  true))
                .collect(Collectors.toList());

        String[] row01Cells = {"row01_cell01", "row01_cell02", "row01_cell03", "row01_cell04"};
        List<TableCell> row_01 = IntStream.range(0, row01Cells.length)
                .mapToObj(i -> new TableCell(row01Cells[i], i == 0 ? 2 : null, null, true))
                .collect(Collectors.toList());

        String[] row02Cells = {"row02_cell01", "row02_cell02", "row02_cell03", "row02_cell04"};
        List<TableCell> row_02 = IntStream.range(0, row02Cells.length)
                .filter(i -> i != 0) // 첫 번째 요소 (인덱스 0) 제외 한 나머지 배열만 반환 (rowspan으로 건너뛰기 해줌)
                .mapToObj(i -> new TableCell(row02Cells[i],  null, null, true))
                .collect(Collectors.toList());

        // row 합치기
        List<TableCell>[] rows = new List[]{row_00, row_01, row_02};
        List<TableRow> tableRows = Arrays.stream(rows)
                .map(TableRow::new) // (TableRow::new 생성자 참조 구문) ==>> row_00 -> new TableRow(row_00)
                .collect(Collectors.toList());

        Table table = new Table(tableRows);
        log.info(table.toHtml());


        /*
        *
        * 메소드 레퍼런스(Method References)
          메소드 레퍼런스는 네 가지 주요 형태로 사용됩니다:

          정적 메소드 참조 (Static Method References): 클래스명::정적메소드명
          인스턴스 메소드 참조 (Instance Method References): 인스턴스참조변수::인스턴스메소드명
          특정 타입의 임의 객체에 대한 인스턴스 메소드 참조 (Arbitrary Object of a Particular Type Method References): 클래스명::인스턴스메소드명
          생성자 참조 (Constructor References): 클래스명::new
        *
        * */

    }
}