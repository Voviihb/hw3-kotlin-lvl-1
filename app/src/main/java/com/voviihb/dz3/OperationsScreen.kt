package com.voviihb.dz3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OperationsScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                mainScreen(parentFragmentManager)
            }
        }
    }

    companion object {
        fun newInstance() =
            OperationsScreenFragment()
    }


}

@Composable
fun mainScreen(parentFragmentManager: FragmentManager) {
    val list = remember {
        mutableStateOf(arrayOf<Array<String>>())
    }
    val amount1 = remember {
        mutableStateOf("-")
    }
    val amount2 = remember {
        mutableStateOf("-")
    }
    val amount3 = remember {
        mutableStateOf("-")
    }

    Box {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.background2),
            contentDescription = "Фон",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            val height = LocalConfiguration.current.screenHeightDp.dp
            Column(modifier = Modifier.height(height - 105.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 30.dp, 0.dp, 0.dp)
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.amount),
                        contentDescription = stringResource(R.string.amount1)
                    )
                    Text(stringResource(R.string.title1), fontSize = 6.em, color = Color.Gray)
                    Text(amount1.value, fontSize = 8.em)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            stringResource(R.string.title1),
                            fontSize = 4.5.em,
                            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )
                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.calendar),
                            contentDescription = "Выбор периода",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.height(30.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(0.dp, 0.dp, 5.dp, 0.dp)
                        ) {
                            amountCard(
                                name = stringResource(R.string.amount2),
                                sum = amount2.value,
                                parentFragmentManager
                            ) { AccountsScreenFragment.newInstance() }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            amountCard(
                                name = stringResource(R.string.amount3),
                                sum = amount3.value,
                                parentFragmentManager
                            ) { NewOutcomeFragment.newInstance() }
                        }
                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            stringResource(R.string.title3),
                            fontSize = 4.5.em,
                            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )

                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.filter),
                            contentDescription = "Сортировать",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.height(30.dp)
                        )
                    }

                    if (list.value.isEmpty())
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(0.dp, 50.dp, 0.dp, 0.dp)
                                .fillMaxWidth()
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(60.dp))
                        }
                    else
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        ) {
                            itemsIndexed(list.value) { i, arr ->
                                historyCard(arr)
                            }
                        }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(105.dp)
                    .padding(0.dp, 5.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                redirectCard(
                    R.drawable.main_screen,
                    stringResource(R.string.first),
                    parentFragmentManager
                ) { OperationsScreenFragment.newInstance() }
                redirectCard(
                    R.drawable.bank_accounts, stringResource(R.string.second),
                    parentFragmentManager
                ) { AccountsScreenFragment.newInstance() }
                redirectCard(
                    R.drawable.analytics, stringResource(R.string.third),
                    parentFragmentManager
                ) { OperationsScreenFragment.newInstance() }
                redirectCard(
                    R.drawable.more, stringResource(R.string.forth),
                    parentFragmentManager
                ) { OperationsScreenFragment.newInstance() }
            }
        }
    }

    get_data(list, amount1, amount2, amount3)
}

fun get_data(
    list: MutableState<Array<Array<String>>>, amount1: MutableState<String>,
    amount2: MutableState<String>, amount3: MutableState<String>
) {
    CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, e -> println(e) }).launch {
        delay(4000)
        amount1.value = "9 400 ₽"
        amount2.value = "+66 800 ₽"
        amount3.value = "-57 400 ₽"
        list.value = arrayOf(
            arrayOf(
                "Продукты",
                "Карта **** **** **** 8943",
                "5 октября 2023 г.",
                "-1 100 ₽"
            ),
            arrayOf(
                "Зарплата",
                "Карта **** **** **** 6532",
                "2 октября 2023 г.",
                "+10 500 ₽"
            ),
            arrayOf(
                "Продукты",
                "Карта **** **** **** 8943",
                "5 октября 2023 г.",
                "-1 100 ₽"
            ),
            arrayOf(
                "Зарплата",
                "Карта **** **** **** 6532",
                "2 октября 2023 г.",
                "+10 500 ₽"
            ),
            arrayOf(
                "Продукты",
                "Карта **** **** **** 8943",
                "5 октября 2023 г.",
                "-1 100 ₽"
            ),
            arrayOf(
                "Зарплата",
                "Карта **** **** **** 6532",
                "2 октября 2023 г.",
                "+10 500 ₽"
            ),
        )
    }
}

@Composable
fun amountCard(
    name: String,
    sum: String,
    parentFragmentManager: FragmentManager,
    func: () -> Fragment
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp, 10.dp, 0.dp)
        ) {
            Column(
                modifier = Modifier.height(28.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(name, fontSize = 4.em, color = Color.Gray)
            }

            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.go_to),
                contentDescription = "Просмотреть все",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(15.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(sum, fontSize = 4.5.em)

            Button(
                onClick = { add(parentFragmentManager, func) },
                contentPadding = PaddingValues(0.dp),
                shape = MaterialTheme.shapes.extraSmall,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.size(40.dp)
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.plus),
                    contentDescription = "Добавить",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(40.dp)
                )
            }

        }
    }
}

@Composable
fun historyCard(arr: Array<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.basket),
                        contentDescription = "Иконка категории",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .height(30.dp)
                    )
                    Column(
                        modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            arr[0],
                            fontSize = 4.5.em,
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
                        )
                        Text(arr[1], fontSize = 3.5.em, color = Color.Gray)
                        Text(arr[2], fontSize = 3.5.em, color = Color.Gray)
                    }
                }

                Text(arr[3], fontSize = 4.5.em)
            }
        }
    }
}


@Composable
fun redirectCard(
    resourse: Int, name: String, parentFragmentManager: FragmentManager,
    func: () -> Fragment
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier.clickable(onClick = { add(parentFragmentManager) { func() } })
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(resourse),
                contentDescription = "Перейти на",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(60.dp)
            )
            Text(name, fontSize = 4.em)
        }
    }
}


fun add(parentFragmentManager: FragmentManager, func: () -> Fragment) {
    parentFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, func())
        .addToBackStack(null)
        .commit()
}