package alex.com.githubchecker.models;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;

import alex.com.githubchecker.R;
import alex.com.githubchecker.components.app.GithubCheckerApp;
import timber.log.Timber;

/**
 * Created by Alex on 11/17/2017.
 */

public class Diff {

    private static String KEY_META_DIFF = "diff";
    private static String KEY_META_INDEX = "index";
    private static String KEY_META_SUBTRACTION = "---";
    private static String KEY_META_ADDITION = "+++";
    private static String KEY_META_LINE = "@@";
    private static char KEY_ADDITION = '+';
    private static char KEY_SUBTRACTION = '-';
    private static int SubtractionsColor = 0;
    private static int AdditionsColor = 0;

    private SpannableStringBuilder additions;
    private SpannableStringBuilder subtractions;

    private Diff(SpannableStringBuilder additions, SpannableStringBuilder subtractions) {
        this.additions = additions;
        this.subtractions = subtractions;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static Diff Parse(String content) {

        //Set static colors -- this is only done once
        if (SubtractionsColor == 0 || AdditionsColor == 0) {
            SubtractionsColor = GithubCheckerApp.appComponent.provideContext().getResources().getColor(R.color.diff_subtraction);
            AdditionsColor = GithubCheckerApp.appComponent.provideContext().getResources().getColor(R.color.diff_addition);
        }

        String[] contentArr = content.split("\n");
        Timber.i("Diff parsed content of length: " + content.length() + " into " + contentArr.length + " lines");

        SpannableStringBuilder additionsBuilder = new SpannableStringBuilder("");
        SpannableStringBuilder subtractionsBuilder = new SpannableStringBuilder("");


        //Handling these spans is tricky because it remembers the last span you set
        //You should only set the span color if the color changes
        //If you always set the color, the app slows to a crawl
        //Solution: Only set span when color changes
        boolean lastAdditionSpanColored = false;
        boolean lastSubtractionSpanColored = false;

        boolean currentlyInMetadataBlock = true;
        for (String str : contentArr) {

            //Metadata header start tag
            if (str.startsWith(KEY_META_DIFF)) {
                currentlyInMetadataBlock = true;
            }

            //Content in metadata header
            else if (currentlyInMetadataBlock) {
                if (str.startsWith(KEY_META_INDEX)) {
                    //ignore index metadata
                }
                //Add filename to corresponding side
                else if (str.startsWith(KEY_META_SUBTRACTION)) {

                    subtractionsBuilder.append(str);
                    subtractionsBuilder.append("\n");
                } else if (str.startsWith(KEY_META_ADDITION)) {
                    additionsBuilder.append(str);
                    additionsBuilder.append("\n");
                    currentlyInMetadataBlock = false;
                }
            }

            //Inter-appearing metadata line tags
            else if (str.startsWith(KEY_META_LINE)) {
                //Ignore line metadata
            }

            //Not in metadata block, handle line as content
            //Content must be careful to not create too many spans, as it is SLOW
            else {
                if (str.charAt(0) == KEY_SUBTRACTION) {
                    if (!lastSubtractionSpanColored) {
                        subtractionsBuilder.append(str, new BackgroundColorSpan(SubtractionsColor), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        lastSubtractionSpanColored = true;
                    } else {
                        subtractionsBuilder.append(str);
                    }
                } else if (str.charAt(0) == KEY_ADDITION) {
                    if (!lastAdditionSpanColored) {
                        additionsBuilder.append(str, new BackgroundColorSpan(AdditionsColor), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        lastAdditionSpanColored = true;
                    } else {
                        additionsBuilder.append(str);
                    }
                } else {
                    if (lastSubtractionSpanColored) {
                        subtractionsBuilder.append(str, new BackgroundColorSpan(Color.TRANSPARENT), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        lastSubtractionSpanColored = false;
                    } else {
                        subtractionsBuilder.append(str);
                    }

                    if (lastAdditionSpanColored) {
                        additionsBuilder.append(str, new BackgroundColorSpan(Color.TRANSPARENT), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        lastAdditionSpanColored = false;
                    } else {
                        additionsBuilder.append(str);
                    }
                }

                subtractionsBuilder.append("\n");
                additionsBuilder.append("\n");
            }
        }

        return new Diff(additionsBuilder, subtractionsBuilder);
    }

    public SpannableStringBuilder getAdditionsSpan() {
        return additions;
    }

    public SpannableStringBuilder getSubtractionsSpan() {
        return subtractions;
    }

}
