package zunior.lotto.generator.model;

import zunior.lotto.generator.service.LottoNumberGenerator;

import java.util.Collections;
import java.util.List;

public class LottoTicket {

    private final List<Integer> lottoNumbers;

    private LottoTicket(LottoNumberGenerator lottoNumberGenerator) {
        this.lottoNumbers = lottoNumberGenerator.generate();
        Collections.sort(this.lottoNumbers);
    }

    public static LottoTicket create(LottoNumberGenerator lottoNumberGenerator) {
        return new LottoTicket(lottoNumberGenerator);
    }

    public LottoResult check(List<Integer> winningNumbers) {
        int matchCount = Math.toIntExact(matchWinningNumbers(winningNumbers));
        return LottoResult.of(matchCount);
    }

    private long matchWinningNumbers(List<Integer> winningNumbers) {
        return lottoNumbers.stream()
                .filter(number -> winningNumbers.contains(number))
                .count();
    }

    public Integer[] toArray() {
        return lottoNumbers.toArray(new Integer[0]);
    }
}
