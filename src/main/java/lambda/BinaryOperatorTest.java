package lambda;


import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorTest
{

    public static void main( String[] args )
    {

        BinaryOperator<Integer> opAdd = ( x, y ) -> x + y;
        System.out.println( compute( 3, 5, opAdd ) );
        System.out.println( "=====================" );

        Comparator<String> cmp = ( x, y ) -> {
            if( x.length() > y.length() )
                return 1;
            else if( x.length() < y.length() )
                return -1;
            return 0;
        };
        System.out.println( min( "ab", "cdee", cmp ) );
    }

    public static int compute( int a, int b, BinaryOperator<Integer> op )
    {
        return op.apply( a, b );
    }

    public static String min( String a, String b, Comparator<String> cmp )
    {
        return BinaryOperator.minBy( cmp ).apply( a, b );
    }
}
