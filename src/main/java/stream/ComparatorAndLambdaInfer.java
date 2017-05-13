package stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ComparatorAndLambdaInfer
{
    public static void main( String[] args )
    {
        List<String> strs = Arrays.asList( "j203A", "hello", "welcome", "god" );

        Comparator<String> c = Comparator.comparingInt( String::length );
        c = Comparator.comparingInt( x -> x.length() );
        Collections.sort( strs, c );
        System.out.println( strs );

        c = c.reversed();
        Collections.sort( strs, c );
        System.out.println( strs );

        c = c.thenComparing( String::compareTo );
        Collections.sort( strs, c );
        System.out.println( strs );

        Collections.sort( strs, Comparator.comparingInt( x -> x.length() ) );
        System.out.println( strs );

        // 无法进行类型推断
        Collections.sort( strs, Comparator.comparingInt( ( String x ) -> x.length() ).reversed() );
        System.out.println( strs );

        Collections.sort( strs, Comparator.comparingInt( String::length ).reversed() );
        System.out.println( strs );

    }
}
