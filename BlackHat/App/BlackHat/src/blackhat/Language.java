/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackhat;

import java.util.ResourceBundle;

/**
 *
 * @author abaza
 */
public class Language {
    public static final TextMessage.Language KEY_LANGAUGAE = TextMessage.Language.EN;
    public static final TextMessage tt = new TextMessage(KEY_LANGAUGAE);
    public static final ResourceBundle local = tt.StartLanguage(tt.getAppLanguage());
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public String getSystemName() {
        return OS;
    }

    public ResourceBundle GetLocal() {
        return local;
    }
}
