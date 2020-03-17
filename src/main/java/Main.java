import zunior.lotto.generator.model.LottoPayment;
import zunior.lotto.generator.model.PurchaseLottoTickets;
import zunior.lotto.generator.model.WinningLottoTicket;
import zunior.lotto.generator.view.OutputConsoleView;

import static zunior.lotto.generator.service.LottoStore.*;

public class Main {

    public static void main(String[] args) {
        LottoPayment lottoPayment = inputLottoPayment();

        PurchaseLottoTickets purchaseLottoTickets = inputLottoTickets(lottoPayment);

        OutputConsoleView.printLottoTickets(purchaseLottoTickets);

        WinningLottoTicket winningLottoTicket = inputWinningLottoTicket();

        showResult(purchaseLottoTickets, winningLottoTicket);
    }
}