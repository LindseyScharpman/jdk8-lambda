package stream;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

public class CollectorCharacteristics<T> implements Collector<T, Set<T>, Map<T, T>>
{
    @Override
    public Supplier<Set<T>> supplier()
    {
        return () -> {
            // 如果是并行流且没有设置CONCURRENT属性,则会创建多个容器
            // 如果是并行流并且设置了CONCURRENT属性 或者 不是并行流 则只会创建一个容器
            System.out.println( "----验证并行流&CONCURRENT 属性之间的关系----" );
            return new HashSet<>();
        };
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator()
    {
        return ( set, e ) -> {
            // 如果设置了CONCURRENT属性,则在accumulator一定不能出现使用set的场景,除了set.add(e)
            // 比如下面的打印set,由于设置了CONCURRENT属性,因此在遍历的时候,别的线程可能正在添加元素,这样就会出现并发修改异常
            System.out.println( "acc:" + set + "," + Thread.currentThread().getName() );
            //System.out.println( "acc:" + "," + Thread.currentThread().getName() );
            set.add( e );
        };
    }

    @Override
    public BinaryOperator<Set<T>> combiner()
    {
        return ( set1, set2 ) -> {
            System.out.println( "!!! combiner is called when  parallelStream & NOT set CONCURRENT !!!\n" + Thread.currentThread().getName() );
            System.out.println( "set1:" + set1 );
            System.out.println( "set2:" + set2 );
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
        // 因为finisher函数肯定不满足identity,所以肯定不能设置IDENTITY_FINISH
        // 设置IDENTITY_FINISH属性,表示不调用finisher函数,直接强制转换A->R
        // 设置CONCURRENT属性,表示使用同一个容器,即使是并行流parallelStream也是如此,因为只使用一个容器,所以不会调用combiner函数
        return Collections.unmodifiableSet( EnumSet.of( UNORDERED ) );
    }

    public static void main( String[] args )
    {
        Map<String, String> map = null;
        List<String> strs = Arrays.asList( "hello", "world", "welcome", "a", "b", "c", "b" );
        System.out.println( Runtime.getRuntime().availableProcessors() );


        for( int i = 0; i < 1; ++i )
        {
            //map = strs.stream().collect( new CollectorCharacteristics<>() );
            map = strs.parallelStream().collect( new CollectorCharacteristics<>() );
            System.out.println();

        }

        System.out.println( "===================" );
        System.out.println( map );

    }
}
