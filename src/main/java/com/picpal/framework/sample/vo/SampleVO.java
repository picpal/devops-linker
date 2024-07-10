package com.picpal.framework.sample.vo;

public class SampleVO {
    // 객체 레이어간 값의 전달을 위해 사용
    // VO의 핵심 역할은 equals()와 hashcode()를 오버라이딩
    // VO 내부에 선언된 속성(Field)의 모든 값들이 VO 객체마다 값이 같아야, 똑같은 객체라고 판별
    // VO는 조금 더 특정한 Business Logic의 결과 값을 담는 객체
    // 객체안의 값을 통해서도 비교 해야 하는 중요 로직에서 사용할 Data를 담기 위해 사용
    // 사용범위 : Business(Service) <-> Presentation(Controller)
}
