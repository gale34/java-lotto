package zunior.lotto.generator.model;

import zunior.lotto.generator.exception.LottoResultException;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LottoResult {
    ZERO(new LottoMatchCount(0, false), 0),
    ONE(new LottoMatchCount(1, false), 0),
    TWO(new LottoMatchCount(2, false),0),
    THREE(new LottoMatchCount(3, false),5000),
    FOUR(new LottoMatchCount(4, false),50000),
    FIVE(new LottoMatchCount(5, false), 1500000),
    FIVE_WITH_BONUS(new LottoMatchCount(5, true), 3000000),
    SIX(new LottoMatchCount(6, false), 2000000000);

    private final LottoMatchCount lottoMatchCount;
    private final Integer price;

    private static final Map<LottoMatchCount, LottoResult> LOTTO_RESULTS = Collections.unmodifiableMap(
            Stream.of(values())
                    .collect(Collectors.toMap(LottoResult::getLottoMatchCount, Function.identity())));

    LottoResult(final LottoMatchCount lottoMatchCount, final Integer price) {
        this.lottoMatchCount = lottoMatchCount;
        this.price = price;
    }

    public Boolean isBlank() {
        return price.equals(0);
    }

    public LottoMatchCount getLottoMatchCount() {
        return lottoMatchCount;
    }

    public Integer getMatchCount() {
        return lottoMatchCount.getMatchCount();
    }

    public Boolean getBonusMatched() {
        return lottoMatchCount.getBonusMatched();
    }

    public Integer getPrice() {
        return price;
    }

    public static LottoResult of(int count, boolean hasBonusNumber) {
        return Optional.ofNullable(LOTTO_RESULTS.get(new LottoMatchCount(count, hasBonusNumber)))
                .orElseThrow(() -> new LottoResultException("비정상적인 결과입니다. 당첨 갯수가 0보다 작거나 6개보다 많을 수 없습니다!"));
    }
}
