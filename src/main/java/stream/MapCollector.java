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

// GroupingByTest--> Set<GroupingByTest> --> Map<GroupingByTest,GroupingByTest>
// 因为Set<GroupingByTest>类型和Map<GroupingByTest,GroupingByTest>不一样,所以肯定不能设置IDENTITY_FINISH
public class MapCollector<T> implements Collector<T, Set<T>, Map<T, T>>
{
    @Override
    public Supplier<Set<T>> supplier()
    {
        return HashSet::new;
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator()
    {
        return ( set, e ) -> {
            System.out.println( "acc:" + set + "," + Thread.currentThread() );
            set.add( e );
        };
    }

    @Override
    public BinaryOperator<Set<T>> combiner()
    {
        return ( set1, set2 ) -> {
            set1.addAll( set2 );
            return set1;
        };
    }

    @Override
    public Function<Set<T>, Map<T, T>> finisher()
    {
        return ( x ) -> {
            Map<T, T> map = new HashMap<>();
            x.forEach( ( e ) -> map.put( e, e ) );
            return map;
        };
    }

    @Override
    public Set<Characteristics> characteristics()
    {
        // 因为finisher函数肯定不满足identity,所以肯定不能设置IDENTITY_FINISH,
        return Collections.unmodifiableSet( EnumSet.of( UNORDERED, CONCURRENT ) );
    }

    public static void main( String[] args )
    {
        Map<String, String> map = null;
        for( int i = 0; i < 100; ++i )
        {
            List<String> strs = Arrays.asList( "hello", "world", "welcome", "a", "b", "c", "b" );
            // map = strs.stream().collect( new MapCollector<>() );
            map = strs.parallelStream().collect( new MapCollector<>() );
        }

        System.out.println( "===================" );
        System.out.println( map );

    }
}
