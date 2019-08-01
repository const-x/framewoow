package idv.constx.demo.common.event;

public interface IEventHandler<T extends BusinessEvent<?>> {

	void handle(T event);
	
}
