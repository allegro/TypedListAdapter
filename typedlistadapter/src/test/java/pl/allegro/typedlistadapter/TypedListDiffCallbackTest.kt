package pl.allegro.typedlistadapter

import android.view.View
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TypedListDiffCallbackTest {

    private val areItemsTheSame: (TestItem, TestItem) -> Boolean = mock()
    private val areContentsTheSame: (TestItem, TestItem) -> Boolean = mock()
    private val getChangePayload: (TestItem, TestItem) -> Any = mock()
    private val isItemCompatible: ItemCompatibilityChecker<TestItem> = mock()

    private val viewHolderFactories: TypedListViewHolderFactory<TestItem> =
        object : TypedListViewHolderFactory<TestItem>(
            layoutId = 0,
            viewHolderClass = DummyViewHolder::class.java,
            createViewHolder = { DummyViewHolder(it) },
            areItemsTheSame = areItemsTheSame,
            areContentsTheSame = areContentsTheSame,
            getChangePayload = getChangePayload,
            isItemCompatible = isItemCompatible
        ) {
        }

    private val callback = TypedListDiffCallback(
        viewHolderFactories = listOf(viewHolderFactories)
    )

    @Test
    fun `contents should be the same`() {
        // given
        val oldItem = TestItem
        val newItem = TestItem
        whenever(areItemsTheSame(any(), any())).thenReturn(true)
        whenever(areContentsTheSame(any(), any())).thenReturn(true)
        whenever(isItemCompatible(any(), any())).thenReturn(true)

        // when
        callback.areContentsTheSame(oldItem, newItem)

        // then
        verify(areContentsTheSame).invoke(oldItem, newItem)
    }

    @Test
    fun `items should be the same`() {
        // given
        val oldItem = TestItem
        val newItem = TestItem
        whenever(areItemsTheSame(any(), any())).thenReturn(true)
        whenever(isItemCompatible(any(), any())).thenReturn(true)

        // when
        callback.areItemsTheSame(oldItem, newItem)

        // then
        verify(areItemsTheSame).invoke(oldItem, newItem)
    }


    @Test
    fun `should get change payload`() {
        // given
        val oldItem = TestItem
        val newItem = TestItem
        val payload: Any = mock()
        whenever(areItemsTheSame(any(), any())).thenReturn(true)
        whenever(isItemCompatible(any(), any())).thenReturn(true)
        whenever(getChangePayload(any(), any())).thenReturn(payload)

        // when
        callback.getChangePayload(oldItem, newItem)

        // then
        verify(getChangePayload).invoke(oldItem, newItem)
    }

    object TestItem : TypedListItem

    class DummyViewHolder(view: View) : TypedListViewHolder<TestItem>(view) {
        override fun bind(item: TestItem) {
            // nop
        }
    }
}
