package stream;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimpleStreamTest
{
    public static void main( String[] args )
    {

        //IntStream.range( 3, 9 ).forEach( (x) -> System.out.println( x ) );
        //IntStream.rangeClosed( 3, 8 ).forEach( System.out::println );

        int sum = IntStream.of( 1, 2, 3, 4 )
                .map( ( x ) -> 2 * x )
                .sum();
        System.out.println( sum );

        sum = IntStream.of( 1, 2, 3, 4 )
                .map( ( x ) -> 2 * x )
                .reduce( Integer::sum )
                .getAsInt();
        System.out.println( sum );
        System.out.println( "=========" );

        // toArray
        Stream<String> stringStream = Stream.of( "US", "UK", "CN", "AS" );
        String[] cs = stringStream.toArray( ( len ) -> {
            return new String[len];
        } );
        System.out.println( Arrays.toString( cs ) );

        stringStream = Stream.of( "US", "UK", "CN", "AS" );
        cs = stringStream.toArray( String[]::new );
        System.out.println( Arrays.toString( cs ) );
        System.out.println( "=========" );


        // toList
        stringStream = Stream.of( "US", "UK", "CN", "AS" );
        System.out.println( stringStream.collect( Collectors.toList() ) );
        stringStream = Stream.of( "US", "UK", "CN", "AS" );
        // 第3个参数是用于parallel,最终合并时候用,串行Stream操作没有影响
        ArrayList<String> r = stringStream.collect( ArrayList::new, ArrayList::add, ArrayList::addAll );
        System.out.println( r );
        stringStream = Stream.of( "US", "UK", "CN", "AS" );
        r = stringStream.collect( ArrayList::new, ( x, y ) -> x.add( y ), ( x, y ) -> {} );
        System.out.println( r );

        // toSet
        stringStream = Stream.of( "US", "UK", "CN", "AS", "AS" );
        HashSet<String> s = stringStream.collect( Collectors.toCollection( HashSet::new ) );
        System.out.println( s );

        //toString
        stringStream = Stream.of( "US", "UK", "CN", "AS", "AS" );
        String str = stringStream.collect( Collectors.joining( ":" ) );
        System.out.println( str );

    }

}
