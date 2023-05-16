package client.project;

import java.io.Serializable;

public class Movie implements Serializable {
    private final String Title;
    private final int YearOfRelease;
    private final String[] Genre = new String[3];
    private final int RunningTime;
    private String ProductionCompany;
    private final long Budget;
    private final long Revenue;

    public Movie(String[] movie) {
        this.Title = movie[0];
        this.YearOfRelease = Integer.parseInt(movie[1], 10);
        this.Genre[0] = movie[2];
        this.Genre[1] = movie[3];
        this.Genre[2] = movie[4];
        this.RunningTime = Integer.parseInt(movie[5]);
        this.ProductionCompany = movie[6];
        this.Budget = Long.parseLong(movie[7],10);
        this.Revenue = Long.parseLong(movie[8], 10);
        String allGenre;
        if(!(movie[2].equals(""))) {
            allGenre =  movie[2];
            if(!(movie[3].equals(""))){
                allGenre = allGenre + ", " + movie[3];
                if (!(movie[4].equals("")))
                    allGenre = allGenre + ", " + movie[4];
            }
        } else if(!(movie[3].equals(""))){
            allGenre = movie[3];
            if (!(movie[4].equals("")))
                allGenre = allGenre + ", " + Genre[2];
        } else if(!(movie[4].equals("")))
            allGenre = movie[4];
    }
    public String getTitle() {
        return Title;
    }
    public int getYearOfRelease() {
        return YearOfRelease;
    }
    public String getProductionCompany() {
        return ProductionCompany;
    }
    public int getRunningTime() {
        return RunningTime;
    }
    public long getBudget() {
        return Budget;
    }
    public long getRevenue() {
        return Revenue;
    }
    public void setProductionCompany(String productionCompany) {
        this.ProductionCompany = productionCompany;
    }
    public long getProfit(){
        return getRevenue()-getBudget();
    }
    public String getGenre1() { return Genre[0]; }
    public String getGenre2() { return Genre[1]; }
    public String getGenre3() { return Genre[2]; }
}