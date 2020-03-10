package zunior.lotto.generator.model;

import zunior.lotto.generator.service.LottoNumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PurchaseLottoTickets {

    private final List<PurchaseLottoTicket> tickets;

    private PurchaseLottoTickets(List<PurchaseLottoTicket> tickets) {
        this.tickets = tickets;
    }

    public static PurchaseLottoTickets create(int ticketCount, LottoNumberGenerator lottoNumberGenerator) {
        validate(ticketCount, lottoNumberGenerator);

        List<PurchaseLottoTicket> tickets = new ArrayList<>();
        IntStream.range(0, ticketCount)
                .forEach(i -> tickets.add(PurchaseLottoTicket.create(lottoNumberGenerator)));
        return new PurchaseLottoTickets(tickets);
    }

    public static PurchaseLottoTickets merge(PurchaseLottoTickets base, PurchaseLottoTickets... purchaseLottoTickets) {
        List<PurchaseLottoTicket> mergeResult = new ArrayList<>();
        mergeResult.addAll(base.tickets);
        Arrays.stream(purchaseLottoTickets)
                .forEach(lottoTickets -> mergeResult.addAll(lottoTickets.tickets));
        return new PurchaseLottoTickets(mergeResult);
    }

    public LottoResults checkAll(WinningLottoTicket winningNumbers) {
        List<LottoResult> lottoResults = tickets.stream()
                .map(lottoTicket -> lottoTicket.check(winningNumbers))
                .collect(Collectors.toList());
        return LottoResults.create(lottoResults);
    }

    public Integer getAllTicketCount() {
        return tickets.size();
    }

    public Integer getTicketCountByLottoType(LottoType lottoType) {
        return Math.toIntExact(tickets.stream()
                .filter(purchaseLottoTicket -> purchaseLottoTicket.equalsLottoType(lottoType))
                .count());
    }

    public List<Integer[]> getLottoNumbers() {
        return tickets.stream()
                .map(LottoTicket::toArray)
                .collect(Collectors.toList());
    }

    private static void validate(int lottoCount, LottoNumberGenerator lottoNumberGenerator) {
        if (lottoCount < 0 || lottoNumberGenerator == null) {
            throw new RuntimeException("올바르지 않은 값들입니다.");
        }
    }
}
