package com.github.GuillaumeReinisch.Naomie

import org.scalatra._

class NaomieServlet extends NaomieStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

}
