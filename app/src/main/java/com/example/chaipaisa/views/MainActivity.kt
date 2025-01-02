package com.example.chaipaisa.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.chaipaisa.R
import com.example.chaipaisa.ui.theme.ChaiPaisaTheme
import com.example.chaipaisa.viewmodels.MainViewmodel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChaiPaisaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewmodel=ViewModelProvider(this)[MainViewmodel::class.java]
                    Greeting(viewmodel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting( viewmodel: MainViewmodel,modifier: Modifier = Modifier) {
    val flag by viewmodel.flag.observeAsState()
    val context = LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = stringResource(id = R.string.logo_des)
    )
    if (flag == true){
        Log.e("GOT VALUE ","TRUE")
        val intent = Intent(context, DashboardActivity::class.java)
        // Start the activity
        context.startActivity(intent)

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChaiPaisaTheme {
//        Greeting()
    }
}