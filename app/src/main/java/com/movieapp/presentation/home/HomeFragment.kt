package com.movieapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.compose.rememberAsyncImagePainter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.movieapp.R
import com.movieapp.domain.model.Home
import com.movieapp.domain.model.HomeTypeModel
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
        Column(modifier = Modifier.background(colorResource(id = R.color.blue))) {


            ItemHeader()
            if (data.home.isNotEmpty()) {

                LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                    data.home.forEach {

                        when (it) {
                            is HomeTypeModel.Title -> {
                                item {
                                    ItemTitle(title = it)
                                }

                            }
                            is HomeTypeModel.Horizontal -> {
                                item {
                                    LazyRow() {
                                        items(
                                            items = it.data,
                                            itemContent = { data ->
                                                ItemHorizontal(home = data)
                                            }
                                        )
                                    }
                                }
                            }
                            is HomeTypeModel.Vertical -> {
                                item {
                                    ItemVertical(home = it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ItemHeader() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.dark))
        ) {
            Row(modifier = Modifier.padding(top = 30.dp, bottom = 10.dp)) {
                Image(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .width(40.dp)
                        .height(40.dp), painter = painterResource(id = R.drawable.logo), contentDescription = "logo"
                )
                Text(
                    text = "OzkaFlix",
                    modifier = Modifier
                        .padding(start = 30.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.light_brown),
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica_bold))
                    )
                )
            }

        }
    }

    @Composable
    fun ItemTitle(title: HomeTypeModel.Title) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title.title,
                modifier = Modifier
                    .padding(bottom = 10.dp),
                style = TextStyle(
                    color = colorResource(id = R.color.white),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.helvetica_bold))
                )
            )
        }
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
                    .padding(bottom = 10.dp, end = 10.dp)
                    .clickable(onClick = {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId = home.id))
                    }),
                shape = RoundedCornerShape(10.dp),
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
                                color = colorResource(id = R.color.white),
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
                    maxLines = 1, overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 20.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica))
                    )
                )
            }
        }
    }

    @Composable
    fun ItemVertical(
        home: HomeTypeModel.Vertical,
    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 10.dp)
                    .clickable(onClick = {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId = home.id))
                    }),
                shape = RoundedCornerShape(10.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(
                            model = "https://image.tmdb.org/t/p/w500${home.image}"
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
                                color = colorResource(id = R.color.white),
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.helvetica))
                            )
                        )
                    }


                }
            }

            Box(
                modifier = Modifier,
                contentAlignment = Alignment.BottomStart
            ) {

                Text(
                    text = home.title,
                    modifier = Modifier.padding(bottom = 20.dp),
                    maxLines = 1, overflow = TextOverflow.Ellipsis,

                    style = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica))
                    )
                )
            }
        }
    }
}




