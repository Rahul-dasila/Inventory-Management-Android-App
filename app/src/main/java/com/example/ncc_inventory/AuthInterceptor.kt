import android.content.Context
import com.example.ncc_inventory.TokenManager
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = TokenManager.getToken(context)
        val newRequest = token?.let {
            originalRequest.newBuilder()
                .header("x-auth-token", it)
                .build()
        }
        return chain.proceed(newRequest)
    }
}
