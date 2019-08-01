package idv.constx.demo.common.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


/**
 * 事件处理器
 */
@Component
public class EventListener  implements ApplicationContextAware{
    
	private  ApplicationContext appcontext;
	
	@TransactionalEventListener(phase= TransactionPhase.AFTER_COMMIT,fallbackExecution=true)
    public void onApplicationEvent(BusinessEvent<?> event) {
    	IEventHandler handler = (IEventHandler) appcontext.getBean(event.getTopic());
    	handler.handle(event);
    }

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.appcontext = 	arg0;	
	}
}
