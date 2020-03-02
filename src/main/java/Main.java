import zunior.lotto.generator.model.*;
import zunior.lotto.generator.service.LottoNumberGenerator;
import zunior.lotto.generator.service.impl.AutomaticLottoNumberGenerator;
import zunior.lotto.generator.service.impl.ManualLottoNumberGenerator;
import zunior.lotto.generator.view.InputView;
import zunior.lotto.generator.view.OutputConsoleView;

import java.util.List;
import java.util.stream.Collectors;

import static zunior.lotto.generator.utils.LottoConstant.LOTTO_TICKET_PRICE;
import static zunior.lotto.generator.utils.LottoUtil.convertToIntegerList;

public class Main {

    public static void main(String[] args) {
        LottoPayment lottoPayment = inputLottoPayment();

        PurchaseLottoTickets purchaseLottoTickets = inputLottoTickets(lottoPayment);

        OutputConsoleView.printLottoTickets(purchaseLottoTickets);

        showResult(purchaseLottoTickets);
    }

    private static LottoPayment inputLottoPayment() {
        Integer money = InputView.inputMoney();
        return LottoPayment.of(money);
    }

    private static PurchaseLottoTickets inputLottoTickets(LottoPayment lottoPayment) {
        PurchaseLottoTickets manualLottoTickets = inputManualLottoTickets();
        lottoPayment.buyLottoTickets(manualLottoTickets.getAllTicketCount(), LOTTO_TICKET_PRICE);
        PurchaseLottoTickets automaticLottoTickets = showAutomaticLottoTickets(lottoPayment);
        return PurchaseLottoTickets.merge(manualLottoTickets, automaticLottoTickets);
    }

    private static PurchaseLottoTickets inputManualLottoTickets() {
        List<List<Integer>> manualLottoNumbers = InputView.inputManualLotto()
                .stream()
                .map(numberString -> convertToIntegerList(numberString))
                .collect(Collectors.toList());

        LottoNumberGenerator lottoNumberGenerator = new ManualLottoNumberGenerator(manualLottoNumbers);
        return PurchaseLottoTickets.create(manualLottoNumbers.size(), lottoNumberGenerator);
    }

    private static PurchaseLottoTickets showAutomaticLottoTickets(LottoPayment lottoPayment) {
        LottoNumberGenerator lottoNumberGenerator = new AutomaticLottoNumberGenerator();
        return PurchaseLottoTickets.create(lottoPayment, lottoNumberGenerator);
    }

    private static void showResult(PurchaseLottoTickets purchaseLottoTickets) {
        String winningNumberString = InputView.inputWinningNumber();
        Integer bonusNumber = InputView.inputBonusNumber();

        List<Integer> winningNumbers = convertToIntegerList(winningNumberString);
        LottoResults lottoResults = purchaseLottoTickets.checkAll(WinningLottoTicket.create(winningNumbers, bonusNumber));
        OutputConsoleView.printLottoResults(lottoResults);
    }
}