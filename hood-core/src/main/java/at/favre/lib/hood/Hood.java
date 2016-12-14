package at.favre.lib.hood;


import at.favre.lib.hood.page.Config;
import at.favre.lib.hood.page.DebugPages;
import at.favre.lib.hood.page.Pages;

public class Hood {

    private Hood() {
    }

    public static Pages create(Config config) {
        return DebugPages.Factory.create(config);
    }

}
