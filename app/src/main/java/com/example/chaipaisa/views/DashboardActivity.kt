package com.example.chaipaisa.views

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chaipaisa.ui.theme.ChaiPaisaTheme
import com.example.chaipaisa.viewmodels.DashboardViewmodel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity: ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChaiPaisaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding->
                  val viewmodel= ViewModelProvider(this).get(DashboardViewmodel::class.java)
                    DashScreen(
                        modifier = Modifier.padding(innerPadding),viewmodel
                    )
                }
            }



        }
    }

}

@Composable
fun DashboardNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "A") {
        composable("A") { FragmentA(navController) }
        composable("B") { FragmentB(navController) }
    }
}


//@SuppressLint("RestrictedApi")
//@Composable
//fun FragmentA(navController: NavController) {
//    val currentRoute = navController.currentBackStackEntry?.destination?.route
//
//    Column {
//        Text("This is Fragment A. Current Route: $currentRoute")
//        Button(onClick = { navController.navigate("B") }) {
//            Text("Go to Fragment B")
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FragmentA(navController: NavController,viewmodel: DashboardViewmodel= hiltViewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    var channelName by remember { mutableStateOf("") }
    var channelType by remember { mutableStateOf("") }
    val channelTypeOptions = listOf("Personal", "Public")
    var dropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ChaiPaisa") },
                actions = {
                    IconButton(onClick = { /* Handle search action */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
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
            items(10) { index ->
                Text(
                    text = "Group $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
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





@Composable
fun FragmentB(navController: NavController) {
    Column {
        navController.currentDestination?.route?.let { PieChartView(it) }
        Text("This is Fragment B")
        Button(onClick = { navController.navigate("fragment_a") }) {
            Text("Go to Fragment A")
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
                    setColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
                    sliceSpace = 3f
                    selectionShift = 5f
                }

                val pieData = PieData(dataSet)
                data = pieData
            }
        },
        modifier = Modifier.fillMaxSize() // Set the size of the PieChart
    )
}





@Composable
fun DashScreen(modifier: Modifier = Modifier, viewmodel: DashboardViewmodel = hiltViewModel()) {

    DashboardNavigation()


}

//@Preview(showBackground = true)
//@Composable
//fun testpreview(){
//    ChaiPaisaTheme {
//        DashScreen()
//    }
//}
