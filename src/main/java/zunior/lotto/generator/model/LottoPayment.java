package zunior.lotto.generator.model;

public class LottoPayment {
    private final Integer money;

    private LottoPayment(int money) {
        this.money = money;
    }

    public static LottoPayment of(Integer money) {
        validate(money);
        return new LottoPayment(money);
    }

    private static void validate(Integer money) {
        if(money == null || money < 0) {
            throw new RuntimeException("구매 금액이 적합하지 않습니다.");
        }
    }

    public Integer getLottoTicketCount(Integer lottoTicketPrice) {
        return money / lottoTicketPrice;
    }

    public Long calculateProfit(Long totalPrice) {
        return Math.round(totalPrice * 100 / (double) money);
    }
}
