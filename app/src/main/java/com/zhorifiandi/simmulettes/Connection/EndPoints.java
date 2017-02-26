package com.zhorifiandi.simmulettes.Connection;

/**
 * Created by user-ari on 2/21/2017.
 */

public class EndPoints {
    public static final String IP_ADDRESS = "krisizho-mixer.hol.es";
    public static final String URL_REGISTER_DEVICE = "http://"+IP_ADDRESS+"/RegisterDevice.php";
    public static final String URL_SEND_SINGLE_PUSH = "http://"+IP_ADDRESS+"/sendSinglePush.php";
    public static final String URL_SEND_MULTIPLE_PUSH = "http://"+IP_ADDRESS+"/sendMultiplePush.php";
    public static final String URL_FETCH_DEVICES = "http://"+IP_ADDRESS+"/GetRegisteredDevices.php";
    public static final String URL_LOG_OUT = "http://"+IP_ADDRESS+"/LogoutDevice.php";
}
