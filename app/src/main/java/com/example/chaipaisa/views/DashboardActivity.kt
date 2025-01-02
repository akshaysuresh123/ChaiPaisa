package com.example.chaipaisa.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chaipaisa.R
import com.example.chaipaisa.ui.theme.ChaiPaisaTheme
import com.example.chaipaisa.viewmodels.DashboardViewmodel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

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
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }



        }
    }

}

@Composable
fun DashboardNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "fragment_a") {
        composable("fragment_a") { FragmentA(navController) }
        composable("fragment_b") { FragmentB(navController) }
    }
}


@SuppressLint("RestrictedApi")
@Composable
fun FragmentA(navController: NavController) {
    Column {

        Text("This is Fragment A"+navController.currentDestination?.route)
        Button(onClick = { navController.navigate("fragment_b") }) {
            Text("Go to Fragment B")
        }
    }
}

@Composable
fun FragmentB(navController: NavController) {
    Column {
        Text("This is Fragment B")
        PieChartView()
        Button(onClick = { navController.navigate("fragment_a") }) {
            Text("Go to Fragment A")
        }
    }
}

@Composable
fun PieChartView() {
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
                val entries = listOf(
                    PieEntry(30f, "A"),
                    PieEntry(40f, "B"),
                    PieEntry(20f, "C"),
                    PieEntry(10f, "D")
                )

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
fun DashScreen( modifier: Modifier = Modifier) {

    DashboardNavigation()


}

@Preview(showBackground = true)
@Composable
fun testpreview(){
    ChaiPaisaTheme {
        DashScreen()
    }
}
