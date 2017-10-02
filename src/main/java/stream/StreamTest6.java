package stream;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Stream的collect方法是可变操作
 * Stream的reduce方法是不可变操作
 */
public class StreamTest6
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

        // TODO 区别
        System.out.println( "count:" + students.stream().count() );
        System.out.println( "count:" + students.parallelStream().collect( Collectors.counting() ) );

        // TODO 区别
        // collect是可变
        Optional<Student> minS = students.stream().min( Comparator.comparingInt( x -> x.getScore() ) );
        minS.ifPresent( System.out::println );
        minS = students.stream().collect( Collectors.minBy( Comparator.comparingInt( x -> x.getScore() ) ) );
        minS.ifPresent( System.out::println );

        DoubleSummaryStatistics ds = students.stream().
                collect( Collectors.summarizingDouble( x -> x.getScore() ) );
        System.out.println( ds.toString() );

        String allName = students.stream()
                .map( x -> x.getName() )
                .collect( Collectors.joining( ",", "[", "]" ) );
        System.out.println( allName );


        // 多重分组/分区
        System.out.println( "=======================" );
        Map<Integer, List<Student>> m = students.stream().collect( Collectors.groupingBy( x -> x.getScore() ) );
        System.out.println( m );
        Map<Integer, Map<String, List<Student>>> map = students.stream()
                .collect( Collectors.groupingBy( x -> x.getScore(),
                        Collectors.groupingBy( x -> x.getName() ) ) );
        System.out.println( map );

        Map<Integer, Map<Boolean, List<Student>>> map1 = students.stream()
                .collect( Collectors.groupingBy( x -> x.getScore(),
                        Collectors.partitioningBy( x -> x.getName().equals( "A2" ) ) ) );
        System.out.println( map1 );

        Map<Boolean, Long> map3 = students.stream().collect(
                Collectors.partitioningBy( x -> x.getScore() > 60,
                        Collectors.counting() ) );
        System.out.println( map3 );

        System.out.println( "=========================" );
        Map<String, Student> map4 = students.stream()
                .collect( Collectors.groupingBy( x -> x.getName(),
                        Collectors.collectingAndThen( Collectors.minBy(
                                Comparator.comparingInt( x -> x.getScore() ) ), x -> x.get() ) ) );
        System.out.println( map4 );


        Map<String, Optional<Student>> map5 = students.stream().collect(
                Collectors.groupingBy( x -> x.getName(),
                        Collectors.minBy( Comparator.comparingInt( x -> x.getScore() ) ) ) );
        System.out.println( map5 );

        Map<String, Integer> map6 = students.stream()
                .collect( Collectors.groupingBy( x -> x.getName(),
                        Collectors.collectingAndThen(
                                Collectors.minBy( Comparator.comparingInt( x -> x.getScore() ) ), x -> x.get().getScore() ) ) );
        System.out.println( map6 );


        // 性能测试
        benchMarkForCollectAndReduce();

    }

    public static void benchMarkForCollectAndReduce()
    {
        List<String> x = new ArrayList<>();
        for( int i = 0; i < 100000; ++i )
            x.add( "" + i );

        long s = System.nanoTime();
        String r = x.stream().reduce( "", ( x1, y ) -> x1.concat( y ) );
        System.out.println( "Stream.collect()消耗时间" + TimeUnit.NANOSECONDS.toSeconds( System.nanoTime() - s ) + "s" );

        s = System.nanoTime();
        String r2 = x.stream().collect( Collectors.joining( "" ) );
        System.out.println( "Stream.collect()消耗时间" + TimeUnit.NANOSECONDS.toSeconds( System.nanoTime() - s ) + "s" );

        assert r.length() == r2.length();

    }
}
