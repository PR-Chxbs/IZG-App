import android.util.Base64
import org.json.JSONObject

fun isJwtExpired(token: String): Boolean {
    return try {
        val parts = token.split(".")
        if (parts.size != 3) return true
        val payload = String(Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING))
        val exp = JSONObject(payload).getLong("exp")
        val now = System.currentTimeMillis() / 1000
        now >= exp
    } catch (e: Exception) {
        e.printStackTrace()
        true
    }
}