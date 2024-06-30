package no.foundation.serializer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.concurrent.*;
import javax.management.AttributeList;
import no.foundation.serializer.exceptions.JsonException;

final class TypeProvider {

    static boolean isBasicType(Object value) {
        return isString(value)
                || isNumber(value)
                || isBoolean(value)
                || isTemporal(value)
                || isNull(value);
    }

    static boolean isNull(Object value) {
        return value == null;
    }

    static boolean isBoolean(Object value) {
        return value instanceof Boolean;
    }

    static boolean isNumber(Object value) {
        return value instanceof Number;
    }

    static boolean isString(Object value) {
        return value instanceof String;
    }

    static boolean isTemporal(Class<?> type) {
        return type.isInstance(Temporal.class)
                || type.isInstance(Date.class)
                || type.isInstance(TimeZone.class);
    }

    static boolean isTemporal(Object value) {
        return value instanceof Temporal
                || value instanceof Date
                || value instanceof TimeZone;
    }

    static Object convertTemporal(Class<?> type, Object value) {
        if (value instanceof String temporal) {
            try {
                if (type.equals(Date.class)) {
                    return Date.from(Instant.parse(temporal));
                } else if (type.equals(java.sql.Date.class)) {
                    return java.sql.Date.valueOf(LocalDate.parse(temporal));
                } else if (type.equals(java.sql.Time.class)) {
                    return java.sql.Time.valueOf(LocalTime.parse(temporal));
                } else if (type.equals(java.sql.Timestamp.class)) {
                    return java.sql.Timestamp.valueOf(LocalDateTime.parse(temporal));
                } else if (type.equals(LocalDate.class)) {
                    return LocalDate.parse(temporal);
                } else if (type.equals(LocalDateTime.class)) {
                    return LocalDateTime.parse(temporal);
                } else if (type.equals(Instant.class)) {
                    return Instant.parse(temporal);
                } else if (type.equals(LocalTime.class)) {
                    return LocalTime.parse(temporal);
                } else if (type.equals(ZonedDateTime.class)) {
                    return ZonedDateTime.parse(temporal);
                } else if (type.equals(OffsetDateTime.class)) {
                    return OffsetDateTime.parse(temporal);
                } else if (type.equals(Period.class)) {
                    return Period.parse(temporal);
                } else if (type.equals(Year.class)) {
                    return Year.parse(temporal);
                } else if (type.equals(YearMonth.class)) {
                    return YearMonth.parse(temporal);
                } else if (type.equals(ZoneId.class)) {
                    return ZoneId.of(temporal);
                } else if (type.equals(Month.class)) {
                    return Month.valueOf(temporal.toUpperCase());
                } else if (type.equals(MonthDay.class)) {
                    return MonthDay.parse(temporal);
                } else if (type.equals(DayOfWeek.class)) {
                    return DayOfWeek.valueOf(temporal.toUpperCase());
                } else if (type.equals(TimeZone.class)) {
                    return TimeZone.getTimeZone(temporal);
                } else if (type.equals(OffsetTime.class)) {
                    return OffsetTime.parse(temporal);
                } else if (type.equals(ZoneOffset.class)) {
                    return ZoneOffset.of(temporal);
                }
            } catch (DateTimeParseException e) {
                throw new JsonException("Can't parse string to temporal type, cause: ", e);
            }
        }
        return null;
    }

