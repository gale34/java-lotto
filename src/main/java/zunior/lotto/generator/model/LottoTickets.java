package zunior.lotto.generator.model;

import zunior.lotto.generator.service.LottoNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static zunior.lotto.generator.utils.LottoUtil.validateLottoNumber;

public class LottoTickets {

    private final List<LottoTicket> tickets;

    private static final Integer LOTTO_TICKET_PRICE = 1000;

    private LottoTickets(List<LottoTicket> tickets) {
        this.tickets = tickets;
    }

    public static LottoTickets create(LottoPayment lottoPayment, LottoNumberGenerator lottoNumberGenerator) {
        List<LottoTicket> tickets = new ArrayList<>();
        int ticketCount = lottoPayment.getLottoTicketCount(LOTTO_TICKET_PRICE);
        for (int i = 0; i < ticketCount; i++) {
            tickets.add(LottoTicket.create(lottoNumberGenerator));
        }
        return new LottoTickets(tickets);
    }

    public LottoResults checkAll(List<Integer> winningNumbers) {
        validateLottoNumber(winningNumbers);
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
}
