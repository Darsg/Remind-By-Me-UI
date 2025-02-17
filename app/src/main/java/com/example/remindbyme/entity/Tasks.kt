package com.example.remindbyme.entity

data class Tasks(
    var id: Int?,
    var taskName: String,
    var startDate: String,
    var endDate: String,
    var description: String?,
    var itemList: List<Items?>
) {
    override fun toString(): String {
        return "Tasks(id=$id, taskName='$taskName', startDate='$startDate', endDate='$endDate', description='$description', itemList=$itemList)"
    }
}