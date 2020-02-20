package zunior.lotto.generator.model;

import java.util.List;

public class WinningLottoTicket extends LottoTicket {
    protected WinningLottoTicket(List<Integer> numbers) {
        super(numbers);
    }

    public static WinningLottoTicket create(List<Integer> numbers) {
        return new WinningLottoTicket(numbers);
    }

    public Boolean contains(Integer number) {
        return lottoNumbers.contains(number);
    }
}
