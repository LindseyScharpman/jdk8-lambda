package lambda;

import java.util.function.Function;
import java.util.function.IntToDoubleFunction;


public class RecursiveLambda {

    public static void main(String[] args) {

        IntToDoubleFunction[] foo = {null};
        foo[0] = x -> (x == 0) ? 1 : x * foo[0].applyAsDouble( x - 1 );
        System.out.println( foo[0].applyAsDouble( 10 ) );

        // fuck generics
        Function<Integer, Integer>[] fuckRecusive = new Function[1];
        fuckRecusive[0] = (x) -> x == 1 ? 1 : x * (fuckRecusive[0]).apply( x - 1 );
        System.out.println( fuckRecusive[0].apply( 10 ) );
    }
}
