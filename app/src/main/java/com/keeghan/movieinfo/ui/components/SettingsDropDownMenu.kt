package com.keeghan.movieinfo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/** Generic DropDownList for various setting screen
 * @param options is a list of available options
 * @param selectedOption is the position of the currently selected item from the options
 * @param onOptionSelect is the behaviour when an item is selected
 * that is handled by calling composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDropDownMenu(
    options: List<String>,
    selectedOption: Int,
    onOptionSelect: (String) -> Unit,
) {
    var mExpanded by remember { mutableStateOf(false) }
    //val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Box(
        modifier = Modifier
            .fillMaxWidth()
        //  .height(35.dp),
        , contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = mExpanded,
            onExpandedChange = { mExpanded = !mExpanded },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            BasicTextField(
                value = options[selectedOption],
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor(),
                enabled = false
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
                ) {
                    Text(text = options[selectedOption])
                    Spacer(modifier = Modifier.weight(1f))
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = mExpanded)
                }
            }

            ExposedDropdownMenu(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background),
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false }) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it) },
                        onClick = {
                            onOptionSelect(it)
                            mExpanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
    Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)
}
