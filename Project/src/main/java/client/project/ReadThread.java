package client.project;

import java.io.IOException;
import java.util.ArrayList;

import static client.project.ClientMain.companyMovie;

public class ReadThread implements Runnable{
    public Thread thread;
    ReadThread(){
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        while (ClientMain.runReadThread) {
            Object object = null;
            try {
                object = ClientMain.socketWrapper.read();
            } catch (IOException | ClassNotFoundException exception) {
                System.out.println("Socket closed: " + exception);
            }
            if (object == null) ClientMain.runReadThread = false;
            else if(object instanceof ArrayList) {
                companyMovie = (ArrayList<Movie>) object;
                ClientMain.allMovies.setAll(companyMovie);
                ClientMain.recentMovies.setAll(getMostRecentMovies());
                ClientMain.maxRevenueMovie.setAll(getMoviesWithMaxRevenue());
            }
        }
    }
    public ArrayList<Movie> getMostRecentMovies(){
        ArrayList<Movie> mostRecentMovies = new ArrayList<>();
        int recentYear = getMostRecentYear();
        for (Movie movie: companyMovie)
            if (movie.getYearOfRelease() == recentYear)
                mostRecentMovies.add(movie);
        return mostRecentMovies;
    }
    public int getMostRecentYear(){
        int recentYear = 0;
        for (Movie movie: companyMovie)
            if(movie.getYearOfRelease() > recentYear)
                recentYear = movie.getYearOfRelease();
        return recentYear;
    }
    public long getMaxRevenue() {
        long maxRevenue = 0;
        for (Movie movie: companyMovie)
            if(maxRevenue < movie.getRevenue())
                maxRevenue = movie.getRevenue();
        return maxRevenue;
    }
    public ArrayList<Movie> getMoviesWithMaxRevenue() {
        long maxRevenue = getMaxRevenue();
        ArrayList<Movie> moviesWithMaxRevenue = new ArrayList<>();
        for (Movie movie : companyMovie)
            if (movie.getRevenue() == maxRevenue)
                moviesWithMaxRevenue.add(movie);
        return moviesWithMaxRevenue;
    }
}
