package matt.pas.myflp.domain.order;

import java.time.LocalDateTime;

public record DateRangeForOrdersSummary(
        LocalDateTime start,
        LocalDateTime end
) {
}
