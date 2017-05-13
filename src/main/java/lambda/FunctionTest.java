package lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 函数式接口:
 * 1: 一个接口,并且只有一个抽象方法,(重写Object的方法并不算抽象方法,因为接口任何一个实现都会有一个有效的方法实现)
 * 2: FunctioalInterface可以让编译器自动验证是不是函数式接口(类似Override)
 * 3:函数式接口可以有: Lambda表达式,method reference,constructor references.
 * 总结:函数式接口只能有一个由我们必须要实现的方法
 */
public class FunctionTest
{

    public static void main( String[] args )
    {
        Function<Integer, Integer> square = ( x ) -> x * x;

        // high order function: x^4
        Function<Integer, Integer> fourOrder = square.compose( square );

        //andThen

        // compute the x^4 and print
        Arrays.asList( 1, 2, 3 ).forEach( x -> System.out.println( fourOrder.apply( x ) ) );

    }
}

