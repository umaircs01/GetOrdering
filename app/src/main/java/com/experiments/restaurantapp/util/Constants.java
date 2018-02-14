package com.experiments.restaurantapp.util;

/**
 * Created by Fenil on 21-Nov-16.
 */

public class Constants {

    public interface Delays {
        long SPLASH_DELAY = 1500;
    }

    public interface BundleExtras {
        String SAVED_SEARCH_REQUEST = "saved_search_request";
    }

    public interface Urls{
        String TAKKI_WEBSITE = "http://www.mytakki.com";
        String TERMS_URL = "http://www.nuroworks.com/takki/legal/termsandconditions.html";
        String PRIVACY_URL = "http://www.nuroworks.com/takki/legal/privacy.html";
        String COPYRIGHT_URL = "http://www.nuroworks.com/takki/legal/copyright.html ";
        String FAQ_URL = "http://www.nuroworks.com/takki/legal/faq.html";
    }
    public interface LocationConfig{
        int HIGH_ACCURACY = 0;
        int BALANCED_ACCURACY = 1;
        int LOW_ACCURACY = 2;
    }
}
