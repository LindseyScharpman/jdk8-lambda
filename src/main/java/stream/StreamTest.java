package stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 判断一个操作是惰性求值还是及早求值很简单:只需看它的返回值。如果返回值是 Stream ,
 * 那么是惰性求值;如果返回值是另一个值或为空,那么就是及早求值。使用这些操作的理
 * 想方式就是形成一个惰性求值的链,最后用一个及早求值的操作返回想要的结果
 * 同時參考: effective java Builder 模式
 */
public class StreamTest
{
    //p51 exercise3.10 1
    // <R> Stream<R> map(Function<? super GroupingByTest, ? extends R> mapper);
    public static <I, O> List<O> mapUsingReduce( Stream<I> stream, Function<? super I, ? extends O> mapper )
    {
        return stream.reduce( new ArrayList<O>(), ( acc, x ) -> {
                    List<O> r = new ArrayList<O>( acc );
                    r.add( mapper.apply( x ) );
                    return r;
                },
                ( List<O> left, List<O> right ) -> {
                    List<O> result = new ArrayList<O>( left );
                    left.addAll( right );
                    return result;
                } );
    }

    // Stream<GroupingByTest> filter(Predicate<? super GroupingByTest> predicate);
    public static <I> List<I> filterUsingReduce( Stream<I> stream, Predicate<? super I> predicate )
    {
        //Collectors.reducing()
        return stream.reduce( new ArrayList<I>(), ( acc, x ) -> {
            List<I> r = new ArrayList<I>( acc );
            if( predicate.test( x ) )
                r.add( x );
            return r;
        }, ( List<I> left, List<I> right ) -> {
            List<I> result = new ArrayList<I>( left );
            left.addAll( right );
            return result;
        } );
    }


    public static void main( String[] args )
    {
        List<Integer> ints = Arrays.asList( 1, 2, 34, 5, 6, 78, 8 );
        System.out.println( "MY_MAP:" + mapUsingReduce( ints.stream(), ( x ) -> x % 2 == 0 ) );
        System.out.println( "MY_FILTER:" + filterUsingReduce( ints.stream(), ( x ) -> x % 2 == 0 ) );
    }
}
