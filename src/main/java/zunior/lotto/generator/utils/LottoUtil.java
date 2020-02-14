package zunior.lotto.generator.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static zunior.lotto.generator.utils.LottoConstant.LOTTO_NUMBER_SIZE;

public class LottoUtil {
    private static final String COMMA_DELIMITER = ",";

    public static String[] separateLineWithComma(String line) {
        return line.split(COMMA_DELIMITER);
    }

    public static List<Integer> convertToIntegerList(String line) {
        String[] separateLine = separateLineWithComma(line);
        return Arrays.stream(separateLine)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
    }

    public static void validateLottoNumber(List<Integer> lottoNumber) {
        if (lottoNumber.size() != LOTTO_NUMBER_SIZE) {
            throw new RuntimeException("로또 숫자는 반드시 6개여야 합니다.");
        }

        if (hasLimitNumber(lottoNumber)) {
            throw new RuntimeException("로또 숫자는 0보다 크고 45보다 작은 숫자여야 합니다.");
        }
    }

    private static boolean hasLimitNumber(List<Integer> lottoNumber) {
        return lottoNumber.stream()
                .anyMatch(number -> number < 0 || number > 45);
    }
}
