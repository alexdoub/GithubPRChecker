package alex.com.githubchecker.components.app.api.model;

import timber.log.Timber;

/**
 * Created by Alex on 11/17/2017.
 */

public class Diff extends BaseModel {

    private static String KEY_META_DIFF = "diff";
    private static String KEY_META_INDEX = "index";
    private static String KEY_META_SUBTRACTION = "---";
    private static String KEY_META_ADDITION = "+++";
    private static String KEY_META_LINE = "@@";
    private static char KEY_ADDITION = '+';
    private static char KEY_SUBTRACTION = '-';


    private String additions;
    private String subtractions;

    private Diff(String additions, String subtractions) {
        this.additions = additions;
        this.subtractions = subtractions;
    }

    public static Diff Parse(String content) {

        StringBuilder additionsBuilder = new StringBuilder("");
        StringBuilder subtractionsBuilder = new StringBuilder("");

        String[] contentArr = content.split("\n");
        Timber.d("Diff parsed content of length: " + content.length() + " into " + contentArr.length + " lines");
        for (String str : contentArr) {

            boolean isMetadataLine = false;

            //Metadata
            if (str.startsWith(KEY_META_DIFF)) {
                Timber.v("Diff.Parse - Skipping meta-DIFF: " + str);
                isMetadataLine = true;
            } else if (str.startsWith(KEY_META_INDEX)) {
                Timber.v("Diff.Parse - Skipping meta-INDEX: " + str);
                isMetadataLine = true;
            } else if (str.startsWith(KEY_META_SUBTRACTION)) {
                Timber.v("Diff.Parse - Skipping meta-SUB: " + str);
                isMetadataLine = true;
            } else if (str.startsWith(KEY_META_ADDITION)) {
                Timber.v("Diff.Parse - Skipping meta-ADD: " + str);
                isMetadataLine = true;
            } else if (str.startsWith(KEY_META_LINE)) {
                Timber.v("Diff.Parse - Skipping meta-LINE: " + str);
                isMetadataLine = true;
            }

            //Content
            if (!isMetadataLine) {
                Timber.v("Diff.Parse - handling content line");
                if (str.charAt(0) == KEY_SUBTRACTION) {
                    subtractionsBuilder.append(str);
                } else if (str.charAt(0) == KEY_ADDITION) {
                    additionsBuilder.append(str);
                } else {
                    subtractionsBuilder.append(str);
                    additionsBuilder.append(str);
                }

                subtractionsBuilder.append("\n");
                additionsBuilder.append("\n");
            }
        }

        return new Diff(additionsBuilder.toString(), subtractionsBuilder.toString());
    }

    public String getAdditions() {
        return additions;
    }

    public String getSubtractions() {
        return subtractions;
    }

}
