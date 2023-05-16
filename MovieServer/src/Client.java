public class Client {
    public String clientName;
    public SocketWrapper socketWrapper;
    Client(String clientName, SocketWrapper socketWrapper){
        this.clientName = clientName;
        this.socketWrapper = socketWrapper;
    }
}
