import zunior.lotto.generator.exception.PaymentException;
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

        WinningLottoTicket winningLottoTicket = inputWinningLottoTicket();

        showResult(purchaseLottoTickets, winningLottoTicket);
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
        if (lottoPayment == null) {
            throw new PaymentException("정상적인 금액이 아닙니다.");
        }

        LottoNumberGenerator lottoNumberGenerator = new AutomaticLottoNumberGenerator();
        return PurchaseLottoTickets.create(lottoPayment.buyLottoTicketsWithMaximum(LOTTO_TICKET_PRICE), lottoNumberGenerator);
    }

    private static WinningLottoTicket inputWinningLottoTicket() {
        String winningNumberString = InputView.inputWinningNumber();
        List<Integer> winningNumbers = convertToIntegerList(winningNumberString);
        Integer bonusNumber = InputView.inputBonusNumber();
        return WinningLottoTicket.create(winningNumbers, bonusNumber);
    }


    private static void showResult(PurchaseLottoTickets purchaseLottoTickets, WinningLottoTicket winningLottoTicket) {
        LottoResults lottoResults = purchaseLottoTickets.checkAll(winningLottoTicket);
        OutputConsoleView.printLottoResults(lottoResults);
    }
}