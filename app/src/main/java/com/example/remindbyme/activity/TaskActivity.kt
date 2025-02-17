package com.example.remindbyme.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.remindbyme.common.CustomComponent
import com.example.remindbyme.common.CustomComponent.CustomDatePicker
import com.example.remindbyme.common.CustomComponent.CustomHeightSpacer
import com.example.remindbyme.common.CustomComponent.CustomIconButton
import com.example.remindbyme.common.CustomComponent.CustomTextBox
import com.example.remindbyme.common.CustomComponent.CustomTextInputFieldScreen
import com.example.remindbyme.common.CustomComponent.CustomTitle
import com.example.remindbyme.common.CustomComponent.ListTaskItem
import com.example.remindbyme.common.CustomComponent.NetworkCheck
import com.example.remindbyme.common.CustomComponent.checkInternet
import com.example.remindbyme.common.CustomComponent.jsonObjects
import com.example.remindbyme.dialog.ItemDialog
import com.example.remindbyme.entity.Items
import com.example.remindbyme.entity.Tasks
import com.example.remindbyme.ui.theme.RemindByMeTheme
import com.example.remindbyme.ui.theme.buttonColor

class TaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomComponent.SetStatusBarColor(buttonColor)

            val title = intent.getStringExtra("title") ?: "Task"

            TaskScreen(title) { onSaveClick() }
        }
    }

    private fun onSaveClick() {
        setResult(RESULT_OK, Intent())
        finish()
    }
}

/**
 *  This is Main function
 *  Screen has 3 part Header, MainBody and Footer for displaying content
 *  Also we showing dialog box in this function while user will be edit and adding new Items
 */
@Composable
fun TaskScreen(
    title: String,
    onSaveClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var jsonObject by remember { mutableStateOf(jsonObjects) }
    var item by remember { mutableStateOf(Items(null, "Sample Task", 2, "This is a note")) }

    var showNetworkCheckDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(title)

            MainBody(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                jsonObject,
                onAddClick = { showDialog = true; item = Items(null, "", 0, "") },
                onEditClick = { selectedItem -> item = selectedItem },
                onUpdate = { updatedTask -> jsonObject = updatedTask }
            )

            Footer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onSaveClick = {
                    if (!checkInternet(context)) {
                        showNetworkCheckDialog = true
                    } else {
                        onSaveClick()
                    }
                }
            )
        }
    }

    /**
     * displaying Dialog of Internet not available
     */
    if (showNetworkCheckDialog) {
        NetworkCheck(onDismiss = { showNetworkCheckDialog = false })
    }

    /**
     * displaying Dialog of Items where we can edit and add
     */
    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                color = Color.White
            ) {
                ItemDialog(
                    onClickSave = {
                        jsonObject = jsonObject.copy(
                            itemList = jsonObject.itemList.toMutableList().apply {
                                val index = indexOfFirst { it?.id == item.id }
                                if (index != -1) {
                                    set(index, item.copy())
                                } else {
                                    add(item.copy())
                                }
                            }
                        )
                        item = Items(null, "", 0, "")
                        showDialog = false
                    },
                    onClickCancel = { showDialog = false },
                    items = item
                ).MainMethod()
            }
        }
    }
}

/**
 *  This function displaying header in Page
 */
@Composable
fun Header(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(buttonColor)
            .padding(horizontal = 15.dp, vertical = 5.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

/**
 *  This function contains all body part data UI
 *  @param onAddClick - use for show Dialog and update Items object to null
 *  @param onEditClick - use for set edit item in Item object
 *  @param onUpdate - While changing value in other input field it will update our UI data
 *                    automatically like change Task name, description and so on;
 */
@Composable
fun MainBody(modifier: Modifier = Modifier, jsonObject: Tasks, onAddClick: () -> Unit, onEditClick: (Items) -> Unit, onUpdate: (Tasks) -> Unit ) {

    fun handleEditClick(items: Items){
        onAddClick()
        onEditClick(items)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            CustomTextInputFieldScreen(text = jsonObject.taskName, hint = "Task name", onTextChange = { onUpdate(jsonObject.copy(taskName = it)) })
        }

        item { CustomHeightSpacer() }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomDatePicker(
                    modifier = Modifier.weight(1f),
                    label = "Start Date",
                    date = jsonObject.startDate,
                    onDateSelected = { onUpdate(jsonObject.copy(startDate = it)) }
                )

                Spacer(modifier = Modifier.width(8.dp))

                CustomDatePicker(
                    modifier = Modifier.weight(1f),
                    label = "End Date",
                    date = jsonObject.endDate,
                    onDateSelected = { onUpdate(jsonObject.copy(endDate = it)) }
                )
            }
        }

        item { CustomHeightSpacer() }

        item {
            CustomTextBox(
                modifier = modifier,
                description = jsonObject.description.toString(),
                "description",
                onDescriptionChange = { onUpdate(jsonObject.copy(description = it)) }
            )
        }

        item { CustomHeightSpacer() }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomTitle("\uD83D\uDC4B Hey Fam, Bring Anything?")

                CustomIconButton(Icons.Default.Add, buttonColor) {
                    onAddClick()
                }
            }
        }

        item { CustomHeightSpacer() }

        items(jsonObject.itemList.size) { index ->
            jsonObject.itemList.getOrNull(index)?.let { item ->
                ListTaskItem(
                    taskName = item.itemName,
                    notes = item.notes ?: "",
                    onEditClick = { handleEditClick(item) },
                    onDeleteClick = {
                        val updatedList = if (item.id == null) {
                            jsonObject.itemList.filterNot { it == item }
                        } else {
                            jsonObject.itemList.filterNot { it?.id == item.id }
                        }

                        onUpdate(jsonObject.copy(itemList = updatedList))
                        println("Deleted item: ${item.itemName}")
                    }
                )
            }
        }
    }
}


/**
 *  This function contains cancel and delete button in bottom of page
 */
@Composable
fun Footer(modifier: Modifier, onSaveClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CustomComponent.CustomOutlineButton(
            text = "Cancel",
            onClick = ::clickCancel,
            modifier = Modifier.weight(1f)
        )

        CustomComponent.CustomButton(
            text = "Save",
            onClick = onSaveClick,
            modifier = Modifier.weight(1f)
        )
    }
}


fun clickCancel() {
    println("🚀 Cancel button clicked!")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RemindByMeTheme {
        TaskScreen("Whoops!!") {
            println("ok")
        }
    }
}
