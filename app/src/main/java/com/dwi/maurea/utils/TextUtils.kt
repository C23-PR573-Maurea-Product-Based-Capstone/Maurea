package com.dwi.maurea.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import java.util.regex.Pattern

object TextUtils {

    fun setBoldClickableText(
        textView: TextView,
        @StringRes fullTextResId: Int,
        @StringRes targetTextResId: Int,
        onClickListener: View.OnClickListener
    ) {
        val fullText = textView.context.getString(fullTextResId)
        val spannableString = SpannableString(fullText)
        val targetText = textView.context.getString(targetTextResId)

        val pattern = Pattern.compile(targetText)
        val matcher = pattern.matcher(fullText)

        while (matcher.find()) {
            val startIndex = matcher.start()
            val endIndex = matcher.end()

            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannableString.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        onClickListener.onClick(widget)
                    }
                },
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        textView.text = spannableString
        textView.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }
}