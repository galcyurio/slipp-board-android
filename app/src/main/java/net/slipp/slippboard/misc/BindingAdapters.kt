package net.slipp.slippboard.misc

import android.databinding.BindingAdapter
import android.support.v4.widget.ContentLoadingProgressBar

@BindingAdapter("isLoading")
fun bindIsLoading(view: ContentLoadingProgressBar, isLoading: Boolean) {
    when (isLoading) {
        true -> view.show()
        false -> view.hide()
    }
}