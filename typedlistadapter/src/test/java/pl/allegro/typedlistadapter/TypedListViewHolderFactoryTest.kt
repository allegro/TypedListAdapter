package pl.allegro.typedlistadapter

import android.view.View
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TypedListViewHolderFactoryTest {

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

    @Test
    fun `should check if item is compatible`() {
        // given
        val item = TestItem
        whenever(isItemCompatible(any(), any())).thenReturn(true)

        // when
        viewHolderFactories.isItemCompatible(item)

        // then
        verify(isItemCompatible).invoke(TestItem::class.java, item)
    }

    @Test
    fun `should check if item are same class and are the same`() {
        // given
        val oldItem = TestItem
        val newItem = TestItem
        whenever(areItemsTheSame(any(), any())).thenReturn(true)

        // when
        viewHolderFactories.hasItemsTheSameClassAndAreTheSame(oldItem, newItem)

        // then
        verify(areItemsTheSame).invoke(oldItem, newItem)
    }

    @Test
    fun `should get change payload`() {
        // given
        val payload: Any = mock()
        val oldItem = TestItem
        val newItem = TestItem
        whenever(areItemsTheSame(any(), any())).thenReturn(true)
        whenever(getChangePayload(any(), any())).thenReturn(payload)

        // when
        viewHolderFactories.getChangePayload(oldItem, newItem)

        // then
        verify(getChangePayload).invoke(oldItem, newItem)
    }

    @Test
    fun `should bind viewHolder`() {
        // given
        val item = TestItem
        val viewHolder: DummyViewHolder = mock()

        // when
        viewHolderFactories.bind(item, viewHolder)

        // then
        verify(viewHolder).bind(item)
    }

    @Test
    fun `should bind viewHolder with payloads`() {
        // given
        val payload: Any = mock()
        val item = TestItem
        val viewHolder: DummyViewHolder = mock()
        val payloads = mutableListOf(payload)

        // when
        viewHolderFactories.bind(item, viewHolder, payloads)

        // then
        verify(viewHolder).bind(item, payloads)
    }

    object TestItem : TypedListItem

    open class DummyViewHolder(view: View) : TypedListViewHolder<TestItem>(view) {
        override fun bind(item: TestItem) {
            // nop
        }
    }
}
