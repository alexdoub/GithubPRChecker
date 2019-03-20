package alex.com.githubchecker.models

import alex.com.githubchecker.R
import alex.com.githubchecker.components.app.GithubCheckerApp
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import timber.log.Timber

/**
 * Created by Alex on 11/17/2017.
 */

class Diff(val additionsSpan: SpannableStringBuilder, val subtractionsSpan: SpannableStringBuilder)

class DiffParser {
    @Suppress("DEPRECATION")
    companion object {

        private const val KEY_META_DIFF = "diff"
        private const val KEY_META_INDEX = "index"
        private const val KEY_META_SUBTRACTION = "---"
        private const val KEY_META_ADDITION = "+++"
        private const val KEY_META_LINE = "@@"
        private const val KEY_ADDITION = '+'
        private const val KEY_SUBTRACTION = '-'
        private val SubtractionsColor: Int by lazy { GithubCheckerApp.appComponent.provideContext().resources.getColor(R.color.diff_subtraction) }
        private val AdditionsColor: Int by lazy { GithubCheckerApp.appComponent.provideContext().resources.getColor(R.color.diff_addition) }

        fun parse(content: String): Diff {

            val contentArr = content.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Timber.i("Diff parsed content of length: ${content.length} into ${contentArr.size} lines")

            val additionsBuilder = SpannableStringBuilder("")
            val subtractionsBuilder = SpannableStringBuilder("")

            //Handling these spans is tricky because it remembers the last span you set
            //You should only set the span color if the color changes
            //If you always set the color, the app slows to a crawl
            //Solution: Only set span when color changes
            var lastAdditionSpanColored = false
            var lastSubtractionSpanColored = false

            var currentlyInMetadataBlock = true
            for (str in contentArr) {
                when {
                    //Metadata header start tag
                    str.startsWith(KEY_META_DIFF) -> currentlyInMetadataBlock = true

                    //Content in metadata header
                    currentlyInMetadataBlock -> when {
                        str.startsWith(KEY_META_INDEX) -> {
                            //ignore index metadata
                        }
                        //Add filename to corresponding side
                        str.startsWith(KEY_META_SUBTRACTION) -> {
                            subtractionsBuilder.append(str)
                            subtractionsBuilder.append("\n")
                        }
                        //Add filename to corresponding side
                        str.startsWith(KEY_META_ADDITION) -> {
                            additionsBuilder.append(str)
                            additionsBuilder.append("\n")
                            currentlyInMetadataBlock = false
                        }
                    }

                    //Inter-appearing metadata line tags
                    str.startsWith(KEY_META_LINE) -> {
                        //Ignore line metadata
                    }

                    //Not in metadata block, handle line as content
                    //Content must be careful to not create too many spans, as it is SLOW
                    else -> {
                        if (str[0] == KEY_SUBTRACTION) {
                            if (!lastSubtractionSpanColored) {
                                subtractionsBuilder.append(str, BackgroundColorSpan(SubtractionsColor), Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                                lastSubtractionSpanColored = true
                            } else {
                                subtractionsBuilder.append(str)
                            }
                        } else if (str[0] == KEY_ADDITION) {
                            if (!lastAdditionSpanColored) {
                                additionsBuilder.append(str, BackgroundColorSpan(AdditionsColor), Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                                lastAdditionSpanColored = true
                            } else {
                                additionsBuilder.append(str)
                            }
                        } else {
                            if (lastSubtractionSpanColored) {
                                subtractionsBuilder.append(str, BackgroundColorSpan(Color.TRANSPARENT), Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                                lastSubtractionSpanColored = false
                            } else {
                                subtractionsBuilder.append(str)
                            }

                            if (lastAdditionSpanColored) {
                                additionsBuilder.append(str, BackgroundColorSpan(Color.TRANSPARENT), Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                                lastAdditionSpanColored = false
                            } else {
                                additionsBuilder.append(str)
                            }
                        }

                        subtractionsBuilder.append("\n")
                        additionsBuilder.append("\n")
                    }
                }
            }

            return Diff(additionsBuilder, subtractionsBuilder)
        }
    }
}
