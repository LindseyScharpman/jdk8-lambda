package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest3
{
    public static void main( String[] args )
    {
        // 转成一个List/Set
        List<String> strs = Arrays.asList( "hello world", "world welcome", "hello welcome", "welcome" );

        // 错误做法
        List<String[]> strArrayListstrs = strs.stream().map( x -> x.split( " " ) )
                .collect( Collectors.toList() );
        System.out.println( strArrayListstrs );
        System.out.println( "============" );

        // 正确做法
        Stream<String> strsList = strs.stream().flatMap( x -> Stream.of( x.split( " " ) ) );
        List<String> st = strsList.collect( Collectors.toList() );
        System.out.println( st );

        System.out.println( "============" );

        // eclipse编译器的BUG
        strs.stream()
                .flatMap( x -> Stream.of( x.split( " " ) ) )
                .collect( Collectors.toList() );
        System.out.println( st );

        st = strs.stream()
                .flatMap( x -> Arrays.stream( x.split( " " ) ) )
                .collect( Collectors.toList() );
        System.out.println( st );

        System.out.println( "=============" );
        st = strs.stream().map( x -> x.split( " " ) )
                .flatMap( x -> Arrays.stream( x ) )
                .collect( Collectors.toList() );
        System.out.println( st );
    }
}
