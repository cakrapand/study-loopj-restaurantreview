package com.example.restaurantreviewloopj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.restaurantreviewloopj.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    companion object{
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        getRestaurant()

    }

    private fun postReview(){

    }

    private fun findRestaurant(){

    }

    private fun setRestaurantData(restaurant: Restaurant){
        binding.tvTitle.text = restaurant.name
        binding.tvDescription.text = restaurant.description
        Glide.with(this@MainActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(binding.ivPicture)
    }

    private fun setReviewData(consumerReviews: List<CustomerReviewsItem>){
        val listReview = ArrayList<String>()
        for (review in consumerReviews){
            listReview.add("""${review.review}- ${review.name}""".trimIndent())
        }
        binding.rvReview.adapter = ReviewAdapter(listReview)
//        binding.edReview.setText("")
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    fun getRestaurant(){
        showLoading(true)
        val client = AsyncHttpClient()
        val url = "https://restaurant-api.dicoding.dev/detail/${RESTAURANT_ID}"

        client.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                showLoading(false)
                val response = String(responseBody!!)
                parseJson(response)
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Toast.makeText(this@MainActivity, error?.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${error?.message}", )
            }
        })
    }

    fun parseJson(response: String){
        val jsonObject = JSONObject(response)
        val dataRestaurant = jsonObject.getJSONObject("restaurant")

        val dataPicture = dataRestaurant.getString("pictureId")
        val dataName = dataRestaurant.getString("name")
        val dataRating = dataRestaurant.getString("rating")
        val dataDescription = dataRestaurant.getString("description")
        val dataId = dataRestaurant.getString("id")
        val dataReview = dataRestaurant.getJSONArray("customerReviews")

        val listDataReview = mutableListOf<CustomerReviewsItem>()

        for (i in 0 until dataReview.length()){
            val dataObject = dataReview.getJSONObject(i)
            val name = dataObject.getString("name")
            val review = dataObject.getString("review")
            val date = dataObject.getString("date")

            listDataReview.add(CustomerReviewsItem(date, review, name))
        }

        val restaurant = Restaurant(listDataReview, dataPicture, dataName, dataRating, dataDescription, dataId)

        setRestaurantData(restaurant)
        setReviewData(listDataReview)
    }
}