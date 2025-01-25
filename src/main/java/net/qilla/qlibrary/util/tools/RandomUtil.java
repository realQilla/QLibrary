package net.qilla.qlibrary.util.tools;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static int between(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double between(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static float between(float min, float max) {
        return ThreadLocalRandom.current().nextFloat(min, max);
    }

    public static long between(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    public static long offset(long value, long offset) {
        return between(value - offset, value + offset);
    }

    public static float offset(float value, float offset) {
        return between(value - offset, value + offset);
    }

    public static <T> Optional<T> getRandomItem(Collection<T> collection) {
        return collection.isEmpty()
                ? Optional.empty()
                : collection.stream().skip(between(0, collection.size() - 1)).findFirst();
    }
}
