package jodaAtime;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class JavaTimeTest
{
    public static void main( String[] args )
    {
        LocalDate localDate = LocalDate.now();
        System.out.println( localDate );
        System.out.println( localDate.getYear() + ":" + localDate.getMonthValue() + ":" + localDate.getDayOfMonth() );
        System.out.println( "----------------" );

        localDate = LocalDate.of( 2017, 2, 3 );
        System.out.println( localDate );
        MonthDay md1 = MonthDay.of( 3, 8 );
        MonthDay mdFrom = MonthDay.from( LocalDate.of( 2011, 3, 8 ) );
        System.out.println( md1 );
        System.out.println( mdFrom );
        System.out.println( md1.equals( mdFrom ) );
        System.out.println( "----------------" );

        LocalTime lt = LocalTime.now();
        System.out.println( lt );
        lt = lt.plus( 3, ChronoUnit.HOURS );
        System.out.println( lt );
        System.out.println( "----------------" );

        Clock clock = Clock.systemDefaultZone();
        System.out.println( clock );
        ZoneId zoneId = ZoneId.of( "Asia/Shanghai" );
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println( ldt );
        // 带有时区信息
        ZonedDateTime zdt = ZonedDateTime.of( ldt, zoneId );
        System.out.println( zdt );
        System.out.println( "----------------" );

        YearMonth ym = YearMonth.now();
        System.out.println( ym );
        ym = ym.minusMonths( 3 );
        System.out.println( ym.lengthOfMonth() );
        System.out.println( ym.lengthOfYear() );
        System.out.println( ym.isLeapYear() );
        System.out.println(ldt.toLocalDate().isLeapYear());
        System.out.println( "----------------" );

        //UTC时间
        Instant instant = Instant.now();
        System.out.println(instant);
    }
}
