/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * 
 * @author gferjani
 */
public class Client
{
	private String nomServ = "4lexandre-pc";
	private String msg = "Hello serveur RX302";
	private String page="/index.html";
	URL adresseAbsolu=null;
	URLConnection adresseco;
	private ByteArrayOutputStream buffer1;
	private BufferedOutputStream stock;
	private InetAddress IPServeur;
	private Socket cli;
	private OutputStream output;
	private InputStream input;
	private int portServeur=80;
	private byte[] buffer;

	public Client()
	{

		try
		{
			cli = new Socket(nomServ,portServeur);
		} catch (SocketException ex)
		{
			System.out.println("port déjà occupé");
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		buffer = new byte[1024];
		try
		{
			IPServeur = IPServeur.getByName(nomServ);
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			buffer = msg.getBytes("ascii");
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run()
	{
		// **************PARTIE INITIALISATION*****************
		System.out.println(IPServeur);
		
//		try
//		{
//			adresseAbsolu = new URL("http://localhost/index.html");
//		} catch (MalformedURLException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try
//		{
//			adresseco=adresseAbsolu.openConnection();
//		} catch (IOException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		//System.out.println(adresseAbsolu.toString());
		String IP=IPServeur.toString();
		IP=IP.substring("localhost/".length());
		System.out.println(IP);
		this.msg = "GET "+IPServeur+"/ffdsqgdsf.html.html HTTP/1.1\r\n";// normalement
		String msg2="GET 127.0.0.1/index.html";
		System.out.println(msg);
		System.out.println(msg2);
		try
		{
			output=cli.getOutputStream();
			//input=adresseco.getInputStream();
			input=cli.getInputStream();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.envoiServeur();
		//this.recoiServeur();
		this.CreerFichierEcriture();
		System.out.println("Serveur:" + nomServ + " port:"
				+ portServeur + "\n");
		msg=this.transmoData_String(msg);
		System.out.println(msg + "\n");
		
		
		// **************PARTIE SCANNER*************************
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir un mot :");
		msg = sc.nextLine();
		this.transmoString_Data();
		this.envoiServeur();
	}
	public void CreerFichierEcriture()
	{
		File monfichier=new File("C:\\Temp\\monfichier.txt");
		try
		{
			
			BufferedWriter fichier=new BufferedWriter(new FileWriter(monfichier));
			BufferedReader br=new BufferedReader(new InputStreamReader(this.input));
			this.buffer1=new ByteArrayOutputStream(1024);
			this.stock=new BufferedOutputStream(this.buffer1);
			String s;
			//System.out.println(br.readLine());
			while((s=br.readLine())!=null)
			{
			 System.out.println(s);
			 stock.write(s.getBytes(),0,s.length());
			 //fichier.wwrite(s.getBytes());
			}
			fichier.close();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String transmoData_String(String s)
	{
		try {
			 s=new String(buffer,"ascii");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public void transmoString_Data()
	{
		try
		{
			buffer = msg.getBytes("ascii");
		} catch (UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void envoiServeur()
	{
		transmoString_Data();
		try
		{
			output.write(buffer);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void recoiServeur()
	{
		buffer = new byte[1024];
		try
		{
			input.read(buffer);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		Client c = new Client();
		c.run();
	}
}
