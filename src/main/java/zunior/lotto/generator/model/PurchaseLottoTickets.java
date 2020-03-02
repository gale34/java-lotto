package zunior.lotto.generator.model;

import zunior.lotto.generator.exception.PaymentException;
import zunior.lotto.generator.service.LottoNumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static zunior.lotto.generator.utils.LottoConstant.LOTTO_TICKET_PRICE;

public class PurchaseLottoTickets {

    private final List<PurchaseLottoTicket> tickets;

    private PurchaseLottoTickets(List<PurchaseLottoTicket> tickets) {
        this.tickets = tickets;
    }

    public static PurchaseLottoTickets create(LottoPayment lottoPayment, LottoNumberGenerator lottoNumberGenerator) {
        if (lottoPayment == null) {
            throw new PaymentException("정상적인 금액이 아닙니다.");
        }

        int ticketCount = lottoPayment.buyLottoTicketsWithMaximum(LOTTO_TICKET_PRICE);
        return create(ticketCount, lottoNumberGenerator);
    }

    public static PurchaseLottoTickets create(int ticketCount, LottoNumberGenerator lottoNumberGenerator) {
        validate(ticketCount, lottoNumberGenerator);

        List<PurchaseLottoTicket> tickets = new ArrayList<>();
        IntStream.range(0, ticketCount)
                .forEach(i -> tickets.add(PurchaseLottoTicket.create(lottoNumberGenerator.generate(), lottoNumberGenerator.getLottoType())));
        return new PurchaseLottoTickets(tickets);
    }

    public static PurchaseLottoTickets merge(PurchaseLottoTickets... purchaseLottoTickets) {
        List<PurchaseLottoTicket> mergeResult = new ArrayList<>();
        Arrays.stream(purchaseLottoTickets)
                .forEach(lottoTickets -> mergeResult.addAll(lottoTickets.getTickets()));
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

    public Integer getAutomaticTicketCount() {
        return Math.toIntExact(tickets.stream()
                .filter(purchaseLottoTicket -> purchaseLottoTicket.getLottoType() == LottoType.AUTOMATIC)
                .count());
    }

    public Integer getManualTicketCount() {
        return Math.toIntExact(tickets.stream()
                .filter(purchaseLottoTicket -> purchaseLottoTicket.getLottoType() == LottoType.MANUAL)
                .count());
    }

    public List<Integer[]> getLottoNumbers() {
        return tickets.stream()
                .map(lottoTicket -> lottoTicket.toArray())
                .collect(Collectors.toList());
    }

    // private getter?????
    private List<PurchaseLottoTicket> getTickets() {
        return this.tickets;
    }

    private static void validate(int lottoCount, LottoNumberGenerator lottoNumberGenerator) {
        if (lottoCount < 0 || lottoNumberGenerator == null) {
            throw new RuntimeException("올바르지 않은 값들입니다.");
        }
    }
}
