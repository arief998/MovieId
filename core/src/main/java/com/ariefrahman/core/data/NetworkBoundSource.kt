package com.ariefrahman.core.data

import com.ariefrahman.core.data.source.remote.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundSource<ResultType, RequestType> {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map {
                        Resource.Success(it)
                    })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map {
                        Resource.Success(it)
                    })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(
                        Resource.Error<ResultType>(apiResponse.errorMessage)
                    )
                }
            }
        } else {
            emitAll(loadFromDB().map {
                Resource.Success(it)
            })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}