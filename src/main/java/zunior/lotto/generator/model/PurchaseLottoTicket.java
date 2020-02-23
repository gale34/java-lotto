package zunior.lotto.generator.model;

import java.util.List;

public class PurchaseLottoTicket extends LottoTicket {

    protected PurchaseLottoTicket(List<Integer> numbers) {
        super(numbers);
    }

    public static PurchaseLottoTicket create(List<Integer> numbers) {
        return new PurchaseLottoTicket(numbers);
    }

    public LottoResult check(WinningLottoTicket winningNumbers) {
        int matchCount = Math.toIntExact(matchWinningNumbers(winningNumbers));
        return LottoResult.of(matchCount);
    }

    private long matchWinningNumbers(WinningLottoTicket winningNumbers) {
        return lottoNumbers.stream()
                .filter(winningNumbers::contains)
                .count();
    }
}
