/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.serveur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 4lexandre
 */
public class Web {

    private static byte[] error(int nb) {
        String message = "";
        message += "\nERROR " + nb + "\n";
        switch (nb) {
            case 404:
                message += "File not found";
                break;
            case 400:
                message += "Bad Request";
                break;
            case 418:
                message += "I'm a teapot";
                break;
            case 423:
                message += "Locked";
                break;

        }

        return message.getBytes();
    }

    private static byte[] error(int nb, String message) {
        int i = 0;
        byte[] temp = error(nb);
        byte[] errorData = new byte[message.length() + temp.length];
        while (i < message.length()) {
            errorData[i] = message.getBytes()[i];
            i++;
        }
        while (i < message.length() + temp.length) {
            errorData[i] = errorData[i - message.length()];
            i++;
        }
        return errorData;
    }

    private static byte[] getFile(String filename) {
        try {
            FileInputStream f = new FileInputStream(filename);
            int size = 1024, i, j;
            boolean loop = true;
            int lastSize = 0;
            byte[] file;
            byte[] temp = new byte[size];
            ArrayList<byte[]> tempList = new ArrayList<>();
            do {
                lastSize = f.read(temp);
                if (lastSize < size) {
                    loop = false;
                } else {
                    tempList.add(temp);
                }
            } while (loop);
            file = new byte[size * tempList.size() + lastSize];
            for (i = 0; i < tempList.size(); i++) {
                for (j = 0; j < size; j++) {
                    file[i * tempList.size() + j] = tempList.get(i)[j];
                }
            }
            for (j = 0; j < lastSize; j++) {
                file[i * tempList.size() + j] = temp[j];
            }
            return file;
        } catch (FileNotFoundException ex) {
            return error(404, "File : " + filename);
        } catch (IOException ex) {
            return error(423, "File : " + filename);
        }
    }

    private static String getFileFromRequest(byte[] request) {

        return "";
    }

    public static byte[] get(byte[] request) {
        String filename = getFileFromRequest(request);
        if ("".equals(filename)) {
            return error(400);
        } else {
            return getFile(filename);
        }
    }
}
