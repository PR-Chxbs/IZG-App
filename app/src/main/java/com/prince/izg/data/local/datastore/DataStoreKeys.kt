package com.prince.izg.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    val USER_ID = stringPreferencesKey("user_id")

}
