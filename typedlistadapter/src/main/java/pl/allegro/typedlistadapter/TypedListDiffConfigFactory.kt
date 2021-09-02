package pl.allegro.typedlistadapter

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.recyclerview.widget.AsyncDifferConfig

class TypedListDiffConfigFactory(
    private val viewHolderFactories: List<TypedListViewHolderFactory<out TypedListItem>>
) {

    @SuppressLint("RestrictedApi") // Use same logic as in architecture components. It's all Google stuff
    fun create(): AsyncDifferConfig<TypedListItem> {
        return AsyncDifferConfig.Builder(TypedListDiffCallback(viewHolderFactories))
            .setBackgroundThreadExecutor(ArchTaskExecutor.getIOThreadExecutor())
            .build()
    }
}