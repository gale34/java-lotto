package zunior.lotto.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import zunior.lotto.generator.exception.LottoResultException;
import zunior.lotto.generator.model.LottoResult;
import zunior.lotto.generator.model.LottoResults;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("로또 결과 테스트")
public class LottoResultsTests {

    @DisplayName("당첨 카운트 테스트 - 정상")
    @ParameterizedTest
    @CsvSource({"0, false", "1,false", "2, false", "3,false", "4, false", "5,false", "5, true", "6,false"})
    public void lottoResultCountNormalTest(int count, boolean hasBonusNumber) {
        assertDoesNotThrow(() -> LottoResult.of(count, hasBonusNumber));
    }

    @DisplayName("당첨 카운트 테스트 - 비정상")
    @ParameterizedTest
    @CsvSource({"7,true", "-3, false"})
    public void lottoResultCountTest(int count, boolean hasBonusNumber) {
        assertThatExceptionOfType(LottoResultException.class).isThrownBy(() -> LottoResult.of(count, hasBonusNumber));
    }

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
    @ParameterizedTest
    @MethodSource
    public void profitTest(List<LottoResult> lottoResultList, int expectedProfit) {
        LottoResults lottoResults = LottoResults.create(lottoResultList);
        assertThat(lottoResults.getProfit()).isEqualTo(expectedProfit);
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

    private static Stream profitTest() {
        return Stream.of(
                Arguments.of(Arrays.asList(LottoResult.ONE, LottoResult.ZERO, LottoResult.TWO, LottoResult.FIVE), 37500),
                Arguments.of(Arrays.asList(LottoResult.THREE, LottoResult.FIVE, LottoResult.ONE), 50167),
                Arguments.of(Arrays.asList(LottoResult.ONE, LottoResult.TWO, LottoResult.ZERO), 0),
                Arguments.of(Arrays.asList(LottoResult.ONE, LottoResult.TWO, LottoResult.ZERO, LottoResult.ZERO, LottoResult.THREE), 100)
        );
    }
}
