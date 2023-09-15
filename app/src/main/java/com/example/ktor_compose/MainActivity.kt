package com.example.ktor_compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ktor_compose.model.ApiResult
import com.example.ktor_compose.model.Quote
import com.example.ktor_compose.ui.theme.Ktor_composeTheme
import com.example.ktor_compose.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ktor_composeTheme {
                val viewModel= viewModel<AppViewModel>()
                val apiResult by viewModel.quotes.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize().padding(start = 4.dp, end = 4.dp),
                        contentAlignment = Alignment.Center) {
                        when(apiResult){
                            is ApiResult.Loading->{
                                CircularProgressIndicator(modifier = Modifier.size(70.dp), color = Color.Blue)
                            }
                            is ApiResult.Error->{
                                Toast.makeText(this@MainActivity,apiResult.error , Toast.LENGTH_SHORT).show()
                            }
                            is ApiResult.Success->{
                                val list=apiResult.data ?: emptyList()
                                LazyColumn(modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)){
                                    items(items = list){quote->
                                        QuoteItem(quote = quote)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuoteItem(quote: Quote) {
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement =Arrangement.spacedBy(4.dp) ) {
        Text(text = quote.category ?: "category")
        Text(text = quote.author ?: "author", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = quote.quote ?:"quote", fontSize = 18.sp)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuoteItem(Quote("author","category","quote"))
}