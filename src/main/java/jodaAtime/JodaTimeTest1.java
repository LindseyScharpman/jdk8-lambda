package jodaAtime;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Date;

public class JodaTimeTest1
{
    // UTC时间格式(不带时区)2017-05-18T21:29:36.751Z
    public static void main( String[] args )
    {
        // 默认时区
        DateTime today = new DateTime();
        DateTime tomorrow = today.plusDays( 1 );
        System.out.println( today + "\t" + tomorrow );
        System.out.println( tomorrow.toString( "yyyy-MM-dd" ) );
        System.out.println( today.getZone() );

        DateTime first = today.withDayOfMonth( 1 );
        System.out.println( first );

        System.out.println( "--------------------" );

        LocalDate localDate = new LocalDate();
        System.out.println( localDate );
        localDate = localDate.minusMonths( 3 ).dayOfMonth().withMaximumValue();
        System.out.println( localDate );

        System.out.println( "--------------------" );

        // 计算2年前第3个月最后1天的日期
        DateTime t = new DateTime();
        DateTime t1 = t.minusYears( 2 ).withMonthOfYear( 3 ).dayOfMonth().withMaximumValue();
        System.out.println( t1 );
        DateTime t2 = t.minusYears( 2 ).monthOfYear().setCopy( 3 ).dayOfMonth().withMaximumValue();
        System.out.println( t2 );
        System.out.println( t2.equals( t1 ) );
    }
}
