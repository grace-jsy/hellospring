package gracespring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class WebApiExRateProvider {
    BigDecimal getWebExRate(String currency) throws IOException {
        URL url = new URL("https://open.er-api.com/v6/latest/USD" + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        return data.rates().get("KRW");
    }
}

/*
    PaymentService에서 미구현 상태였던 getExRate를 실제로 구현.
    외부 환율 API를 호출해서 BigDecimal 타입으로 환율 값을 변환.
    즉, 이 서비스는 환율을 웹 API를 통해 가져오는 바익으로 구체화된 것.
 */
