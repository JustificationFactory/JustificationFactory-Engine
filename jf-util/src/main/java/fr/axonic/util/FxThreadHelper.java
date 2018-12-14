package fr.axonic.util;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;

public final class FxThreadHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FxThreadHelper.class);

    public static void run(Runnable runnable) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(runnable);
        } else {
            runnable.run();
        }
    }

    public static void runAndWait(Runnable runnable) {
        if (!Platform.isFxApplicationThread()) {

            final CountDownLatch doneLatch = new CountDownLatch(1);

            Runnable runnableToWaitFor = new Runnable() {

                @Override
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        doneLatch.countDown();
                    }
                }
            };
            Platform.runLater(runnableToWaitFor);

            try {
                doneLatch.await();
            } catch (InterruptedException e) {
                // DO NOTHING
            }

        } else {
            runnable.run();
        }
    }

    private static abstract class RunnableAdapter<R> implements Runnable {

        private Callable<R> callable;
        private R           result;
        private Exception   exception;

        public RunnableAdapter(Callable<R> callable) {
            this.callable = callable;
        }

        @Override
        public void run() {
            setResult(null);
            setException(null);
            try {
                setResult(callable.call());
            } catch (Exception e) {
                this.setException(e);
            } finally {
                onFinally();
            }
        }

        public abstract void onFinally();

        public R getResult() {
            return result;
        }

        private void setResult(R result) {
            this.result = result;
        }

        public Exception getException() {
            return exception;
        }

        private void setException(Exception exception) {
            this.exception = exception;
        }

    }

    public static <R> R runAndWait(Callable<R> callable) throws Exception {
        if (!Platform.isFxApplicationThread()) {

            final CountDownLatch doneLatch = new CountDownLatch(1);

            RunnableAdapter<R> runnableToWaitFor = new RunnableAdapter<R>(callable) {

                @Override
                public void onFinally() {
                    doneLatch.countDown();
                }

            };
            Platform.runLater(runnableToWaitFor);

            doneLatch.await();

            if (runnableToWaitFor.getException() != null) {
                throw runnableToWaitFor.getException();
            } else {
                return runnableToWaitFor.getResult();
            }

        } else {
            return callable.call();
        }
    }

    /**
     * This methods serves only for debug purposes and returns true if it is called outside the JavaFX thread.
     * 
     * @return
     */
    public static boolean assertNotFxThread() {
        boolean result;
        if (Platform.isFxApplicationThread()) {
            LOGGER.warn("This method should not be called in the JavaFX Application Thread. "
                    + Arrays.toString(Thread.currentThread().getStackTrace()));
            result = false;
        } else {
            result = true;
        }
        return result;
    }

}
