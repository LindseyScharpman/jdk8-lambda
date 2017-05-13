package stream;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * groupBy
 * partitionBy(groupBy的特殊情况)
 */
public class StreamTest5
{
    public static void main( String[] args )
    {
        Student s1 = new Student( "A1", 67 );
        Student s2 = new Student( "A2", 43 );
        Student s3 = new Student( "A3", 56 );
        Student s4 = new Student( "A4", 89 );
        Student s5 = new Student( "A2", 99 );
        List<Student> students = Arrays.asList( s1, s2, s3, s4, s5 );


        Map<String, List<Student>> map = students.stream()
                .collect( Collectors.groupingBy( Student::getName ) );
        System.out.println( map );

        // 用groupBy实现partitionBy
        Map<Boolean, List<Student>> map2 = students.stream()
                .collect( Collectors.groupingBy( x -> x.getScore() > 60 ) );
        System.out.println( map2 );
        map2 = students.stream().collect( Collectors.partitioningBy( x -> x.getScore() > 60 ) );
        System.out.println( map2 );

        // 分2个区,并求出每个区的平均分
        Map<Boolean, Double> map3 = students.stream()
                .collect( Collectors.partitioningBy( x -> x.getScore() > 60,
                        Collectors.averagingDouble( Student::getScore ) ) );
        System.out.println( map3 );
        // 分2个区,并求出每个区的统计数据
        Map<Boolean, DoubleSummaryStatistics> map31 = students.stream()
                .collect( Collectors.partitioningBy( x -> x.getScore() > 60,
                        Collectors.summarizingDouble( x -> x.getScore() ) ) );
        System.out.println( map31 );

        //分组,每个组包含该组每个人的分数
        Map<String, List<Integer>> map4 = students.stream()
                .collect( Collectors.groupingBy( Student::getName,
                        Collectors.mapping( x -> x.getScore(), Collectors.toList() ) ) );
        System.out.println( map4 );

    }
}
