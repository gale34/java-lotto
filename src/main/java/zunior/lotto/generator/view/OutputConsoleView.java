package zunior.lotto.generator.view;

import zunior.lotto.generator.model.LottoPayment;
import zunior.lotto.generator.model.LottoResult;
import zunior.lotto.generator.model.LottoResults;
import zunior.lotto.generator.model.PurchaseLottoTickets;

import java.util.Arrays;
import java.util.Map;

public class OutputConsoleView {

    private static final String resultFormat = "%d개 일치 (%d원) - %d개";
    private static final String bonusResultFormat = "%d개 일치, 보너스 볼 일치(%d원) - %d개";

    public static void printLottoTickets(PurchaseLottoTickets purchaseLottoTickets) {
        System.out.println(purchaseLottoTickets.getTicketCount() + "개를 구매했습니다.");
        purchaseLottoTickets.getLottoNumbers()
                .stream()
                .forEach(numbers -> System.out.println(Arrays.deepToString(numbers)));
        System.out.println();
    }

    public static void printLottoResults(LottoResults lottoResults, LottoPayment payment) {
        System.out.println("\n당첨 통계");
        System.out.println("---------");
        Map<LottoResult, Long> result = lottoResults.getResult();
        result.keySet()
                .stream()
                .filter(lottoResult -> !lottoResult.isBlank())
                .forEach(lottoResult -> printWinResult(result, lottoResult));
        System.out.println("총 수익률은 " + lottoResults.getProfit(payment) + "%입니다.");
    }

    private static void printWinResult(Map<LottoResult, Long> result, LottoResult lottoResult) {
        String stringFormat = lottoResult == LottoResult.FIVE_WITH_BONUS ? bonusResultFormat : resultFormat;

        System.out.println(String.format(
                stringFormat,
                lottoResult.getMatchCount(),
                lottoResult.getPrice(),
                result.get(lottoResult)));
    }
}
