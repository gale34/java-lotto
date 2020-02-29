package zunior.lotto.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import zunior.lotto.generator.model.LottoResult;
import zunior.lotto.generator.model.PurchaseLottoTicket;
import zunior.lotto.generator.model.WinningLottoTicket;
import zunior.lotto.generator.service.LottoNumberGenerator;
import zunior.lotto.generator.service.impl.AutomaticLottoNumberGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static zunior.lotto.generator.utils.LottoConstant.*;

@DisplayName("로또 테스트")
public class LottoTests {

    @DisplayName("로또 자동 생성 번호 개수 검증")
    @Test
    public void lottoNumberSizeTest() {
        assertThat(lottoNumbers(new AutomaticLottoNumberGenerator()).length).isEqualTo(LOTTO_NUMBER_SIZE);
    }

    @DisplayName("로또 자동 생성 번호 검증")
    @Test
    public void lottoNumbersTest() {
        Arrays.stream(lottoNumbers(new AutomaticLottoNumberGenerator()))
                .forEach(number -> assertThat(number).isBetween(LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER));
    }

    @DisplayName("당첨 테스트")
    @ParameterizedTest
    @MethodSource("lottoWinTestStream")
    @ExtendWith(MockitoExtension.class)
    public void lottoWinTest(List<Integer> winningNumbers, Integer bonusNumber, List<Integer> lottoNumbers, LottoResult lottoResult) {
        LottoNumberGenerator lottoNumberGenerator = mock(LottoNumberGenerator.class);
        given(lottoNumberGenerator.generate()).willReturn(lottoNumbers);

        PurchaseLottoTicket lottoTicket = PurchaseLottoTicket.create(lottoNumberGenerator.generate());
        LottoResult actualResult = lottoTicket.check(WinningLottoTicket.create(winningNumbers,bonusNumber));
        assertThat(actualResult).isEqualTo(lottoResult);
    }

    private Integer[] lottoNumbers(LottoNumberGenerator lottoNumberGenerator) {
        PurchaseLottoTicket lottoTicket = PurchaseLottoTicket.create(lottoNumberGenerator.generate());
        return lottoTicket.toArray();
    }

    private static Stream lottoWinTestStream() {
        return Stream.of(
                Arguments.of(Arrays.asList(1,2,3,4,5,6), 45, Arrays.asList(3,4,5,6,7,8), LottoResult.FOUR),
                Arguments.of(Arrays.asList(6,7,12,22,26,36), 45, Arrays.asList(4,9,12,22,26,41), LottoResult.THREE),
                Arguments.of(Arrays.asList(5,12,25,26,38,45), 42, Arrays.asList(5,12,25,26,38,45), LottoResult.SIX),
                Arguments.of(Arrays.asList(5,12,25,26,38,45), 42, Arrays.asList(15,16,17,18,19,20), LottoResult.ZERO),
                Arguments.of(Arrays.asList(5,12,25,26,38,45), 42, Arrays.asList(15,16,17,18,19,20), LottoResult.ZERO),
                Arguments.of(Arrays.asList(1,2,3,4,5,6), 7, Arrays.asList(1,2,3,4,5,7), LottoResult.FIVE_WITH_BONUS)
        );
    }

}
