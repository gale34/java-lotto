import zunior.lotto.generator.model.LottoPayment;
import zunior.lotto.generator.model.LottoResults;
import zunior.lotto.generator.model.LottoTickets;
import zunior.lotto.generator.service.LottoNumberGenerator;
import zunior.lotto.generator.service.impl.AutomaticLottoNumberGenerator;
import zunior.lotto.generator.view.InputView;
import zunior.lotto.generator.view.OutputConsoleView;

import java.util.List;

import static zunior.lotto.generator.utils.LottoUtil.convertToIntegerList;

public class Main {

    public static void main(String[] args) {
        LottoPayment lottoPayment = inputLottoPayment();

        LottoTickets lottoTickets = showLottoTickets(lottoPayment);

        showResult(lottoPayment, lottoTickets);
    }

    private static LottoPayment inputLottoPayment() {
        Integer money = InputView.inputMoney();
        return LottoPayment.of(money);
    }

    private static LottoTickets showLottoTickets(LottoPayment lottoPayment) {
        LottoNumberGenerator lottoNumberGenerator = new AutomaticLottoNumberGenerator();
        LottoTickets lottoTickets = LottoTickets.create(lottoPayment, lottoNumberGenerator);
        OutputConsoleView.printLottoTickets(lottoTickets);
        return lottoTickets;
    }

    private static void showResult(LottoPayment lottoPayment, LottoTickets lottoTickets) {
        String winningNumberString = InputView.inputWinningNumber();
        List<Integer> winningNumbers = convertToIntegerList(winningNumberString);
        LottoResults lottoResults = lottoTickets.checkAll(winningNumbers);
        OutputConsoleView.printLottoResults(lottoResults, lottoPayment);
    }
}