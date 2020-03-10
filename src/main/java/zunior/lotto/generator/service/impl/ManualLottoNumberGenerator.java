package zunior.lotto.generator.service.impl;

import zunior.lotto.generator.model.LottoType;
import zunior.lotto.generator.service.LottoNumberGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ManualLottoNumberGenerator implements LottoNumberGenerator {

    private Queue<List<Integer>> manualLottoNumbers;

    public ManualLottoNumberGenerator(List<List<Integer>> lottoNumbers) {
        manualLottoNumbers = new LinkedList<>();
        manualLottoNumbers.addAll(lottoNumbers);
    }

    @Override
    public List<Integer> generate() {
        return manualLottoNumbers.poll();
    }

    @Override
    public LottoType getLottoType() {
        return LottoType.MANUAL;
    }
}
