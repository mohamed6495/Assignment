package com.expert.apps.ui.products.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.expert.apps.data.repository.Repository
import com.expert.apps.ui.products.model.Product

import com.expert.apps.ui.products.model.responeProduct
import com.expert.apps.utils.Resource
import com.expert.apps.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ProductViewModel  @Inject constructor(private val apiHelper: Repository)  : BaseViewModel() {
    val productLiveData: MutableLiveData<Resource<responeProduct>> = MutableLiveData()
    fun getProduct(){
        productLiveData.postValue(Resource.Loading())
        launch {
            apiHelper.getProduct().let { response ->
                productLiveData.postValue(handleResponse(response))
            }
        }
    }
    private fun handleResponse(response: Response<responeProduct>): Resource<responeProduct> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
            //    insertCompetitions(resultResponse.products)
                return Resource.Success(resultResponse)
            }
        }
        val jObjError = JSONObject(response.errorBody()!!.string())
        return Resource.Error(jObjError.getString("message"))
    }

     fun insertCompetitions(competitionsInfoResponse: Product) = viewModelScope.launch {
        apiHelper.insertProductDB(competitionsInfoResponse)
    }

  fun getProductsCash(): LiveData<List<Product>> = apiHelper.getProductCash()
    fun removeCity(product: Product) = viewModelScope.launch {
        apiHelper.removeProduct(product)
    }
}
