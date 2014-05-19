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
public class Serveur implements Runnable {

    private final int port;
    private ServerSocket SServeur;
    private Socket SClient;
    private OutputStream OS;
    private InputStream IS;
    private final int size = 1024;
    private final byte[] buffer = new byte[size];
    private final ArrayList<byte[]> inputBuffer = new ArrayList<>();
    private byte[] request;

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
            SClient = SServeur.accept();
            IS = SClient.getInputStream();
            int lastSize = 0, i, j;
            boolean loop = true;
            while (loop) {
                lastSize = IS.read(buffer);
                if (lastSize < size) {
                    loop = false;
                } else {
                    inputBuffer.add(buffer);
                }
            }
            request = new byte[size * inputBuffer.size() + lastSize];
            for (i = 0; i < inputBuffer.size(); i++) {
                for (j = 0; j < size; j++) {
                    request[i * inputBuffer.size() + j] = inputBuffer.get(i)[j];
                }
            }
            for (j = 0; j < lastSize; j++) {
                request[i * inputBuffer.size() + j] = buffer[j];
            }
        } catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
