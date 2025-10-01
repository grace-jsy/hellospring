package gracespring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

abstract public class PaymentService {
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exRate = getExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }

    abstract BigDecimal getExRate(String currency) throws IOException;
}


/*
    결제 준비 로직(prepare)은 공통으로 제공
    하지만 환율 조회(getExRate) 부분은 구체적으로 어떻게 가져올지는 정하지 않고 추상 메서드로 열어둠.
    즉, 템플릿 메서드 패턴처럼 동작:
    - 큰 틀은 정해져 있고 (prepare)
    - 세부 구현은 하위 클래스가 오버라이드해서 채움 (getExRate)
 */