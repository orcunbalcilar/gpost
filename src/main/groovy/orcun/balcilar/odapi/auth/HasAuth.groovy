package orcun.balcilar.odapi.auth

trait HasAuth {
    Auth auth

    void basicAuth(@DelegatesTo(BasicAuth) Closure closure) {
        auth = new BasicAuth()
        closure.delegate = auth
        closure.run()
    }

    String getAuthString() {
        auth.getAuthString()
    }
}