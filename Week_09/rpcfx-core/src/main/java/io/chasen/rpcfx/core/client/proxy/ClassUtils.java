package io.chasen.rpcfx.core.client.proxy;

import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

public class ClassUtils {

    /**
     * Get all interfaces from the specified type
     *
     * @param type             the specified type
     * @return non-null read-only {@link Set}
     * @since 2.7.6
     */
    public static Set<Class<?>> getAllInterfaces(Class<?> type) {
        if (type == null || type.isPrimitive()) {
            return emptySet();
        }

        Set<Class<?>> allInterfaces = new LinkedHashSet<>();
        Set<Class<?>> resolved = new LinkedHashSet<>();
        Queue<Class<?>> waitResolve = new LinkedList<>();

        resolved.add(type);
        Class<?> clazz = type;
        while (clazz != null) {

            Class<?>[] interfaces = clazz.getInterfaces();

            if (interfaces != null && interfaces.length != 0) {
                // add current interfaces
                Arrays.stream(interfaces)
                        .filter(resolved::add)
                        .forEach(cls -> {
                            allInterfaces.add(cls);
                            waitResolve.add(cls);
                        });
            }

            // add all super classes to waitResolve
            getAllSuperClasses(clazz)
                    .stream()
                    .filter(resolved::add)
                    .forEach(waitResolve::add);

            clazz = waitResolve.poll();
        }
        return allInterfaces;
    }

    /**
     * Get all super classes from the specified type
     *
     * @param type         the specified type
     * @return non-null read-only {@link Set}
     * @since 2.7.6
     */
    public static Set<Class<?>> getAllSuperClasses(Class<?> type) {

        Set<Class<?>> allSuperClasses = new LinkedHashSet<>();

        Class<?> superClass = type.getSuperclass();
        while (superClass != null) {
            // add current super class
            allSuperClasses.add(superClass);
            superClass = superClass.getSuperclass();
        }

        return unmodifiableSet(allSuperClasses);
    }
}
