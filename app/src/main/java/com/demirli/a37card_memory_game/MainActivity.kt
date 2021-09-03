package com.demirli.a37card_memory_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var imageViewList: List<Pair<ImageView,Pair<Int,Int>>>
    private lateinit var shuffledListOfImageView: List<Pair<ImageView,Pair<Int,Int>>>

    private lateinit var coverImageViewList: List<Pair<ImageView,Pair<Int,Int>>>

    private lateinit var selectionPairCoordinates: ArrayList<Pair<Pair<Int,Int>,Pair<Int,Int>>>

    private lateinit var runnableForVisibility: Runnable
    private lateinit var handlerForVisibility: Handler

    private lateinit var runnableForCounter: Runnable
    private lateinit var handlerForCounter: Handler

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Kod çalışıyor ama rezalet oldu.

        createImageViewAndCoordinatesList() //Views for image and Coordinates

        createCoverImageViewAndCoordinatesList()//Views for cover and coordinates

        setUiAndCardsVisiblityLogic()

        start_btn.setOnClickListener {

            setVisiblityForStart()

            shuffledListOfImageView = imageViewList.shuffled()
            setImageInImageViews(setImageUrlList(),shuffledListOfImageView)

            handlerForCounter.removeCallbacks(runnableForCounter)
            counter_tv.text = "0"
            startCounter()
        }
    }

    private fun setImageUrlList() : List<Image>{
        val imageUrlList = listOf(
            Image(1,"https://raw.githubusercontent.com/apavlinovic/PogoAssets/master/decrypted_assets/pokemon_icon_129_00_shiny.png"),
            Image(2,"https://raw.githubusercontent.com/apavlinovic/PogoAssets/master/decrypted_assets/pokemon_icon_130_00_shiny.png"),
            Image(3,"https://raw.githubusercontent.com/apavlinovic/PogoAssets/master/decrypted_assets/pokemon_icon_302_00_shiny.png"),
            Image(4,"https://raw.githubusercontent.com/apavlinovic/PogoAssets/master/decrypted_assets/pokemon_icon_172_00_shiny.png"),
            Image(5,"https://raw.githubusercontent.com/apavlinovic/PogoAssets/master/decrypted_assets/pokemon_icon_025_00_shiny.png"),
            Image(6,"https://raw.githubusercontent.com/apavlinovic/PogoAssets/master/decrypted_assets/pokemon_icon_026_00_shiny.png"),
            Image(7,"https://raw.githubusercontent.com/apavlinovic/PogoAssets/master/decrypted_assets/pokemon_icon_355_00_shiny.png"),
            Image(8,"https://raw.githubusercontent.com/apavlinovic/PogoAssets/master/decrypted_assets/pokemon_icon_356_00_shiny.png")

        )
        return imageUrlList
    }

    private fun createImageViewAndCoordinatesList(){
        imageViewList = listOf(
            Pair(imageView_0_0,Pair(0,0)),
            Pair(imageView_0_1,Pair(0,1)),
            Pair(imageView_0_2,Pair(0,2)),
            Pair(imageView_0_3,Pair(0,3)),
            Pair(imageView_1_0,Pair(1,0)),
            Pair(imageView_1_1,Pair(1,1)),
            Pair(imageView_1_2,Pair(1,2)),
            Pair(imageView_1_3,Pair(1,3)),
            Pair(imageView_2_0,Pair(2,0)),
            Pair(imageView_2_1,Pair(2,1)),
            Pair(imageView_2_2,Pair(2,2)),
            Pair(imageView_2_3,Pair(2,3)),
            Pair(imageView_3_0,Pair(3,0)),
            Pair(imageView_3_1,Pair(3,1)),
            Pair(imageView_3_2,Pair(3,2)),
            Pair(imageView_3_3,Pair(3,3))
        )
    }

    private fun createCoverImageViewAndCoordinatesList(){
        coverImageViewList = listOf(
            Pair(imageView_0_0_cover,Pair(0,0)),
            Pair(imageView_0_1_cover,Pair(0,1)),
            Pair(imageView_0_2_cover,Pair(0,2)),
            Pair(imageView_0_3_cover,Pair(0,3)),
            Pair(imageView_1_0_cover,Pair(1,0)),
            Pair(imageView_1_1_cover,Pair(1,1)),
            Pair(imageView_1_2_cover,Pair(1,2)),
            Pair(imageView_1_3_cover,Pair(1,3)),
            Pair(imageView_2_0_cover,Pair(2,0)),
            Pair(imageView_2_1_cover,Pair(2,1)),
            Pair(imageView_2_2_cover,Pair(2,2)),
            Pair(imageView_2_3_cover,Pair(2,3)),
            Pair(imageView_3_0_cover,Pair(3,0)),
            Pair(imageView_3_1_cover,Pair(3,1)),
            Pair(imageView_3_2_cover,Pair(3,2)),
            Pair(imageView_3_3_cover,Pair(3,3))
        )
    }

    private fun setUiAndCardsVisiblityLogic(){

        selectionPairCoordinates = arrayListOf()

        runnableForVisibility = Runnable { }
        handlerForVisibility = Handler()
        runnableForCounter = Runnable {  }
        handlerForCounter = Handler()

        var countFS = 0
        var firstChoise = Pair(0,0)
        for (i in  0 until imageViewList.size){
            imageViewList[i].first.setOnClickListener {
                if(countFS == 0){
                    firstChoise = imageViewList[i].second
                    coverImageViewList.map {
                        if(it.second == firstChoise){
                            it.first.visibility = View.INVISIBLE
                        }
                    }
                    countFS += 1
                }else if(countFS == 1){
                    val secondChoice = imageViewList[i].second
                    coverImageViewList.map {
                        if(it.second == secondChoice){
                            it.first.visibility = View.INVISIBLE


                        }
                    }
                    val selectionPairFS = Pair(firstChoise, secondChoice)
                    val selectionPairSF = Pair(secondChoice, firstChoise)
                    countFS = 0

                    if(selectionPairCoordinates.contains(selectionPairFS) || selectionPairCoordinates.contains(selectionPairSF)){
                        println("Eşleşti")

                        runnableForVisibility = object: Runnable{
                            override fun run() {
                                shuffledListOfImageView.map {
                                    if (it.second == selectionPairFS.first || it.second == selectionPairFS.second){
                                        it.first.visibility = View.INVISIBLE
                                    }

                                    if(shuffledListOfImageView.all { it.first.visibility == View.INVISIBLE }){
                                        handlerForCounter.removeCallbacks(runnableForCounter)
                                        Toast.makeText(this@MainActivity, "Your Finishing Time Is: $score",Toast.LENGTH_LONG).show()
                                    }
                                }
                                handlerForVisibility.removeCallbacks(runnableForVisibility)
                            }
                        }
                        handlerForVisibility.postDelayed(runnableForVisibility, 1000)

                    }else{
                        println("Eşleşmedi")
                        shuffledListOfImageView.map { it.first.isClickable = false }
                        runnableForVisibility = object: Runnable{
                            override fun run() {

                                coverImageViewList.map {
                                    if (it.second == selectionPairFS.first || it.second == selectionPairFS.second){
                                        it.first.visibility = View.VISIBLE
                                    }
                                }
                                shuffledListOfImageView.map { it.first.isClickable = true }
                                handlerForVisibility.removeCallbacks(runnableForVisibility)
                            }
                        }
                        handlerForVisibility.postDelayed(runnableForVisibility, 1000)
                    }
                }
            }
        }
    }

    private fun setVisiblityForStart(){
        coverImageViewList.map {
            it.first.visibility = View.VISIBLE
        }

        imageViewList.map {
            it.first.visibility = View.VISIBLE
        }
    }

    private fun setImageInImageViews(imageUrlList: List<Image>, shuffledListOfImageView: List<Pair<ImageView,Pair<Int,Int>>>){

        selectionPairCoordinates.clear()
        var count = 0

        for (i in 0 until  imageUrlList.size){

            Picasso.get().load(imageUrlList[i].imageUrl).into(shuffledListOfImageView[count].first)
            Picasso.get().load(imageUrlList[i].imageUrl).into(shuffledListOfImageView[count + 1].first)

            selectionPairCoordinates.add(Pair(shuffledListOfImageView[count].second,shuffledListOfImageView[count + 1].second))

            count += 2
        }
    }

    private fun startCounter() {
        score = 0
        runnableForCounter = object: Runnable{
            override fun run() {
                score += 1
                counter_tv.text = score.toString()
                handlerForCounter.postDelayed(runnableForCounter, 1000)
            }
        }
        handlerForCounter.postDelayed(runnableForCounter, 1000)
    }
}
