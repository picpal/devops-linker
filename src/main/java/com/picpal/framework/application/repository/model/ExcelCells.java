package com.picpal.framework.sample.repository.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
public class ExcelCells {
    // 기본 생성자 protected로 접근 제한하여 new로 생성 억제 -> builder pattern 사용 강제
    // 기본 생성자 접근 제한자는 protected 까지 허용)
    // 기본 생성자의 접근 제한자를 private으로 걸면, 추후에 Lazy Loading 사용 시 Proxy 관련 예외가 발생


    /*
    * 실제 DataBase의 테이블과 1 : 1로 Mapping 되는 Class로, DB의 테이블내에 존재하는 컬럼만을 속성(필드)으로 정의
    * 사용범위 : Persistence(DAO, Repository) <-> Business(Service)
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long primaryKey;

    private String col1; // "유효기간구간순번"
    private String col2; // "일련번호"
    private String col3; // "쿠폰핀번호"
    private String col4; // "업체코드"
    private String col5; // "상품코드"
    private String col6; // "유효종료일자"
    private String col7; // "유효시작일자"


}
