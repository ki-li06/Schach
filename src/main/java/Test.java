import java.util.ArrayList;
import java.util.List;

import static util.Random.randomValue;

public class Test {
    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(22);
        integerList.add(333);
        for (int i = 0; i < 20; i++) {
            System.out.println(randomValue(integerList));
        }

    }
}
