package zunior.lotto.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import zunior.lotto.generator.model.LottoPayment;
import zunior.lotto.generator.model.LottoResult;
import zunior.lotto.generator.model.LottoResults;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("로또 결과 테스트")
public class LottoResultsTests {

    @DisplayName("당첨 결과 테스트")
    @ParameterizedTest
    @MethodSource
    public void gameResultsTest(List<LottoResult> lottoResultList, Integer[] expected) {
        LottoResults lottoResults = LottoResults.create(lottoResultList);
        Map<LottoResult, Long> result = lottoResults.getResult();
        Integer[] actual = result.keySet()
                .stream()
                .filter(lottoResult -> !lottoResult.isBlank())
                .map(lottoResult -> Math.toIntExact(result.get(lottoResult)))
                .toArray(Integer[]::new);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("수익률 테스트")
    @ParameterizedTest(name = "투자금 : {0}. 당첨 총 금액 : {1}. 결과 : {2}%")
    @CsvSource({"5000,5000,100", "1000, 50000, 5000", "10000, 5000, 50", "50000, 0, 0", "15000, 5000, 33"})
    public void profitTest(int investmentAmount, long price, int expectedProfit) {
        LottoPayment payment = LottoPayment.of(investmentAmount);
        assertThat(payment.calculateProfit(price)).isEqualTo(expectedProfit);
    }

    private static Stream gameResultsTest() {
        return Stream.of(
                // 두번쨰 Array는 5등, 4등, 3등, 2등, 1등 갯수 array.
                Arguments.of(Arrays.asList(LottoResult.ONE, LottoResult.ZERO, LottoResult.TWO), new Integer[]{0, 0, 0, 0, 0}),
                Arguments.of(Arrays.asList(LottoResult.THREE, LottoResult.FIVE, LottoResult.ONE), new Integer[]{1, 0, 1, 0, 0}),
                Arguments.of(Arrays.asList(LottoResult.FOUR, LottoResult.FIVE, LottoResult.SIX), new Integer[]{0, 1, 1, 0, 1}),
                Arguments.of(Arrays.asList(LottoResult.SIX, LottoResult.SIX, LottoResult.SIX), new Integer[]{0, 0, 0, 0, 3}),
                Arguments.of(Arrays.asList(LottoResult.SIX, LottoResult.FIVE_WITH_BONUS, LottoResult.FIVE), new Integer[]{0, 0, 1, 1, 1}),
                Arguments.of(Arrays.asList(LottoResult.FIVE_WITH_BONUS, LottoResult.ONE, LottoResult.ZERO), new Integer[]{0, 0, 0, 1, 0})
        );
    }
}
