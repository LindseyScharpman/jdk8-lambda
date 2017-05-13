package stream;

import java.awt.peer.ListPeer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamLazyValTest2
{
    public static void main( String[] args )
    {
        Stream.of( Arrays.asList( 0, 1, 2 ), Arrays.asList( 3, 4 ), Arrays.asList( 5, 6, 7 ) )
                .flatMap( list -> list.stream().filter( x -> x % 2 == 0 ) )
                .collect( Collectors.toList() )
                .forEach( System.out::println );
        System.out.println( "================" );

        // lazy eval
        Stream.of( Arrays.asList( 0, 1, 2 ), Arrays.asList( 3, 4 ), Arrays.asList( 5, 6, 7 ) )
                .flatMap( list -> list.stream().filter( x -> x % 2 == 0 ) )
                .map( x -> {
                    System.out.println( "don't show" );
                    return x * x;
                } );//.collect( Collectors.toList());
        System.out.println( "================" );

        // lazy eval
        // 后面的DCVDG并没有被执行,因为findFirst找到第一个后就停止
        List<String> strs = Arrays.asList( "A", "BC", "VDF", "DCVDG" );
        strs.stream().mapToInt( x -> {
            System.out.println( "should show:" + x );
            //System.exit(0);
            return x.length();
        } ).filter( x -> x > 2 ).findFirst().ifPresent( System.out::println );

    }
}
