package zunior.lotto.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import zunior.lotto.generator.model.LottoPayment;
import zunior.lotto.generator.model.PurchaseLottoTickets;
import zunior.lotto.generator.service.impl.AutomaticLottoNumberGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("로또 구입 테스트")
public class LottoPurchaseTests {

    @DisplayName("정상 구매")
    @ParameterizedTest(name = "구입 금액 : {0}. 구입한 로또 갯수 : {1}")
    @CsvSource({"14000, 14", "1000, 1", "600, 0", "2500, 2"})
    public void purchaseTest(int cost, int lottoCount) {
        LottoPayment lottoPayment = LottoPayment.of(cost);
        PurchaseLottoTickets purchaseLottoTickets = PurchaseLottoTickets.create(lottoPayment, new AutomaticLottoNumberGenerator());
        assertThat(purchaseLottoTickets.getAllTicketCount()).isEqualTo(lottoCount);
    }

    @DisplayName("비정상 금액(null, 음수) 지불")
    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1, -100})
    public void purchaseWithMinusPaymentTest(Integer cost) {
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> LottoPayment.of(cost));
    }
}
