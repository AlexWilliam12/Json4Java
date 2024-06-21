package no.foundation.serializer.tree;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents a JSON array, which is a list of {@link JsonNode} elements.
 */
public final class JsonArray implements List<JsonNode>, JsonNode {

    private final List<JsonNode> values;

    /**
     * Constructs an empty JSON array.
     */
    @Contract(pure = true)
    public JsonArray() {
        values = new ArrayList<>();
    }

    public static @NotNull JsonArray of(Object @NotNull ... values) {
        JsonConverter converter = new JsonConverter();
        JsonArray array = new JsonArray();
        for (Object value : values) {
            array.add(converter.convert(value));
        }
        return array;
    }

    /**
     * Returns the underlying list of {@link JsonNode} values in this JSON array.
     *
     * @return the list of {@link JsonNode} values in this array
     */
    @Contract(pure = true)
    public List<JsonNode> getValues() {
        return values;
    }

    /**
     * Returns the number of elements in this JSON array.
     *
     * @return the number of elements in this array
     */
    @Contract(pure = true)
    @Override
    public int size() {
        return values.size();
    }

    /**
     * Checks whether this JSON array is empty.
     *
     * @return {@code true} if this array contains no elements, {@code false} otherwise
     */
    @Contract(pure = true)
    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    /**
     * Checks whether this JSON array contains the specified element.
     *
     * @param o the element to check for
     * @return {@code true} if this array contains the specified element, {@code false} otherwise
     */
    @Contract(pure = true)
    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    /**
     * Returns an iterator over the elements in this JSON array.
     *
     * @return an iterator over the elements in this array
     */
    @Override
    public @NotNull Iterator<JsonNode> iterator() {
        return values.iterator();
    }

    /**
     * Returns an array containing all the elements in this JSON array in proper sequence.
     *
     * @return an array containing all the elements in this array
     */
    @Contract(pure = true)
    @Override
    public Object @NotNull [] toArray() {
        return values.toArray();
    }

    /**
     * Returns an array containing all the elements in this JSON array in proper sequence;
     * the runtime type of the returned array is that of the specified array.
     *
     * @param a   the array into which the elements of this JSON array are to be stored
     * @param <T> the component type of the array
     * @return an array containing all the elements in this array
     */
    @Override
    public <T> T @NotNull [] toArray(T @NotNull [] a) {
        return values.toArray(a);
    }

    /**
     * Adds the specified element to the end of this JSON array.
     *
     * @param value the element to be added
     * @return {@code true} if this array changed as a result of the call, {@code false} otherwise
     */
    @Contract("_ -> true")
    @Override
    public boolean add(JsonNode value) {
        return values.add(value);
    }

    /**
     * Removes the first occurrence of the specified element from this JSON array, if it is present.
     *
     * @param value the element to be removed
     * @return {@code true} if this array contained the specified element, {@code false} otherwise
     */
    @Override
    public boolean remove(Object value) {
        return values.remove(value);
    }

    /**
     * Checks whether this JSON array contains all the elements in the specified collection.
     *
     * @param collection the collection to be checked for containment in this array
     * @return {@code true} if this array contains all the elements in the specified collection, {@code false} otherwise
     */
    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return new HashSet<>(values).containsAll(collection);
    }

    /**
     * Adds all the elements in the specified collection to this JSON array.
     *
     * @param collection the collection containing elements to be added to this array
     * @return {@code true} if this array changed as a result of the call, {@code false} otherwise
     */
    @Override
    public boolean addAll(@NotNull Collection<? extends JsonNode> collection) {
        return values.addAll(collection);
    }

    /**
     * Inserts all the elements in the specified collection into this JSON array at the specified position.
     *
     * @param index      the index at which to insert the first element from the specified collection
     * @param collection the collection containing elements to be added to this array
     * @return {@code true} if this array changed as a result of the call, {@code false} otherwise
     */
    @Override
    public boolean addAll(int index, @NotNull Collection<? extends JsonNode> collection) {
        return values.addAll(index, collection);
    }

    /**
     * Removes from this JSON array all of its elements that are contained in the specified collection.
     *
     * @param collection the collection containing elements to be removed from this array
     * @return {@code true} if this array changed as a result of the call, {@code false} otherwise
     */
    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        return values.removeAll(collection);
    }

    /**
     * Retains only the elements in this JSON array that are contained in the specified collection.
     *
     * @param collection the collection containing elements to be retained in this array
     * @return {@code true} if this array changed as a result of the call, {@code false} otherwise
     */
    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        return values.retainAll(collection);
    }

    /**
     * Removes all the elements from this JSON array.
     */
    @Override
    public void clear() {
        values.clear();
    }

    /**
     * Returns the element at the specified position in this JSON array.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this array
     */
    @Contract(pure = true)
    @Override
    public JsonNode get(int index) {
        return values.get(index);
    }

    /**
     * Replaces the element at the specified position in this JSON array with the specified element.
     *
     * @param index the index of the element to replace
     * @param value the element to be stored at the specified position
     * @return the element previously at the specified position
     */
    @Override
    public JsonNode set(int index, JsonNode value) {
        return values.set(index, value);
    }

    /**
     * Inserts the specified element at the specified position in this JSON array.
     *
     * @param index the index at which the specified element is to be inserted
     * @param value the element to be inserted
     */
    @Override
    public void add(int index, JsonNode value) {
        values.add(index, value);
    }

    /**
     * Removes the element at the specified position in this JSON array.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the array
     */
    @Override
    public JsonNode remove(int index) {
        return values.remove(index);
    }

    /**
     * Returns the index of the first occurrence of the specified element in this JSON array, or -1 if this array does not contain the element.
     *
     * @param value the element to search for
     * @return the index of the first occurrence of the specified element, or -1 if this array does not contain the element
     */
    @Override
    public int indexOf(Object value) {
        return values.indexOf(value);
    }

    /**
     * Returns the index of the last occurrence of the specified element in this JSON array, or -1 if this array does not contain the element.
     *
     * @param value the element to search for
     * @return the index of the last occurrence of the specified element, or -1 if this array does not contain the element
     */
    @Override
    public int lastIndexOf(Object value) {
        return values.lastIndexOf(value);
    }

    /**
     * Returns a list iterator over the elements in this JSON array (in proper sequence).
     *
     * @return a list iterator over the elements in this array
     */
    @Override
    public @NotNull ListIterator<JsonNode> listIterator() {
        return values.listIterator();
    }

    /**
     * Returns a list iterator over the elements in this JSON array (in proper sequence), starting at the specified position in the array.
     *
     * @param index the index of the first element to be returned from the list iterator (by a call to next)
     * @return a list iterator over the elements in this array
     */
    @Override
    public @NotNull ListIterator<JsonNode> listIterator(int index) {
        return values.listIterator(index);
    }

    /**
     * Returns a view of the portion of this JSON array between the specified {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     *
     * @param fromIndex the start index (inclusive) of the subList
     * @param toIndex   the end index (exclusive) of the subList
     * @return a view of the specified range within this array
     */
    @Override
    public @NotNull List<JsonNode> subList(int fromIndex, int toIndex) {
        return values.subList(fromIndex, toIndex);
    }

    /**
     * Returns a string representation of this JSON array.
     *
     * @return a string representation of this JSON array
     */
    @Override
    public @NotNull String toString() {
        @NotNull String result = "[]";
        if (!values.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            for (JsonNode value : values) {
                builder.append(value.toString());
                builder.append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
            builder.append("]");
            result = builder.toString();
        }
        return result;
    }
}
