package zunior.lotto.generator.model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static zunior.lotto.generator.utils.LottoConstant.LOTTO_TICKET_PRICE;

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

    private static Map<LottoResult, Long> getDefaultResult() {
        Map<LottoResult, Long> resultWithEmptyWin = new LinkedHashMap<>();
        Arrays.stream(LottoResult.values())
                .forEach(lottoResult -> resultWithEmptyWin.put(lottoResult, 0L));
        return resultWithEmptyWin;
    }

    public Long getProfit() {
        return Math.round(getTotalPrice() * 100 / getPurchaseAmount());
    }

    public Map<LottoResult, Long> getResult() {
        Map<LottoResult, Long> result = getDefaultResult();
        result.putAll(this.result);
        return result;
    }

    private Long getTotalPrice() {
        return result.keySet()
                .stream()
                .mapToLong(lottoResult -> lottoResult.getPrice() * result.get(lottoResult))
                .sum();
    }

    private double getPurchaseAmount() {
        return (double) result.keySet()
                .stream()
                .mapToLong(lottoResult -> result.getOrDefault(lottoResult, 0L))
                .sum() * LOTTO_TICKET_PRICE;
    }
}
