package pl.allegro.typedlistadapter

import android.view.View
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TypedListViewHolderFactoryTest {

    private val viewHolderFactories: TypedListViewHolderFactory<TestItem> =
        object : TypedListViewHolderFactory<TestItem>(
            layoutId = 0,
            viewHolderClass = TestViewHolder::class.java,
            createViewHolder = { TestViewHolder(it) },
            areItemsTheSame = { first, second -> first.id == second.id },
            areContentsTheSame = { first, second -> first.message == second.message },
            getChangePayload = { _, second -> Payload.Message(second.message) },
        ) {
        }

    @Test
    fun `should check if item is not compatible`() {
        // when
        val result = viewHolderFactories.isItemCompatible(IncompatibleItem)

        // then
        assertFalse(result)
    }

    @Test
    fun `should check if item is compatible`() {
        // given
        val item = TestItem(1, "Test message")

        // when
        val result = viewHolderFactories.isItemCompatible(item)

        // then
        assertTrue(result)
    }

    @Test
    fun `should check if item are same class and are the same`() {
        // given
        val oldItem = TestItem(1, "Test message")
        val newItem = TestItem(1, "Test message")

        // when
        val result = viewHolderFactories.hasItemsTheSameClassAndAreTheSame(oldItem, newItem)

        // then
        assertTrue(result)
    }

    @Test
    fun `should get change payload`() {
        // given
        val oldItem = TestItem(1, "Test message")
        val newItem = TestItem(1, "New message")

        // when
        val result = viewHolderFactories.getChangePayload(oldItem, newItem)

        // then
        assertEquals(result, Payload.Message("New message"))
    }

    @Test
    fun `should bind viewHolder`() {
        // given
        val item = TestItem(1, "Test message")
        val viewHolder: TestViewHolder = mock()

        // when
        viewHolderFactories.bind(item, viewHolder)

        // then
        verify(viewHolder).bind(item)
    }

    @Test
    fun `should bind viewHolder with payloads`() {
        // given
        val item = TestItem(1, "Old Message")
        val viewHolder: TestViewHolder = mock()
        val payloads = mutableListOf<Any>(Payload.Message("New message"))

        // when
        viewHolderFactories.bind(item, viewHolder, payloads)

        // then
        verify(viewHolder).bind(item, payloads)
    }

    private data class TestItem(
        val id: Int,
        val message: String
    ) : TypedListItem

    private object IncompatibleItem : TypedListItem

    private open class TestViewHolder(view: View) : TypedListViewHolder<TestItem>(view) {
        override fun bind(item: TestItem) {
            // nop
        }
    }

    private sealed class Payload {
        data class Message(val message: String) : Payload()
    }
}
