package idv.constx.demo.common.event;

import org.springframework.context.ApplicationEvent;

public class BusinessEvent<T> extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;
	
	private String topic;

	public BusinessEvent(T source) {
		super(source);
	}
	
	public BusinessEvent(T source, String topic) {
		super(source);
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}
	
}
