package pl.allegro.typedlistadapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class TypedListAdapterTest {

    private val typedListAdapter = TypedListAdapter(viewHolderFactories = listOf())

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `should get index of item`() {
        // given
        typedListAdapter.submitList(listOf(A, A, B))

        // when
        val index = typedListAdapter.getIndexOfItem(B)

        // then
        assertEquals(index, 2)
    }

    @Test
    fun `should get index of item with predicate`() {
        // given
        typedListAdapter.submitList(listOf(A, A, B))

        // when
        val index = typedListAdapter.getIndexOfItem { item -> item == B }

        // then
        assertEquals(index, 2)
    }

    @Test
    fun `should submit list`() {
        // given
        val newItems = listOf(A, B, B, A)

        // when
        typedListAdapter.submitList(newItems)

        // then
        assertEquals(typedListAdapter.itemCount, 4)
    }

    @Test
    fun `should submit empty list`() {
        // given
        val newItems: List<TypedListItem>? = null

        // when
        typedListAdapter.submitList(newItems)

        // then
        assertEquals(typedListAdapter.itemCount, 0)
    }

    @Test
    fun `should invoke callback after submitting list`() {
        // given
        val callback: Runnable = mock()

        // when
        typedListAdapter.submitList(listOf(A, B, B, A), callback)

        // then
        verify(callback, times(1)).run()
    }

    @Test
    fun `should unbind view holder`(){
        // given
        val viewHolder: TypedListViewHolder<TypedListItem> = mock()

        // when
        typedListAdapter.onViewRecycled(viewHolder)

        // then
        verify(viewHolder, times(1)).unbind()
    }

    object A : TypedListItem
    object B : TypedListItem
}
