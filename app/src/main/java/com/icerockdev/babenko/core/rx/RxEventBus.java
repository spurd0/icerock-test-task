package com.icerockdev.babenko.core.rx;


import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Roman Babenko on 17/10/17.
 */
@SuppressWarnings("unchecked")
public class RxEventBus {
    private static final RxEventBus INSTANSE = new RxEventBus();
    private final Subject mBusSubject = PublishSubject.create().toSerialized();

    public static RxEventBus getInstanse() {
        return INSTANSE;
    }

    public <T> Disposable register(final Class<T> eventClassFilter, Consumer<T> onNext,
                                   ObservableTransformer<? super T, T> transformer) {
        return mBusSubject
                .filter(Functions.isInstanceOf(eventClassFilter))
                .map(o -> (T) o)
                .compose(transformer)
                .subscribe(onNext);
    }

    public void post(Object object) {
        mBusSubject.onNext(object);
    }

}
