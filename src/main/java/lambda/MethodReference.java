package lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 四种方法引用
 * 类名:静态方法名
 * 对象引用名:实例方法名
 * 类名:实例方法名(此种方式会把lambda表达式的第一个参数作为方法调用的对象,其余参数作为该方法的其他参数)
 * 构造器引用
 */
public class MethodReference {
    public static void main(String[] args) {
        List<String> strs = Arrays.asList( "abc", "def", "aef" );

        Collections.sort( strs, (x, y) -> x.compareToIgnoreCase( y ) );
        System.out.println( strs );

        //类名:静态方法名
        Collections.sort( strs, MethodReference::staticCmpStr );
        System.out.println( strs );

        //对象引用名:实例方法名
        Collections.sort( strs, new MethodReference()::instanceCmpStr );
        System.out.println( strs );

        //类名:实例方法名
        Collections.sort( strs, String::compareToIgnoreCase );
        System.out.println( strs );

        //构造器引用
        System.out.println( getString( "hw", String::new ) );
    }


    public static int staticCmpStr(String s1, String s2) {
        return s1.compareToIgnoreCase( s2 );
    }

    public int instanceCmpStr(String s1, String s2) {
        return s1.compareToIgnoreCase( s2 );
    }

    public static String getString(String str, Function<String, String> function) {
        return function.apply( str );
    }
}
