package eu.europeana.corelib.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Date util classes
 *
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 *
 */
public class DateUtilsTest {

  @Test
  public void cloneTest() {
    assertNull("Should return null", DateUtils.clone(null));
    Date today = new Date();
    assertEquals("Cloned day not the same", today, DateUtils.clone(today));
  }

  @Test
  public void formatTest() {
    Date date = new Date(10000000L);
    assertTrue(StringUtils.equals(DateUtils.format(date), "1970-01-01T02:46:40.000Z"));
  }

  @Test
  public void parseTest() {
    assertEquals(10000000L, DateUtils.parse("1970-01-01T02:46:40.000Z").getTime());
  }

  @Test
  public void test_isYearOrYearRange() {
    //check all possibilities for year range value
    assertTrue(DateUtils.isYearRange("1880-1990"));
    assertFalse(DateUtils.isYearRange("2000-1990"));
    assertFalse(DateUtils.isYearRange("Geschichte 1775-1798"));
    assertFalse(DateUtils.isYearRange("Geschichte 1775"));
    assertFalse(DateUtils.isYearRange("1775 -Geschichte"));
    assertFalse(DateUtils.isYearRange("1775 -"));
    assertFalse(DateUtils.isYearRange(""));
    assertFalse(DateUtils.isYearRange(null));

    //check year
    assertTrue(DateUtils.isYear("1880"));
    assertFalse(DateUtils.isYear("188F"));
    assertFalse(DateUtils.isYear("1C80"));
    assertFalse(DateUtils.isYear("abcd"));
    assertFalse(DateUtils.isYear(""));
    assertFalse(DateUtils.isYear(null));
  }

  @Test
  public void test_isIsoDate() {
    //yyyy-MM-dd format check
    assertTrue(DateUtils.isIsoDate("2000-02-13"));
    assertFalse(DateUtils.isIsoDate("13-02-2000"));
    assertFalse(DateUtils.isIsoDate("testing-90"));
    assertFalse(DateUtils.isIsoDate("8765 testing"));
    assertFalse(DateUtils.isIsoDate(""));
    assertFalse(DateUtils.isIsoDate(null));
  }

  @Test
  public void test_isIsoDateTime() {
    //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'  Date time check
    assertTrue(DateUtils.isIsoDateTime("2017-07-26T01:00:00.000Z"));
    assertFalse(DateUtils.isIsoDateTime("2017-07-26T01:00:00Z"));
    assertFalse(DateUtils.isIsoDateTime("2017-07-2601:00:00.000Z"));
    assertFalse(DateUtils.isIsoDateTime("2017-07-26T01:00:00.000"));
    assertFalse(DateUtils.isIsoDateTime("2017-07-26T01::00.000Z"));
    assertFalse(DateUtils.isIsoDateTime(""));
    assertFalse(DateUtils.isIsoDateTime(null));
  }

  private static final int THREAD_COUNT = 50;
  private static final int ITERATIONS_PER_THREAD = 2000;

  /**
   * Hit DateUtils.format() from many threads concurrently,
   * all starting at the same instant via a CyclicBarrier,
   * to surface any shared mutable state issues
   * (e.g. a non-thread-safe SimpleDateFormat/Calendar).
   */
  @Test
  public void test_format_isThreadSafe_underConcurrentLoad() throws InterruptedException {
    // Given
    ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
    CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT);
    List<Throwable> failures = new CopyOnWriteArrayList<>();
    AtomicInteger mismatchesCount = new AtomicInteger(0);

    // Each thread gets a distinct deterministic date so we can verify
    // the correct string comes back for that thread - not just that no exception was thrown.
    List<Callable<Void>> testTasks = IntStream.range(0, THREAD_COUNT)
                                          .<Callable<Void>>mapToObj(i -> () -> {
                                            Date date = Date.from(Instant.parse("2026-01-01T00:00:00.000Z")
                                                                         .plus(i, ChronoUnit.DAYS));
                                            String expectedDate = DateUtils.format(date); // baseline, computed once before the storm

                                            barrier.await(); // release all threads at once

                                            for (int j = 0; j < ITERATIONS_PER_THREAD; j++) {
                                              String actualDate = DateUtils.format(date);
                                              if (!expectedDate.equals(actualDate)) {
                                                mismatchesCount.incrementAndGet();
                                              }
                                            }
                                            return null;
                                          }).toList();
    // When
    List<Future<Void>> futures = executor.invokeAll(testTasks);
    executor.shutdown();

