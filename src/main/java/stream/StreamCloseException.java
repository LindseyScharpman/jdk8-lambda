package stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class StreamCloseException
{
    public static void main( String[] args )
    {
        List<String> strs = Arrays.asList( "A", "B", "C" );

//        try( Stream<String> stream = strs.stream() )
//        {
//            stream.onClose( () -> {
//                System.out.println( "first" );
//            } ).onClose( () -> {
//                System.out.println( "second" );
//            } ).forEach( System.out::println );
//        }
//        System.out.println( "===========" );


//        try( Stream<String> stream = strs.stream() )
//        {
//            stream.onClose( () -> {
//                System.out.println( "first" );
//                throw new RuntimeException( "first" );
//            } ).onClose( () -> {
//                System.out.println( "second" );
//                throw new NullPointerException( "second" );
//            } ).forEach( System.out::println );
//        }
//        System.out.println( "===========" );


        try( Stream<String> stream = strs.stream() )
        {
            RuntimeException theSameException = new RuntimeException( "same" );
            stream.onClose( () -> {
                System.out.println( "first" );
                throw theSameException;
            } ).onClose( () -> {
                System.out.println( "second" );
                throw theSameException;
            } ).forEach( System.out::println );
        }

    }
}
