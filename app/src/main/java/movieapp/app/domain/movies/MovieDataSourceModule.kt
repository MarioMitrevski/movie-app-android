package movieapp.app.domain.movies

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import movieapp.app.data.movie.MovieDataSource
import movieapp.app.datasources.movie.NetworkMovieDataSource

@InstallIn(SingletonComponent::class)
@Module
abstract class MovieDataSourceModule {

    @Binds
    abstract fun bindMovieDataSource(
        networkMovieDataSource: NetworkMovieDataSource
    ): MovieDataSource

}