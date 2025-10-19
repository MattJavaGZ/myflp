package matt.pas.myflp.domain.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


public class OrderDateRangeCalculator {


    static DateRangeForOrdersSummary dateRangeToSummary(String summaryType) {
        return switch (summaryType) {
            case "currentMonth" -> getCurrentMonthDateRange();
            case "previousMonth" -> getPreviousMonthDateRange();
            case "last3Months" -> getLast3MonthsDateRange();
            case "last6Months" -> getLast6MonthsDateRange();
            case "currentYear" -> getCurrentYearDateRange();
            case "previousYear" -> getPreviousYearDateRange();
            default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        };
    }

    private static DateRangeForOrdersSummary getCurrentMonthDateRange () {
        final LocalDateTime now = LocalDateTime.now();
        final int year = now.getYear();
        final int month = now.getMonthValue();
        final int daysInMounth = now.toLocalDate().lengthOfMonth();

        return new DateRangeForOrdersSummary(
                LocalDateTime.of(year, month, 1, 0, 0), LocalDateTime.of(year, month, daysInMounth, 0, 0));
    }

    private static DateRangeForOrdersSummary getPreviousMonthDateRange () {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime nowMinus1Mounth = now.minusMonths(1);
        final int year = nowMinus1Mounth.getYear();
        final int monthValue = nowMinus1Mounth.getMonthValue();
        final int daysInMounth = nowMinus1Mounth.toLocalDate().lengthOfMonth();

        return new DateRangeForOrdersSummary(
                LocalDateTime.of(year, monthValue, 1,0,0), LocalDateTime.of(year, monthValue, daysInMounth, 1, 0)
        );
    }

    private static DateRangeForOrdersSummary getLast3MonthsDateRange () {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime nowMinus3Mounth = now.minusMonths(3);

        return new DateRangeForOrdersSummary(nowMinus3Mounth, now);
    }

    private static DateRangeForOrdersSummary getLast6MonthsDateRange (){
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime nowMinus6Mounth = now.minusMonths(6);

        return new DateRangeForOrdersSummary(nowMinus6Mounth, now);
    }

    private static DateRangeForOrdersSummary getCurrentYearDateRange (){
        final LocalDateTime now = LocalDateTime.now();
        final int year = now.getYear();

        return new DateRangeForOrdersSummary(
                LocalDateTime.of(year, 1, 1, 0, 0), LocalDateTime.of(year, 12, 31, 0, 0)
        );
    }

    private static DateRangeForOrdersSummary getPreviousYearDateRange (){
        final LocalDateTime now = LocalDateTime.now();
        final int year = now.getYear() - 1;

        return new DateRangeForOrdersSummary(
                LocalDateTime.of(year, 1, 1, 0, 0), LocalDateTime.of(year, 12, 31, 0, 0)
        );
    }
}
