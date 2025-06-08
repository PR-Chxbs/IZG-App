package com.prince.izg.di

import com.prince.izg.data.remote.api.*
import com.prince.izg.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepository(userApi)
    }

    @Provides
    @Singleton
    fun providePostRepository(postApi: PostApi): PostRepository {
        return PostRepository(postApi)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryApi: CategoryApi): CategoryRepository {
        return CategoryRepository(categoryApi)
    }

    @Provides
    @Singleton
    fun provideStockRepository(stockApi: StockApi): StockRepository {
        return StockRepository(stockApi)
    }

    @Provides
    @Singleton
    fun provideEventRepository(eventApi: EventApi): EventRepository {
        return EventRepository(eventApi)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepository(authApi)
    }
}
