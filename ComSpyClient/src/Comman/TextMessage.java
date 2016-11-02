/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comman;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Seif Abaza
 */
public class TextMessage {

    ResourceBundle txt = null;

    public TextMessage(Language lang) {
        setAppLanguage(lang);
    }

    /**
     * @return the appLanguage
     */
    public Language getAppLanguage() {
        return appLanguage;
    }

    public void setAppLanguage(Language appLanguage) {
        this.appLanguage = appLanguage;
    }

    public static enum Language {
        EN,
        AR,
        RU,
    };
    public Language appLanguage = null;

    private ResourceBundle install(Language lang) {
        if (lang == null) {
            lang = Language.EN;
        }
        switch (lang) {
            case EN:
                Locale defaultLocale = Locale.getDefault();
                txt = ResourceBundle.getBundle("lang.defualtLanguage", defaultLocale);
                break;
            case AR:
                Locale arabiclocale = new Locale("ar", "EG");
                txt = ResourceBundle.getBundle("lang.defualtLanguage", arabiclocale);
                break;
            case RU:
                Locale russianlocale = new Locale("ru", "RU");
                txt = ResourceBundle.getBundle("lang.defualtLanguage", russianlocale);
                break;
            default:
                Locale defaultLocalef = Locale.getDefault();
                txt = ResourceBundle.getBundle("lang.defualtLanguage", defaultLocalef);
                break;

        }
        return txt;
    }

    public ResourceBundle StartLanguage(Language lang) {
        if (txt == null) {
            txt = install(lang);
            return txt;
        } else {
            return txt;
        }
    }
}
