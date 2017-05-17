package stream;

import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest1
{
    public static void main( String[] args )
    {
        //IntStream.iterate( 0, (x)->x+1 ).limit( 10 ).forEach( System.out::println );

        // distinct and limit 顺序问题
        // IntStream.iterate( 0, x->(x+1)%2 ).distinct().limit( 10 ).forEach( System.out::println );
        IntStream.iterate( 0, x -> ( x + 1 ) % 2 ).limit( 10 ).distinct().forEach( System.out::println );
        System.out.println( "========" );

        // max() return Optional, ifPresent is the recommended usage
        IntStream.of( 1, 2, 3, 4, 5 ).filter( x -> x > 200 ).max().ifPresent( System.out::println );
        int sum = IntStream.of( 1, 2, 3, 4, 5 ).filter( x -> x > 1 ).map( x -> x * x ).skip( 2 ).limit( 2 ).sum();
        System.out.println( sum );    //41

        // statistics
        System.out.println( IntStream.of( 1, 2, 3, 4, 5 ).sum() );
        System.out.println( IntStream.of( 1, 2, 3, 4, 5 ).filter( x -> x > 200 ).summaryStatistics().getMax() );

        Stream<Integer> s1 = Stream.of( 1, 2, 3 );
        System.out.println( s1 );
        Stream<Integer> s2 = s1.map( x -> x * x );
        System.out.println( s2 );
        Stream<Integer> s3 = s2.distinct();
        System.out.println( s3 );

        Stream<String> gen = Stream.generate( UUID.randomUUID()::toString );
        gen.findFirst().ifPresent( System.out::println );
    }
}
