package com.dheeraj.datemonthpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dheeraj.datemonthpicker.ui.theme.DateMonthPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DateMonthPickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DateMonthPickerTheme {
        MainScreen()
    }
}


@Composable
fun MonthPicker(
    selectedMonth: String?,
    onMonthSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White) // Set the desired background color
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                val months = listOf(
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
                )

                Text(
                    text = "Select Month",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                months.forEach { month ->
                    Text(
                        text = month,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onMonthSelected(month) }
                            .padding(vertical = 4.dp),
                        style = MaterialTheme.typography.body1,
                        color = if (month == selectedMonth) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}


@Composable
fun DatePicker(
    selectedDate: String?,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White) // Set the desired background color
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                val dates = (1..31).map { it.toString() }

                Text(
                    text = "Select Date",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                dates.forEach { date ->
                    Text(
                        text = date,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDateSelected(date) }
                            .padding(vertical = 4.dp),
                        style = MaterialTheme.typography.body1,
                        color = if (date == selectedDate) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var showMonthPicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedMonth by remember { mutableStateOf("Click here to select month") }
    var selectedDate by remember { mutableStateOf("Click here to select date") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {


        Text(
            text = "Selected Month: $selectedMonth",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Selected Date: $selectedDate",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ClickableText(
            text = AnnotatedString.Builder().apply {
                append(selectedMonth)
                addStyle(
                    style = SpanStyle(textDecoration = TextDecoration.Underline),
                    start = 0,
                    end = selectedMonth.length
                )
            }.toAnnotatedString(),
            onClick = {
                showMonthPicker = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showMonthPicker = true },
            style = MaterialTheme.typography.body1
        )

        ClickableText(
            text = AnnotatedString.Builder().apply {
                append(selectedDate)
                addStyle(
                    style = SpanStyle(textDecoration = TextDecoration.Underline),
                    start = 0,
                    end = selectedDate.length
                )
            }.toAnnotatedString(),
            onClick = {
                showDatePicker = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            style = MaterialTheme.typography.body1
        )
    }

    if (showMonthPicker) {
        MonthPicker(     selectedMonth = selectedMonth,
            onMonthSelected = { month ->
                selectedMonth = month
                showMonthPicker = false
            },
            onDismiss = { showMonthPicker = false }
        )
    }

    if (showDatePicker) {
        DatePicker(
            selectedDate = selectedDate,
            onDateSelected = { date ->
                selectedDate = date
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}
