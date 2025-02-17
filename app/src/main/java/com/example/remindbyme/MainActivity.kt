package com.example.remindbyme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.remindbyme.activity.TaskActivity
import com.example.remindbyme.entity.UserInfo
import com.example.remindbyme.ui.theme.RemindByMeTheme
import com.example.remindbyme.common.CustomComponent
import com.example.remindbyme.ui.theme.buttonColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RemindByMeTheme {
                CustomComponent.SetStatusBarColor(buttonColor)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(this, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

/**
 *  This is Main page of our application
 */
@Composable
fun Main(activity: ComponentActivity, modifier: Modifier = Modifier) {
    val isTaskDialogOpen = remember { mutableStateOf(false) }

    Column(modifier.fillMaxSize()) {
        Header(activity, modifier)
        List(modifier)
    }
}

/**
 *  This function contains Profile Icon, Add button and List button
 *  1. Click on Profile Icon Button open setting page
 *  2. Click on Add button create a new task
 *  3. Click on List button open task list
 */
@Composable
fun Header(activity: ComponentActivity, modifier: Modifier = Modifier) {
    val userInfo = UserInfo(
        1,
        "Darsh Dobariya",
        "9874587458",
        "https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png",
        "darsh@mail.com",
        "Address One",
        "Address Two",
        "Gujarat",
        "India",
        "698569"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(buttonColor)
            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Profile Image
            Card(
                modifier = Modifier
                    .size(50.dp)
                    .border(2.dp, Color.White, RoundedCornerShape(50.dp)),
                shape = RoundedCornerShape(50.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(userInfo.profileImage),
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // User name
            Text(
                text = "66",
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            // Add new Task button
            CustomComponent.CustomRoundCardButton(
                id = R.drawable.icon_add,
                onClick = { handleAddNewTask(activity) }
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Show list of task button
            CustomComponent.CustomRoundCardButton(
                id = R.drawable.icon_list,
                onClick = { println("Please wait for a moment") }
            )
        }
    }
}

/**
 *  This function shows a data which is available in
 *  Current and Upcoming List
 */
@Composable
fun List(modifier: Modifier = Modifier) {

}

fun handleAddNewTask(activity: ComponentActivity) {
    val intent = Intent(activity, TaskActivity::class.java)
    intent.putExtra("title", "Add New Task")
    activity.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RemindByMeTheme {
        Main(activity = ComponentActivity())
    }
}