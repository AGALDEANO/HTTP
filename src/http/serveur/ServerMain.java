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
        Serveur serv = new Serveur();
        serv.start();
    }
}
