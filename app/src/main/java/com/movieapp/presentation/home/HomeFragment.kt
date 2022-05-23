package com.movieapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberAsyncImagePainter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.movieapp.R
import com.movieapp.domain.model.Home
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE



        return ComposeView(requireContext()).apply {
            setContent {
                Run()


            }
        }
    }

    @Composable
    fun Run() {
        val data by viewModel.uiState.collectAsState()



        if (data.home.isNotEmpty()) {


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        items(
                            items = data.home[0],
                            itemContent = {
                                ItemHorizontal(home = it)
                            }
                        )
                    }
                }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        items(
                            items = data.home[1],
                            itemContent = {
                                ItemHorizontal(home = it)
                            }
                        )
                    }
                }
                items(items=data.home[2], itemContent = {
                    ItemVertical(home = it)
                })
            }
        }


    }

    @Composable
    fun ItemTitle(){

    }

    @Composable
    fun ItemHorizontal(
        home: Home,
        modifier: Modifier = Modifier
    ) {
        Column {
            Card(
                modifier = modifier
                    .width(130.dp)
                    .height(180.dp)

                    .padding(bottom = 10.dp, end = 10.dp),
                shape = RoundedCornerShape(20.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(
                            model = "https://image.tmdb.org/t/p/w500${home.poster}"
                        ),
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                    )
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Card(
                        modifier = Modifier.padding(8.dp),
                        shape = RoundedCornerShape(20.dp),
                        backgroundColor = colorResource(id = R.color.transparent),
                    ) {
                        Text(
                            text = home.imdb,
                            modifier = Modifier
                                .padding(5.dp),
                            style = TextStyle(
                                color = colorResource(id = R.color.text),
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.helvetica))
                            )
                        )
                    }


                }
            }

            Box(
                modifier = Modifier
                    .width(120.dp),
                contentAlignment = Alignment.BottomStart
            ) {

                Text(
                    text = home.title,
                    modifier = Modifier.padding(bottom = 10.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.text),
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica))
                    )
                )
            }
        }
    }

    @Composable
    fun ItemVertical(
        home: Home,
        modifier: Modifier = Modifier
    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(20.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(
                            model = "https://image.tmdb.org/t/p/w500${home.poster}"
                        ),
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                    )
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Card(
                        modifier = Modifier.padding(8.dp),
                        shape = RoundedCornerShape(20.dp),
                        backgroundColor = colorResource(id = R.color.transparent),
                    ) {
                        Text(
                            text = home.imdb,
                            modifier = Modifier
                                .padding(5.dp),
                            style = TextStyle(
                                color = colorResource(id = R.color.text),
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.helvetica))
                            )
                        )
                    }


                }
            }

            Box(
                modifier = Modifier
                    .width(120.dp),
                contentAlignment = Alignment.BottomStart
            ) {

                Text(
                    text = home.title,
                    modifier = Modifier.padding(bottom = 10.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.text),
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica))
                    )
                )
            }
        }
    }
}




