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

public class PaymentService {
    /*
        prepare 메서드
        이 메서드는 "결제를 준비한다" 라는 행동을 하는 함수
        환율을 가져오고 계산해서, 최종적으로 새로운 Payment 객체를 만들어서 반환함
     */
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        // 환율 가져오기
        // https://open.er-api.com/v6/latest/USD
        URL url = new URL("https://open.er-api.com/v6/latest/USD" + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        BigDecimal exRate = data.rates().get("KRW");

        // 금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        // 유효 시간 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }

    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}

/*
    PaymentService는 Payment라는 상품을 만들어내는 공장이고
    prepare는 그 공장에서 제품을 조립하는 메서드
    Payment는 최종적으로 완성된 제품(데이터 묶음)이 된다.

    === 흐름 정리 ===
    main에서 prepare 호출
    100L, "USD" 매개변수 값을 넘긴다.
    새로운 Payment 객체를 생성한다.
    최종적으로 Payment 객체를 받아서 System.out.println으로 출력한다.
 */
