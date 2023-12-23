package com.voviihb.dz3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
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
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                TopBar(totalMoney = totalMoney)
                                Spacer(modifier = Modifier.height(20.dp))
                                ConnectedBanks(context = LocalContext.current)
                            }

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectedBanks(context: Context) {
    val bankList = mutableListOf<Bank>(
        Bank("Tinkoff", R.drawable.tinkoff_logo),
        Bank("Sber", R.drawable.sber_logo),
        Bank("AlfaBank", R.drawable.alfa_logo)
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Connected banks", fontSize = 18.sp, fontWeight = FontWeight.Medium, modifier = Modifier
                .padding(8.dp)
        )
        LazyRow() {
            item {
                Card(
                    onClick = {
                        Toast.makeText(
                            context,
                            "add new selected",
                            Toast.LENGTH_SHORT
                        ).show()
                    },

                    modifier = Modifier
                        .padding(8.dp)
                        .size(120.dp),
                )
                {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        Row(
                        ) {
                            Icon(
                                Icons.Sharp.Add,
                                contentDescription = "Add",
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(4.dp)
                                    .border(1.dp, Color.Black)
                            )
                        }
                        Row {
                            Text(text = stringResource(R.string.new_connection), fontSize = 16.sp)
                        }
                    }
                }
            }
            items(bankList) { bank ->
                BankCard(context = context, bank = bank)
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankCard(context: Context, bank: Bank) {
    Card(
        onClick = {
            Toast.makeText(
                context,
                "selected ${bank.bankName}",
                Toast.LENGTH_SHORT
            ).show()
        },

        modifier = Modifier
            .padding(8.dp)
            .size(120.dp),
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Row(
            ) {
                Image(
                    painterResource(id = bank.bankLogo),
                    "Bank logo",
                    modifier = Modifier.size(80.dp),
                    alignment = Alignment.Center
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = bank.bankName, fontSize = 16.sp)
            }
        }
    }
}