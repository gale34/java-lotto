import zunior.lotto.generator.model.LottoPayment;
import zunior.lotto.generator.model.LottoResults;
import zunior.lotto.generator.model.PurchaseLottoTickets;
import zunior.lotto.generator.model.WinningLottoTicket;
import zunior.lotto.generator.service.LottoNumberGenerator;
import zunior.lotto.generator.service.impl.AutomaticLottoNumberGenerator;
import zunior.lotto.generator.view.InputView;
import zunior.lotto.generator.view.OutputConsoleView;

import java.util.List;

import static zunior.lotto.generator.utils.LottoUtil.convertToIntegerList;

public class Main {

    public static void main(String[] args) {
        LottoPayment lottoPayment = inputLottoPayment();

        PurchaseLottoTickets purchaseLottoTickets = showLottoTickets(lottoPayment);

        showResult(lottoPayment, purchaseLottoTickets);
    }

    private static LottoPayment inputLottoPayment() {
        Integer money = InputView.inputMoney();
        return LottoPayment.of(money);
    }

    private static PurchaseLottoTickets showLottoTickets(LottoPayment lottoPayment) {
        LottoNumberGenerator lottoNumberGenerator = new AutomaticLottoNumberGenerator();
        PurchaseLottoTickets purchaseLottoTickets = PurchaseLottoTickets.create(lottoPayment, lottoNumberGenerator);
        OutputConsoleView.printLottoTickets(purchaseLottoTickets);
        return purchaseLottoTickets;
    }

    private static void showResult(LottoPayment lottoPayment, PurchaseLottoTickets purchaseLottoTickets) {
        String winningNumberString = InputView.inputWinningNumber();
        Integer bonusNumber = InputView.inputBonusNumber();

        List<Integer> winningNumbers = convertToIntegerList(winningNumberString);
        LottoResults lottoResults = purchaseLottoTickets.checkAll(WinningLottoTicket.create(winningNumbers), bonusNumber);
        OutputConsoleView.printLottoResults(lottoResults, lottoPayment);
    }
}