package zunior.lotto.generator.view;

import zunior.lotto.generator.model.LottoPayment;
import zunior.lotto.generator.model.LottoResult;
import zunior.lotto.generator.model.LottoResults;
import zunior.lotto.generator.model.LottoTickets;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OutputConsoleView {

    public static void printLottoTickets(LottoTickets lottoTickets) {
        System.out.println(lottoTickets.getTicketCount() + "개를 구매했습니다.");
        List<Integer[]> test = lottoTickets.getLottoNumbers();
        test
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
                .filter(lottoResult -> !lottoResult.equals(LottoResult.BLANK))
                .forEach(lottoResult -> printWinResult(result, lottoResult));
        System.out.println("총 수익률은 " + lottoResults.getProfit(payment) + "%입니다.");
    }

    private static void printWinResult(Map<LottoResult, Long> result, LottoResult lottoResult) {
        System.out.println(lottoResult.getMatchCount()
                + "개 일치 ("
                + lottoResult.getPrice()
                + ") - "
                + result.get(lottoResult)
                + "개");
    }
}
