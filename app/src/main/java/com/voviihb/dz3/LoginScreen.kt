package com.voviihb.dz3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class LoginScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
//        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        Log.d("!!!", "vm " + viewModel.hashCode().toString())

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
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {

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


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginForm(
    viewModel: LoginViewModel,
    loginForm: LoginForm,
    loading: Boolean,
    error: String
) {
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier.imePadding()
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
            Column(

            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(topEnd = 32.dp))

                ) {
                    TextField(
                        value = loginForm.email,
                        onValueChange = {
                            viewModel.emailChanged(it)
                            viewModel.clearError()
                        },
                        label = { Text(stringResource(R.string.e_mail_hint)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusRequester.requestFocus() }
                        ),
                        singleLine = true,
                        isError = error != "",
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Email,
                                contentDescription = stringResource(R.string.email_logo)
                            )
                        },
                        trailingIcon = {
                            if (error != "") {
                                Icon(
                                    Icons.Rounded.Close,
                                    stringResource(R.string.error),
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                            }
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
                        value = loginForm.password,
                        onValueChange = {
                            viewModel.passwordChanged(it)
                            viewModel.clearError()
                        },
                        label = { Text(stringResource(R.string.password_hint)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        ),
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
                            if (error != "") {
                                Icon(
                                    Icons.Rounded.Close,
                                    stringResource(R.string.error),
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                                viewModel.passwordChanged("")
                            }
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Lock, contentDescription = stringResource(
                                    R.string.lock_logo
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
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
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .then(Modifier.size(64.dp))
                    .offset(x = (-25).dp),
            ) {
                Icon(
                    painterResource(id = R.drawable.arrow_next_icon),
                    contentDescription = stringResource(R.string.check_logo),
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

