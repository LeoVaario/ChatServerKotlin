import java.time.LocalDateTime

class ChatMessage(val message : String, val user : String, val timeStamp : LocalDateTime){
    override fun toString(): String {
        return "$user: $message --${timeStamp.hour}:${timeStamp.minute} ${timeStamp.dayOfMonth}.${timeStamp.monthValue}"
    }
}