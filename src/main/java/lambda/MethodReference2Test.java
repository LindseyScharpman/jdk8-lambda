package lambda;

import java.util.function.Consumer;
import java.util.function.Function;

public class MethodReference2Test
{
    public static void main( String[] args )
    {
        Consumer<Integer> c1 = ( x ) -> System.out.println( x );
        Consumer<Integer> c2 = MethodReference2Test::add; // c2 return int but is ignored.

        c2 = c1.andThen( x -> System.out.println( x * x ) );
        c2.accept( 4 );

        Function<Integer, Integer> c = ( x ) -> Integer.valueOf( x );
    }

    public static int add( int x )
    {
        return 0;
    }

}
