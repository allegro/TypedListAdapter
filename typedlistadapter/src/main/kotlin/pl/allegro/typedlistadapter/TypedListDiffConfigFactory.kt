package pl.allegro.typedlistadapter

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.recyclerview.widget.AsyncDifferConfig

class TypedListDiffConfigFactory(
    private val viewHolderFactories: List<TypedListViewHolderFactory<out TypedListItem>>
) {

    fun create(): AsyncDifferConfig<TypedListItem> {
        return AsyncDifferConfig.Builder(TypedListDiffCallback(viewHolderFactories))
            .setBackgroundThreadExecutor(backgroundThreadExecutorInstance())
            .build()
    }

    // This is the same executor as used internally across Architecture Components libraries.
    // Why it isn't used in RecyclerView library, remains a mystery. We decided to use it,
    // because unlike default RV executor, this one works flawlessly with Espresso tests.
    @SuppressLint("RestrictedApi")
    private fun backgroundThreadExecutorInstance() = ArchTaskExecutor.getIOThreadExecutor()
}