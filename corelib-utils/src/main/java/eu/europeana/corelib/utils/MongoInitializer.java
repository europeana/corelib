package eu.europeana.corelib.utils;

import java.util.function.Supplier;


/**
 * This class provides a generic implementation via Java functions
 *
 * The initialization logic needs to be implemented by subclasses in the {@code initialize()} method.
 * Access to the data object is provided through the {@code get()} method.
 */
public abstract class MongoInitializer<T> implements Supplier<T> {

    @Override
    public T get() {
        return initialize();
    }

    /**
     * Creates and initializes the object managed by this class.
     * This method is called by {@link #get()} when the object
     * is accessed for the first time.
     *
     * @return the managed object, or null if object cannot be initialized
     */
    protected abstract T initialize();
}
