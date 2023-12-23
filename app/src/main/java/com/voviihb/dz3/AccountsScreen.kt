package com.voviihb.dz3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment

class AccountsScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val totalMoney = 20000.15
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.background),
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
//                            horizontalArrangement = Arrangement.Start
                        ) {
                            TopBar(totalMoney = totalMoney)
                        }


                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AccountsScreenFragment()
    }
}


@Composable
fun TopBar(totalMoney: Double) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = totalMoney.toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Icon(
                        painterResource(id = R.drawable.ruble_icon),
                        contentDescription = "Currency icon",
                        modifier = Modifier.size(30.dp),
                    )
                }

                Text(
                    text = stringResource(R.string.balance),
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .then(Modifier.size(30.dp))
                ) {
                    Icon(
                        Icons.Sharp.DateRange,
                        contentDescription = "Date range",
                        modifier = Modifier.size(30.dp),
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .then(Modifier.size(30.dp))
                ) {
                    Icon(
                        Icons.Sharp.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(30.dp),
                    )
                }
            }
        }
    }
}
