package app;

import java.util.HashMap;
import java.util.List;

public class PenaltySeries {

    private Integer firstTeamSore;
    private Integer secondTeamSore;
    private Integer seriesNum;
    private boolean isFinished;
    private Integer currentPenaltyNumber;
    private String firstTeamName;
    private String secondTeamName;

    private HashMap<String, Integer> firstTeamPlayerGoals;
    private HashMap<String, Integer> secondTeamPlayerGoals;

    public PenaltySeries(String team1, String team2) {
        this.firstTeamName = team1;
        this.secondTeamName = team2;

        firstTeamSore = 0;
        secondTeamSore = 0;
        seriesNum = 1;
        isFinished = false;
        currentPenaltyNumber = 1;
        firstTeamPlayerGoals = new HashMap<String, Integer>();
        secondTeamPlayerGoals = new HashMap<String, Integer>();
    }

    public List<Boolean> addGoalToFirst(String name) {
        if(isFinished) {
            throw new IllegalStateException();
        }
        firstTeamSore++;
        addGoalToPlayer(name, firstTeamPlayerGoals);
        return getLastTenGoals(name);
    }

    public List<Boolean> addGoalToSecond(String name) {
        if(isFinished) {
            throw new IllegalStateException();
        }
        secondTeamSore++;
        addGoalToPlayer(name, secondTeamPlayerGoals);
        return getLastTenGoals(name);
    }

    private void addGoalToPlayer(String name, HashMap<String, Integer> teamGoals) {
        if(!teamGoals.containsKey(name)) {
            teamGoals.put(name, 1);
        }
        else {
            teamGoals.put(name, teamGoals.get(name) + 1);
        }
    }

    List<Boolean> getLastTenGoals(String name) {
        return null; //TODO: call service
    }

    List<Player> getPlayersWithoutGoals() {
        return null; //TODO: call service
    }

    Integer getPlayerCost(String name) {
        return null; //TODO: call service
    }

    List<String> getCurrentPlayers(String teamName) {
        return null; //TODO: call service
    }

    public void nextRound() {
        checkWinCondition();
        if(currentPenaltyNumber != 5) {
            currentPenaltyNumber++;
        }
        else {
            seriesNum++;
        }
    }

    public String score() {
        StringBuilder sb = new StringBuilder();
        sb.append("Series: ");
        sb.append(seriesNum);
        sb.append(" | ");
        sb.append(firstTeamName);
        sb.append(" - ");
        sb.append(firstTeamSore);
        sb.append(" / ");
        sb.append(secondTeamName);
        sb.append(" - ");
        sb.append(secondTeamSore);

        if(seriesNum >= 7) {
            sb.append(" | ");
            sb.append("Total cost of players that missed: ");

            Integer firstTeamCost = getCostOfPlayersWithoutGoals(firstTeamName, firstTeamPlayerGoals);
            Integer secondTeamCost = getCostOfPlayersWithoutGoals(secondTeamName, secondTeamPlayerGoals);

            sb.append(firstTeamCost);
            sb.append(" / ");
            sb.append(secondTeamCost);
        }

        return sb.toString();
    }

    private Integer getCostOfPlayersWithoutGoals(String teamName, HashMap<String, Integer> teamGoals) {
        Integer result = 0;

        for(String player : getCurrentPlayers(teamName)) {
            if(!teamGoals.containsKey(player)) {
                result += getPlayerCost(player);
            }
        }

        return result;
    }

    public Integer outcome() {
        if(isFinished) {
            if (firstTeamSore > secondTeamSore) {
                return 1;
            }
            else if (secondTeamSore > firstTeamSore) {
                return 2;
            }
        }

        return 0;
    }

    public boolean isFinished() {
        return isFinished;
    }

    private void checkWinCondition() {
        if(seriesNum.equals(1) && currentPenaltyNumber.equals(5)) {
            if(firstTeamSore > secondTeamSore || firstTeamSore < secondTeamSore) {
                isFinished = true;
            }
        }
        else if(seriesNum.equals(1) && currentPenaltyNumber < 5) {
            if(isPrematureEnding()) {
                isFinished = true;
            }
        }
        else if(seriesNum > 1) {
            isFinished = firstTeamSore > secondTeamSore || firstTeamSore < secondTeamSore;
        }
    }

    private boolean isPrematureEnding() {
        return firstTeamSore > secondTeamSore &&
           firstTeamSore - secondTeamSore > 5 - currentPenaltyNumber ||
           secondTeamSore > firstTeamSore &&
           secondTeamSore - firstTeamSore > 5 - currentPenaltyNumber;
    }
}
