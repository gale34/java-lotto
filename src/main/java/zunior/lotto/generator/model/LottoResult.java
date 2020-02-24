package zunior.lotto.generator.model;

import zunior.lotto.generator.exception.LottoResultException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static zunior.lotto.generator.utils.LottoConstant.LOTTO_MATCH_COUNT_FOR_BONUS_NUMBER;

public enum LottoResult {
    ZERO(0, 0),
    ONE(1, 0),
    TWO(2, 0),
    THREE(3, 5000),
    FOUR(4, 50000),
    FIVE(5, 1500000),
    FIVE_WITH_BONUS(5, 3000000),
    SIX(6, 2000000000);

    private final Integer matchCount;
    private final Integer price;

    private static final Map<Integer, LottoResult> lottoResults = Collections.unmodifiableMap(
            Stream.of(values())
                    .filter(lottoResult -> lottoResult == FIVE_WITH_BONUS)
                    .collect(Collectors.toMap(LottoResult::getMatchCount, Function.identity())));

    LottoResult(final Integer matchCount, final Integer price) {
        this.matchCount = matchCount;
        this.price = price;
    }

    public Boolean isBlank() {
        return price.equals(0);
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public Integer getPrice() {
        return price;
    }

    public static LottoResult of(int count, boolean hasBonusNumber) {
        if(count == LOTTO_MATCH_COUNT_FOR_BONUS_NUMBER && hasBonusNumber) {
            return FIVE_WITH_BONUS;
        }

        return Optional.ofNullable(lottoResults.get(count))
                .orElseThrow(() -> new LottoResultException("비정상적인 결과입니다. 당첨 갯수가 0보다 작거나 6개보다 많을 수 없습니다!"));
    }
}
