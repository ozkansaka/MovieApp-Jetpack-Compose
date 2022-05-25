package com.movieapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.compose.rememberAsyncImagePainter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.movieapp.R
import com.movieapp.domain.model.Search
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE


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
    fun Run(data: SearchUiState) {
        Column(modifier = Modifier.background(colorResource(id = R.color.blue))) {
            ItemHeader()

            val textState = remember { mutableStateOf(TextFieldValue("")) }


            SearchView(state = textState, placeHolder = "Search...")


            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                items(
                    items = data.search,
                    itemContent = {
                        ItemSearch(search = it)
                    }
                )
            }
        }
    }

    @Composable
    fun SearchView(
        state: MutableState<TextFieldValue>,
        placeHolder: String
    ) {
        Card(shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(10.dp)) {
            TextField(modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(),
                placeholder = { Text(placeHolder) },
                value = state.value,
                onValueChange = { value ->
                    state.value = value
                    if (state.value.text == "") {
                        viewModel.query = "a"
                    } else {
                        viewModel.query = state.value.text
                    }
                    viewModel.run()
                }
            )
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
                    text = "Search",
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
    fun ItemSearch(
        search: Search,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),

            ) {
            Card(
                modifier = Modifier
                    .height(180.dp)
                    .width(130.dp)
                    .clickable(onClick = {
                        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(movieId = search.id))
                    }),
                shape = RoundedCornerShape(10.dp),
            ) {
                Box {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(
                            model = "https://image.tmdb.org/t/p/w500${search.image}"
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
                            text = search.imdb!!,
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
                    .fillMaxSize()
                    .padding(10.dp),
            ) {
                Column {
                    Text(
                        text = search.title!!,
                        maxLines = 1, overflow = TextOverflow.Ellipsis,
                        modifier = Modifier,
                        style = TextStyle(
                            color = colorResource(id = R.color.white),
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.helvetica_bold))
                        )
                    )

                    Text(
                        text = search.description!!,
                        maxLines = 6, overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 10.dp),
                        style = TextStyle(
                            color = colorResource(id = R.color.white),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.helvetica))
                        )
                    )
                }
            }
        }
    }
}