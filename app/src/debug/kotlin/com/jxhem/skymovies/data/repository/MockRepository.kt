package com.jxhem.skymovies.data.repository

import com.google.gson.Gson
import com.jxhem.skymovies.data.net.Model
import com.jxhem.skymovies.data.source.DataSource
import io.reactivex.Observable

class MockRepository : DataSource() {


    private val responseString: String =
        "{\"data\":[{\"id\":912312,\"title\":\"Dunkirk\",\"year\":\"2017\",\"genre\":\"History\",\"poster\":\"https://goo.gl/1zUyyq\"},{\"id\":11241,\"title\":\"Jumanji: welcome to the jungle\",\"year\":\"2017\",\"genre\":\"Action\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg\"},{\"id\":55122,\"title\":\"The Maze Runner\",\"year\":\"2014\",\"genre\":\"Action\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/coss7RgL0NH6g4fC2s5atvf3dFO.jpg\"},{\"id\":37344,\"title\":\"John Wick\",\"year\":\"2014\",\"genre\":\"Action\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/5vHssUeVe25bMrof1HyaPyWgaP.jpg\"},{\"id\":46232,\"title\":\"Star Wars: The Last Jedi\",\"year\":\"2017\",\"genre\":\"Fantasy\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/kOVEVeg59E0wsnXmF9nrh6OmWII.jpg\"},{\"id\":5123112,\"title\":\"Coco\",\"year\":\"2017\",\"genre\":\"Animation\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/eKi8dIrr8voobbaGzDpe8w0PVb.jpg\"},{\"id\":5556,\"title\":\"Blade Runner 2049\",\"year\":\"2017\",\"genre\":\"Mystery\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/gajva2L0rPYkEWjzgFlBXCAVBE5.jpg\"},{\"id\":712323,\"title\":\"Fantastic Beasts and Where to Find Them\",\"year\":\"2016\",\"genre\":\"Adventure\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/gri0DDxsERr6B2sOR1fGLxLpSLx.jpg\"},{\"id\":8666,\"title\":\"Lucy\",\"year\":\"2014\",\"genre\":\"Sci-Fi\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/rwn876MeqienhOVSSjtUPnwxn0Z.jpg\"},{\"id\":12356,\"title\":\"Batman vs Superman\",\"year\":\"2016\",\"genre\":\"Action\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/cGOPbv9wA5gEejkUN892JrveARt.jpg\"},{\"id\":66287,\"title\":\"Guardians of the Galaxy\",\"year\":\"2014\",\"genre\":\"Sci-Fi\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/rwn876MeqienhOVSSjtUPnwxn0Z.jpg\"},{\"id\":9678,\"title\":\"Atomic Blonde\",\"year\":\"2017\",\"genre\":\"Action\",\"poster\":\"https://image.tmdb.org/t/p/w1280/kV9R5h0Yct1kR8Hf8sJ1nX0Vz4x.jpg\"},{\"id\":8666,\"title\":\"Power Rangers\",\"year\":\"2017\",\"genre\":\"Fantasy\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/iRAZIEgfB9N0BObV0QI61Nxh92h.jpg\"},{\"id\":12873192,\"title\":\"Ted 2\",\"year\":\"2015\",\"genre\":\"Fantasy\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/A7HtCxFe7Ms8H7e7o2zawppbuDT.jpg\"},{\"id\":86668,\"title\":\"Sicario\",\"year\":\"2015\",\"genre\":\"Action\",\"poster\":\"https://image.tmdb.org/t/p/w370_and_h556_bestv2/p2SdfGmQRaw8xhFbexlHL7srMM8.jpg\"}]}"

    private var response: Model.ApiResponse

    init {
        var gson = Gson()
        response = gson.fromJson<Model.ApiResponse>(responseString, Model.ApiResponse::class.java)
    }

    override fun getMovies(): Observable<Model.ApiResponse> {
        return Observable.just(response)
    }
}