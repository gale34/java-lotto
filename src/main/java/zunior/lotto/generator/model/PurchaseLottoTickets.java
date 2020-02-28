package zunior.lotto.generator.model;

import zunior.lotto.generator.service.LottoNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PurchaseLottoTickets {

    private final List<PurchaseLottoTicket> tickets;

    private static final Integer LOTTO_TICKET_PRICE = 1000;

    private PurchaseLottoTickets(List<PurchaseLottoTicket> tickets) {
        this.tickets = tickets;
    }

    public static PurchaseLottoTickets create(LottoPayment lottoPayment, LottoNumberGenerator lottoNumberGenerator) {
        validate(lottoPayment, lottoNumberGenerator);

        List<PurchaseLottoTicket> tickets = new ArrayList<>();
        int ticketCount = lottoPayment.getLottoTicketCount(LOTTO_TICKET_PRICE);
        IntStream.range(0, ticketCount)
                .forEach(i -> tickets.add(PurchaseLottoTicket.create(lottoNumberGenerator.generate())));
        return new PurchaseLottoTickets(tickets);
    }

    public LottoResults checkAll(WinningLottoTicket winningNumbers) {
        List<LottoResult> lottoResults = tickets.stream()
                .map(lottoTicket -> lottoTicket.check(winningNumbers))
                .collect(Collectors.toList());
        return LottoResults.create(lottoResults);
    }

    public Integer getTicketCount() {
        return tickets.size();
    }

    public List<Integer[]> getLottoNumbers() {
        return tickets.stream()
                .map(lottoTicket -> lottoTicket.toArray())
                .collect(Collectors.toList());
    }

    private static void validate(LottoPayment lottoPayment, LottoNumberGenerator lottoNumberGenerator) {
        if(lottoPayment == null || lottoNumberGenerator == null) {
            throw new RuntimeException("올바르지 않은 값들입니다.");
        }
    }
}