    // Then
    assertTrue("Executor did not terminate in time", executor.awaitTermination(5, TimeUnit.SECONDS));

    for (Future<Void> future : futures) {
      try {
        future.get();
      } catch (ExecutionException e) {
        failures.add(e.getCause());
      }
    }

    assertTrue("Exceptions thrown during concurrent format(): " + failures, failures.isEmpty());
    assertEquals("format() returned an incorrect value under concurrent access", 0, mismatchesCount.get());
  }

  /**
   * Hit from many threads concurrently, but now doing a full round trip
   * of format -> parse -> format concurrently,
   * which exercises both DateUtils.format() and DateUtils.parse() at once.
   */
  @Test
  public void test_formatAndParse_areThreadSafe_underConcurrentLoad() throws InterruptedException {
    // Given
    ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
    CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT);
    List<Throwable> failures = new CopyOnWriteArrayList<>();
    AtomicInteger mismatchesCount = new AtomicInteger(0);

    List<Callable<Void>> testTasks = IntStream.range(0, THREAD_COUNT)
                                          .<Callable<Void>>mapToObj(i -> () -> {
                                            Date original = Date.from(Instant.parse("2026-08-18T12:30:45.123Z")
                                                                             .plus(i, ChronoUnit.HOURS));
                                            String originalFormatted = DateUtils.format(original);

                                            barrier.await();

                                            for (int j = 0; j < ITERATIONS_PER_THREAD; j++) {
                                              String dateFormatted = DateUtils.format(original);
                                              Date dateParsedBack = DateUtils.parse(dateFormatted);

                                              if (dateParsedBack == null || !dateFormatted.equals(originalFormatted)
                                                  || dateParsedBack.getTime() != original.getTime()) {
                                                mismatchesCount.incrementAndGet();
                                              }
                                            }
                                            return null;
                                          }).toList();
    // When
    List<Future<Void>> futures = executor.invokeAll(testTasks);
    executor.shutdown();

    // Then
    assertTrue("Executor did not terminate in time", executor.awaitTermination(5, TimeUnit.SECONDS));

    for (Future<Void> future : futures) {
      try {
        future.get();
      } catch (ExecutionException e) {
        failures.add(e.getCause());
      }
    }

    assertTrue("Exceptions thrown during concurrent format/parse: " + failures, failures.isEmpty());
    assertEquals("format()/parse() round-trip failed under concurrent access", 0, mismatchesCount.get());
  }

  /**
   * Use virtual threads to run a much higher number of concurrent callers cheaply
   * good for shaking out rare races conditions that need many interleaving to surface.
   */
  @Test
  public void test_format_isThreadSafe_withVirtualThreads() throws InterruptedException {
    // Given
    int virtualThreadCount = 2000;
    List<Throwable> failures = new CopyOnWriteArrayList<>();
    AtomicInteger mismatchesCount = new AtomicInteger(0);
    CountDownLatch latch = new CountDownLatch(virtualThreadCount);

    // When
    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < virtualThreadCount; i++) {
        int index = i;
        executor.submit(() -> {
          try {
            Date date = Date.from(Instant.parse("2026-01-01T00:00:00.000Z")
                                         .plus(index % 365, ChronoUnit.DAYS));
            String expectedDate = DateUtils.format(date);
            String actualDate = DateUtils.format(date);
            if (!expectedDate.equals(actualDate)) {
              mismatchesCount.incrementAndGet();
            }
          } catch (Throwable t) {
            failures.add(t);
          } finally {
            latch.countDown();
          }
        });
      }
      assertTrue("Virtual thread tasks did not complete in time", latch.await(5, TimeUnit.SECONDS));
    }

    // Then
    assertTrue("Exceptions thrown during concurrent format() on virtual threads: " + failures, failures.isEmpty());
    assertEquals("format() returned an incorrect value under virtual-thread concurrency", 0, mismatchesCount.get());
  }
}
