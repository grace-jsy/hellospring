package gracespring.hellospring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExRateData(String result, Map<String, BigDecimal> rates) {
}

/*
    외부 API 응답을 받아서 매핑하기 위한 DTO 역할을 하는 클래스
    getter, equals, hashCode, toString, 생성자 등을 자동으로 만들어 주는 불변 데이터 클래스
    즉, 읽기 전용 컨테이너
 */