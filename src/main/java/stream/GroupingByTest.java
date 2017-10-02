package stream;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GroupingByTest
{
    public static void main( String[] args )
    {
        Student s1 = new Student( "A1", 67 );
        Student s2 = new Student( "A2", 43 );
        Student s3 = new Student( "A3", 56 );
        Student s4 = new Student( "A4", 89 );
        Student s5 = new Student( "A2", 99 );
        Student s6 = new Student( "A5", 99 );
        List<Student> students = Arrays.asList( s1, s2, s3, s4, s5, s6 );

        //分组,每个组包含该组每个人的分数
        Map<String, List<Integer>> map1 = students.stream()
                .collect( Collectors.groupingBy( Student::getName,
                        Collectors.mapping( x -> x.getScore(), Collectors.toList() ) ) );
        System.out.println( map1 );

        //分组,每个组包含该组每个人的分数
        Map<String, Long> map2 = students.stream()
                .collect( Collectors.groupingBy( Student::getName, TreeMap<String, Long>::new,
                        Collectors.counting() ) );
        System.out.println( map2 );


        // 测试groupingBy,下游收集器参数带IDENTITY_FINISH
        Map<String, List<Student>> mapT1 = students.stream()
                .collect( GroupingByTest.groupingBy( Student::getName, TreeMap::new, Collectors.toList() ) );
        System.out.println( mapT1 );


        // 测试groupingBy,下游收集器参数不带IDENTITY_FINISH
        Map<String, Long> mapT2 = students.stream()
                .collect( GroupingByTest.groupingBy( Student::getName, TreeMap<String, Long>::new,
                        Collectors.counting() ) );
        System.out.println( mapT2 );


        Map<Boolean, Long> mapT3 = students.stream()
                .collect( GroupingByTest.groupingBy( ( Student x ) -> x.getScore() > 50, TreeMap::new, Collectors.counting() ) );
        System.out.println( mapT3 );
        mapT3 = students.stream()
                .collect( Collectors.groupingBy( x -> x.getScore() > 50, TreeMap::new, Collectors.counting() ) );
        System.out.println( mapT3 );

    }

    // 自己的groupingBy
    @SuppressWarnings( "unchecked" )
    public static <T, A, K, D> MyCollectorImpl<? super T, ?, Map<K, D>> groupingBy(
            Function<? super T, K> classfier,
            Supplier<Map<K, D>> supplier,
            Collector<? super T, A, D> downStream )
    {
        Supplier<A> downSuppiler = downStream.supplier();
        BiConsumer<A, ? super T> downAcc = downStream.accumulator();
        Function<A, D> downFinisher = downStream.finisher();
        Set<Collector.Characteristics> downCS = downStream.characteristics();

        // 下游收集器只需要在上游收集器中间拦截下处理过程即可
        // T ---(classfier)---> Map<K,A> ---(下游的finisher)---> Map<K,D>
        BiConsumer<Map<K, A>, ? super T> curAcc = ( m, t ) ->
        {
            // t对应的键为key,key对应的容器里面添加t
            K key = classfier.apply( t );
            A container = m.computeIfAbsent( key, ( arbitrary ) -> downSuppiler.get() );
            downAcc.accept( container, t );
        };

        // 处理A == D的情况
        if( downCS.contains( Collector.Characteristics.IDENTITY_FINISH ) )
        {
            // 绝对的强制转换
            Supplier<Map<K, A>> curSupplier = (Supplier<Map<K, A>>) (Object) supplier;
            BinaryOperator<Map<K, A>> curCombiner = GroupingByTest.mapMerger( downStream.combiner() );
            return new MyCollectorImpl<>( curSupplier, curAcc, curCombiner, MyCollectorImpl.CH_ID );
        }
        // 处理A != D的情况
        else
        {
            Supplier<Map<K, D>> curSupplier = supplier;
            BinaryOperator<Map<K, A>> curCombiner = GroupingByTest.mapMerger( downStream.combiner() );
            // Map<K,A> ---> Map<K,D>
            Function<Map<K, A>, Map<K, D>> curFinisher = ( m ) ->
            {
                m.replaceAll( ( k, v ) -> (A) downFinisher.apply( v ) );
                return (Map<K, D>) m;
            };
            return new MyCollectorImpl( curSupplier, curAcc, curCombiner, curFinisher, downCS );
        }
    }

    public static <K, V, M extends Map<K, V>>
    BinaryOperator<M> mapMerger( BinaryOperator<V> mergeFunction )
    {
        return ( m1, m2 ) ->
        {
            for( Map.Entry<K, V> e : m2.entrySet() )
                m1.merge( e.getKey(), e.getValue(), mergeFunction );
            return m1;
        };
    }

    static class MyCollectorImpl<T, A, R> implements Collector<T, A, R>
    {
        static final Set<Collector.Characteristics> CH_CONCURRENT_ID
                = Collections.unmodifiableSet( EnumSet.of( Collector.Characteristics.CONCURRENT,
                Collector.Characteristics.UNORDERED,
                Collector.Characteristics.IDENTITY_FINISH ) );
        static final Set<Collector.Characteristics> CH_CONCURRENT_NOID
                = Collections.unmodifiableSet( EnumSet.of( Collector.Characteristics.CONCURRENT,
                Collector.Characteristics.UNORDERED ) );
        static final Set<Collector.Characteristics> CH_ID
                = Collections.unmodifiableSet( EnumSet.of( Collector.Characteristics.IDENTITY_FINISH ) );
        static final Set<Collector.Characteristics> CH_UNORDERED_ID
                = Collections.unmodifiableSet( EnumSet.of( Collector.Characteristics.UNORDERED,
                Collector.Characteristics.IDENTITY_FINISH ) );
        static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();


        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        MyCollectorImpl( Supplier<A> supplier,
                         BiConsumer<A, T> accumulator,
                         BinaryOperator<A> combiner,
                         Function<A, R> finisher,
                         Set<Characteristics> characteristics )
        {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        MyCollectorImpl( Supplier<A> supplier,
                         BiConsumer<A, T> accumulator,
                         BinaryOperator<A> combiner,
                         Set<Characteristics> characteristics )
        {
            this( supplier, accumulator, combiner, i -> (R) i, characteristics );
        }

        @Override
        public BiConsumer<A, T> accumulator()
        {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier()
        {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner()
        {
            return combiner;
        }

        @Override
        public Function<A, R> finisher()
        {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics()
        {
            return characteristics;
        }
    }
}
