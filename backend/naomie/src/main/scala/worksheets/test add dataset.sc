import com.github.GuillaumeReinisch.Naomie.forms.DatasetForm
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.json4s.jackson.JsonMethods.compact
import org.json4s.{DefaultFormats, Extraction, Formats}

implicit val formats: Formats = DefaultFormats



val dataset = DatasetForm("weather",Vector("toto","tata","tutu"),
                          Map("temp"->Vector(1.0,2.0,3.0), "humidity"->Vector(1.0,2.0,3.0)))

val urlPost = "http://localhost:8080/naomie/data/addDataset/Austin"
val post = new HttpPost(urlPost)
post.setHeader("Content-type", "application/json")
post.setEntity(new StringEntity(compact( Extraction.decompose(dataset))))
(new DefaultHttpClient).execute(post)
