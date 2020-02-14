package zunior.lotto.generator.model;

import java.util.Arrays;

public enum LottoResult {
    // 영어로 꽝을 a blank라고 한다고..
    BLANK(new Integer[]{0,1,2}, 0),
    THREE(new Integer[]{3}, 5000),
    FOUR(new Integer[]{4}, 50000),
    FIVE(new Integer[]{5}, 1500000),
    SIX(new Integer[]{6}, 2000000000);

    private final Integer[] matchCounts;
    private final Integer price;

    LottoResult(final Integer[] matchCounts, final Integer price) {
        this.matchCounts = matchCounts;
        this.price = price;
    }

    public Boolean hasCount(int count) {
        return Arrays.stream(matchCounts)
                .anyMatch(matchCount -> matchCount.equals(count));
    }

    public Integer getMatchCount() {
        return matchCounts[0];
    }

    public Integer getPrice() {
        return price;
    }

    public static LottoResult of(int count) {
        return Arrays.stream(LottoResult.values())
                .filter(lottoResult -> lottoResult.hasCount(count))
                .findAny()
                .orElseThrow(() -> new RuntimeException("비정상적인 결과입니다. 당첨 갯수가 0보다 작거나 6개보다 많을 수 없습니다!"));
    }
}
