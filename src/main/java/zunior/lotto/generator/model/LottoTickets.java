package zunior.lotto.generator.model;

import zunior.lotto.generator.LottoNumberGenerator;

import java.util.ArrayList;
import java.util.List;

public class LottoTickets {

    private final List<LottoTicket> tickets;

    private static final Integer LOTTO_TICKER_PRICE = 1000;

    private LottoTickets(List<LottoTicket> tickets) {
        this.tickets = tickets;
    }

    public static LottoTickets create(Payment payment, LottoNumberGenerator lottoNumberGenerator) {
        List<LottoTicket> tickets = new ArrayList<>();
        int ticketCount = payment.getLottoTicketCount(LOTTO_TICKER_PRICE);
        for(int i = 0; i < ticketCount; i++) {
            tickets.add(LottoTicket.create(lottoNumberGenerator));
        }

        return new LottoTickets(tickets);
    }

    public Integer getTicketCount() {
        return tickets.size();
    }
}
