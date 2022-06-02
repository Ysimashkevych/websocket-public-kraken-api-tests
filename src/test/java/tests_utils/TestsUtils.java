package tests_utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestsUtils {
    public static List<String> findFirstDataMessage(List<String> messagesList){
        Predicate<String> predicate = item -> item.matches("\\[.+\\]");
        return messagesList.stream().filter(predicate).collect(Collectors.toList());
    }
}
