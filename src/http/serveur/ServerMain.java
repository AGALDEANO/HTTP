/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package http.serveur;

/**
 *
 * @author 4lexandre
 */
public class ServerMain {
    public static void main(String[] args)
    {
        String request = "GET /dfsdgfqf.html";
        String response = Web.byteToString(Web.get(request.split(" ")));
        System.out.println("\n"+request+"\n");
        System.out.println(response);
        request = "GET 127.0.0.1/index.html";
        response = Web.byteToString(Web.get(request.split(" ")));
        System.out.println("\n"+request+"\n");
        System.out.println(response);
        request = "GET 127.0.0.1/src\\html\\Main.java";
        response = Web.byteToString(Web.get(request.split(" ")));
        System.out.println("\n"+request+"\n");
        System.out.println(response);
    }
}
