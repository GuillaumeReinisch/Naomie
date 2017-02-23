import org.json4s._
import org.json4s.jackson.JsonMethods._

val urlBase ="http://api.worldweatheronline.com/premium/v1/past-weather.ashx?key=05d283ea10134f78b0c182351172002&q=%s&format=json&date=%s&enddate=%s&tp=12"
val city  = "Austin"
val dates = List(
  ("2016-01-01","2016-01-31"),
  ("2016-02-01","2016-02-29"),
  ("2016-03-01","2016-03-31"),
  ("2016-04-01","2016-04-30"),
  ("2016-05-01","2016-05-31"),
  ("2016-06-01","2016-06-30"),
  ("2016-07-01","2016-07-31"),
  ("2016-08-01","2016-08-31"),
  ("2016-09-01","2016-09-30"),
  ("2016-10-01","2016-10-31"),
  ("2016-11-01","2016-11-30"),
  ("2016-12-01","2016-12-31")
)
val urls = dates.map{ case(start,end) => urlBase.format(city,start,end) }

val monthlyAverage = for( url <- urls ) yield {

  val values : List[(Double, Double, Double, Double, Double)]= for {
    JObject(child) <- parse( scala.io.Source.fromURL(url).mkString)
    JField("tempC", JString(temp)) <- child
    JField("humidity", JString(humidity)) <- child
    JField("pressure", JString(pressure)) <- child
    JField("windspeedKmph", JString(windspeedKmph)) <- child
    JField("cloudcover", JString(cloudcover)) <- child
  } yield (temp.toDouble,humidity.toDouble,pressure.toDouble,cloudcover.toDouble,windspeedKmph.toDouble)

  val zeroList = (0.0,0.0,0.0,0.0,0.0);
  values.foldRight(zeroList){case((a,b,c,d,e),(f,g,h,i,j)) => (a+f/values.length,b+g/values.length,c+h/values.length,d+i/values.length,e+j/values.length) }
}
