package com.example.chaipaisa.customcompose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chaipaisa.models.UserDetails
import com.example.chaipaisa.viewmodels.SingleUserViewModel
import com.example.chaipaisa.helperfunctions.SendWhatsappreminder


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SingleUserFragment(navController: NavController,upi:String,
//                       modifier: Modifier = Modifier,
//                       viewmodel: SingleUserViewModel = hiltViewModel()
//) {
//    val userdet by viewmodel.userDetail.observeAsState()
//    val totalamount by viewmodel.totalsum.observeAsState()
//
//    LaunchedEffect(upi) {
//        viewmodel.fetchuser(upi)
//
//
//    }
//
//    // Sample data for demonstration
//    val userName = userdet?.name
//    val channelName = userdet?.channel_id
//    val activeTransactions = 5
//    val totalPendingPayment = totalamount.toString()
//
//    // Observing data (if needed)
//    val transactions = viewmodel.transactions.observeAsState(initial = emptyList())
//    val context= LocalContext.current
//    var isDialogOpen by remember { mutableStateOf(false) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("User Dashboard") },
//                actions = {
//                    IconButton(onClick = { /* Handle options click */ }) {
//                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
//                    }
//                }
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = { isDialogOpen=true}) {
//                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
//            }
//        },
//        content = { paddingValues ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//            ) {
//                // Top 30% of the screen
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height( (LocalConfiguration.current.screenHeightDp).times(0.2f).dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//
//                    ) {
//
//                        Spacer(modifier = Modifier.width(16.dp))
//
//                        // User details
//                        Column {
//                            if (channelName != null) {
//                                Text(text = channelName, style = MaterialTheme.typography.labelSmall)
//                            }
//                            if (userName != null) {
//                                Text(text = userName, style = MaterialTheme.typography.titleLarge)
//                            }
//                            Text(
//                                text = "Active Transactions: $activeTransactions",
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//
//                        Spacer(modifier = Modifier.weight(1f))
//
//                        // Total pending payment
//                        Column(
//                            horizontalAlignment = Alignment.End,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//                            Text(text = totalPendingPayment, style = MaterialTheme.typography.titleLarge)
//                            Button(onClick = {SendWhatsappreminder("as","You have pending bill of : ${totalPendingPayment}rs",context) }) {
//                                Text("Request Money")
//                            }
//                        }
//                    }
//                }
//
//                // LazyColumn for transactions
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(top = 16.dp)
//                ) {
//                    items(transactions.value) { transaction ->
//                        // Replace with your transaction card
//                        TransactionCard(transaction)
//                    }
//                }
//            }
//        }
//    )
//    // Popup Dialog
//    if (isDialogOpen) {
//        if (userName != null) {
//            AddTransactionDialog(channelName,userName,upi,
//                onDismiss = { isDialogOpen = false },
//                onAddTransaction = { newTransaction ->
//                    viewmodel.addTransaction(newTransaction) // Update ViewModel logic
//                    isDialogOpen = false
//                }
//            )
//        }
//    }
//
//}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleUserFragment(
    navController: NavController,
    upi: String,
    modifier: Modifier = Modifier,
    viewmodel: SingleUserViewModel = hiltViewModel()
) {
    val userdet by viewmodel.userDetail.observeAsState()
    val totalamount by viewmodel.totalsum.observeAsState()

    LaunchedEffect(upi) {
        viewmodel.fetchuser(upi)
    }

    val userName = userdet?.name
    val channelName = userdet?.channel_id
    val totalPendingPayment = totalamount.toString()
    val transactions = viewmodel.transactions.observeAsState(initial = emptyList())
    val context = LocalContext.current

    var isDialogOpen by remember { mutableStateOf(false) }
    val selectedTransactions = remember { mutableStateListOf<UserDetails>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (selectedTransactions.isNotEmpty()) {
                        "${selectedTransactions.size} Selected"
                    } else {
                        "User Dashboard"
                    })
                },
                actions = {
                    if (selectedTransactions.isNotEmpty()) {
                        IconButton(onClick = {
                            viewmodel.deleteSelectedTransactions(selectedTransactions,upi)
                            selectedTransactions.clear()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Selected")
                        }
                    } else {
                        IconButton(onClick = { /* Handle options click */ }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { isDialogOpen = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // User info section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((LocalConfiguration.current.screenHeightDp * 0.2f).dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(16.dp))

                        // User details
                        Column {
                            channelName?.let {
                                Text(text = it, style = MaterialTheme.typography.labelSmall)
                            }
                            userName?.let {
                                Text(text = it, style = MaterialTheme.typography.titleLarge)
                            }
                            Text(
                                text = "Active Transactions: ${transactions.value.size}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // Total pending payment
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = totalPendingPayment, style = MaterialTheme.typography.titleLarge)
                            Button(onClick = {
                                SendWhatsappreminder(
                                    "as",
                                    "You have pending bill of: ${totalPendingPayment}rs",
                                    context
                                )
                            }) {
                                Text("Request Money")
                            }
                        }
                    }
                }

                // Transaction list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {
                    items(transactions.value) { transaction ->
                        TransactionCard(
                            transaction = transaction,
                            isSelected = selectedTransactions.contains(transaction),
                            onClick = {
                                if (selectedTransactions.isNotEmpty()) {
                                    toggleTransactionSelection(transaction, selectedTransactions)
                                }
                            },
                            onLongClick = {
                                toggleTransactionSelection(transaction, selectedTransactions)
                            }
                        )
                    }
                }
            }
        }
    )

    // Add transaction dialog
    if (isDialogOpen) {
        userName?.let {
            AddTransactionDialog(
                channelName = channelName ?: "",
                username = it,
                upi_id = upi,
                onDismiss = { isDialogOpen = false },
                onAddTransaction = { newTransaction ->
                    viewmodel.addTransaction(newTransaction)
                    isDialogOpen = false
                }
            )
        }
    }
}



