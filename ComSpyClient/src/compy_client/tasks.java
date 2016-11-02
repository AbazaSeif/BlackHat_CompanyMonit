/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compy_client;

import Comman.KeysVer;
import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import Comman.LOCAL_KEYS;
import Comman.SERVER_KEYS;
import java.util.HashMap;
import ui_server.MainControl;

/**
 *
 * @author abaza
 */
public class tasks extends Thread {

    SERVER_KEYS Server_Var = new SERVER_KEYS();
    LOCAL_KEYS Local_Var = new LOCAL_KEYS();
    MainControl GMC;
    boolean Key = false;
    Comman.ConnectionServer Server = null;
    public void init(MainControl MC) {
        Server = new Comman.ConnectionServer();
        this.GMC = MC;
        Key = true;
        start();
        
    }
    public void Stop(){
        Key = false;
        Server = null;
        this.interrupt();
        
    }

    private void getWhoOnline() {
        String[][] Data = {
            {Server_Var.TYPE_COMMENT, Server_Var.OnlineUsers},
            {Local_Var.USERS_SUUID, Args.get(Keys.SUUID)},
            {Local_Var.USERS_STATUS, "1"}
        };
        HashMap<String, String> Ret = Server.Contaner(Data);
        if (Ret != null) {
            KeysVer.ComputersName = new HashMap<>();
            if (Server.DoneOrNot(Ret)) {
                for (int i = 0; i <= Ret.size() - 1; i++) {
                    String reternData = Ret.get(String.valueOf(i));
                    if (reternData != null) {
                        KeysVer.ComputersName.put(i, reternData);
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while (Key) {
            try {
                getWhoOnline();
                this.GMC.WhoConnectedNow();
                Thread.sleep(30000);
            } catch (InterruptedException ex) {
            }
        }
    }

}
