package zunior.lotto.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import zunior.lotto.generator.exception.PaymentException;
import zunior.lotto.generator.model.LottoPayment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static zunior.lotto.generator.utils.LottoConstant.LOTTO_TICKET_PRICE;

@DisplayName("로또 구입 테스트")
public class LottoPurchaseTests {

    @DisplayName("정상 구매")
    @ParameterizedTest(name = "구입 금액 : {0}. 구입한 로또 갯수 : {1}")
    @CsvSource({"14000, 14", "1000, 1", "600, 0", "2500, 2"})
    public void purchaseTest(int cost, int lottoCount) {
        LottoPayment lottoPayment = LottoPayment.of(cost);
        assertThat(lottoPayment.buyLottoTicketsWithMaximum(LOTTO_TICKET_PRICE)).isEqualTo(lottoCount);
    }

    @DisplayName("비정상 금액(null, 음수) 지불")
    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1, -100})
    public void purchaseWithMinusPaymentTest(Integer cost) {
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> LottoPayment.of(cost));
    }

    @DisplayName("부족한 돈 테스트")
    @ParameterizedTest
    @CsvSource({"1234,2", "2541, 3"})
    public void purchaseWithMinusPaymentTest(Integer cost, Integer needCount) {
        LottoPayment lottoPayment = LottoPayment.of(cost);
        assertThatExceptionOfType(PaymentException.class).isThrownBy(() -> lottoPayment.buyLottoTickets(needCount, LOTTO_TICKET_PRICE));
    }
}
