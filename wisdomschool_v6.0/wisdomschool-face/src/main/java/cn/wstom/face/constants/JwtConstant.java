package cn.wstom.face.constants;

public class JwtConstant {

    public static final String tokenHeader = "Authorization";

    public static final String CLAIM_KEY_USERID = "id";
    public static final String CLAIM_KEY_USERNAME = "username";
    public static final String CLAIM_KEY_CREATED = "created";
    public static final String CLAIM_KEY_HOLDTIME = "holdtime";
    //用于区分token，充当存入redis中的key
    public static final String CLAIM_KEY_GROUP = "group";
}

