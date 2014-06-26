/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.serveur;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.serveur;

import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.serveur;

import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.serveur;

import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 4lexandre
 */
public class Serveur extends Thread {

    private final int port;
    private ServerSocket SServeur;
    private Socket SClient;
    private OutputStream OS;
    private InputStream IS;
    private final int size = 1024;
    private final byte[] buffer = new byte[size];
    private byte[] response;
    private final ArrayList<byte[]> inputBuffer = new ArrayList<>();
    private byte[] request;
    private String[] requestParts;
    private byte[] file;

    public Serveur(int _port) {
        port = _port;
        try {
            SServeur = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
        SClient = new Socket();
    }

    public Serveur() {
        port = 80;
        try {
            SServeur = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
        SClient = new Socket();
    }

    @Override
    public void run() {
        try {
            boolean fin = false;
            SClient = SServeur.accept();
            do {
                try {

                    IS = SClient.getInputStream();
                    OS = SClient.getOutputStream();

                    System.out.println();
                    request = Web.inputToRequest(IS);

                    requestParts = Web.requestToString(request);
                    if (requestParts.length < 2) {
                        response = Web.createErrorResponse(400);
                    } else if ("GET".equals(requestParts[0])) {
                        response = Web.get(requestParts);
                    }
                    System.out.println("============================================================");
                    for (int i = 0; i < response.length; i++) {
                        System.out.print((char) response[i]);
                    }
                    OS.write(response);
                    OS.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
                    fin = true;
                }
            } while (!fin);
            SClient.close();
            SServeur.close();
        } catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
