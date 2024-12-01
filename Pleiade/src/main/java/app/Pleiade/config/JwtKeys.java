package app.Pleiade.config;

import io.jsonwebtoken.SignatureAlgorithm;

public class JwtKeys {

    public static final String SECRET_KEY = "UMACHAVESECRETADASUAAPIAQUIUMACHAVESECRETADASUAAPIAQUIUMACHAVESECRETADASUAAPIAQUIUMACHAVESECRETADASUAAPIAQUI";
    public static final SignatureAlgorithm ALGORITMO_ASSINATURA = SignatureAlgorithm.HS256;
    public static final int HORAS_EXPIRACAO_TOKEN = 1;

}
