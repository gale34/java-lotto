package zunior.lotto.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zunior.lotto.generator.model.LottoTicket;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("로또 테스트")
public class LottoTests {

    private static final Integer LOTTO_MIN_NUMBER = 1;
    private static final Integer LOTTO_MAX_NUMBER = 45;

    private static final Integer LOTTO_NUMBER_SIZE = 6;

    @DisplayName("로또 자동 생성 번호 개수 검증")
    @Test
    public void lottoNumberSizeTest() {
        assertThat(lottoNumbers().size()).isEqualTo(LOTTO_NUMBER_SIZE);
    }

    @DisplayName("로또 자동 생성 번호 검증")
    @Test
    public void lottoNumbersTest() {
        lottoNumbers().stream()
                .forEach(number -> assertThat(number).isBetween(LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER));
    }

    private List<Integer> lottoNumbers() {
        LottoTicket lottoTicket = new LottoTicket();
        return lottoTicket.getNumbers();
    }

}
