package zunior.lotto.generator.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LottoUtil {
    private static final String COMMA_DELIMITER = ",";

    public static String[] separateLineWithComma(String line) {
        return line.split(COMMA_DELIMITER);
    }

    public static List<Integer> convertToIntegerList(String line) {
        String[] separateLine = separateLineWithComma(line);
        return Arrays.stream(separateLine)
                .mapToInt(number -> Integer.parseInt(number))
                .boxed()
                .collect(Collectors.toList());
    }
}
