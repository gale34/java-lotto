package zunior.lotto.generator.model;

import java.util.List;

public class PurchaseLottoTicket extends LottoTicket {

    private LottoType lottoType;

    protected PurchaseLottoTicket(List<Integer> numbers, LottoType lottoType) {
        super(numbers);
        this.lottoType = lottoType;
    }

    public static PurchaseLottoTicket create(List<Integer> numbers, LottoType lottoType) {
        return new PurchaseLottoTicket(numbers, lottoType);
    }

    public LottoResult check(WinningLottoTicket winningNumbers) {
        int matchCount = Math.toIntExact(matchWinningNumbers(winningNumbers));
        return LottoResult.of(matchCount, containBonusNumber(winningNumbers.getBonusNumber()));
    }

    public LottoType getLottoType() {
        return lottoType;
    }

    private long matchWinningNumbers(WinningLottoTicket winningNumbers) {
        return lottoNumbers.stream()
                .filter(winningNumbers::contains)
                .count();
    }

    private boolean containBonusNumber(Integer bonusNumber) {
        return lottoNumbers.contains(bonusNumber);
    }

}
