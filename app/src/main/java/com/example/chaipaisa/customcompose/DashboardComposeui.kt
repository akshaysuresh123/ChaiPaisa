package com.example.chaipaisa.customcompose

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chaipaisa.viewmodels.DashboardViewmodel

@Composable
fun ChannelCard(channelName: String, activeMembers: Int, balanceAmount: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Channel name
            Column {
                Text(
                    text = channelName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "$activeMembers active members",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Balance amount
            Text(
                text = "₹${balanceAmount}", // Format for 2 decimal places
                style = MaterialTheme.typography.bodyMedium,
                color = if (balanceAmount >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FragmentA(navController: NavController, viewmodel: DashboardViewmodel = hiltViewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    var channelName by remember { mutableStateOf("") }
    var channelType by remember { mutableStateOf("") }
    val channelTypeOptions = listOf("Personal", "Public")
    var dropdownExpanded by remember { mutableStateOf(false) }
    val channels by viewmodel.allchanels.observeAsState(emptyList())


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ChaiPaisa") },
                actions = {
//                    IconButton(onClick = { /* Handle search action */ }) {
//                        Icon(Icons.Default.Search, contentDescription = "Search")
//                    }
                    IconButton(onClick = { /* Handle menu options */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Options")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },

                ) {
                Icon(Icons.Default.Add, contentDescription = "Add Group")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {

            // Placeholder for now; populate with actual data
            items(channels) { chanel ->

                ChannelCard(chanel.channel_name,chanel.activemembers,chanel.net_total,modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // Handle the click event here
                        // For example, navigate to a detail screen or show a toast
                       //navController.navigate("channel_details/${chanel.id}")
                        viewmodel.setcurrent_channnel_id(chanel.channel_name)
                         navController.navigate("B")

                        println("Channel clicked: ${chanel.channel_name}")
                    })

            }

        }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Add New Group",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        TextField(
                            value = channelName,
                            onValueChange = { channelName = it },
                            label = { Text("Channel Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Box {
                            OutlinedTextField(
                                value = channelType,
                                onValueChange = { },
                                label = { Text("Channel Type") },
                                readOnly = true,
                                enabled = false,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {

                                        Log.e("outline click","WORKING")
                                        dropdownExpanded = true }
                            )
                            DropdownMenu(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false }
                            ) {
                                channelTypeOptions.forEach { type ->
                                    DropdownMenuItem(
                                        onClick = {
                                            channelType = type
                                            dropdownExpanded = false
                                        },
                                        text = {Text(type)},

                                        )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(onClick = { showDialog = false }) {
                                Text("Cancel")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                // Handle "Create Channel" logic
                                viewmodel.Addnewchannel(channelName,channelType)

                                showDialog = false
                            }) {
                                Text("Create")
                            }
                        }
                    }
                }
            }
        }




    }
}
