package no.foundation.serializer;

import no.foundation.serializer.exceptions.ObjectConverterException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.TimeZone;

class ObjectTypeProvider {

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

    static boolean isTemporal(Object value) {
        return value instanceof Temporal
                || value instanceof Date
                || value instanceof TimeZone;
    }

    @SuppressWarnings("IfCanBeSwitch")
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
                throw new ObjectConverterException(e);
            }
        }
        return null;
    }

    @SuppressWarnings("IfCanBeSwitch")
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
}
