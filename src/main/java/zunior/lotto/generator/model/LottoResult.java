package zunior.lotto.generator.model;

import java.util.Arrays;

public enum LottoResult {
    // 영어로 꽝을 a blank라고 한다고..
    BLANK(new Integer[]{0,1,2}),
    THREE(new Integer[]{3}),
    FOUR(new Integer[]{4}),
    FIVE(new Integer[]{5}),
    SIX(new Integer[]{6});

    private final Integer[] matchCounts;

    LottoResult(final Integer[] matchCounts) {
        this.matchCounts = matchCounts;
    }

    public Boolean hasCount(int count) {
        return Arrays.stream(matchCounts)
                .anyMatch(matchCount -> matchCount.equals(count));
    }

    public static LottoResult of(int count) {
        return Arrays.stream(LottoResult.values())
                .filter(lottoResult -> lottoResult.hasCount(count))
                .findAny()
                .orElseThrow(() -> new RuntimeException("비정상적인 결과입니다. 당첨 갯수가 6개보다 많을 수 없습니다!"));
    }
}