// Function to toggle transaction selection
private fun toggleTransactionSelection(
    transaction: UserDetails,
    selectedTransactions: MutableList<UserDetails>
) {
    if (selectedTransactions.contains(transaction)) {
        selectedTransactions.remove(transaction)
    } else {
        selectedTransactions.add(transaction)
    }
}





@Composable
fun AddTransactionDialog(
    channelName: String?,
    username:String,
    upi_id:String,
    onDismiss: () -> Unit,
    onAddTransaction: (UserDetails) -> Unit
) {
    var transactionName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    var paidOrNot by remember { mutableStateOf("Paid") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Transaction") },
        text = {
            Column {
                OutlinedTextField(
                    value = transactionName,
                    onValueChange = { transactionName = it },
                    label = { Text("Transaction Name") }
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                DropdownMenuField(
                    label = "Paid or Not",
                    selectedValue = paidOrNot,
                    options = listOf("Paid", "Not Paid"),
                    onValueSelected = { paidOrNot = it }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Create a new UserDetails object
                    val newTransaction = channelName?.let {
                        UserDetails(
                            id = 0, // Replace with autogenerated or unique ID logic
                            username = username, // Replace with actual username if needed
                            upi_id = upi_id,
                            transaction_name = transactionName,
                            paid_or_not = paidOrNot,
                            channel_id = it,
                            amount = amount.toInt(),
                            date = date
                        )
                    }
                    if (newTransaction != null) {
                        onAddTransaction(newTransaction)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Composable
fun DropdownMenuField(
    label: String,
    selectedValue: String,
    options: List<String>,
    onValueSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }


        // Wrap the OutlinedTextField with a Box and apply clickable
        Box(
            modifier = Modifier
                .fillMaxWidth().clickable { expanded = true  }
               // Make the whole box clickable
        ) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                label = { Text(label) },
                enabled = false,
                readOnly = true, // Disable direct editing
                modifier =Modifier
                    .fillMaxWidth()
                    .clickable {

                        Log.e("outline click","WORKING")
                        expanded = true } // Ensure the field spans the full width
            )
        }

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onValueSelected(option) // Pass the selected option

                        expanded = false // Close the dropdown
                    }
                )
            }
        }
}

@Composable
fun TransactionCard(
    transaction: UserDetails,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = { onLongClick()
                    }
                )
            }
            .background(if (isSelected) Color.LightGray else Color.Transparent),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = transaction.transaction_name, style = MaterialTheme.typography.bodyLarge)
            Text(text = transaction.amount.toString(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}



//@Composable
//fun TransactionCard(transaction: UserDetails) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = transaction.transaction_name, style = MaterialTheme.typography.bodyLarge)
//            Text(text = transaction.amount.toString(), style = MaterialTheme.typography.bodyLarge)
//        }
//    }
//}
