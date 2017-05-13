package lambda;


import java.util.function.Predicate;

public class PredicateTest {
    public static void main(String[] args) {
        Predicate<Integer> predicate = (x) -> x % 2 == 0;

        Predicate<String> isEq = Predicate.isEqual("stringTest");
        System.out.println(isEq.test("string"));
    }
}
