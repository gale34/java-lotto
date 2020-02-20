package zunior.lotto.generator.model;

import zunior.lotto.generator.exception.LottoTicketException;

import java.util.Collections;
import java.util.List;

import static zunior.lotto.generator.utils.LottoConstant.*;

public class LottoTicket {

    protected final List<Integer> lottoNumbers;

    protected LottoTicket(List<Integer> numbers) {
        this.lottoNumbers = numbers;
        validate();
        Collections.sort(this.lottoNumbers);
    }

    public Integer[] toArray() {
        return lottoNumbers.toArray(new Integer[0]);
    }

    public void validate() {
        if (lottoNumbers == null || lottoNumbers.size() != LOTTO_NUMBER_SIZE) {
            throw new LottoTicketException("로또 숫자는 반드시 6개여야 합니다.");
        }

        if (hasLimitNumber()) {
            throw new LottoTicketException("로또 숫자는 0보다 크고 45보다 작은 숫자여야 합니다.");
        }

        if (!isAllDistinct()) {
            throw new LottoTicketException("로또 숫자 중 겹치는 숫자가 있어서는 안됩니다.");
        }
    }

    private boolean hasLimitNumber() {
        return lottoNumbers.stream()
                .anyMatch(number -> number < LOTTO_MIN_NUMBER || number > LOTTO_MAX_NUMBER);
    }

    private boolean isAllDistinct() {
        return lottoNumbers.stream()
                .distinct()
                .count() == LOTTO_NUMBER_SIZE;
    }
}
