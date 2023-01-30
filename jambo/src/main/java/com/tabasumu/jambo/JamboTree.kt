package com.tabasumu.jambo

import android.app.Application
import com.tabasumu.jambo.data.JamboLog
import com.tabasumu.jambo.data.LogType
import com.tabasumu.jambo.helpers.NotificationHelper
import com.tabasumu.jambo.helpers.getLogType
import com.tabasumu.jambo.ui.JamboViewModel
import timber.log.Timber
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @project Jambo
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Tue Jan 2023
 */
class JamboTree @JvmOverloads constructor(
    private val application: Application,
    private val enableNotifications: Boolean = false
) : Timber.DebugTree(), Thread.UncaughtExceptionHandler {

    private val viewModel = JamboViewModel(application)
    private val notificationHelper = NotificationHelper(application)
    private val oldHandler = Thread.getDefaultUncaughtExceptionHandler()

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    private fun saveLog(log: JamboLog) {
        viewModel.saveLog(log)
        if (enableNotifications) notificationHelper.notify(log)
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)
        saveLog(
            JamboLog(
                tag = tag.toString(),
                packageName = application.packageName,
                message = message,
                type = getLogType(priority)
            )
        )
    }

    private fun getStackTraceString(t: Throwable): String {
        // Don't replace this with Log.getStackTraceString() - it hides
        // UnknownHostException, which is not what we want.
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        saveLog(
            JamboLog(
                tag = Exception(throwable).localizedMessage,
                packageName = application.packageName,
                message = getStackTraceString(throwable),
                type = LogType.ERROR
            )
        )
        oldHandler?.uncaughtException(thread, throwable)
    }

}