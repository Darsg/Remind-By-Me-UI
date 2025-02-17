package com.example.remindbyme.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remindbyme.common.CustomComponent
import com.example.remindbyme.common.CustomComponent.CustomTextBox
import com.example.remindbyme.entity.Items

class ItemDialog(
    private val onClickSave: () -> Unit,
    private val onClickCancel: () -> Unit,
    private val items: Items
) {
    @Composable
    fun MainMethod() {
        var itemName by remember { mutableStateOf(items.itemName) }
        var itemQuantity by remember { mutableStateOf(items.quantity.toString()) }
        var itemNotes by remember { mutableStateOf(items.notes) }

        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemNotes?.let { it ->
                    ItemDialogBody(
                        itemName = itemName,
                        itemQuantity = itemQuantity,
                        itemNotes = it,
                        onNameChange = { itemName = it },
                        onQuantityChange = { itemQuantity = it },
                        onNotesChange = { itemNotes = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                ItemDialogFooter(
                    onSaveClick = {
                        items.itemName = itemName
                        items.quantity = itemQuantity.toIntOrNull() ?: 0
                        items.notes = itemNotes
                        onClickSave()
                    },
                    onCancelClick = onClickCancel
                )
            }
        }
    }

    @Composable
    fun ItemDialogBody(
        itemName: String,
        itemQuantity: String,
        itemNotes: String,
        onNameChange: (String) -> Unit,
        onQuantityChange: (String) -> Unit,
        onNotesChange: (String) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                CustomComponent.CustomTextInputFieldScreen(
                    text = itemName,
                    hint = "Item name",
                    onTextChange = onNameChange
                )
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                CustomComponent.CustomNumberInputFieldScreen(
                    text = itemQuantity,
                    hint = "Quantity",
                    onTextChange = onQuantityChange
                )
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                CustomTextBox(
                    modifier = Modifier.fillMaxWidth(),
                    description = itemNotes,
                    hint = "Notes",
                    onDescriptionChange = onNotesChange
                )
            }
        }
    }

    @Composable
    fun ItemDialogFooter(onSaveClick: () -> Unit, onCancelClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomComponent.CustomOutlineButton(
                text = "Cancel",
                onClick = onCancelClick,
                modifier = Modifier.weight(1f)
            )

            CustomComponent.CustomButton(
                text = "Save",
                onClick = onSaveClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

fun handleSaveClick() {}
fun handleCancelClick() {}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ItemDialog(
        onClickSave = ::handleSaveClick,
        onClickCancel = ::handleCancelClick,
        items = Items(1, "FirstName", 2, "Nothing bro just chill")
    ).MainMethod()
}