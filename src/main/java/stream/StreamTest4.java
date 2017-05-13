package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 交叉组合
public class StreamTest4
{
    public static void main( String[] args )
    {
        List<String> s1 = Arrays.asList( "A", "B", "C" );
        List<String> s2 = Arrays.asList( "hello", "world", "welcome" );

        List<String> s12s2 = s1.stream().flatMap( x -> s2.stream().map( y -> x + ":" + y ) )
                .collect( Collectors.toList() );
        System.out.println( s12s2 );

        // 3个列表的笛卡尔积
        List<String> s3 = Arrays.asList( "狗", "福", "趋" );
        List<String> combines = s1.stream().flatMap( x ->
                s2.stream().flatMap( y -> s3.stream().map( z -> x + ":" + y + ":" + z ) ) )
                .collect( Collectors.toList() );
        System.out.println( combines );
    }
}
