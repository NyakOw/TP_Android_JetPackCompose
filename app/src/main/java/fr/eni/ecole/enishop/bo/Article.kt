package fr.eni.ecole.enishop.bo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fr.eni.ecole.enishop.utils.DateRoomConverter
import java.util.Date

@Entity
@TypeConverters(DateRoomConverter::class)
data class Article(

    @PrimaryKey(autoGenerate = true)

    var id : Long = 0,
    var name : String = "",
    var description : String = "",
    var price : Double = 0.0,
    var urlImage : String = "",
    var category : String = "",
    var date : Date = Date()
)
