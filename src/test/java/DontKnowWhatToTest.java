import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class DontKnowWhatToTest {
    @Test
    public void setToArrayTest(){
        HashSet<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        String[] array = set.toArray(new String[0]);
        Arrays.stream(array).forEach(System.out::println);
    }

    @Test
    public void testAssignableFrom(){
        Class<Integer> integerClass = Integer.class;
        Integer i = new Integer(1000);
        Class<? extends Integer> aClass = i.getClass();
        System.out.println(aClass==integerClass);
        System.out.println(aClass.isAssignableFrom(integerClass));
    }
}
