interface ChatHistorySelectiveObservable : ChatHistoryObservable{
    fun notifySelectiveObserver(message: ChatMessage, recipient : Observer)
}