import client.project.Movie;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class readWriteThread implements Runnable{
    String fromClient;
    private final Client client;
    public readWriteThread(Client client) {
        ServerMain.clientList.add(client);
        this.client = client;
        new Thread(this).start();
    }

    @Override
    public void run(){
        try {
            while (true){
                fromClient = (String) client.socketWrapper.read();
                switch (fromClient) {
                    case "Add movie" -> {
                        Movie movie = (Movie) client.socketWrapper.read();
                        ServerMain.movieDataBase.add(movie);
                        for (Client x : ServerMain.clientList)
                            if (client.clientName.equalsIgnoreCase(x.clientName))
                                x.socketWrapper.write(getMoviesOfCompany(client.clientName));
                        writeToDatabase();
                    }
                    case "Transfer movie" -> {
                        String movieTitle, destinationCompany;
                        movieTitle = (String) client.socketWrapper.read();
                        destinationCompany = (String) client.socketWrapper.read();
                        if (ServerMain.companyList.contains(destinationCompany)) {
                            getMovieByTitle(movieTitle).setProductionCompany(destinationCompany);
                            for (Client x : ServerMain.clientList)
                                if (client.clientName.equalsIgnoreCase(x.clientName))
                                    x.socketWrapper.write(getMoviesOfCompany(client.clientName));
                            for (Client x : ServerMain.clientList)
                                if (destinationCompany.equalsIgnoreCase(x.clientName))
                                    x.socketWrapper.write(getMoviesOfCompany(x.clientName));
                            writeToDatabase();
                        }
                    }
                    case "Close" -> client.socketWrapper.write(null);
                }
            }
        }catch (Exception exception){
            System.out.println("Connection lost: " + exception);
        }finally {
            try {
                client.socketWrapper.closeConnection();
                ServerMain.clientList.remove(client);

            }catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }
    public ArrayList<Movie> getMoviesOfCompany(String company) {
        ArrayList<Movie> moviesOfACompany = new ArrayList<>();
        for (Movie movie : ServerMain.movieDataBase)
            if (company.equalsIgnoreCase(movie.getProductionCompany()))
                moviesOfACompany.add(movie);
        return moviesOfACompany;
    }
    public Movie getMovieByTitle(String movieTitle){
        for (Movie movie: ServerMain.movieDataBase)
            if (movie.getTitle().equalsIgnoreCase(movieTitle))
                return movie;
        return null;
    }
    void writeToDatabase() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ServerMain.FILE_NAME));
        for (Movie movie : ServerMain.movieDataBase){
            String[] movieAttribute = new String[9];
            movieAttribute[0] = movie.getTitle();
            movieAttribute[1] = String.valueOf(movie.getYearOfRelease());
            movieAttribute[2] = movie.getGenre1();
            movieAttribute[3] = movie.getGenre2();
            movieAttribute[4] = movie.getGenre3();
            movieAttribute[5] = String.valueOf(movie.getRunningTime());
            movieAttribute[6] = movie.getProductionCompany();
            movieAttribute[7] = String.valueOf(movie.getBudget());
            movieAttribute[8] = String.valueOf(movie.getRevenue());

            bufferedWriter.write(
                    IntStream.of(0, 1, 2, 3, 4, 5, 6, 7, 8).mapToObj(i -> movieAttribute[i]).collect(Collectors.joining(",")));
            bufferedWriter.write("\n");
        }
        bufferedWriter.close();
    }
}