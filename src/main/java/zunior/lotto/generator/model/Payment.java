package zunior.lotto.generator.model;

public class Payment {
    private final Integer money;

    private Payment(int money) {
        this.money = money;
    }

    public static Payment of(Integer money) {
        validate(money);
        return new Payment(money);
    }

    private static void validate(Integer money) {
        if(money == null || money < 0) {
            throw new RuntimeException("구매 금액이 적합하지 않습니다.");
        }
    }

    public int getLottoTicketCount(Integer lottoTickerPrice) {
        return money / lottoTickerPrice;
    }
}
