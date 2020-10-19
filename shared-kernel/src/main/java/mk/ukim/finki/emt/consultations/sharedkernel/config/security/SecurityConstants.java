package mk.ukim.finki.emt.consultations.sharedkernel.config.security;

public class SecurityConstants {

    public static final String SECRET = "f1nk13mtc0nsultat10n5";

    public static final long EXPIRATION_TIME = 1_800_000; // 10 days

    public static final long RENEWAL_TIME = 1_200_000;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public static final String IDENTIFIER_HEADER = "Identifier";

    public static final String ROLE_HEADER = "Role";

}
