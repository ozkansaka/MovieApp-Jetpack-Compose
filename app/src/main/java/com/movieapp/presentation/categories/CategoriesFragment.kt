package com.movieapp.presentation.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.movieapp.R
import com.movieapp.domain.model.Categories
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class CategoriesFragment : Fragment() {


    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE



        return ComposeView(requireContext()).apply {
            setContent {
                val data by viewModel.uiState.collectAsState()

                Run(data)
            }
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Run(data: CategoriesUiState) {

        Column(modifier = Modifier.background(colorResource(id = R.color.blue))) {
            ItemHeader()
            LazyVerticalGrid(
                cells = GridCells.Fixed(2), contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(data.categories.size) {
                    ItemCategory(categories = data.categories[it])

                }
            }
        }
    }


    @Composable
    fun ItemHeader() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(colorResource(id = R.color.dark))
        ) {
            Row(modifier = Modifier.padding(top = 30.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Categories",
                    modifier = Modifier
                        .padding(start = 20.dp),
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
    fun ItemCategory(modifier: Modifier = Modifier, categories: Categories) {

        Card(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 20.dp, end = 10.dp)
                .clickable(onClick = {
                    findNavController().navigate(CategoriesFragmentDirections.actionCategoriesFragmentToCategoryDetailFragment(id = categories.id, title = categories.name))
                }),
            backgroundColor = colorResource(id = R.color.dark),
            shape = RoundedCornerShape(200.dp),
        ) {
            Box(
                modifier = modifier
                    .width(130.dp)
                    .height(100.dp),
                contentAlignment = Alignment.Center


            ) {
                Text(
                    text = categories.name,
                    modifier = Modifier,
                    style = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.helvetica_bold)),
                    )
                )
            }
        }
    }
}


