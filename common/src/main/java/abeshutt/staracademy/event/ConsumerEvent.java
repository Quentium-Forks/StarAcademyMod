package abeshutt.staracademy.event;

import java.util.function.Consumer;

public class ConsumerEvent<D, L extends ConsumerEvent.Listener<D>> extends Event<D, Void, D, L> {

    @Override
    public D invoke(D data) {
        for(L listener : this.orderedListeners) {
           listener.invoke(data);
        }

        return data;
    }

    public static class Listener<D> extends Event.Listener<D, Void> {
        private final Consumer<D> function;

        public Listener(Consumer<D> function) {
            super();
            this.function = function;
        }

        public Listener(int order, Consumer<D> function) {
            super(order);
            this.function = function;
        }

        @Override
        public Void invoke(D data) {
            this.function.accept(data);
            return null;
        }
    }

}
