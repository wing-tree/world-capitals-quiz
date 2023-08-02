package wing.tree.world.capitals.quiz

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar

fun calendar(timeInMillis: Long? = null): Calendar = Calendar.getInstance().apply {
    timeInMillis?.let {
        this.timeInMillis = it
    }
}

fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}

fun shareApp(context: Context) {
    val intent = Intent(Intent.ACTION_SEND)
    val text = "https://play.google.com/store/apps/details?id=${context.packageName}"

    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)

    Intent.createChooser(intent, context.getString(R.string.share_the_app)).also {
        it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        context.startActivity(it)
    }
}
