package abeshutt.staracademy.event;

import java.util.*;

public abstract class Event<D, V, R, L extends Event.Listener<D, V>> {

    protected final Map<Object, List<L>> keyedListeners;
    protected final List<L> orderedListeners;

    public Event() {
        this.keyedListeners = new HashMap<>();
        this.orderedListeners = new ArrayList<>();
    }

    public abstract R invoke(D data);

    protected void subscribe(L listener) {
        this.subscribe(null, listener);
    }

    protected void subscribe(Object reference, L listener) {
        this.keyedListeners.computeIfAbsent(reference, key -> new ArrayList<>()).add(listener);
        this.orderedListeners.add(listener);
        this.orderedListeners.sort(Comparator.comparingInt(Listener::getOrder));
    }

    protected void unsubscribe(Object reference) {
        List<L> listeners = this.keyedListeners.remove(reference);
        if(listeners == null) return;
        listeners.forEach(this.orderedListeners::remove);
        this.orderedListeners.sort(Comparator.comparingInt(Listener::getOrder));
    }

    protected static abstract class Listener<K, V> {
        private final int order;

        public Listener() {
            this(0);
        }

        public Listener(int order) {
            this.order = order;
        }

        public int getOrder() {
            return this.order;
        }

        public abstract V invoke(K data);
    }

}
