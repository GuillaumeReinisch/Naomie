import com.github.GuillaumeReinisch.Naomie._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new NaomieSwagger

  override def init(context: ServletContext) {
    context.mount(new NaomieServlet, "/naomie/*");
    context mount (new ResourcesApp, "/api-docs/*");
  }
}
