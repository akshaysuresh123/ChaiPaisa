package com.example.chaipaisa.customcompose

import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chaipaisa.viewmodels.DashboardViewmodel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FragmentB(navController: NavController, viewmodel: DashboardViewmodel = hiltViewModel(),) {
    // Variables for controlling visibility of PieChart
    var showdialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var upi_id by remember { mutableStateOf("") }
    var showPieChart by remember { mutableStateOf(true) }  // Change based on your condition
    val members by viewmodel.allusers.observeAsState(emptyList())// Example members list
    val channe_id by viewmodel.current_channel_id.observeAsState("")

    LaunchedEffect(channe_id) {
        viewmodel.getAlluser(channe_id)
    }

    // Column that holds everything
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // TopAppBar
        TopAppBar(
            title = {
                Text("ChaiPaisa")
            },
            actions = {
                IconButton(onClick = { /* Handle 3-dot options */ }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More options")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Content
        Box(modifier = Modifier.fillMaxSize()) {
            // Conditional PieChart
            if (showPieChart) {
                // You can customize the PieChart as per your needs
                navController.currentDestination?.route?.let { PieChartView(it) } // Takes up 30% of screen height
            }

            // LazyColumn to display members, below PieChart
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = if (showPieChart) 200.dp else 0.dp)  // Adjust for PieChart height
            ) {
                items(members) { member ->
                    MemberCard(memberName = member.name)
                }
            }

            if(showdialog){
                Dialog(onDismissRequest = {
                    showdialog=false
                }) {
                    Surface(shape = MaterialTheme.shapes.medium,
                        tonalElevation = 16.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp).fillMaxWidth()
                        ) {

                            Text(text = "Add New Member",style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(bottom = 16.dp))



                            TextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("User Name") },
                                modifier = Modifier.fillMaxWidth().padding(2.dp)
                            )

                            TextField(
                                value = upi_id,
                                onValueChange = { upi_id = it },
                                label = { Text("User UPI Id") },
                                modifier = Modifier.fillMaxWidth().padding(2.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(onClick = { showdialog = false }) {
                                    Text("Cancel")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = {
                                    // Handle "Add User" logic

                                    viewmodel.Addnewuser(name,upi_id ,channe_id)


                                    showdialog = false
                                }) {
                                    Text("Create")
                                }
                            }





                        }


                    }





                }


            }


            // Floating Action Button at the bottom right
            FloatingActionButton(
                onClick = {showdialog=true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Group")
            }
        }
    }
}

@Composable
fun PieChartView(currunt_fragment:String) {
    // Using AndroidView to include MPAndroidChart PieChart inside Compose
    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                // Set up PieChart properties here
                setUsePercentValues(true)
                isDrawHoleEnabled = true
                holeRadius = 58f
                transparentCircleRadius = 61f
                description.isEnabled = false
                centerText = "Pie Chart"
                setDrawEntryLabels(false)

                // Setting data for the PieChart

                var entries: List<PieEntry>? =null

                if(currunt_fragment.equals("A")){
                    entries = listOf(
                        PieEntry(20f, "A"),
                        PieEntry(50f, "B"),
                        PieEntry(15f, "C"),
                        PieEntry(15f, "D")
                    )
                }else if(currunt_fragment.equals("B")){
                    entries = listOf(
                        PieEntry(30f, "A"),
                        PieEntry(40f, "B"),
                        PieEntry(20f, "C"),
                        PieEntry(10f, "D")
                    )
                }

                val dataSet = PieDataSet(entries, "Labels").apply {
                    setColors(android.graphics.Color.RED, android.graphics.Color.BLUE, android.graphics.Color.GREEN, android.graphics.Color.YELLOW)
                    sliceSpace = 3f
                    selectionShift = 5f
                }

                val pieData = PieData(dataSet)
                data = pieData
            }
        },
        modifier = Modifier.fillMaxWidth().height(200.dp) // Set the size of the PieChart
    )
}



// Member Card Layout for LazyColumn
@Composable
fun MemberCard(memberName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = memberName)
            Text(text = "Balance: ₹100") // Add dynamic balance amount as required
        }
    }
}
