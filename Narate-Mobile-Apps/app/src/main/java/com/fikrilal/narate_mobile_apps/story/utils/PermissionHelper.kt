import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity

@Composable
fun rememberPermissionRequester(): PermissionRequester {
    val context = LocalContext.current
    return remember {
        PermissionRequester(context)
    }
}

class PermissionRequester(private val context: Context) {
    private var activity: FragmentActivity? = null

    init {
        if (context is FragmentActivity) {
            activity = context
        }
    }

    fun requestPermissions(permissions: Array<String>, onPermissionResults: (Map<String, Boolean>) -> Unit) {
        val activity = this.activity
        if (activity != null) {
            val permissionsLauncher = activity.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissionsMap: Map<String, Boolean> ->
                onPermissionResults(permissionsMap)
            }
            permissionsLauncher.launch(permissions)
        } else {
            onPermissionResults(permissions.associateWith { false })
        }
    }
}
