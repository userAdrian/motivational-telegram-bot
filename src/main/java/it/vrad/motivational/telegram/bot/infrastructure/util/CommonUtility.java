package it.vrad.motivational.telegram.bot.infrastructure.util;

import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionUtility;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for common operations and helpers.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtility {

    /**
     * Removes null values from a 2D array (matrix).
     *
     * @param matrix input matrix
     * @return matrix with nulls removed
     */
    public static <T> T[][] removeNullValues(T[][] matrix) {
        Objects.requireNonNull(matrix);

        return Arrays.stream(matrix)
                .map(row -> Arrays.stream(row)
                        .filter(Objects::nonNull)
                        .toArray(size -> initializeArrayFromType(row, size))
                )
                .filter(row -> row.length > 0)
                .toArray(size -> initializeMatrixFromType(matrix, size));
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] initializeArrayFromType(T[] type, int length) {
        return (T[]) Array.newInstance(type.getClass().getComponentType(), length);
    }

    @SuppressWarnings("unchecked")
    private static <T> T[][] initializeMatrixFromType(T[][] type, int length) {
        return (T[][]) Array.newInstance(type.getClass().getComponentType(), length);
    }

    /**
     * Selects a random element from a list.
     *
     * @param list input list
     * @return random element
     */
    public static <T> T selectRandomElement(List<T> list) {
        ExceptionUtility.requireNonEmpty(list);

        int index = RandomUtils.insecure().randomInt(0, list.size());

        return list.get(index);
    }

    /**
     * Gets a trimmed string element from a CSV record by header.
     *
     * @param csvRecord CSV record
     * @param header    CSV header enum
     * @return trimmed string value
     */
    public static String getElement(CSVRecord csvRecord, Enum<?> header) {
        return StringUtils.trimToEmpty(csvRecord.get(header));
    }

    /**
     * Shrinks a byte array to the specified size.
     * If the input array length is less than or equal to the target size, the original array is returned.
     * If the input array length is greater than the target size, a new array of the specified size is returned.
     *
     * @param array input array
     * @param size  target size
     * @return shrunk array or the original array if length {@literal <=} size
     */
    public static byte[] shrinkArray(byte[] array, int size) {
        if (array.length > size) {
            return Arrays.copyOf(array, size);
        }

        return array;
    }

}
