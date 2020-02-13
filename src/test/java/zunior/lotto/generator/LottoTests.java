package zunior.lotto.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import zunior.lotto.generator.model.LottoResult;
import zunior.lotto.generator.model.LottoTicket;
import zunior.lotto.generator.model.LottoTickets;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("로또 테스트")
public class LottoTests {

    private static final Integer LOTTO_MIN_NUMBER = 1;
    private static final Integer LOTTO_MAX_NUMBER = 45;

    private static final Integer LOTTO_NUMBER_SIZE = 6;

    @DisplayName("로또 자동 생성 번호 개수 검증")
    @Test
    public void lottoNumberSizeTest() {
        assertThat(lottoNumbers(new AutomaticLottoNumberGenerator()).size()).isEqualTo(LOTTO_NUMBER_SIZE);
    }

    @DisplayName("로또 자동 생성 번호 검증")
    @Test
    public void lottoNumbersTest() {
        lottoNumbers(new AutomaticLottoNumberGenerator()).stream()
                .forEach(number -> assertThat(number).isBetween(LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER));
    }

    @DisplayName("당첨 테스트")
    @ParameterizedTest
    @MethodSource("lottoWinTestStream")
    @ExtendWith(MockitoExtension.class)
    public void lottoWinTest(List<Integer> winNumbers, List<Integer> lottoNumbers, LottoResult lottoResult) {
        LottoNumberGenerator lottoNumberGenerator = mock(LottoNumberGenerator.class);
        given(lottoNumberGenerator.generate()).willReturn(lottoNumbers);

        LottoTicket lottoTicket = new LottoTicket(lottoNumberGenerator);
        LottoResult actualResult = lottoTicket.check(winNumbers);
        assertThat(actualResult).isEqualTo(lottoResult);
    }

    private List<Integer> lottoNumbers(LottoNumberGenerator lottoNumberGenerator) {
        LottoTicket lottoTicket = new LottoTicket(lottoNumberGenerator);
        return lottoTicket.getNumbers();
    }

    private static Stream lottoWinTestStream() {
        return Stream.of(
                Arguments.of(Arrays.asList(1,2,3,4,5,6), Arrays.asList(3,4,5,6,7,8), LottoResult.FOUR),
                Arguments.of(Arrays.asList(6,7,12,22,26,36), Arrays.asList(4,9,12,22,26,41), LottoResult.THREE),
                Arguments.of(Arrays.asList(5,12,25,26,38,45), Arrays.asList(5,12,25,26,38,45), LottoResult.SIX),
                Arguments.of(Arrays.asList(5,12,25,26,38,45), Arrays.asList(15,16,17,18,19,20), LottoResult.BLANK)
        );
    }

}
