package zunior.lotto.generator.service;

import zunior.lotto.generator.model.LottoType;

import java.util.List;

public interface LottoNumberGenerator {
    List<Integer> generate();

    LottoType getLottoType();
}
