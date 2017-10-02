package fuzzy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WQS on 2017/10/2.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class StreamDiffLoop {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("hello2");
        list.add("world");

        // 只会打印hello, 因为流是短路的,找到第一个后后面的都不会执行
        list.stream().mapToInt(x -> {
            System.out.println(x);
            return x.length();
        }).filter(x -> x == 5)
                .findFirst()
                .ifPresent(System.out::println);
    }
}
