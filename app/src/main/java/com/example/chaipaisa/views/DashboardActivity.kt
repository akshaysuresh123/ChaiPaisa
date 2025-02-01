package com.example.chaipaisa.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chaipaisa.ui.theme.ChaiPaisaTheme
import com.example.chaipaisa.viewmodels.DashboardViewmodel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import com.example.chaipaisa.customcompose.*

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
        composable(
            route = "B/{channelName}",
            arguments = listOf(navArgument("channelName") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val channelName  = backStackEntry.arguments?.getString("channelName") ?: ""
            FragmentB(navController, channelName)
        }
        composable("singeluserpayment/{user_upi}",   arguments = listOf(navArgument("user_upi") {
            type = NavType.StringType
        })
        ){ backStackEntry ->
            val user_upi = backStackEntry.arguments?.getString("user_upi") ?: ""
            SingleUserFragment(navController,user_upi) }
    }
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
