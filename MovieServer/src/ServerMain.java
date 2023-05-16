import client.project.Movie;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {
    static ArrayList<Movie> movieDataBase;
    static ArrayList<String> companyList;
    public static ArrayList<Client> clientList;
    public static final String FILE_NAME = "movies.txt";

    public static void main(String[] args) throws IOException {
        new ServerMain();
    }
    ServerMain() throws IOException {
        clientList = new ArrayList<>();
        movieDataBase = getMovieList();
        companyList = getCompanyList();
        connectClient();
    }
    private void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
        String clientName = (String) socketWrapper.read();
        while (!companyList.contains(clientName)){
            socketWrapper.write("You are not registered.");
            clientName = (String) socketWrapper.read();
        }
        socketWrapper.write("No objection");
        socketWrapper.write(getMoviesOfCompany(clientName));
        Client client = new Client(clientName, socketWrapper);
        new readWriteThread(client);
    }
    private void connectClient()  {
        try{
            try(ServerSocket serverSocket = new ServerSocket(12345)) {
                while (true){
                    try {
                        Socket clientSocket = serverSocket.accept();
                        serve(clientSocket);
                    } catch (IOException | ClassNotFoundException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }catch (Exception exception){
            System.out.println("Server starts: " + exception);
        }
    }
    private ArrayList<String> getCompanyList(){
        ArrayList<String> companyList = new ArrayList<>();
        for(Movie movie: movieDataBase) {
            if (!companyList.contains(movie.getProductionCompany()))
                companyList.add(movie.getProductionCompany());
        }
        return companyList;
    }
    private ArrayList<Movie> getMovieList() throws IOException {
        ArrayList<Movie> movieList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) break;
            movieList.add(new Movie(line.split(",")));
        }
        bufferedReader.close();
        return movieList;
    }
    public ArrayList<Movie> getMoviesOfCompany(String company) {
        ArrayList<Movie> moviesOfACompany = new ArrayList<>();
        for (Movie movie : ServerMain.movieDataBase)
            if (company.equalsIgnoreCase(movie.getProductionCompany()))
                moviesOfACompany.add(movie);
        return moviesOfACompany;
    }
}
