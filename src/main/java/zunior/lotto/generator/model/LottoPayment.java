package zunior.lotto.generator.model;

import zunior.lotto.generator.exception.PaymentException;

public class LottoPayment {
    private Integer money;

    private LottoPayment(int money) {
        validate(money);
        this.money = money;
    }

    public static LottoPayment of(Integer money) {
        return new LottoPayment(money);
    }

    public void buyLottoTickets(Integer lottoCount, Integer lottoTicketPrice) {
        money -= lottoCount * lottoTicketPrice;

        if (money < 0) {
            throw new PaymentException(lottoCount + "장 구입하기엔 돈이 부족합니다.");
        }
    }

    public Integer buyLottoTicketsWithMaximum(Integer lottoTicketPrice) {
        return money / lottoTicketPrice;
    }

    private static void validate(Integer money) {
        if (money == null || money < 0) {
            throw new PaymentException("구매 금액이 적합하지 않습니다.");
        }
    }
}
