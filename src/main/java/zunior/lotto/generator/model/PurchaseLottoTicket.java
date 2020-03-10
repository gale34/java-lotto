package zunior.lotto.generator.model;

import zunior.lotto.generator.service.LottoNumberGenerator;

import java.util.List;

public class PurchaseLottoTicket extends LottoTicket {

    private LottoType lottoType;

    private PurchaseLottoTicket(List<Integer> numbers, LottoType lottoType) {
        super(numbers);
        this.lottoType = lottoType;
    }

    public static PurchaseLottoTicket create(LottoNumberGenerator lottoNumberGenerator) {
        return new PurchaseLottoTicket(lottoNumberGenerator.generate(), lottoNumberGenerator.getLottoType());
    }

    public LottoResult check(WinningLottoTicket winningNumbers) {
        int matchCount = Math.toIntExact(matchWinningNumbers(winningNumbers));
        return LottoResult.of(matchCount, containBonusNumber(winningNumbers.getBonusNumber()));
    }

    public LottoType getLottoType() {
        return lottoType;
    }

    public boolean equalsLottoType(LottoType lottoType) {
        return this.lottoType.equals(lottoType);
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
