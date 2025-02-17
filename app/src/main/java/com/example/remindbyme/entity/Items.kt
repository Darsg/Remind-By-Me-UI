package com.example.remindbyme.entity

data class Items(
    val id: Int?,
    var itemName: String,
    var quantity: Int?,
    var notes: String?
) {

    override fun toString(): String {
        return "Items(id=$id, itemName='$itemName', quantity='$quantity', notes='$notes')"
    }
}