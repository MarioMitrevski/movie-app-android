package movieapp.app.util.typeadapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateTypeAdapter : TypeAdapter<LocalDate?>() {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @Throws(IOException::class)
    override fun write(out: JsonWriter, date: LocalDate?) {
        if (date == null) {
            out.nullValue()
        } else {
            out.value(formatter.format(date))
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): LocalDate? {
        return when (`in`.peek()) {
            JsonToken.NULL -> {
                `in`.nextNull()
                null
            }
            else -> {
                val date = `in`.nextString()
                LocalDate.parse(date, formatter)
            }
        }
    }
}