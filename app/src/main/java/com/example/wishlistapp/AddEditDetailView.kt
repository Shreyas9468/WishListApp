package com.example.wishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(

    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    if (id != 0L) {
        val wish = viewModel.getWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescription = wish.value.description
    }
    else{
        viewModel.wishTitleState=""
        viewModel.wishDescription=""
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                title =
                if (id.toInt() != 0) {
                    stringResource(id = R.string.update_wish)
                } else {
                    stringResource(id = R.string.add_wish)
                }

            ) {
                navController.navigateUp()
            }
        },


        ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Tile", value = viewModel.wishTitleState, onValueChanged = {
                viewModel.onWishTitleChanged(it)
            })

            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Description",
                value = viewModel.wishDescription,
                onValueChanged = {
                    viewModel.onWishDescriptionChanged(it)
                })
            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {


                if (viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescription.isNotEmpty()) {
                    if (id != 0L) {
                            viewModel.updateWish(
                                wish = Wish(
                                    id = id,
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescription.trim(),
                                )
                            )
                        snackMessage.value = "Wish has been Updated"
                    } else {
                        //Add wish
                        viewModel.addWish(
                            wish = Wish(
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescription.trim()
                            )

                        )
                        snackMessage.value = "Wish has been Created"
                    }
                } else {
                    snackMessage.value = "Enter fields to create a Wish"
                }


                scope.launch {

                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }


            }


            ) {
                Text(
                    text =


                    if (id != 0L) {
                        stringResource(id = R.string.update_wish)
                    } else {
                        stringResource(id = R.string.add_wish)
                    },
                    style = TextStyle(
                        fontSize = 18.sp
                    )

                )

            }

        }
    }

}


@Composable
fun WishTextField(

    label: String,
    value: String,
    onValueChanged: (String) -> Unit

) {
    OutlinedTextField(
        value = value, onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(

            textColor = Color.Black,
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black)
        )
    )
}
