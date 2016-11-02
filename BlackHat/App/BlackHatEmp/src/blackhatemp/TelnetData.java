/*
 * GNU Library or Lesser Public License (LGPL)
 */

package blackhatemp;

/**
 * @author Geoffrey
 */
public class TelnetData {

    public static final int CLIENT = 0;
    public static final int SERVER = 1;

    public static final int HEXADECIMAL = 10;
    public static final int ASCII = 11;

    public static final int NOTHING = 20;
    public static final int NL = 21;
    public static final int CR = 22;
    public static final int CRNL = 23;

    private int styleType;
    // CLIENT : client
    // SERVER : server
    private byte[] data;

    public TelnetData(byte[] data, int styleType) {
        this.data = data;
        this.styleType = styleType;
    }

    public TelnetData(int encoding, String dataString, int suffixType, int styleType) {
        this.styleType = styleType;
        switch (encoding) {
            case HEXADECIMAL:
                dataString = dataString.replaceAll("[^0-9A-Fa-f]", "");
                if (dataString.length() % 2 > 0) {
                    dataString += "0";
                }
                switch (suffixType) {
                    case NOTHING:
                        break;
                    case NL:
                        dataString += "0A";
                        break;
                    case CR:
                        dataString += "0D";
                        break;
                    case CRNL:
                        dataString += "0D0A";
                        break;
                }
                data = new byte[(dataString.length()) / 2];
                for (int i = 0; i < data.length; i++) {
                    int currentInt = Integer.parseInt(dataString.substring((i * 2), (i * 2) + 2), 16);
                    data[i] = (byte) (currentInt > Byte.MAX_VALUE ? currentInt : currentInt - 256);
                }
                break;
            case ASCII:
                switch (suffixType) {
                    case NOTHING:
                        break;
                    case NL:
                        dataString += "\n";
                        break;
                    case CR:
                        dataString += "\r";
                        break;
                    case CRNL:
                        dataString += "\r\n";
                        break;
                }
                data = new byte[dataString.length()];
                for (int i = 0; i < data.length; i++) {
                    int currentChar = dataString.charAt(i) % 256;
                    data[i] = (byte) (currentChar > Byte.MAX_VALUE ? currentChar : currentChar - 256);
                }
                break;
        }
    }

    public byte[] getData() {
        return data;
    }

    public String getString(int encoding) {
        StringBuffer stringBuffer = null;
        
        switch (encoding) {
            case HEXADECIMAL:
                stringBuffer = new StringBuffer(data.length * 3 - 1);
                for (int i = 0; i < data.length; i++) {
                    int dataInt = data[i] >= 0 ? data[i] : 256 + data[i];
                    if (dataInt < 16) {
                        stringBuffer.append("0");
                    }
                    stringBuffer.append(Integer.toString(dataInt, 16).toUpperCase());
                    stringBuffer.append(" ");
                }
                break;
            case ASCII:
                stringBuffer = new StringBuffer(data.length);
                for (int i = 0; i < data.length; i++) {
                    stringBuffer.append((char) (data[i] >= 0 ? data[i] : 256 + data[i]));
                }
                break;
        }
        return stringBuffer.toString();
    }

    public int getStyleType() {
        return styleType;
    }
}
