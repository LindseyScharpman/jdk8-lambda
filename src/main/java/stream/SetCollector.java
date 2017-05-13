package stream;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collector.Characteristics.UNORDERED;

public class SetCollector<T> implements Collector<T, Set<T>, Set<T>>
{
    @Override
    public Supplier<Set<T>> supplier()
    {
        System.out.println( "supplier invoked!" );
        return HashSet::new;
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator()
    {
        // Set::add //注意:不可以使用HashSet::add
        System.out.println( "accumulator invoked!" );
        return ( x, y ) -> x.add( y );
    }

    @Override
    public BinaryOperator<Set<T>> combiner()
    {
        System.out.println( "combiner invoked!" );
        return ( x, y ) -> {
            y.addAll( x );
            return y;
        };
    }

    @Override
    public Function<Set<T>, Set<T>> finisher()
    {
        // Function.identity();
        System.out.println( "finisher invoked!" );
        return x -> x;
    }

    @Override
    public Set<Characteristics> characteristics()
    {
        // 下面2行代码也可以,不过一般不这么做
        // Set<Characteristics> c = new HashSet<>();
        // c.add( Characteristics.IDENTITY_FINISH );

        // 如果设置了IDENTITY_FINISH,则不会调用finisher函数,直接强制转换
        // CONCURRENT表示对同一个容器并发访问
        // return Collections.unmodifiableSet( EnumSet.of( IDENTITY_FINISH, UNORDERED ) );
        return Collections.unmodifiableSet( EnumSet.of( UNORDERED, CONCURRENT ) );
    }

    public static void main( String[] args )
    {
        List<String> strs = Arrays.asList( "hello", "world", "welcome", "a", "b", "c", "b" );
        Set<String> set = strs.stream().collect( new SetCollector<>() );

        // 下面的这一行代码也可以,因为我们自定义的Collector就是HashSet
        // Collectors.toSet()内部也是HashSet
        // HashSet<String> set = (HashSet<String>) strs.stream().collect( new SetCollector<String>() );

        System.out.println( set );
    }
}
