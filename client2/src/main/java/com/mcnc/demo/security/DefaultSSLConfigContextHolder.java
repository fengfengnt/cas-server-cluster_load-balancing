package com.mcnc.demo.security;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DefaultSSLConfigContextHolder {
	private static Logger logger = LoggerFactory.getLogger(DefaultSSLConfigContextHolder.class);
	
	/**
	 * Remove Host name restriction during SSL validation
	 */
	public static void allowAllHostnamesVerification() {
		// Create all-trusting host name verifier
		HostnameVerifier allowAllHostsVerifier = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allowAllHostsVerifier);
	}
	
	/**
	 * Remove Trust certificate validation on SSL connection
	 */
	public static void allowForAnyConnections() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
				logger.debug("Client is always trusted");
			
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
				logger.debug("Server is always trusted");

			}

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		} };

		// Install the all-trusting trust manager
		// Create custom SSL context
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Removes all any default restriction on SSL connection
	 */
	public static void removeDefaultRestriction() {
		allowAllHostnamesVerification();
		allowForAnyConnections();
	}
}
