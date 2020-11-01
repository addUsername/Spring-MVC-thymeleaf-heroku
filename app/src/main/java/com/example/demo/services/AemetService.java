package com.example.demo.services;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.utils.AemetDTO;
import com.example.demo.utils.AemetDTOWeek;
import com.example.demo.utils.AemetDTOWeek2;
import com.example.demo.utils.AemetServiceDTO;
import com.google.gson.Gson;

@Component
public class AemetService {
	
	private RestTemplate restTemplate;    
	private String url1;
	private String url2;
	private String urlDay;
	private String urlWeek;
	private String token;
    
    public AemetService() {}
    
    public void setIdema(String idema){
    	makeUrls(idema);
    	trustSelfSignedSSL();
    }
    private void makeUrls(String idema) {
    	//LEL, dude trust me
    	
    	this.url1 = urlDay+"/"+idema+"?api_key="+token;
    	
    	String[] a = urlWeek.split("###");
    	Date fechaFin = new Date();
        Date fechaIni = new Date(fechaFin.getTime() - (1000 * 60 * 60 * 24 * 7)); //7 dias
    	String ini = String.format("%d-%02d-%02dT00:00:00UTC",fechaIni.getYear()+1900,fechaIni.getMonth(),fechaIni.getDate());
    	String end = String.format("%d-%02d-%02dT00:00:00UTC",fechaFin.getYear()+1900,fechaFin.getMonth(),fechaFin.getDate());
    	
    	this.url2 = (a[0]+ini+a[1]+end+a[2]+"/"+idema+"?api_key="+token);
    	System.out.println(url2);
	}

	//THis is super cool and usefull, first it gets variables from application.propeties (w/o @Whatever)
    //and then trimming whitespaces bc yes
    @Autowired
    public AemetService(@Value("${aemet.urlDay}")String urlDay, @Value("${aemet.urlWeek}") String urlWeek,@Value("${aemet.token}") String token) {
		super();
		this.urlDay = urlDay.substring(1, urlDay.length()-2);
		this.urlWeek = urlWeek.substring(1, urlWeek.length()-2);
		this.token = token.substring(1, token.length()-1);
		this.restTemplate = new RestTemplate();
	}
	//Esto es para evitar SSL cetificados.. en prod hay que qitarlo xd 
    //https://stackoverflow.com/questions/1725863/why-cant-i-find-the-truststore-for-an-ssl-handshake
    public static void trustSelfSignedSSL() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
				
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
			};
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLContext.setDefault(ctx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Async
    public Future<AemetDTO[]> getData() {
    	return new AsyncResult<AemetDTO[]>(
    			parseDate(getMoreData(getPostsPlainJSON(url1))
    			));    	
    }
    @Async
	public Future<AemetDTOWeek2[]> getDataWeek() {		
		
		return new AsyncResult<AemetDTOWeek2[]>(
				getMoreData2(getPostsPlainJSON(url2))
    			);  
	}

	private AemetDTO[] parseDate(AemetDTO[] moreData) {
    	
    	for(AemetDTO row: moreData) {
    		//2020-10-30T21:00:00
    		String[] a = row.getFint().split("-");
    		String[] b = a[2].split(":");
    		String[] c = b[0].split("T");
    		//Y M D H M Sec
    		Date day = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1])-1, Integer.parseInt(c[0]),
    				Integer.parseInt(c[1]), Integer.parseInt(b[1]), Integer.parseInt(b[2]));
    		row.setFint(""+day.getTime());
    	}
		return moreData;
	}

	private AemetDTO[] getMoreData(String url) {
    	Gson gson = new Gson();
    	//por usar gson..
        String resultado = restTemplate.getForObject(url, String.class);	
		AemetDTO[] response = gson.fromJson(resultado, AemetDTO[].class);
		return response;		
    }
    private AemetDTOWeek2[] getMoreData2(String url) {
    	Gson gson = new Gson();
        String resultado = restTemplate.getForObject(url, String.class);
        System.out.println(resultado);
		AemetDTOWeek[] responseRaw = gson.fromJson(resultado, AemetDTOWeek[].class);
		AemetDTOWeek2 [] response = new AemetDTOWeek2[responseRaw.length];
		for(int i = 0; i<response.length;i++) {
			response[i] = new AemetDTOWeek2(responseRaw[i]);
		}
		return response;
	}
    private String getPostsPlainJSON(String url) {
    	Gson gson = new Gson();
    	System.out.println(url);
        String resultado = restTemplate.getForObject(url, String.class);	
        System.out.println(resultado);
		AemetServiceDTO response = gson.fromJson(resultado, AemetServiceDTO.class);
		return response.getDatos();
    }
    
}
