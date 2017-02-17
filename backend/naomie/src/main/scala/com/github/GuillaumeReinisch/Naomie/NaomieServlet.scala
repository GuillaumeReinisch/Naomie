package com.github.GuillaumeReinisch.Naomie

class NaomieServlet extends NaomieStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Naomie</a>.
      </body>
    </html>
  }

}
