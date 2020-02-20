package zunior.lotto.generator.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static zunior.lotto.generator.utils.LottoConstant.*;

public class LottoUtil {
    private static final String COMMA_DELIMITER = ",";

    private static String[] separateLineWithComma(String line) {
        return line.split(COMMA_DELIMITER);
    }

    public static List<Integer> convertToIntegerList(String line) {
        String[] separateLine = separateLineWithComma(line);
        return Arrays.stream(separateLine)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
    }
}
