package zunior.lotto.generator.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LottoResults {
    private final Map<LottoResult, Long> result;

    private LottoResults(Map<LottoResult, Long> result) {
        this.result = result;
    }

    public static LottoResults create(List<LottoResult> lottoResults) {
        Map<LottoResult, Long> result = lottoResults.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return new LottoResults(result);
    }

    public Long getProfit(LottoPayment payment) {
        return Math.round(getTotalPrice() / (double) payment.getMoney());
    }

    public Map<LottoResult, Long> getResult() {
        return result;
    }

    private Long getTotalPrice() {
        return result.keySet()
                .stream()
                .mapToLong(lottoResult -> lottoResult.getPrice() * result.get(lottoResult))
                .sum();
    }
}
