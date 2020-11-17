package eu.europeana.corelib.utils;


import java.util.function.Supplier;

/**
 * This class provides a thread-safe generic implementation of the lazy initialization
 * pattern.
 *
 * The initialization logic needs to be implemented by subclasses in the {@code initialize()} method.
 * Access to the data object is provided through the {@code get()} method.
 */
public abstract class LazyInitializer<T> implements Supplier<T> {
    private T value;

    // used to track initialization state as initialize() can return null
    private boolean isInitialized = false;

    @Override
    public T get() {
        if (!isInitialized) {
            synchronized (this) {
                if (!isInitialized) {
                    isInitialized = true;
                    value = initialize();
                }
            }
        }

        return value;
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
