/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
	import java.io.IOException;
	import java.io.UnsupportedEncodingException;
	import java.net.*;
	import java.util.Scanner;
package http.client;

/**
 *
 * @author gferjani
 */
	public class Client {
		private String nomServ="localhost";
		private String msg="Hello serveur RX302";
		private InetAddress IPServeur;
		private DatagramSocket cli;
		private DatagramPacket DTG;
		private int portServeur;
		private byte[] buffer;
		public Client() {
			try{
				cli=new DatagramSocket();
			}
			catch(SocketException ex)
			{
				System.out.println("port déjà occupé");
			}
			buffer=new byte[512];
			try {
				IPServeur=IPServeur.getByName(nomServ);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				buffer=msg.getBytes("ascii");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public void run()
		{
			//**************PARTIE INITIALISATION*****************
			String s="";
			this.envoiServeur();
			this.recoiServeur();
			portServeur=DTG.getPort();
			IPServeur=DTG.getAddress();
			System.out.println("Serveur:"+DTG.getAddress().toString()+" port:"+DTG.getPort()+"\n");
			this.transmoData_String(s);
			System.out.println(s+"\n");
			//**************PARTIE SCANNER*************************
			Scanner sc = new Scanner(System.in);
			System.out.println("Veuillez saisir un mot :");
			msg = sc.nextLine();
			this.transmoString_Data();
			this.envoiServeur();
		}
		public String transmoData_String(String s)
		{
			try {
				 s=new String(DTG.getData(),"ascii");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return s;
		}
		public void transmoString_Data()
		{
			try {
				buffer=msg.getBytes("ascii");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		public void envoiServeur()
		{
			DTG=new DatagramPacket(buffer,buffer.length,IPServeur,1025);
			try {
				cli.send(DTG);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public void recoiServeur()
		{
			buffer=new byte[512];
			DTG=new DatagramPacket(buffer,buffer.length);
			try {
				cli.receive(DTG);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public static void main(String [] args)
		{
			Client c=new Client();
			c.run();
		}
	}

}
