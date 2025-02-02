package events;

public class Event<TData> {
    private final EventType type;
    private final TData data;

    public Event(EventType type, TData data) {
        this.type = type;
        this.data = data;
    }

    public EventType getType(){
        return type;
    }

    public TData getData(){
        return data;
    }
}
