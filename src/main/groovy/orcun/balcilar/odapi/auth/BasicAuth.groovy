package orcun.balcilar.odapi.auth

class BasicAuth extends Auth {
    private String username
    private String password

    void username(String username) {
        this.username = username
    }

    void password(String password) {
        this.password = password
    }

    @Override
    String getAuthString() {
        String authString = username + ":" + password
        byte[] authEncBytes = Base64.getEncoder().encode(authString.bytes)
        String authStringEnc = new String(authEncBytes)
        return "Basic " + authStringEnc
    }
}
