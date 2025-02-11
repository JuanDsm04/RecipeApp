package com.jdsm.myapplication.presentation.login

import LoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jdsm.myapplication.ui.theme.RecipeAppTheme
import com.jdsm.myapplication.R
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginRoute(
    onLogIn: () -> Unit,
    viewModel: LoginViewModel= viewModel(factory = LoginViewModel.Factory)
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.successfulLogin) {
        if (state.successfulLogin) {
            onLogIn()
        }
    }

    LoginScreen(
        state = state,
        onLogIn = {
            viewModel.onEvent(LoginEvent.Login)
        },
        onEmailChange = {
            viewModel.onEvent(LoginEvent.EmailChange(it))
        },
        onPasswordChange = {
            viewModel.onEvent(LoginEvent.PasswordChange(it))
        },
        onIsPasswordVisibleChange = {
            viewModel.onEvent(LoginEvent.IsPasswordVisibleChange)
        }
    )
}

@Composable 
fun LoginScreen(
    state: LoginState,
    onLogIn: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onIsPasswordVisibleChange: () -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ){
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = stringResource(id = R.string.login_description),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(225.dp)
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(30.dp)
        ){
            Column {
                Text(
                    text = stringResource(id = R.string.welcome_app),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    label = {
                        Text(text = stringResource(id = R.string.enter_email))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    isError = state.hasError
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    label = {
                        Text(text = stringResource(id = R.string.enter_password))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val imageResource = if (state.isPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility

                        IconButton(onClick = onIsPasswordVisibleChange) {
                            Icon(
                                painter = painterResource(id = imageResource),
                                contentDescription = stringResource(id = R.string.password_icon),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = state.hasError,
                    supportingText = {
                        if (state.hasError) Text(text = stringResource(id = R.string.login_error))
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = { onLogIn() },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.login_text_button),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    RecipeAppTheme {
        LoginScreen(
            onLogIn = {},
            state = LoginState(
                password = "",
                hasError = false,
                successfulLogin = true,
                email = "",
                isPasswordVisible = true
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onIsPasswordVisibleChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreenError() {
    RecipeAppTheme {
        LoginScreen(
            onLogIn = {},
            state = LoginState(
                password = "",
                hasError = true,
                successfulLogin = false,
                email = "",
                isPasswordVisible = true
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onIsPasswordVisibleChange = {}
        )
    }
}