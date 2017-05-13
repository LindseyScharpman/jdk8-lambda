package stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorCombine
{
    public static void main( String[] args )
    {
        List<String> strs = Arrays.asList( "j203A", "hello", "welcome", "god", "hex" );

        Collections.sort( strs,
                Comparator.comparingInt( String::length )
                        .thenComparing( Comparator.comparing( String::toLowerCase ) ) );
        System.out.println( strs );

        Collections.sort( strs,
                Comparator.comparingInt( String::length )
                        .thenComparing( String.CASE_INSENSITIVE_ORDER ) );
        System.out.println( strs );


        Collections.sort( strs,
                Comparator.comparingInt( String::length )
                        .thenComparing( Comparator.comparing( String::toLowerCase, Comparator.reverseOrder() ) ) );
        System.out.println( strs );

    }
}
