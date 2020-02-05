package io.esalenko.github.sample.app.data.persistence

import android.content.Context
import androidx.core.content.edit
import net.openid.appauth.AuthState


class SharedPreferenceManager(ctx: Context) {

    companion object {
        private const val NAME = "gh_shared_prefs"
        private const val AUTH_STATE_JSON = "key_auth_state_json"
    }

    private val sharedPreferences = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun readAuthState(): AuthState? {
        val stateJson = sharedPreferences.getString(AUTH_STATE_JSON, null)
        return if (stateJson != null) {
            AuthState.jsonDeserialize(stateJson)
        } else {
            null
        }
    }

    fun writeAuthState(state: AuthState) {
        sharedPreferences.edit {
            putString(AUTH_STATE_JSON, state.jsonSerializeString())
        }
    }
}
