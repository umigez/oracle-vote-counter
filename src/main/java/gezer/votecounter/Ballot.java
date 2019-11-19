package gezer.votecounter;

import gezer.votecounter.exceptions.InvalidBallot;

import java.util.*;
import java.util.function.Consumer;

public class Ballot implements Iterable<String>, RandomAccess {
    private final List<String> delegate;

    private Ballot(List<String> delegate) throws InvalidBallot {
        this.delegate = Collections.unmodifiableList(delegate);
        validate();
    }

    public static Ballot from(String... ballot) throws InvalidBallot {
        return new Ballot(Arrays.asList(ballot));
    }

    public static Ballot from(List<String> ballot) throws InvalidBallot {
        return new Ballot(new ArrayList<>(ballot));
    }

    private void validate() throws InvalidBallot {
        if (isEmpty()) {
            throw new InvalidBallot("An empty ballot is not valid.");
        }
    }

    public String get(int index) {
        return delegate.get(index);
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public Iterator<String> iterator() {
        return delegate.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        delegate.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return delegate.spliterator();
    }
}
