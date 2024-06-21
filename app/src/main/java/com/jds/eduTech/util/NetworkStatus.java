package com.jds.eduTech.util;

import static android.content.Context.WIFI_SERVICE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import androidx.annotation.NonNull;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class NetworkStatus extends ConnectivityManager.NetworkCallback {

  final NetworkRequest networkRequest;
  static boolean status=false;
  static ConnectivityManager connectivityManager;
  public NetworkStatus(){
    networkRequest = new NetworkRequest.Builder()
      .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
      .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
      .build();
  }

  public void enable(Context context) {
    connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager != null) {
      connectivityManager.registerNetworkCallback(networkRequest, this);
    }
  }

  @Override
  public void onAvailable(@NonNull Network network) {
    super.onAvailable(network);
    status=true;
    status();
  }

  @Override
  public void onLost(@NonNull Network network) {
    super.onLost(network);
    status=false;
    status();
  }

  @Override
  public void onUnavailable() {
    super.onUnavailable();
    status=false;
    status();
  }

  public boolean status(){
    return status;
  }
  public static String GetDeviceip(Context context) {
    String ip = "";
    try {

      if(NetworkStatus.status){
        String networkType=connectivityManager.isActiveNetworkMetered() ? "LTE" : "WIFI";
        if(networkType.equalsIgnoreCase("WIFI")){
          System.out.println("IP address wifi:: " + GetDeviceipWiFiData(context));
          ip = GetDeviceipWiFiData(context);
        }else{
          System.out.println("IP address mobile data:: " + GetDeviceipMobileData(true));
          ip = GetDeviceipMobileData(true);
        }
      }
    }catch (Exception e){
      System.out.println("IP Exception:: " + e.getLocalizedMessage());
    }
    return ip;
  }



  public static String GetDeviceipMobileData(boolean useIPv4) {
    try {
      List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
      for (NetworkInterface intf : interfaces) {
        List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
        for (InetAddress addr : addrs) {
          if (!addr.isLoopbackAddress()) {
            String sAddr = addr.getHostAddress();
            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
            boolean isIPv4 = sAddr.indexOf(':')<0;

            if (useIPv4) {
              if (isIPv4)
                return sAddr;
            } else {
              if (!isIPv4) {
                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
              }
            }
          }
        }
      }
    } catch (Exception ignored) { } // for now eat exceptions
    return "";
  }



  public static String GetDeviceipWiFiData(Context context)
  {

    WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);

    @SuppressWarnings("deprecation")

    String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

    return ip;

  }

}
