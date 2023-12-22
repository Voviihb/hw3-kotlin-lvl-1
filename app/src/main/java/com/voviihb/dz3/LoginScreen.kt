package com.voviihb.dz3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


class LoginScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val viewModel = LoginViewModel(LoginRepo())
        return ComposeView(requireContext()).apply {
            setContent {
                val loginForm by viewModel.loginFormState.collectAsState(
                    initial = LoginForm(
                        "",
                        ""
                    )
                )
                val logged by viewModel.isLogged.collectAsState(initial = false)
                val loading by viewModel.loading.collectAsState(initial = false)
                val errorMsg by viewModel.errorMessage.collectAsState(initial = "")

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.background),
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(120.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            Image(
                                painterResource(id = R.drawable.logo),
                                contentDescription = getString(R.string.logo),
                                modifier = Modifier
                                    .size(200.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 16.dp, 16.dp, 16.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            LoginForm(
                                parentFragmentManager,
                                viewModel,
                                loginForm,
                                loading,
                                errorMsg
                            )
                        }

                        if (logged) {
                            LaunchedEffect(Unit) {
                                parentFragmentManager.beginTransaction()
                                    .replace(
                                        R.id.fragment_container,
                                        AccountsScreenFragment.newInstance()
                                    )
                                    .commit()
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
            LoginScreenFragment()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(
    parentFragmentManager: FragmentManager,
    viewModel: LoginViewModel,
    loginForm: LoginForm,
    loading: Boolean,
    error: String
) {
    var email by remember { mutableStateOf("") } // сделать синхронизацию с viewModel
    var password by remember { mutableStateOf("") } // сделать очистку в случае ошибки
    Column(
    ) {
        Text(
            stringResource(id = R.string.login),
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(topEnd = 32.dp))
                ) {
                    TextField(
                        value = email,
                        onValueChange = {
                            email = it
                            viewModel.emailChanged(it)
                        },
                        label = { Text("E-Mail") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        isError = error != "",
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Email,
                                contentDescription = "Email logo"
                            )
                        },
                        trailingIcon = {
                            if (error != "")
                                Icon(
                                    Icons.Rounded.Close,
                                    "error",
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Gray,
                            disabledTextColor = Color.Transparent,
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(bottom = 8.dp)
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(bottomEnd = 32.dp)
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(bottomEnd = 32.dp)
                        )
                        .clip(RoundedCornerShape(bottomEnd = 32.dp))

                ) {
                    TextField(
                        value = password,
                        onValueChange = {
                            password = it
                            viewModel.passwordChanged(it)
                        },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        isError = error != "",
                        supportingText = {
                            if (error != "") {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = error,
                                    color = Color.Red
                                )
                            }
                        },
                        trailingIcon = {
                            if (error != "")
                                Icon(
                                    Icons.Rounded.Close,
                                    "error",
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                        },
                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Lock logo") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Gray,
                            disabledTextColor = Color.Transparent,
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        ),
                    )
                }
            }
            IconButton(
                onClick = {
                    viewModel.clearError()
                    viewModel.login()
                },
                modifier = Modifier
                    .then(Modifier.size(64.dp))
                    .offset(x = (-25).dp),
            ) {
                Icon(
                    painterResource(id = R.drawable.arrow_next_icon),
                    contentDescription = "CheckLogo",
                    modifier = Modifier.size(64.dp),
                    tint = colorResource(R.color.light_green),
                )
                if (loading) {
                    ShowLoading()
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(stringResource(R.string.forgot_password), color = Color.Gray)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(bottomEnd = 32.dp, topEnd = 32.dp)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(bottomEnd = 32.dp, topEnd = 32.dp)
                    )
                    .clip(RoundedCornerShape(bottomEnd = 32.dp, topEnd = 32.dp))
                    .clickable { /*TODO*/ }

            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    color = colorResource(id = R.color.peachy_orange),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

}


@Composable
fun ShowLoading() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator()
        }

    }
}


@Composable
fun ShowError(msg: String) {
    Toast.makeText(LocalContext.current, msg, Toast.LENGTH_LONG).show()
}