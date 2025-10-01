package gracespring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new WebApiExRatePaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}

/*
    프로그램 실행을 담당하는 main 메서드가 있음.
    PaymentService라는 추상 타입으로 객체를 선언 -> 다형성 (Polymorphism) 활용.
    실제로는 WebApiExRatePaymentService 인스턴스를 할당해서 실행.
    이렇게 하면 나중에 FileExRatePaymentService, MockExRatePaymentService 같은 다른 구현체로 쉽게 교체 가능.
 */
