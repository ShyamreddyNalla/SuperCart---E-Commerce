package com.example.e_commercesupercart.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.repository.ProductRepository
import com.example.e_commercesupercart.model.subcategories.Product
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository)
    : ViewModel() {

        private val _productLiveData = MutableLiveData<List<Product>>()
    val productLiveData: LiveData<List<Product>> get() = _productLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun getProducts(subCategoryId: String){
        viewModelScope.launch{
            try {
                val response = productRepository.getProductBySubCategory(subCategoryId)
             if(response.isSuccessful){
                 _productLiveData.value = response.body()?.products
             }else{
                 _errorLiveData.value = "Failed to load products"
             }
            }catch (e: Exception){
                _errorLiveData.value = "Error: ${e.message}"
            }

        }
    }
}

  class ProductViewModelFactory(private  val productRepository: ProductRepository): ViewModelProvider.Factory{
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
          return ProductViewModel(productRepository) as T
      }
  }