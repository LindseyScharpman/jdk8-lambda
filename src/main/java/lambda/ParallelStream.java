package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ParallelStream {
    public static void main(String[] args) {
        int[] values = new int[10];
        Arrays.parallelSetAll( values, x -> x );
        System.out.println( Arrays.toString( values ) );

        System.out.println( parallelSumOfSquares( IntStream.of( 1, 2, 3 ) ) );
        System.out.println( multiplyThrough( Arrays.asList( 1, 2, 3, 4 ) ) );  //120
        System.out.println( slowSumOfSquares( Arrays.asList( 1, 2, 3, 4 ) ) );  //30
    }

    //p97 exercise 1
    public static int parallelSumOfSquares(IntStream range) {
        return range.parallel().map( x -> x * x )
                .sum();
    }

    //p97 exercise 2
    public static int multiplyThrough(List<Integer> linkedListOfNumbers) {
        //sequential
//        return linkedListOfNumbers.stream()
//                .reduce( 5, (acc, x) -> x * acc );

        return linkedListOfNumbers.parallelStream().reduce( 1, (acc, x) -> x * acc ) * 5;
    }

    //p97 exercise 2
    public static int slowSumOfSquares(List<Integer> linkedListOfNumbers) {
        return linkedListOfNumbers.parallelStream()
                .mapToInt( x -> x * x )
                .reduce( 0, (acc, x) -> acc + x );
    }
}
