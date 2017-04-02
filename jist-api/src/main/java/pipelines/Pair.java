package pipelines;

class Pair<T, K> {
    T first;
    K second;

    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }

    T getKey() {
        return first;
    }

    K getValue() {
        return second;
    }
}
