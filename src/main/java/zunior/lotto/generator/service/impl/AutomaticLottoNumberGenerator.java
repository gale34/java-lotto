package zunior.lotto.generator.service.impl;

import zunior.lotto.generator.service.LottoNumberGenerator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static zunior.lotto.generator.utils.LottoConstant.*;

public class AutomaticLottoNumberGenerator implements LottoNumberGenerator {

    private static List<Integer> lottoNumbers;

    static {
        lottoNumbers = IntStream.rangeClosed(LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER)
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> generate() {
        Collections.shuffle(lottoNumbers);
        return IntStream.range(0, LOTTO_NUMBER_SIZE)
                .map(i -> lottoNumbers.get(i))
                .boxed()
                .collect(Collectors.toList());
    }
}
