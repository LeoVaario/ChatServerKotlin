import java.time.LocalDateTime
import kotlin.math.min

object ChatHistory : ChatHistorySelectiveObservable{

    private val chatList = mutableListOf<ChatMessage>()
    private val observerSet = mutableSetOf<Observer>()

    override fun registerObserver(observer: Observer) {
        observerSet.add(observer)
    }

    override fun deregisterObserver(observer: Observer) {
        observerSet.remove(observer)
    }

    override fun notifyObservers(message : ChatMessage) {  //null recipient notifies all observers
        for (observer in observerSet){
            observer.update(message)
        }
    }

    override fun notifySelectiveObserver(message: ChatMessage, recipient: Observer) {
        recipient.update(message)
    }

    fun addMessage(message: String, senderUser: String, timeStamp: LocalDateTime, recipient: String?){    //null recipient sends to everyone

        if (recipient == null){                                             //logging to chatlist only if to be sent to everyone
            chatList.add(ChatMessage(message, senderUser, timeStamp))
            notifyObservers(ChatMessage(message, senderUser, timeStamp))
        }
        else {
            val tempObserver = findObsFromString(recipient)
            if (tempObserver != null) {
                notifySelectiveObserver(ChatMessage(message, senderUser, timeStamp), tempObserver)
            }
        }
    }

    private fun findObsFromString(string : String) : Observer?{
        return observerSet.find {
            if(it is CommandInterpreter){
                it.userName == string
            }
            else{
                false
            }
        }
    }

    fun getMessages() = chatList

    fun printMessages(messageNumber : Int, command : CommandInterpreter){ //returns last #messagenumber# of messages
        for (line in chatList.takeLast(min(messageNumber, chatList.size))){
            command.printStream.println(line)
        }
    }
}