    static Number convertNumber(Number number, Class<?> type) {
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return number.intValue();
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return number.longValue();
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            return number.floatValue();
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return number.doubleValue();
        } else if (type.equals(byte.class) || type.equals(Byte.class)) {
            return number.byteValue();
        } else if (type.equals(short.class) || type.equals(Short.class)) {
            return number.shortValue();
        } else if (type.equals(BigInteger.class)) {
            return new BigInteger(number.toString());
        } else if (type.equals(BigDecimal.class)) {
            return new BigDecimal(number.toString());
        }
        return number;
    }

    static <T> Collection<Object> getCollectionInstance(Class<T> type) {
        if (type.equals(Collection.class)) {
            return new ArrayList<>();
        } else if (type.equals(List.class)) {
            return new ArrayList<>();
        } else if (type.equals(NavigableSet.class)) {
            return new TreeSet<>();
        } else if (type.equals(Set.class)) {
            return new HashSet<>();
        } else if (type.equals(LinkedList.class)) {
            return new LinkedList<>();
        } else if (type.equals(Queue.class)) {
            return new LinkedList<>();
        } else if (type.equals(Stack.class)) {
            return new Stack<>();
        } else if (type.equals(HashSet.class)) {
            return new HashSet<>();
        } else if (type.equals(LinkedHashSet.class)) {
            return new LinkedHashSet<>();
        } else if (type.equals(SortedSet.class)) {
            return new TreeSet<>();
        } else if (type.equals(PriorityQueue.class)) {
            return new PriorityQueue<>();
        } else if (type.equals(TreeSet.class)) {
            return new TreeSet<>();
        } else if (type.equals(ArrayDeque.class)) {
            return new ArrayDeque<>();
        } else if (type.equals(BlockingDeque.class)) {
            return new LinkedBlockingDeque<>();
        } else if (type.equals(BlockingQueue.class)) {
            return new LinkedBlockingDeque<>();
        } else if (type.equals(Deque.class)) {
            return new LinkedList<>();
        } else if (type.equals(TransferQueue.class)) {
            return new LinkedTransferQueue<>();
        } else if (type.equals(LinkedBlockingDeque.class)) {
            return new LinkedBlockingDeque<>();
        } else if (type.equals(ArrayBlockingQueue.class)) {
            return new ArrayDeque<>();
        } else if (type.equals(DelayQueue.class)) {
            return new LinkedBlockingDeque<>();
        } else if (type.equals(LinkedBlockingQueue.class)) {
            return new LinkedBlockingDeque<>();
        } else if (type.equals(PriorityBlockingQueue.class)) {
            return new PriorityBlockingQueue<>();
        } else if (type.equals(SynchronousQueue.class)) {
            return new SynchronousQueue<>();
        } else if (type.equals(ConcurrentLinkedQueue.class)) {
            return new ConcurrentLinkedQueue<>();
        } else if (type.equals(LinkedTransferQueue.class)) {
            return new LinkedTransferQueue<>();
        } else if (type.equals(ConcurrentLinkedDeque.class)) {
            return new ConcurrentLinkedDeque<>();
        } else if (type.equals(AbstractList.class)) {
            return new ArrayList<>();
        } else if (type.equals(CopyOnWriteArrayList.class)) {
            return new CopyOnWriteArrayList<>();
        } else if (type.equals(CopyOnWriteArraySet.class)) {
            return new CopyOnWriteArraySet<>();
        } else if (type.equals(AbstractSequentialList.class)) {
            return new ArrayList<>();
        } else if (type.equals(AttributeList.class)) {
            return new AttributeList();
        } else if (type.equals(ConcurrentSkipListSet.class)) {
            return new ConcurrentSkipListSet<>();
        } else if (type.equals(AbstractSet.class)) {
            return new HashSet<>();
        } else if (type.equals(ConcurrentHashMap.KeySetView.class)) {
            return new ConcurrentSkipListSet<>();
        } else if (type.equals(EnumSet.class)) {
            return new HashSet<>();
        }
        return null;
    }

    static <T> Map<Object, Object> getMapInstance(Class<T> type) {
        if (type.equals(Map.class)) {
            return new LinkedHashMap<>();
        } else if (type.equals(HashMap.class)) {
            return new HashMap<>();
        } else if (type.equals(TreeMap.class)) {
            return new TreeMap<>();
        } else if (type.equals(ConcurrentMap.class)) {
            return new ConcurrentHashMap<>();
        } else if (type.equals(ConcurrentHashMap.class)) {
            return new ConcurrentHashMap<>();
        } else if (type.equals(WeakHashMap.class)) {
            return new WeakHashMap<>();
        } else if (type.equals(ConcurrentSkipListMap.class)) {
            return new ConcurrentSkipListMap<>();
        } else if (type.equals(ConcurrentNavigableMap.class)) {
            return new ConcurrentSkipListMap<>();
        } else if (type.equals(NavigableMap.class)) {
            return new TreeMap<>();
        } else if (type.equals(SortedMap.class)) {
            return new TreeMap<>();
        }
        return null;
    }

    private TypeProvider() {
    }
}
