interface ChatHistoryObservable{
    fun registerObserver(observer : Observer)
    fun deregisterObserver(observer : Observer)
    fun notifyObservers(message : ChatMessage)
}