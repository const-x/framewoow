package idv.constx.demo.common.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher implements ApplicationContextAware{
 
	private  ApplicationContext appcontext;
	/**
	 * 发布一个业务事件
	 * @param event
	 */
	public  final void publishEvent(BusinessEvent<?> event){
		appcontext.publishEvent(event);
	}

	@Override
	public void setApplicationContext(ApplicationContext appcontext) throws BeansException {
		this.appcontext = appcontext;
	}
}
