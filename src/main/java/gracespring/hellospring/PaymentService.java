package gracespring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {
    private final WebApiExRateProvider exRateProvider;

    public PaymentService() {
        this.exRateProvider = new WebApiExRateProvider();
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {

        BigDecimal exRate = exRateProvider.getWebExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }

}


/*
    결제 준비 로직(prepare)은 공통으로 제공
    하지만 환율 조회(getExRate) 부분은 구체적으로 어떻게 가져올지는 정하지 않고 추상 메서드로 열어둠.
    즉, 템플릿 메서드 패턴처럼 동작:
    - 큰 틀은 정해져 있고 (prepare)
    - 세부 구현은 하위 클래스가 오버라이드해서 채움 (getExRate)
 */