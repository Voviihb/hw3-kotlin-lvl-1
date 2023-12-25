package com.voviihb.dz3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.voviihb.dz3.data.Account
import com.voviihb.dz3.data.Bank

class AccountsScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(requireActivity())[AccountsViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                val totalMoney by viewModel.totalMoney
                val loading by viewModel.loading
                val errorMsg by viewModel.errorMessage
                val accounts = viewModel.accountsList
                val banks = viewModel.banksList

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.background),
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                TopBar(totalMoney = totalMoney)
                                Spacer(modifier = Modifier.height(20.dp))
                                ConnectedBanks(context = LocalContext.current, bankList = banks)
                                Spacer(modifier = Modifier.height(20.dp))
                                if (loading) {
                                    ShowLoading()
                                }
                                CurrentAccounts(
                                    context = LocalContext.current,
                                    accountList = accounts
                                )
                                if (errorMsg != null) {
                                    ShowError(msg = errorMsg ?: "")
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    companion object {
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
                    text = String.format("%.2f", totalMoney),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Icon(
                    painterResource(id = R.drawable.ruble_icon),
                    contentDescription = stringResource(R.string.currency_icon),
                    modifier = Modifier.size(32.dp),
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
                    .then(Modifier.size(32.dp))
            ) {
                Icon(
                    Icons.Sharp.DateRange,
                    contentDescription = stringResource(R.string.date_range),
                    modifier = Modifier.size(32.dp),
                )
            }

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .then(Modifier.size(32.dp))
            ) {
                Icon(
                    Icons.Sharp.Add,
                    contentDescription = stringResource(R.string.add_icon),
                    modifier = Modifier.size(32.dp),
                )
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectedBanks(context: Context, bankList: List<Bank>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            stringResource(R.string.connected_banks),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 8.dp)
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
                                contentDescription = stringResource(R.string.add_icon),
                                modifier = Modifier
                                    .size(32.dp)
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
                    stringResource(R.string.bank_logo),
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


@Composable
fun CurrentAccounts(context: Context, accountList: List<Account>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            stringResource(R.string.current_accounts),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyColumn() {
            items(accountList) { account ->
                AccountsItem(context = context, account = account)
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    "add new selected",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        },
                )
                {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Sharp.Add,
                                        contentDescription = stringResource(R.string.add_icon),
                                        modifier = Modifier
                                            .size(32.dp)
                                            .padding(4.dp)
                                            .border(1.dp, Color.Black)
                                    )
                                    Text(
                                        text = stringResource(R.string.new_card_bank_deposit_loan),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.peachy_orange)
                                    )
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
fun AccountsItem(context: Context, account: Account) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                Toast
                    .makeText(
                        context,
                        "Selected ${account.accountName}",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            },
    )
    {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(id = account.accountLogo),
                            contentDescription = stringResource(R.string.account_logo),
                            modifier = Modifier
                                .size(32.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = account.accountName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = String.format("%.2f", account.totalMoney),
                        fontSize = 16.sp,
                    )
                    Icon(
                        painterResource(id = R.drawable.ruble_icon),
                        contentDescription = stringResource(R.string.currency_icon),
                        modifier = Modifier.size(16.dp),
                    )
                }


            }

        }
    }
}

@Composable
fun ShowError(msg: String) {
    Toast.makeText(LocalContext.current, msg, Toast.LENGTH_LONG).show()
}