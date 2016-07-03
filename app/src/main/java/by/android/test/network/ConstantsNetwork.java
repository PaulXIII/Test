package by.android.test.network;

/**
 * Created by Павел on 03.07.2016.
 */
public class ConstantsNetwork {

    //data for server
    public final static String URLServer = "http://api.giphy.com/";
    public final static String PUBCLIC_KEY = "dc6zaTOxFJmzC";
    public static final String EXTRA_KEY_IN = "by.android.test.In";

    //actions for BroadcastReceiver
    public static final String ACTION_GIFIntentService = "by.android.test.GIFIntentService.RESPONSE";
    public static final String ACTION_SERACH_GIFIntentService = "by.android.test.GIFIntentService.RESPONSE_SERACH";
    public static final String CATEGORY_GIFIntentService = "by.android.test.GIFIntentService.CATEGORY";
    public static final String CATEGORY_SEARCH_GIFIntentService = "by.android.test.GIFIntentService.CATEGORY_SEARCH";

    //keys for start GIFIntentService from MainActivity
    public static final String EXTRA_KEY_MAINACTIVITY_IN = "by.android.test.MainActivity.In";
    public static final String EXTRA_KEY_MAINACTIVITY_OUT = "by.android.test.MainActivity.Out";
    public static final String MAINACTIVITY_IN = "by.android.test.MainActivity.Value";
    public static final String SEARCHABLE_QUERY="by.android.test.MainActivity.Search";

    //keys for start GIFIntentService from SearchableActivity
    public static final String EXTRA_KEY_SEARCHABLEACTIVITY_IN = "by.android.test.SearchableActivity.In";
    public static final String EXTRA_KEY_SEARCHABLEACTIVITY_OUT = "by.android.test.SearchableActivity.Out";
    public static final String SEARCHABLEACTIVITY_IN = "by.android.test.SearchableActivity.Value";
    public static final String SEARCHABLEACTIVITY_QUERY="by.android.test.SearchableActivity.Search";









}
