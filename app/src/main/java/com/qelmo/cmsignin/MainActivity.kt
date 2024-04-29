package com.qelmo.cmsignin

import android.credentials.GetCredentialException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCustomException
import androidx.credentials.exceptions.GetCredentialInterruptedException
import androidx.credentials.exceptions.GetCredentialProviderConfigurationException
import androidx.credentials.exceptions.GetCredentialUnknownException
import androidx.credentials.exceptions.GetCredentialUnsupportedException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.qelmo.cmsignin.ui.theme.CredentialManagerSignInAppTheme
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CredentialManagerSignInAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GoogleSignInButton()
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun GoogleSignInButton() {

    val context = LocalContext.current

    // create code routine scope to make API call request
    val coroutineScope = rememberCoroutineScope()

    val onClick: () -> Unit = {
        Log.i(TAG, "onClick < ")
        val credentialManager = CredentialManager.create(context)

        val googleIdOption: GetSignInWithGoogleOption =
            GetSignInWithGoogleOption.Builder(
                context.getString(R.string.default_web_client_id)
            ).build()

        // Create credential request
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .setPreferImmediatelyAvailableCredentials(false)
            .build()

        coroutineScope.launch {
            try {
                Log.i(TAG, "credentialManager.getCredential")
                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )

                handleGoogleSignIn(result)

                Toast.makeText(context, "Congratulations !! You are signed in!", Toast.LENGTH_SHORT)
                    .show()
            } catch (e : NoCredentialException) {
                Log.e(TAG, "NoCredentialException")
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT)
                    .show()
            } catch (e : GetCredentialException) {
                Log.e(TAG, "GetCredentialException")
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT)
                    .show()
            }  catch (e: GetCredentialCancellationException) {
                Log.e(TAG, "GetCredentialCancellationException")
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT)
                    .show()
            }  catch (e: GetCredentialCustomException) {
                Log.e(TAG, "GetCredentialCustomException")
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT)
                    .show()
            }  catch (e: GetCredentialProviderConfigurationException) {
                Log.e(TAG, "GetCredentialProviderConfigurationException")
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT)
                    .show()
            }  catch (e: GetCredentialInterruptedException) {
                Log.e(TAG, "GetCredentialInterruptedException")
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT)
                    .show()
            }  catch (e: GetCredentialUnknownException) {
                Log.e(TAG, "GetCredentialUnknownException")
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT)
                    .show()
            }  catch (e: GetCredentialUnsupportedException) {
                Log.e(TAG, "GetCredentialUnsupportedException")
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT)
                    .show()
            } catch (e: GoogleIdTokenParsingException) {
                Log.e(TAG, "GoogleIdTokenParsingException")
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        Log.i(TAG, "onClick >")
    }
    Button(onClick = onClick) {
        Text(text = "Signin with Google")
    }
}

fun handleGoogleSignIn(result: GetCredentialResponse) {
    Log.i(TAG, "handleGoogleSignIn")
    val credential = result.credential

    Log.i(TAG, "GoogleIdTokenCredential createFrom")
    val googleIdTokenCredential = GoogleIdTokenCredential
        .createFrom(credential.data)

    val googleIdToken = googleIdTokenCredential.idToken

    Log.i(TAG, "googleIdToken = $googleIdToken")
}