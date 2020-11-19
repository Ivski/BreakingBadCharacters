package rocks.ivski.bbc.di

import org.koin.dsl.module
import rocks.ivski.bbc.data.repo.CharacterRepo

val repoModule = module {
    single {
        CharacterRepo(get())
    }
}