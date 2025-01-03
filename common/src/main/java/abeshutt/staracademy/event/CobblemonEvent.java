package abeshutt.staracademy.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.reactive.Observable;
import com.cobblemon.mod.common.api.reactive.ObservableSubscription;
import kotlin.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CobblemonEvent<T> extends ConsumerEvent<CobblemonEvent.Payload<T>, CobblemonEvent.Listener<T>> {

    private final Observable<T> source;
    private final List<ObservableSubscription<T>> listeners;

    public CobblemonEvent(Observable<T> source) {
        this.source = source;
        this.listeners = new ArrayList<>();

        for(Priority priority : Priority.values()) {
            this.listeners.add(this.source.subscribe(priority, event -> {
                this.invoke(new Payload<>(event, priority));
                return Unit.INSTANCE;
            }));
        }
    }

    public void subscribe(Priority priority, Consumer<T> listener) {
        this.subscribe(new Listener<>(listener, priority));
    }

    public void subscribe(int order, Priority priority, Consumer<T> listener) {
        this.subscribe(new Listener<>(order, listener, priority));
    }

    public void subscribe(Object reference, Priority priority, Consumer<T> listener) {
        this.subscribe(reference, new Listener<>(listener, priority));
    }

    public void subscribe(Object reference, int order, Priority priority, Consumer<T> listener) {
        this.subscribe(reference, new Listener<>(order, listener, priority));
    }

    public void unsubscribe(Object reference) {
        super.unsubscribe(reference);
    }

    public void dispose() {
        this.listeners.forEach(this.source::unsubscribe);
    }

    protected static class Payload<T> {
        private final T data;
        private final Priority priority;

        public Payload(T data, Priority priority) {
            this.data = data;
            this.priority = priority;
        }

        public T getData() {
            return this.data;
        }

        public Priority getPriority() {
            return this.priority;
        }
    }

    protected static class Listener<T> extends ConsumerEvent.Listener<Payload<T>> {
        private final Priority priority;

        public Listener(Consumer<T> function, Priority priority) {
            super(payload -> function.accept(payload.getData()));
            this.priority = priority;
        }

        public Listener(int order, Consumer<T> function, Priority priority) {
            super(order, payload -> function.accept(payload.getData()));
            this.priority = priority;
        }

        @Override
        public Void invoke(Payload<T> data) {
            if(data.getPriority() == this.priority) {
                return super.invoke(data);
            }

            return null;
        }
    }

}
