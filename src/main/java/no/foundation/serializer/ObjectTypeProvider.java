package no.foundation.serializer;

import no.foundation.serializer.exceptions.ObjectConverterException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.management.AttributeList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.concurrent.*;

/**
 * Utility class that provides methods to identify and convert basic object types.
 * This class is final and cannot be subclassed.
 */
final class ObjectTypeProvider {

    /**
     * Determines if the given object is of a basic type.
     * Basic types include: String, Number, Boolean, Temporal, and null.
     *
     * @param value the object to check.
     * @return true if the object is of a basic type, false otherwise.
     */
    static boolean isBasicType(final Object value) {
        return isString(value)
                || isNumber(value)
                || isBoolean(value)
                || isTemporal(value)
                || isNull(value);
    }

    /**
     * Determines if the given object is null.
     *
     * @param value the object to check.
     * @return true if the object is null, false otherwise.
     */
    static boolean isNull(final Object value) {
        return value == null;
    }

    /**
     * Determines if the given object is a Boolean.
     *
     * @param value the object to check.
     * @return true if the object is a Boolean, false otherwise.
     */
    static boolean isBoolean(final Object value) {
        return value instanceof Boolean;
    }

    /**
     * Determines if the given object is a Number.
     *
     * @param value the object to check.
     * @return true if the object is a Number, false otherwise.
     */
    static boolean isNumber(final Object value) {
        return value instanceof Number;
    }

    /**
     * Determines if the given object is a String.
     *
     * @param value the object to check.
     * @return true if the object is a String, false otherwise.
     */
    static boolean isString(final Object value) {
        return value instanceof String;
    }

    /**
     * Determines if the given object is a temporal type.
     * Temporal types include instances of Temporal, Date, and TimeZone.
     *
     * @param value the object to check.
     * @return true if the object is a temporal type, false otherwise.
     */
    static boolean isTemporal(final Object value) {
        return value instanceof Temporal
                || value instanceof Date
                || value instanceof TimeZone;
    }

    /**
     * Converts a String representation of a temporal value to the specified temporal type.
     * Supported types include various java.time and java.sql temporal types.
     *
     * @param type  the target temporal type class.
     * @param value the String representation of the temporal value.
     * @return the converted temporal object, or null if the conversion fails.
     * @throws ObjectConverterException if the conversion fails due to a parsing error.
     */
    @SuppressWarnings("IfCanBeSwitch")
    static Object convertTemporal(final Class<?> type, final Object value) {
        Object result = null;
        if (value instanceof String temporal) {
            try {
                if (type.equals(Date.class)) {
                    result = Date.from(Instant.parse(temporal));
                } else if (type.equals(java.sql.Date.class)) {
                    result = java.sql.Date.valueOf(LocalDate.parse(temporal));
                } else if (type.equals(java.sql.Time.class)) {
                    result = java.sql.Time.valueOf(LocalTime.parse(temporal));
                } else if (type.equals(java.sql.Timestamp.class)) {
                    result = java.sql.Timestamp.valueOf(LocalDateTime.parse(temporal));
                } else if (type.equals(LocalDate.class)) {
                    result = LocalDate.parse(temporal);
                } else if (type.equals(LocalDateTime.class)) {
                    result = LocalDateTime.parse(temporal);
                } else if (type.equals(Instant.class)) {
                    result = Instant.parse(temporal);
                } else if (type.equals(LocalTime.class)) {
                    result = LocalTime.parse(temporal);
                } else if (type.equals(ZonedDateTime.class)) {
                    result = ZonedDateTime.parse(temporal);
                } else if (type.equals(OffsetDateTime.class)) {
                    result = OffsetDateTime.parse(temporal);
                } else if (type.equals(Period.class)) {
                    result = Period.parse(temporal);
                } else if (type.equals(Year.class)) {
                    result = Year.parse(temporal);
                } else if (type.equals(YearMonth.class)) {
                    result = YearMonth.parse(temporal);
                } else if (type.equals(ZoneId.class)) {
                    result = ZoneId.of(temporal);
                } else if (type.equals(Month.class)) {
                    result = Month.valueOf(temporal.toUpperCase());
                } else if (type.equals(MonthDay.class)) {
                    result = MonthDay.parse(temporal);
                } else if (type.equals(DayOfWeek.class)) {
                    result = DayOfWeek.valueOf(temporal.toUpperCase());
                } else if (type.equals(TimeZone.class)) {
                    result = TimeZone.getTimeZone(temporal);
                } else if (type.equals(OffsetTime.class)) {
                    result = OffsetTime.parse(temporal);
                } else if (type.equals(ZoneOffset.class)) {
                    result = ZoneOffset.of(temporal);
                }
            } catch (DateTimeParseException e) {
                throw new ObjectConverterException(e);
            }
        }
        return result;
    }

    /**
     * Converts a Number to the specified target numeric type.
     * Supported types include various primitive and wrapper numeric types, BigInteger, and BigDecimal.
     *
     * @param number the number to convert.
     * @param type   the target numeric type class.
     * @return the converted number.
     */
    @SuppressWarnings("IfCanBeSwitch")
    static Number convertNumber(final Number number, @NotNull final Class<?> type) {
        Number result = number;
        if (type.equals(int.class) || type.equals(Integer.class)) {
            result = number.intValue();
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            result = number.longValue();
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            result = number.floatValue();
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            result = number.doubleValue();
        } else if (type.equals(byte.class) || type.equals(Byte.class)) {
            result = number.byteValue();
        } else if (type.equals(short.class) || type.equals(Short.class)) {
            result = number.shortValue();
        } else if (type.equals(BigInteger.class)) {
            result = new BigInteger(number.toString());
        } else if (type.equals(BigDecimal.class)) {
            result = new BigDecimal(number.toString());
        }
        return result;
    }

