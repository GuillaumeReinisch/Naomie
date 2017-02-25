import com.github.GuillaumeReinisch.Naomie.forms.ScenarioForm
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.json4s.jackson.JsonMethods.compact
import org.json4s.{DefaultFormats, Extraction, Formats}

implicit val formats: Formats = DefaultFormats


val scenario    = ScenarioForm("SF",Map("User"->"Guigui","Context"->"Test"))

val postScenario = new HttpPost("http://localhost:8080/naomie/data/createScenario")
postScenario.setHeader("Content-type", "application/json")
postScenario.setEntity(new StringEntity(compact(Extraction.decompose(scenario))))
(new DefaultHttpClient).execute(postScenario)
