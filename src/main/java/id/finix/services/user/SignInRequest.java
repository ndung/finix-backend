package id.finix.services.user;

public class SignInRequest {
    private String username;
    private String publicKey;

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}

