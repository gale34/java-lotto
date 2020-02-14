package zunior.lotto.generator.model;

import java.util.Arrays;
import java.util.LinkedHashMap;
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
        return payment.calculateProfit(getTotalPrice());
    }

    public Map<LottoResult, Long> getResult() {
        Map<LottoResult, Long> resultWithEmptyWin = new LinkedHashMap<>();
        Arrays.stream(LottoResult.values())
                .forEach(lottoResult -> resultWithEmptyWin.put(lottoResult, 0L));
        resultWithEmptyWin.putAll(result);
        return resultWithEmptyWin;
    }

    private Long getTotalPrice() {
        return result.keySet()
                .stream()
                .mapToLong(lottoResult -> lottoResult.getPrice() * result.get(lottoResult))
                .sum();
    }
}
