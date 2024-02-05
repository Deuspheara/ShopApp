package fr.deuspheara.eshopapp.data.network.paging

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.map
import com.google.gson.Gson
import fr.deuspheara.eshopapp.data.network.exception.ApiException
import fr.deuspheara.eshopapp.data.network.json.parse
import fr.deuspheara.eshopapp.data.network.model.ResponseContainer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.network.paging.ApiLocalPagingSource
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Api local paging source
 *
 */
class ApiLocalPagingSource<T : Any>(
    private val apiCall: suspend (
        page: Int,
        size: Int
    ) -> List<T>,
    private val dispatcher: CoroutineDispatcher,
    private val gson: Gson
) : PagingSource<Int, T>() {

    companion object {
        private const val TAG = "ApiLocalPagingSource"

        private const val FIRST_PAGE_INDEX = 0

        fun <T : Any, R : Any> Flow<PagingData<T>>.pagingMap(
            transform: (T) -> R
        ): Flow<PagingData<R>> {
            return this.map { pagingData ->
                pagingData.map { transform(it) }
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val pageNumber = params.key ?: FIRST_PAGE_INDEX
            val response = withContext(dispatcher) {
                Log.d(TAG, "load: page: $pageNumber, size: 10")
                apiCall(
                    (pageNumber * 10) + 1,
                    10
                )
            }
            Log.d(TAG, "load: response: $response")
            LoadResult.Page(
                data = response,
                prevKey = if (pageNumber == FIRST_PAGE_INDEX) null else pageNumber - 1,
                nextKey = if (response.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}