package lambda;

import java.util.function.Supplier;

public class SupplierTest {
    public static void main(String[] args) {
        Supplier<Integer> supInt = () -> 1;
        System.out.println( supInt.get() );
    }
}

