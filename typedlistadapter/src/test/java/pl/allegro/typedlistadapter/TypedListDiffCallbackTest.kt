package pl.allegro.typedlistadapter

import android.view.View
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class TypedListDiffCallbackTest {

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

    private val callback = TypedListDiffCallback(
        viewHolderFactories = listOf(viewHolderFactories)
    )

    @Test
    fun `contents should be the same`() {
        // given
        val oldItem = TestItem(1, "Test message", true)
        val newItem = TestItem(1, "Test message", true)

        // when
        val result = callback.areContentsTheSame(oldItem, newItem)

        // then
        assertTrue(result)
    }

    @Test
    fun `items should be different`() {
        // given
        val oldItem = TestItem(1, "Test message", true)
        val newItem = TestItem(2, "Other message", true)

        // when
        val result = callback.areItemsTheSame(oldItem, newItem)

        // then
        assertFalse(result)
    }

    @Test
    fun `items should be the same`() {
        // given
        val oldItem = TestItem(1, "Test message", true)
        val newItem = TestItem(1, "Test message", true)

        // when
        val result = callback.areItemsTheSame(oldItem, newItem)

        // then
        assertTrue(result)
    }

    @Test
    fun `should get change payload`() {
        // given
        val oldItem = TestItem(1, "Test message", true)
        val newItem = TestItem(1, "New message", true)

        // when
        val payload = callback.getChangePayload(oldItem, newItem)

        // then
        assertEquals(Payload.Message("New message"), payload)
    }

    private data class TestItem(
        val id: Int,
        val message: String,
        val read: Boolean
    ) : TypedListItem

    private class TestViewHolder(view: View) : TypedListViewHolder<TestItem>(view) {
        override fun bind(item: TestItem) {
            // nop
        }
    }

    private sealed class Payload {
        data class Message(val message: String) : Payload()
    }
}
