/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.serveur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author 4lexandre
 */
public class Web {

    private static String error(int nb) {
        String message = "";
        message += "<h1>ERROR " + nb + "</h1>";
        message += "\n<p>"+getNameCode(nb)+"</p>";
        return message;
    }

    public static String byteToString(byte[] data) {
        String str = "";
        for (int i = 0; i < data.length; i++) {
            str += (char) data[i];
        }
        return str;
    }

    private static String getNameCode(int code) {
        String str;
        switch (code) {
            case 200:
                str = "OK";
                break;
            case 404:
                str = "File not found";
                break;
            case 400:
                str = "Bad Request";
                break;
            case 418:
                str = "I'm a teapot";
                break;
            case 423:
                str = "Locked";
                break;
            default:
                str = "Bad Request";
        }

        return str;
    }

    public static byte[] createResponse(int code, String body) {
        if (code != 200) {
            if (body == null) {
                body = "";
            }
            body += error(code);
        }
        DateFormat fullDateFormatEN = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("EN", "en"));
        String str = "", content = "";
        str += "HTTP/1.1 " + code + " " + getNameCode(code);
        str += "\nServer: 42";
        str += "\nDate: " + fullDateFormatEN.format(new Date());
        str += "\nContent-Type: text/html";
        str += "\nContent-Length: " + body.length();
        str += "\nExpires: " + fullDateFormatEN.format(new Date());
        str += "\nLast-modified: " + fullDateFormatEN.format(new Date());
        str += "\n\n";
        str += body;

        return str.getBytes();
    }
    
    public static byte[] createResponse(int code, byte[] body) {
        return createResponse(code, byteToString(body));
    }

    public static byte[] createErrorResponse(int code) {
        return createResponse(code, "");
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
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    public static String getFileNameFromRequest(String[] request) {
        String filename = "";
        if(request[1].equals("/")) filename = "index.html";
        else
        {
            String[] path = request[1].split("/", 2);
            filename += path[1];
        }
        return filename;
    }

    public static String[] requestToString(byte[] request) {
        String strRequest = "";
        for (int i = 0; i < request.length; i++) {
            strRequest += request[i];
        }
        return strRequest.split(" ");

    }

    public static byte[] get(String[] request) {
        String filename = getFileNameFromRequest(request);
        if ("".equals(filename)) {
            return createResponse(400, error(400).getBytes());
        } else {
            byte[] getFileFromRequest = getFile(filename);
            if(getFileFromRequest == null) return createResponse(404, filename);
            return createResponse(200, getFileFromRequest);
        }
    }

    public static byte[] inputToRequest(InputStream IS) {
        int size = 1024;
        byte[] buffer = new byte[size];
        ArrayList<byte[]> inputBuffer = new ArrayList<>();
        byte[] request;
        int lastSize = 0, i, j;
        boolean loop = true;
        while (loop) {
            try {
                lastSize = IS.read(buffer);
                if (lastSize < size) {
                    loop = false;
                    inputBuffer.add(buffer);
                } else {
                    inputBuffer.add(buffer);
                }
            } catch (IOException ex) {
                Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
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
        return request;
    }
}
