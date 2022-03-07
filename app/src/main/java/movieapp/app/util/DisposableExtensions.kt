package movieapp.app.util

import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.addTo(autoDisposable: AutoDisposable) {
    autoDisposable.add(this)
}