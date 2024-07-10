package com.picpal.framework.common.utils.ReportGenerator;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HtmlGenerator {

    public Document doc;

    public HtmlGenerator() {
        this.doc = Jsoup.parseBodyFragment("");
    }

    /**
     * list 데이터를 테이블 형태의 html 태그로 변환
     *
     * @param jsonStr 테이블 row 데이터
     * @param params 점검 결과
     *
     * */
    public Element createTable(String jsonStr,boolean useTitle, List<String> params){
        JSONParser parser = new JSONParser();
        JSONArray rows;
        try {
            rows = (JSONArray) parser.parse(jsonStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Element table = doc.createElement("table");
        int rowIdx = 0;
        int valIdx = 0;
        for (Object rowObj : rows) {
            JSONArray rowArray = (JSONArray) rowObj;
            Element tr = table.appendElement("tr");

            int cellIdx = 0;
            for (Object cellObj : rowArray) {
                JSONObject cellJson = (JSONObject) cellObj;
                Map<String, String> cell = new HashMap<>();

                for (Object keyObj : cellJson.keySet()) {
                    String key = (String) keyObj;
                    String value = (String) cellJson.get(key);

                    // Table header 생성 시
                    if(useTitle && rowIdx == 0 ){
                        cell.put(key, value);
                        continue;
                    }

                    // 전달받은 parameter값 맵핑
                    if(rowArray.size() == cellIdx + 1){
                        value = params.size() > valIdx
                                ? params.get(valIdx)
                                : value;
                        valIdx++;
                    }
                    
                    // parameter값 할당
                    cell.put(key, value);
                }
                cellIdx++;

                if(cell.get("text") == null || "".equals(cell.get("text"))){
                    continue;
                }

                tr.appendElement("td")
                        .html(cell.get("text"))
                        .attr("rowspan",cell.get("rowspan") == null ? "1" : cell.get("rowspan"))
                        .attr("colspan",cell.get("colspan") == null ? "1" : cell.get("colspan"))
                        .attr("style","border:1px solid;");
            }
            rowIdx++;
        }

        return table;
    }

    public Element createUl(String[] items, String classAttribute) {
        Element ul = doc.createElement("ul").addClass(classAttribute);
        for (String item : items) {
            ul.appendElement("li").text(item);
        }
        return ul;
    }

    public Element createDl(String definitions) {
        JSONParser parser = new JSONParser();
        JSONArray section;
        try {
            section = (JSONArray) parser.parse(definitions);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Element dl = doc.createElement("dl");
        for (Object sec : section) {
            JSONObject data = (JSONObject) sec;
            for (Object row : data.keySet()) {
                String key = (String) row;
                if("title".equals(key)){
                    dl.appendElement("dt").text((String) data.get(key)).attr("style","margin:5px 0;");
                }

                if("list".equals(key)){
                    JSONArray list = (JSONArray) data.get(key);
                    for (Object item : list) {
                        dl.appendElement("dd").text((String) item);
                    }
                }
            }
        }

        return dl;
    }

    public Element createDiv(String content, String classAttribute , String style) {
        return doc.createElement("div").text(content).attr("style",style).addClass(classAttribute);
    }

    public Element createBr() {
        return doc.body().appendElement("br");
    }

    public Element createSpan(String content, String classAttribute) {
        return doc.createElement("span").text(content).addClass(classAttribute);
    }

    public Element createLink(String href, String text, String classAttribute) {
        return doc.createElement("a").attr("href", href).text(text).addClass(classAttribute);
    }

    public Element createLine(String content, String classAttribute) {
        return doc.createElement("br").text(content).addClass(classAttribute);
    }

    // HTML을 문자열로 반환합니다. 이는 최종적인 HTML 출력을 위해 사용됩니다.
    public String html() {
        return doc.body().html();
    }
}
