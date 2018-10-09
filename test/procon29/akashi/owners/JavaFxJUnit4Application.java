package procon29.akashi.owners;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class JavaFxJUnit4Application extends Application {

    // JUnitの複数スレッド対策用Lockオブジェクト
    private static final ReentrantLock LOCK = new ReentrantLock();

    // JavaFXアプリがスタートしたか（JavaFXの初期化が完了したか）のフラグ
    private static AtomicBoolean started = new AtomicBoolean();

    public static void startJavaFx() {
        try {
            LOCK.lock();

            if (!started.get()) {
                //　JavaFX初期化用のスレッドワーカー作成
                final ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> JavaFxJUnit4Application.launch());

                // JavaFX初期化完了まで待つ。
                while (!started.get()) {
                    Thread.yield();
                }
            }
        } finally {
            LOCK.unlock();
        }
    }

    protected static void launch() {
        Application.launch();
    }

    @Override
    public void start(final Stage stage) {
        started.set(Boolean.TRUE);
    }
}