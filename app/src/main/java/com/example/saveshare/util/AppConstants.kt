package com.example.saveshare.util

object AppConstants {
    object Network {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "4367646c7b658483acf64e00cb9229ad"//TODO should be moved to env variable
    }

    object Database {
        const val NAME = "save-share"
    }

    object Paging {
        const val PAGE_SIZE = 20
        const val STARTING_PAGE_INDEX = 1
    }

    object DateFormats {
        const val VIDEO_DATE_FORMAT = "yyyy-MM-dd"
        const val MONTH_YEAR_FORMAT = "LLL yyyy"
    }

    object Share {
        const val SHARE_LINK = "https://www.themoviedb.org/movie/"
    }

    object Image {
        const val BASE_LINK =
            "https://image.tmdb.org/t/p/w600_and_h600_bestv2"//TODO here can change W adn H for better picture resolution
    }


}