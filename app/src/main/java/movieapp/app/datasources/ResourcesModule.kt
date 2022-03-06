package movieapp.app.datasources

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ResourcesModule {

    @Provides
    @Singleton
    fun provideResources(application: Application): Resources {
        return application.resources
    }
}