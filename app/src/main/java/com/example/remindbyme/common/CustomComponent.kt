package com.example.remindbyme.common

import android.app.DatePickerDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.remindbyme.entity.Items
import com.example.remindbyme.entity.Tasks
import com.example.remindbyme.ui.theme.buttonColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.Calendar

object CustomComponent {

    var jsonObjects = Tasks(
        id = null,
        taskName = "Learn Spring Boot",
        startDate = "13/02/2025",
        endDate = "14/02/2025",
        description = "Hey Fyi, What's going on ?",
        itemList = listOf(
            Items(id = 1, itemName = "Book", quantity = 2, notes = "Spring Boot Guide"),
            Items(id = 2, itemName = "Pen", quantity = 3, notes = "Blue Ink")
        )
    )

    fun checkInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    @Composable
    fun SetStatusBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setStatusBarColor(
                color = color,
                darkIcons = color.luminance() > 0.5
            )
        }
    }

    @Composable
    fun NetworkCheck(onDismiss: () -> Unit) {
        var showDialog by remember { mutableStateOf(true) }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Surface(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "No Internet Connection", fontSize = 20.sp, color = Color.Black)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Please check your internet settings and try again.", fontSize = 16.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                showDialog = false
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                        ) {
                            Text(text = "OK", color = Color.White)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CustomHeightSpacer(){
        Spacer(modifier = Modifier.height(8.dp))
    }

    @Composable
    fun CustomRoundCardButton(id: Int, onClick: () -> Unit) {
        val interactionSource = remember { MutableInteractionSource() }

        Card(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(color = Color.Gray),
                    onClick = onClick
                )
                .background(buttonColor),
            shape = RoundedCornerShape(50.dp)
        ) {
            Image(
                painter = painterResource(id = id),
                contentDescription = "Button Icon",
                modifier = Modifier
                    .fillMaxSize()
                    .background(buttonColor)
            )
        }
    }

    @Composable
    fun CustomButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
        val interactionSource = remember { MutableInteractionSource() }

        Button(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(6.dp),
            interactionSource = interactionSource,
        ) {
            Text(text = text)
        }
    }

    @Composable
    fun CustomOutlineButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
        val interactionSource = remember { MutableInteractionSource() }

        OutlinedButton(
            onClick = onClick,
            modifier = modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(color = buttonColor),
                    onClick = onClick
                ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = buttonColor
            ),
            border = BorderStroke(2.dp, buttonColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = text)
        }
    }

    @Composable
    fun CustomTextInputFieldScreen(
        text: String,
        hint: String,
        onTextChange: (String) -> Unit
    ) {
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = onTextChange,
            label = { Text(text = hint) },
            placeholder = { Text(text = hint) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = buttonColor,
                unfocusedBorderColor = buttonColor,
                cursorColor = buttonColor,
                focusedLabelColor = buttonColor,
                unfocusedLabelColor = buttonColor
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
    }

    @Composable
    fun CustomNumberInputFieldScreen(
        text: String,
        hint: String,
        onTextChange: (String) -> Unit
    ) {
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { newText ->
                if (newText.all { it.isDigit() }) {
                    onTextChange(newText)
                }
            },
            label = { Text(text = hint) },
            placeholder = { Text(text = hint) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = buttonColor,
                unfocusedBorderColor = buttonColor,
                cursorColor = buttonColor,
                focusedLabelColor = buttonColor,
                unfocusedLabelColor = buttonColor
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
    }

    @Composable
    fun CustomDatePicker(
        modifier: Modifier = Modifier,
        label: String,
        date: String,
        onDateSelected: (String) -> Unit
    ) {
        var showDatePicker by remember { mutableStateOf(false) }

        val context = LocalContext.current
        val calendar = Calendar.getInstance()

        if (showDatePicker) {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
                    onDateSelected(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
            showDatePicker = false
        }

        OutlinedTextField(
            value = date,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    tint = buttonColor,
                    contentDescription = "Select Date",
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            textStyle = TextStyle(color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = buttonColor,
                unfocusedBorderColor = buttonColor,
                cursorColor = buttonColor,
                focusedLabelColor = buttonColor,
                unfocusedLabelColor = buttonColor
            )
        )
    }

    @Composable
    fun CustomTextBox(
        modifier: Modifier = Modifier,
        description: String,
        hint: String,
        onDescriptionChange: (String) -> Unit
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(120.dp),
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(hint) },
            placeholder = { Text(text = "Enter $hint...") },
            maxLines = 5,
            textStyle = TextStyle(color = Color.Black),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = buttonColor,
                unfocusedBorderColor = buttonColor,
                cursorColor = buttonColor,
                focusedLabelColor = buttonColor,
                unfocusedLabelColor = buttonColor
            )
        )
    }

    @Composable
    fun CustomTitle(
        text: String
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )
        )
    }

    @Composable
    fun CustomIconButton(
        imageVector: ImageVector,
        tintColor: Color,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        Icon(
            imageVector = imageVector,
            contentDescription = "Add",
            tint = tintColor,
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = false, color = tintColor),
                    onClick = onClick
                )
        )
    }

    @Composable
    fun ListTaskItem(
        taskName: String,
        notes: String,
        onEditClick: () -> Unit,
        onDeleteClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = taskName,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )

                    Row {
                        IconButton(onClick = onEditClick) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = onDeleteClick) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }
                    }
                }

                if (notes.isNotBlank()) {
                    Text(
                        text = notes,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }
    }
}
