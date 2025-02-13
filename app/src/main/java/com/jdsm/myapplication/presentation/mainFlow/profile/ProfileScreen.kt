package com.jdsm.myapplication.presentation.mainFlow.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdsm.myapplication.ui.theme.RecipeAppTheme
import com.jdsm.myapplication.R
import com.jdsm.myapplication.presentation.common.CustomTextButton

@Composable
fun ProfileRoute(
    onLogOutClick: () -> Unit
) {
    ProfileScreen(
        onLogOutClick = onLogOutClick,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ProfileScreen(
    onLogOutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape)
        ) {
            Icon(
                Icons.Outlined.Person,
                contentDescription = stringResource(id = R.string.person_icon),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.account),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextButton(
            text = stringResource(id = R.string.log_out),
            onClick = onLogOutClick
        )
    }
}

@Preview
@Composable
private fun PreviewProfileScreen() {
    RecipeAppTheme {
        Surface {
            ProfileScreen(
                onLogOutClick = {}
            )
        }
    }
}