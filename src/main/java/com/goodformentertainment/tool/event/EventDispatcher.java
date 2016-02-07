package com.goodformentertainment.tool.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.apache.log4j.Logger;

public class EventDispatcher {
    private static final Logger LOG = Logger.getLogger(EventDispatcher.class);

    private static EventDispatcher instance;

    public static EventDispatcher getInstance() {
        if (instance == null) {
            instance = new EventDispatcher();
        }
        return instance;
    }

    public static void registerListener(final EventListener listener) {
        getInstance().register(listener);
    }

    public static void unregisterListener(final EventListener listener) {
        getInstance().unregister(listener);
    }

    public static void dispatchEvent(final Event event) {
        getInstance().dispatch(event);
    }

    private final Collection<EventListener> listeners;

    public EventDispatcher() {
        listeners = Collections.synchronizedCollection(new LinkedList<>());
    }

    public void register(final EventListener listener) {
        listeners.add(listener);
    }

    public void unregister(final EventListener listener) {
        listeners.remove(listener);
    }

    public void dispatch(final Event event) {
        for (final EventListener listener : listeners) {
            final Collection<Method> methods = findMatchingEventHandlerMethods(listener,
                    event.getClass());
            for (final Method method : methods) {
                try {
                    // // Make sure the method is accessible for private methods
                    // method.setAccessible(true);

                    if (method.getParameterTypes().length == 0) {
                        method.invoke(listener);
                    }
                    if (method.getParameterTypes().length == 1) {
                        method.invoke(listener, event);
                    }
                    if (method.getParameterTypes().length == 2) {
                        method.invoke(listener, this, event);
                    }
                } catch (final Exception e) {
                    LOG.error("Error dispatching event to " + listener, e);
                }
            }
        }
    }

    private static Collection<Method> findMatchingEventHandlerMethods(final EventListener listener,
            final Class<? extends Event> eventType) {
        final Method[] methods = listener.getClass().getDeclaredMethods();
        final Collection<Method> result = new ArrayList<>();
        for (final Method method : methods) {
            if (canHandleEvent(method, eventType)) {
                result.add(method);
            }
        }
        return result;
    }

    private static boolean canHandleEvent(final Method method,
            final Class<? extends Event> eventType) {
        final HandleEvent handleEventAnnotation = method.getAnnotation(HandleEvent.class);
        if (handleEventAnnotation != null) {
            final Class<? extends Event>[] values = handleEventAnnotation.type();
            return Arrays.asList(values).contains(eventType);
        }
        return false;
    }
}
