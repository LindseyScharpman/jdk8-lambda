package fuzzy;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

// http://stackoverflow.com/questions/30952893/weird-implementation-of-tryadvance-in-spliterator-ofint/30975719#30975719
// Spliterator.java 668行
public class TypeProblem
{
    public static void main( String[] args )
    {
        Consumer<Integer> consumer = ( x ) -> System.out.println( x );
        IntConsumer intConsumer = ( int x ) -> System.out.println( x );


        System.out.println( consumer + ":" + intConsumer );

        System.out.println( "consumer instanceof Consumer ? " + ( consumer instanceof Consumer ) );
        System.out.println( "intConsumer instanceof Consumer ? " + ( intConsumer instanceof Consumer ) );
        System.out.println();


        test( consumer );
        // test(intConsumer); // compile error
        test( intConsumer::accept );  // ok
        test( consumer::accept );  // ok

        System.out.println( "===========" );
        Consumer mix = new X();
        test( mix );
        System.out.println( "===========" );

        List<Integer> strs = Arrays.asList( 1, 2, 3, 4, 5 );
        strs.stream().forEach( System.out::println );
        strs.stream().map( x -> x + "xxx" ).forEach( System.out::println );

    }

    // 很可能一个consumer实现了Consumer接口和IntConsumer接口
    // 这样instanceof语句就可以通过,然后直接强转
    public static void test( Consumer<? super Integer> consumer )
    {

        if( consumer instanceof IntConsumer )
        {
            System.out.println( "actually is IntConsumer!" );
            ( consumer ).accept( 100 );
        }
        else
        {
            System.out.println( "actually NOT IntConsumer!" );
            ( (IntConsumer) consumer::accept ).accept( 100 );
        }
    }

    static class X implements IntConsumer, Consumer<Integer>
    {

        @Override
        public void accept( Integer integer )
        {
            System.out.println( "call WRAP" );
            System.out.println( integer );
        }

        @Override
        public void accept( int value )
        {
            System.out.println( "call primitive" );
            System.out.println( value );
        }
    }
}