    /**
     * Retrieves an instance of a collection based on the specified type.
     * Returns null if no matching collection type is found.
     *
     * @param type the class type representing the desired collection.
     * @param <T>  the type of elements that the collection can hold.
     * @return an instance of the collection specified by {@code type}, or null if no match is found.
     */
    @SuppressWarnings("IfCanBeSwitch")
    public static <T> @Nullable Collection<Object> getCollectionInstance(@NotNull final Class<T> type) {
        @Nullable Collection<Object> result = null;
        if (type.equals(Collection.class)) {
            result = new ArrayList<>();
        } else if (type.equals(List.class)) {
            result = new ArrayList<>();
        } else if (type.equals(NavigableSet.class)) {
            result = new TreeSet<>();
        } else if (type.equals(Set.class)) {
            result = new HashSet<>();
        } else if (type.equals(LinkedList.class)) {
            result = new LinkedList<>();
        } else if (type.equals(Queue.class)) {
            result = new LinkedList<>();
        } else if (type.equals(Stack.class)) {
            result = new Stack<>();
        } else if (type.equals(HashSet.class)) {
            result = new HashSet<>();
        } else if (type.equals(LinkedHashSet.class)) {
            result = new LinkedHashSet<>();
        } else if (type.equals(SortedSet.class)) {
            result = new TreeSet<>();
        } else if (type.equals(PriorityQueue.class)) {
            result = new PriorityQueue<>();
        } else if (type.equals(TreeSet.class)) {
            result = new TreeSet<>();
        } else if (type.equals(ArrayDeque.class)) {
            result = new ArrayDeque<>();
        } else if (type.equals(BlockingDeque.class)) {
            result = new LinkedBlockingDeque<>();
        } else if (type.equals(BlockingQueue.class)) {
            result = new LinkedBlockingDeque<>();
        } else if (type.equals(Deque.class)) {
            result = new LinkedList<>();
        } else if (type.equals(TransferQueue.class)) {
            result = new LinkedTransferQueue<>();
        } else if (type.equals(LinkedBlockingDeque.class)) {
            result = new LinkedBlockingDeque<>();
        } else if (type.equals(ArrayBlockingQueue.class)) {
            result = new ArrayDeque<>();
        } else if (type.equals(DelayQueue.class)) {
            result = new LinkedBlockingDeque<>();
        } else if (type.equals(LinkedBlockingQueue.class)) {
            result = new LinkedBlockingDeque<>();
        } else if (type.equals(PriorityBlockingQueue.class)) {
            result = new PriorityBlockingQueue<>();
        } else if (type.equals(SynchronousQueue.class)) {
            result = new SynchronousQueue<>();
        } else if (type.equals(ConcurrentLinkedQueue.class)) {
            result = new ConcurrentLinkedQueue<>();
        } else if (type.equals(LinkedTransferQueue.class)) {
            result = new LinkedTransferQueue<>();
        } else if (type.equals(ConcurrentLinkedDeque.class)) {
            result = new ConcurrentLinkedDeque<>();
        } else if (type.equals(AbstractList.class)) {
            result = new ArrayList<>();
        } else if (type.equals(CopyOnWriteArrayList.class)) {
            result = new CopyOnWriteArrayList<>();
        } else if (type.equals(CopyOnWriteArraySet.class)) {
            result = new CopyOnWriteArraySet<>();
        } else if (type.equals(AbstractSequentialList.class)) {
            result = new ArrayList<>();
        } else if (type.equals(AttributeList.class)) {
            result = new AttributeList();
        } else if (type.equals(Vector.class)) {
            result = new Vector<>();
        } else if (type.equals(ConcurrentSkipListSet.class)) {
            result = new ConcurrentSkipListSet<>();
        } else if (type.equals(AbstractSet.class)) {
            result = new HashSet<>();
        } else if (type.equals(ConcurrentHashMap.KeySetView.class)) {
            result = new ConcurrentSkipListSet<>();
        } else if (type.equals(EnumSet.class)) {
            result = new HashSet<>();
        }
        return result;
    }

    /**
     * Retrieves an instance of a map based on the specified type.
     * Returns null if no matching map type is found.
     *
     * @param type the class type representing the desired map.
     * @param <T>  the type of keys and values that the map can hold.
     * @return an instance of the map specified by {@code type}, or null if no match is found.
     */
    @SuppressWarnings("IfCanBeSwitch")
    public static <T> @Nullable Map<Object, Object> getMapInstance(@NotNull final Class<T> type) {
        @Nullable Map<Object, Object> result = null;
        if (type.equals(Map.class)) {
            result = new LinkedHashMap<>();
        } else if (type.equals(HashMap.class)) {
            result = new HashMap<>();
        } else if (type.equals(TreeMap.class)) {
            result = new TreeMap<>();
        } else if (type.equals(ConcurrentMap.class)) {
            result = new ConcurrentHashMap<>();
        } else if (type.equals(ConcurrentHashMap.class)) {
            result = new ConcurrentHashMap<>();
        } else if (type.equals(WeakHashMap.class)) {
            result = new WeakHashMap<>();
        } else if (type.equals(ConcurrentSkipListMap.class)) {
            result = new ConcurrentSkipListMap<>();
        } else if (type.equals(ConcurrentNavigableMap.class)) {
            result = new ConcurrentSkipListMap<>();
        } else if (type.equals(NavigableMap.class)) {
            result = new TreeMap<>();
        } else if (type.equals(SortedMap.class)) {
            result = new TreeMap<>();
        }
        return result;
    }
}