import zunior.lotto.generator.model.LottoPayment;
import zunior.lotto.generator.model.LottoResults;
import zunior.lotto.generator.model.LottoTickets;
import zunior.lotto.generator.service.LottoNumberGenerator;
import zunior.lotto.generator.service.impl.AutomaticLottoNumberGenerator;
import zunior.lotto.generator.view.InputView;
import zunior.lotto.generator.view.OutputConsoleView;

import java.util.List;

import static zunior.lotto.generator.utils.LottoUtil.convertToIntegerList;
import static zunior.lotto.generator.view.OutputConsoleView.printLottoResults;

public class Main {

    public static void main(String[] args) {
        Integer money = InputView.inputMoney();
        LottoPayment lottoPayment = LottoPayment.of(money);
        LottoNumberGenerator lottoNumberGenerator = new AutomaticLottoNumberGenerator();

        LottoTickets lottoTickets = LottoTickets.create(lottoPayment, lottoNumberGenerator);
        OutputConsoleView.printLottoTickets(lottoTickets);

        String winNumberString = InputView.inputWinningNumber();
        List<Integer> winNumbers = convertToIntegerList(winNumberString);
        LottoResults lottoResults = lottoTickets.checkAll(winNumbers);
        printLottoResults(lottoResults, lottoPayment);
    }

}