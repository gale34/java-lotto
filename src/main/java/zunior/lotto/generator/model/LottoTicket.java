package zunior.lotto.generator.model;

import zunior.lotto.generator.LottoNumberGenerator;

import java.util.Iterator;
import java.util.List;

public class LottoTicket {

    private final List<Integer> lottoNumbers;

    private LottoTicket(LottoNumberGenerator lottoNumberGenerator) {
        this.lottoNumbers = lottoNumberGenerator.generate();
    }

    public static LottoTicket create(LottoNumberGenerator lottoNumberGenerator) {
        return new LottoTicket(lottoNumberGenerator);
    }

    public List<Integer> getNumbers() {
        return lottoNumbers;
    }

    public LottoResult check(List<Integer> winNumbers) {
        int matchCount = Math.toIntExact(matchWithWinNumbers(winNumbers));
        return LottoResult.of(matchCount);
    }

    private long matchWithWinNumbers(List<Integer> winNumbers) {
        return lottoNumbers.stream()
                .filter(number -> winNumbers.contains(number))
                .count();
    }
}
