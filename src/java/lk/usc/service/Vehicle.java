/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.usc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.annotation.XmlSeeAlso;
import lk.usc.dto.CommonResponse;
import lk.usc.dto.VehicleDto;
import lk.usc.dto.VehiclesDto;
import lk.usc.persistence.HistoryPresistense;
import lk.usc.persistence.UserPersistence;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author sachin
 */
@XmlSeeAlso({CommonResponse.class, VehicleDto[].class})
@WebService(serviceName = "Vehicle")
public class Vehicle {

    private TrustManager[] trustManager() {
        TrustManager[] managers = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
        };
        return managers;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "search")
    public CommonResponse search(@WebParam(name = "model") String model, @WebParam(name = "api") String api, @WebParam(name = "brand") String brand, @WebParam(name = "location") String location, @WebParam(name = "usedType") String usedType, @WebParam(name = "fuelType") String fuelType, @WebParam(name = "year") String year) {
        CommonResponse resp = new CommonResponse();
        try {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            SSLContext ssl = SSLContext.getInstance("SSL");
            ssl.init(null, trustManager(), new SecureRandom());
            SSLSocketFactory socketFactory = ssl.getSocketFactory();
            clientBuilder.sslSocketFactory(socketFactory, (X509TrustManager) trustManager()[0]);
            clientBuilder.hostnameVerifier((String string, SSLSession ssls) -> true);

            clientBuilder.setConnectTimeout$okhttp(8000);
            clientBuilder.setReadTimeout$okhttp(8000);
            OkHttpClient client = clientBuilder.build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ikman-api.azurewebsites.net/api/v1/vehicles").newBuilder();
            HttpUrl.Builder riyasewanaUrl = HttpUrl.parse("https://riyasewana-api.azurewebsites.net/api/v1/vehicles").newBuilder();
            HttpUrl.Builder patpatUrl = HttpUrl.parse("https://patpat-api.azurewebsites.net/api/v1/vehicles").newBuilder();
            StringBuilder history = new StringBuilder();
            
            if (model != null && model.length() != 0 ) {
                urlBuilder.addQueryParameter("model", model);
                riyasewanaUrl.addQueryParameter("model", model);
                patpatUrl.addQueryParameter("model", model);
                history.append("Model: ");
                history.append(model);
                history.append(" | ");
            }
            if (brand != null && brand.length() != 0) {
                urlBuilder.addQueryParameter("brand", brand);
                riyasewanaUrl.addQueryParameter("brand", brand);
                patpatUrl.addQueryParameter("brand", brand);
                history.append("Brand: ");
                history.append(brand);
                history.append(" | ");
            }
            if (location != null && location.length() != 0) {
                urlBuilder.addQueryParameter("location", location);
                riyasewanaUrl.addQueryParameter("location", location);
                patpatUrl.addQueryParameter("location", location);
                history.append("Location: ");
                history.append(location);
                history.append(" | ");
            }
            if (usedType != null && usedType.length() != 0) {
                urlBuilder.addQueryParameter("usedType", usedType);
                riyasewanaUrl.addQueryParameter("usedType", usedType);
                patpatUrl.addQueryParameter("usedType", usedType);
                history.append("Used Type: ");
                history.append(usedType);
                history.append(" | ");
            }
            if (fuelType != null && fuelType.length() != 0) {
                urlBuilder.addQueryParameter("fuelType", fuelType);
                riyasewanaUrl.addQueryParameter("fuelType", fuelType);
                patpatUrl.addQueryParameter("fuelType", fuelType);
                history.append("Fuel Type: ");
                history.append(fuelType);
                history.append(" | ");
            }
            if (year != null && year.length() != 0) {
                urlBuilder.addQueryParameter("year", year);
                riyasewanaUrl.addQueryParameter("year", year);
                patpatUrl.addQueryParameter("year", year);
                history.append("Year: ");
                history.append(year);
                history.append(" | ");
            }

            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, "API Key ===> {0}", api);
            
            if (api != null && !api.isEmpty()) {
                String userId = UserPersistence.getUserByApiKey(api);
                boolean save = HistoryPresistense.save(userId, history.toString());
                if (save) {
                    Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, "Search Histroy ===> {0}", history.toString());
                }
            }

            String url = urlBuilder.build().toString();
            String reiyasewana = riyasewanaUrl.build().toString();
            String patpat = patpatUrl.build().toString();
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, "Ikman.lk URL ===> {0}", url);
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, "Riyasewana.lk URL ===> {0}", reiyasewana);
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, "Patpat.lk URL ===> {0}", patpat);

            VehiclesDto data = new VehiclesDto();
            ObjectMapper mapper = new ObjectMapper();
            List<VehicleDto> list = new ArrayList<>();
            
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder()
                                .url(url)
                                .get()
                                .build();
                        Response response = client.newCall(request).execute();
                        VehiclesDto respData = mapper.readValue(response.body().charStream(), VehiclesDto.class);
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Ikman.lk DATA ===> {0}", Arrays.toString(respData.getBody()));
                        list.addAll(Arrays.asList(respData.getBody()));
                    } catch (IOException ex) {
                        Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder()
                                .url(reiyasewana)
                                .get()
                                .build();
                        Response response = client.newCall(request).execute();
                        VehiclesDto respData = mapper.readValue(response.body().charStream(), VehiclesDto.class);
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Riyasewana.lk DATA ===> {0}", Arrays.toString(respData.getBody()));
                        list.addAll(Arrays.asList(respData.getBody()));
                    } catch (IOException ex) {
                        Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            Thread t3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder()
                                .url(patpat)
                                .get()
                                .build();
                        Response response = client.newCall(request).execute();
                        VehiclesDto respData = mapper.readValue(response.body().charStream(), VehiclesDto.class);
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Patpat.lk DATA ===> {0}", Arrays.toString(respData.getBody()));
                        list.addAll(Arrays.asList(respData.getBody()));
                    } catch (IOException ex) {
                        Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            t1.start();
            t2.start();
            t3.start();
            
            t1.join();
            t2.join();
            t3.join();
            
            VehicleDto[] emptyArray = new VehicleDto[list.size()];
            VehicleDto[] arr = list.toArray(emptyArray);
            
            data.setBody(arr);
            
            resp.setStatus(data.getStatus());
            resp.setMessage(data.getMessage());
            resp.setData(data.getBody());
        } catch (NoSuchAlgorithmException | KeyManagementException | SQLException | InterruptedException ex) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resp;
    }

}
