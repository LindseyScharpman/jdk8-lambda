package jodaAtime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.ZoneId;
import java.util.Date;

public class TimeConvertUtil
{
    //标准的UTC时间转换成当地时区的时间
    public static Date covertUTC2Date( String utcDate )
    {
        DateTime dt = DateTime.parse( utcDate, DateTimeFormat.forPattern( "yyyy-MM-dd'T'HH:mm:ss.SSSZ" ) );
        return dt.toDate();
    }

    //当地时区的时间转换成标准的UTC时间
    public static String convertDate2UTC( Date javaUtilDate )
    {
        DateTime dt = new DateTime( javaUtilDate, DateTimeZone.UTC );
        return dt.toString();
    }

    public static String convertDate2LocalByDateFormat(Date javaUtilDate, String dateFormat){
        DateTime dt = new DateTime( javaUtilDate );
        return dt.toString(dateFormat);
    }

    public static void main( String[] args )
    {
        System.out.println( covertUTC2Date( "2017-05-17T09:47:45.411Z" ) );
        System.out.println( convertDate2UTC( new Date() ) );
        System.out.println(convertDate2LocalByDateFormat( new Date(), "yyyy-MM-dd HH:mm:ss" ));
    }
}

