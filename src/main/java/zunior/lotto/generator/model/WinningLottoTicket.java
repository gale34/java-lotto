package zunior.lotto.generator.model;

import zunior.lotto.generator.exception.LottoResultException;

import java.util.List;

import static zunior.lotto.generator.utils.LottoConstant.LOTTO_MAX_NUMBER;
import static zunior.lotto.generator.utils.LottoConstant.LOTTO_MIN_NUMBER;

public class WinningLottoTicket extends LottoTicket {

    private final Integer bonusNumber;

    protected WinningLottoTicket(List<Integer> numbers, Integer bonusNumber) {
        super(numbers);
        this.bonusNumber = bonusNumber;
        validateBonusNumber();
    }

    public static WinningLottoTicket create(List<Integer> numbers, Integer bonusNumber) {
        return new WinningLottoTicket(numbers, bonusNumber);
    }

    public Boolean contains(Integer number) {
        return lottoNumbers.contains(number);
    }

    public Integer getBonusNumber() {
        return bonusNumber;
    }

    private void validateBonusNumber() {
        if(bonusNumber == null) {
            throw new LottoResultException("Bonus number is null.");
        }

        if(bonusNumber < LOTTO_MIN_NUMBER || bonusNumber > LOTTO_MAX_NUMBER) {
            throw new LottoResultException("Bonus number must range between 1 and 45.");
        }

        if(lottoNumbers.contains(bonusNumber)) {
            throw new LottoResultException("Winning numbers contains bonus number");
        }
    }
}
