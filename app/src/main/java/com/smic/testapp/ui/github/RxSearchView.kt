package com.smic.testapp.ui.github

import android.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxSearchView {
    fun fromView(searchView: SearchView): Observable<String> {
        val publishSubject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                publishSubject.onComplete();
                return true;
            }

            override fun onQueryTextChange(newText: String): Boolean {
                publishSubject.onNext(newText);
                return true;
            }

        })

        return publishSubject
    }
}