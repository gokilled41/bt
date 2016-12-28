def DEFNAME(source1: SOURCETYPE1): OUTPUTTYPE = {
  val retval = source1.map(line => {
    implicit val formats = new org.json4s.DefaultFormats {
      override def dateFormatter = com.vitria.spark.pipe.DateFormatter.getDateFormatter("DATEFORMATTER")
    } + new CustomSerializer[Long](format => (
      { case JString(s) => s.toDouble.toLong },
      { case x: Long => JInt(x) }))
    +new CustomSerializer[Boolean](format => (
      { case JString(s) => s.toBoolean },
      { case x: Boolean => JBool(x) }))
    +new CustomSerializer[Double](format => (
      { case JString(s) => s.toDouble },
      { case x: Double => JDouble(x) }))
    var json = parse(line)
    json = json transformField {
      case JField(name, value) => (com.vitria.spark.pipe.Encoder.encode(name), value)
    }
    TRANSFORM_ARRAY
    json.extract[CASECLASS]
  })
  RESULT
}
