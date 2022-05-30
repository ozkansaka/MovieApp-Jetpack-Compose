package com.movieapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.compose.rememberAsyncImagePainter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.movieapp.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.GONE

        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView).isAppearanceLightStatusBars = false


        viewModel.movieId = args.movieId
        viewModel.run()


        return ComposeView(requireContext()).apply {
            setContent {
                val data by viewModel.uiState.collectAsState()

                Run(data)
            }
        }


    }

    @Composable
    fun Run(data: DetailUiState) {
        if (data.detail.isNotEmpty()) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(
                            model = "https://image.tmdb.org/t/p/w500${data.detail[0].banner}"
                        ),
                        contentDescription = "banner",
                        contentScale = ContentScale.Crop,
                    )
                }
                Row {
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 0.dp)
                            .width(130.dp)
                            .height(180.dp)
                            .offset {
                                IntOffset(0, -80)
                            }

                    ) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = "https://image.tmdb.org/t/p/w500${data.detail[0].image}"
                                ), contentDescription = "poster"
                            )
                        }
                    }
                    Box {
                        Column {
                            Text(
                                text = data.detail[0].title,
                                modifier = Modifier.padding(top = 10.dp),
                                style = TextStyle(
                                    color = colorResource(id = R.color.white),
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.helvetica_bold))
                                )
                            )
                            Text(
                                text = data.detail[0].runtime + " mins",
                                modifier = Modifier,
                                style = TextStyle(
                                    color = colorResource(id = R.color.light_brown),
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.helvetica))
                                )
                            )
                            Text(
                                text = data.detail[0].imdb,
                                modifier = Modifier,
                                style = TextStyle(
                                    color = colorResource(id = R.color.light_brown),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.helvetica_bold))
                                )
                            )
                        }
                    }
                }
                LazyRow(contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)) {
                    items(
                        items = data.detail[0].genres,
                        itemContent = {
                            ItemGenres(
                                name = it.name
                            )
                        }
                    )
                }

                LazyRow(contentPadding = PaddingValues(horizontal = 10.dp, vertical = 20.dp)) {
                    items(
                        items = data.detail[0].cast!!,
                        itemContent = {
                            if (data.detail[0].cast?.isNotEmpty() == true) {
                                ItemCast(
                                    name = it.name,
                                    image = it.profilePath
                                )
                            }

                        }
                    )
                }

                Text(
                    text = "About",
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica_bold))
                    )
                )

                Text(
                    text = data.detail[0].description,
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica))
                    )
                )
            }
        }
    }

    @Composable
    fun ItemGenres(name: String) {
        Box(modifier = Modifier.padding(end = 20.dp)) {
            Card(
                shape = RoundedCornerShape(20.dp),
                backgroundColor = colorResource(id = R.color.transparent)
            ) {
                Text(
                    text = name,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 20.dp, end = 20.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica_bold))
                    )
                )
            }
        }
    }

    @Composable
    fun ItemCast(image: String?, name: String) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(end = 20.dp)
        ) {
            Box {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "https://image.tmdb.org/t/p/w500${image}"
                    ),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.blue))                      // clip to the circle shape
                )
            }
            Box {
                Text(
                    textAlign = TextAlign.Center,
                    text = name,
                    modifier = Modifier
                        .width(100.dp).height(50.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica_bold))
                    )
                )
            }
        }
    }

}