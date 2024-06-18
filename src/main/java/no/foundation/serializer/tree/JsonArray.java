package no.foundation.serializer.tree;

import java.util.*;

public class JsonArray extends JsonNode implements List<JsonNode> {

    private final List<JsonNode> values;

    public JsonArray(boolean isRoot) {
        super(isRoot);
        values = new ArrayList<>();
    }

    public List<JsonNode> getValues() {
        return values;
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @Override
    public Iterator<JsonNode> iterator() {
        return values.iterator();
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return values.toArray(a);
    }

    @Override
    public boolean add(JsonNode value) {
        return values.add(value);
    }

    @Override
    public boolean remove(Object value) {
        return values.remove(value);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return new HashSet<>(values).containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends JsonNode> collection) {
        return values.addAll(collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends JsonNode> collection) {
        return values.addAll(index, collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return values.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return values.retainAll(collection);
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public JsonNode get(int index) {
        return values.get(index);
    }

    @Override
    public JsonNode set(int index, JsonNode value) {
        return values.set(index, value);
    }

    @Override
    public void add(int index, JsonNode value) {
        values.add(index, value);
    }

    @Override
    public JsonNode remove(int index) {
        return values.remove(index);
    }

    @Override
    public int indexOf(Object value) {
        return values.indexOf(value);
    }

    @Override
    public int lastIndexOf(Object value) {
        return values.lastIndexOf(value);
    }

    @Override
    public ListIterator<JsonNode> listIterator() {
        return values.listIterator();
    }

    @Override
    public ListIterator<JsonNode> listIterator(int index) {
        return values.listIterator(index);
    }

    @Override
    public List<JsonNode> subList(int fromIndex, int toIndex) {
        return values.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        if (values.isEmpty()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (JsonNode value : values) {
            builder.append(value.toString());
            builder.append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append("]");
        return builder.toString();
    }
}
