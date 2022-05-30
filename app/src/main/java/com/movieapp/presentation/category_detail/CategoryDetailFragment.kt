package com.movieapp.presentation.category_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
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
import androidx.navigation.fragment.navArgs
import coil.compose.rememberAsyncImagePainter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.movieapp.R
import com.movieapp.domain.model.CategoryDetail
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoryDetailFragment : Fragment() {


    private val viewModel: CategoryDetailViewModel by viewModels()
    private val args: CategoryDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.GONE


        viewModel.categoryId = args.id
        viewModel.run()



        return ComposeView(requireContext()).apply {
            setContent {
                val data by viewModel.uiState.collectAsState()

                Run(data)


            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Run(data: CategoryDetailUiState) {

        Column(modifier = Modifier.background(colorResource(id = R.color.blue))) {
            ItemHeader()
            LazyVerticalGrid(
                cells = GridCells.Fixed(2), contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(data.categoryDetail.size) {
                    ItemCategoryDetail(categoryDetail = data.categoryDetail[it])

                }
            }
        }
    }

    @Composable
    fun ItemHeader() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(colorResource(id = R.color.dark))
        ) {
            Row(modifier = Modifier.padding(top = 30.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.light_brown)), modifier = Modifier
                        .padding(start = 20.dp)
                        .width(30.dp)
                        .clickable(onClick = {
                            findNavController().popBackStack()
                        })
                        .height(30.dp), painter = painterResource(id = R.drawable.back_button), contentDescription = "back_button"
                )
                Text(
                    text = args.title!!,
                    modifier = Modifier
                        .padding(start = 30.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.light_brown),
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica_bold))
                    )
                )
            }

        }
    }

    @Composable
    fun ItemCategoryDetail(
        categoryDetail: CategoryDetail,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Card(
                modifier = Modifier
                    .height(180.dp)
                    .width(130.dp)
                    .clickable(onClick = {
                        findNavController().navigate(CategoryDetailFragmentDirections.actionCategoryDetailFragmentToDetailFragment(movieId = categoryDetail.id))
                    }),
                shape = RoundedCornerShape(10.dp),
            ) {
                Box {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(
                            model = "https://image.tmdb.org/t/p/w500${categoryDetail.poster}"
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
                            text = categoryDetail.imdb,
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
                    .width(130.dp)
                    .padding(top = 10.dp),
                contentAlignment = Alignment.BottomStart
            ) {

                Text(
                    text = categoryDetail.title,
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
}