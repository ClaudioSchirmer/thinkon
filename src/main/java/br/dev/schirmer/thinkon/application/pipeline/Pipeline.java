package br.dev.schirmer.thinkon.application.pipeline;

import br.dev.schirmer.thinkon.application.exceptions.ApplicationNotificationException;
import br.dev.schirmer.thinkon.domain.exceptions.DomainNotificationException;
import br.dev.schirmer.thinkon.infrastructure.exceptions.InfrastructureNotificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class Pipeline {
    private final Map<String, Handler<?, ?>> handlerMap;

    @Autowired
    public Pipeline(Map<String, Handler<?, ?>> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public <TResult, TRequest extends Request<TResult>> Result<?> dispatch(TRequest request) {
        try {
            return new Result.Success<>(getHandler(request).invoke(request));
        } catch (DomainNotificationException e) {
            return new Result.Failure(e.getNotifications());
        } catch (InfrastructureNotificationException e) {
            return new Result.Failure(e.getNotifications());
        } catch (ApplicationNotificationException e) {
            return new Result.Failure(e.getNotifications());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Result.ExceptionResult(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <TResult, TRequest extends Request<TResult>> Handler<TResult, TRequest> getHandler(TRequest request) {
        String className = request.getClass().getSimpleName();
        String handlerKey = className.substring(0,1).toLowerCase() + className.substring(1) + "Handler";
        return (Handler<TResult, TRequest>) handlerMap.get(handlerKey);
    }

}
