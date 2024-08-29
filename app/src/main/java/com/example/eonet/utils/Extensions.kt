package com.example.eonet.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

fun <T> PublishSubject<T>.observe(
    lifecycleOwner: LifecycleOwner,
    onNext: (T) -> Unit
): Disposable {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { item ->
            // Post the item to the main thread
            onNext(item)
        }.apply {
            lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    if (!isDisposed) {
                        dispose()
                    }
                }
            })
        }
}

fun <T> Observable<T>.observe(
    lifecycleOwner: LifecycleOwner,
    onNext: (T) -> Unit
): Disposable {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { item ->
            // Post the item to the main thread
            onNext(item)
        }.apply {
            lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    if (!isDisposed) {
                        dispose()
                    }
                }
            })
        }
}

// Extension function to divert errors to a PublishSubject
fun <T> Observable<T>.divertErrors(errorSubject: PublishSubject<Throwable>, fallbackValue: T? = null): Observable<T> {
    return this.onErrorResumeNext { throwable: Throwable ->
        // Divert the error to the PublishSubject
        errorSubject.onNext(throwable)

        // Decide whether to emit a fallback value or complete the stream
        if (fallbackValue != null) {
            Observable.just(fallbackValue)
        } else {
            Observable.empty()
        }
    }
}