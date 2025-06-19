package com.prince.izg.ui.components.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.app.DatePickerDialog
import java.util.Calendar

@Composable
fun ShowDatePicker(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, day ->
            val selected = "${year}-${month + 1}-${day}"
            onDateSelected(selected)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        setOnDismissListener { onDismiss() }
        show()
    }
}
