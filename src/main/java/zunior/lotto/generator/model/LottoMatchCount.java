package zunior.lotto.generator.model;

import static zunior.lotto.generator.utils.LottoConstant.LOTTO_WINNING_NUMBER_WITH_BONUS_FIVE;

public class LottoMatchCount {
    private final Integer matchCount;
    private final Boolean bonusMatched;

    public LottoMatchCount(Integer matchCount, Boolean bonusMatched) {
        this.matchCount = matchCount;
        this.bonusMatched = assignBonusMatched(matchCount, bonusMatched);
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public Boolean getBonusMatched() {
        return bonusMatched;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }

        LottoMatchCount target = (LottoMatchCount) obj;

        return target.matchCount.equals(matchCount) && target.bonusMatched == bonusMatched;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        int bonusMatchedInteger = bonusMatched ? 1 : 0;
        hashCode = prime * hashCode + ((matchCount == null) ? 0 : matchCount.hashCode());
        hashCode = prime * hashCode + bonusMatchedInteger;

        return hashCode;
    }

    private Boolean assignBonusMatched(Integer matchCount, Boolean bonusMatched) {
        if (matchCount.equals(LOTTO_WINNING_NUMBER_WITH_BONUS_FIVE)) {
            return bonusMatched;
        }
        return false;
    }
}
