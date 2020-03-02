package zunior.lotto.generator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static Scanner scanner = new Scanner(System.in);

    public static Integer inputMoney() {
        System.out.println("구입금액을 입력해 주세요.");
        final Integer money = scanner.nextInt();
        scanner.nextLine();
        return money;
    }

    public static List<String> inputManualLotto() {
        System.out.println("수동으로 구매할 로또 수를 입력해 주세요.");
        final Integer manualCount = scanner.nextInt();
        scanner.nextLine();

        System.out.println("수동으로 구매할 번호를 입력해 주세요.");
        List<String> manualLottoTickets = new ArrayList<>();
        for(int i = 0; i < manualCount; i++) {
            manualLottoTickets.add(scanner.nextLine());
        }
        
        return manualLottoTickets;
    }

    public static String inputWinningNumber() {
        System.out.println("지난 주 당첨 번호를 입력해 주세요.");
        return scanner.nextLine();
    }

    public static Integer inputBonusNumber() {
        System.out.println("보너스 번호를 입력해주세요.");
        final Integer bonusNumber = scanner.nextInt();
        scanner.nextLine();
        return bonusNumber;
    }
}
