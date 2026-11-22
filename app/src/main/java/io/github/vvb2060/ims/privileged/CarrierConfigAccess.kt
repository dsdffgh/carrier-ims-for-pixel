package io.github.vvb2060.ims.privileged

import android.os.PersistableBundle
import android.telephony.CarrierConfigManager
import android.telephony.TelephonyFrameworkInitializer
import android.util.Log
import com.android.internal.telephony.ICarrierConfigLoader
import rikka.shizuku.ShizukuBinderWrapper

internal object CarrierConfigAccess {
    private const val TAG = "CarrierConfigAccess"

    fun getConfigForSubId(
        subId: Int,
        callingPackage: String,
        manager: CarrierConfigManager,
    ): PersistableBundle? {
        return try {
            carrierConfigLoader().getConfigForSubId(subId, callingPackage)
        } catch (t: Throwable) {
            Log.w(TAG, "ICarrierConfigLoader getConfigForSubId failed for subId=$subId", t)
            manager.getConfigForSubId(subId)
        }
    }

    @Throws(Exception::class)
    fun applyOverrideConfig(
        manager: CarrierConfigManager,
        subId: Int,
        values: PersistableBundle?,
        preferPersistent: Boolean,
        callerTag: String,
    ) {
        if (!preferPersistent) {
            invokeOverrideConfig(manager, subId, values, persistent = false)
            return
        }
        try {
            invokeOverrideConfig(manager, subId, values, persistent = true)
            Log.i(callerTag, "overrideConfig persistent success for subId $subId")
        } catch (persistentError: Throwable) {
            Log.w(
                callerTag,
                "overrideConfig persistent failed for subId $subId, fallback to non-persistent",
                persistentError
            )
            try {
                invokeOverrideConfig(manager, subId, values, persistent = false)
                Log.i(callerTag, "overrideConfig fallback non-persistent success for subId $subId")
            } catch (fallbackError: Throwable) {
                fallbackError.addSuppressed(persistentError)
                throw fallbackError
            }
        }
    }

    @Throws(Exception::class)
    private fun invokeOverrideConfig(
        manager: CarrierConfigManager,
        subId: Int,
        values: PersistableBundle?,
        persistent: Boolean,
    ) {
        val loaderError = try {
            carrierConfigLoader().overrideConfig(subId, values, persistent)
            null
        } catch (t: Throwable) {
            t
        }
        if (loaderError == null) return

        Log.w(TAG, "ICarrierConfigLoader overrideConfig failed for subId=$subId", loaderError)
        try {
            manager.javaClass.getMethod(
                "overrideConfig",
                Int::class.javaPrimitiveType,
                PersistableBundle::class.java,
                Boolean::class.javaPrimitiveType
            ).invoke(manager, subId, values, persistent)
        } catch (_: NoSuchMethodException) {
            manager.javaClass.getMethod(
                "overrideConfig",
                Int::class.javaPrimitiveType,
                PersistableBundle::class.java
            ).invoke(manager, subId, values)
        }
    }

    private fun carrierConfigLoader(): ICarrierConfigLoader {
        val serviceManager = TelephonyFrameworkInitializer.getTelephonyServiceManager()
            ?: throw IllegalStateException("telephony service manager unavailable")
        val registerer = serviceManager.getCarrierConfigServiceRegisterer()
            ?: serviceManager.carrierConfigServiceRegisterer
            ?: throw IllegalStateException("carrier config service registerer unavailable")
        val binder = registerer.get()
            ?: throw IllegalStateException("carrier config service unavailable")
        return ICarrierConfigLoader.Stub.asInterface(ShizukuBinderWrapper(binder))
            ?: throw IllegalStateException("ICarrierConfigLoader unavailable")
    }
}
