package com.example.appcattrilha.Repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.appcattrilha.Common.Common
import com.example.appcattrilha.Model.CatModel
import com.example.appcattrilha.Model.Favorite
import com.example.appcattrilha.Model.FavoriteResponse
import com.example.appcattrilha.Network.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoRepo {
    private val apiService: APIService

    companion object {
        private const val TAG = "FavoRepo"
    }

    init {
        apiService = Common.getAPIService
    }

    val getCatModelLiveData: MutableLiveData<MutableList<CatModel>>
    get() {
        val data: MutableLiveData<MutableList<CatModel>> =
            MutableLiveData<MutableList<CatModel>>()
        apiService.getCatListFavo(Common.loggedUserId.toString()).enqueue(object : Callback<MutableList<CatModel>> {
            override fun onResponse(
                call: Call<MutableList<CatModel>>,
                response: Response<MutableList<CatModel>>
            ) {
                Log.e(TAG, "onResponse: " + response.code())
                if (response.isSuccessful){
                    data.value = response.body()
                }
                else {
                    data.value = mutableListOf()
                }
            }

            override fun onFailure(call: Call<MutableList<CatModel>>, t: Throwable) {
                Log.e(TAG, "OnResponse: " + t.message)
                data.value = mutableListOf()
            }

        })
        return data
    }

    fun saveFavorite(id : String){
        apiService.addFavoritos(
            Favorite(image_id = id, sub_id = Common.loggedUserId.toString())
        ).enqueue(object : Callback<FavoriteResponse> {
            override fun onResponse(
                call: Call<FavoriteResponse>,
                response: Response<FavoriteResponse>
            ) {
                Log.e(TAG, "onResponse: " + response.code())
            }

            override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                Log.e(TAG, "OnResponse: " + t.message)
            }

        })
    }
}