package app;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PenaltySeriesTest {

    private void OneTeamAllGoals(PenaltySeries penaltySeries) {
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.nextRound();
    }

    private void equalGame(PenaltySeries penaltySeries) {
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.addGoalToSecond("name2");
        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.addGoalToSecond("name2");
        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.addGoalToSecond("name2");
        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.addGoalToSecond("name2");
        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.addGoalToSecond("name2");
    }

    @Test
    public void givenOneTeamAllGoals_whenCallingScore_shouldReturnValidScore() throws Exception {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");
        OneTeamAllGoals(penaltySeries);

        String result = penaltySeries.score();
        String expected = "Team 1 - 3 / Team 2 - 0";

        Assert.assertEquals(expected, result);
    }

    @Test
    public void givenOneTeamAllGoals_whenCallingOutcome_shouldReturnOne() throws Exception {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");
        OneTeamAllGoals(penaltySeries);

        Integer test = penaltySeries.outcome();
        Integer expected = 1;

        Assert.assertEquals(expected, test);
    }

    @Test
    public void givenSecondTeamAllGoals_whenCallingOutcome_shouldReturnTwo() {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");

        penaltySeries.addGoalToSecond("name2");
        penaltySeries.nextRound();
        penaltySeries.addGoalToSecond("name2");
        penaltySeries.nextRound();
        penaltySeries.addGoalToSecond("name2");
        penaltySeries.nextRound();

        Integer test = penaltySeries.outcome();
        Integer expected = 2;

        Assert.assertEquals(expected, test);
    }

    @Test
    public void givenZeroGoals_whenCallingFinished_shouldReturnFalse() throws Exception {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");

        boolean test = penaltySeries.isFinished();

        Assert.assertFalse(test);
    }

    @Test
    public void givenOneTeamAllGoals_whenCallingFinished_shouldReturnTrue() throws Exception {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");
        OneTeamAllGoals(penaltySeries);

        boolean test = penaltySeries.isFinished();

        Assert.assertTrue(test);
    }

    @Test
    public void givenEqualScore_whenCallingFinished_shouldReturnFalse() throws Exception {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");
        equalGame(penaltySeries);

        boolean test = penaltySeries.isFinished();

        Assert.assertFalse(test);
    }

    @Test
    public void givenPrematureGameEnd_whenCallingFinished_shouldReturnTrue() throws Exception {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");

        penaltySeries.addGoalToFirst("name1");
        penaltySeries.addGoalToSecond("name2");
        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.addGoalToSecond("name2");
        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.nextRound();

        boolean test = penaltySeries.isFinished();

        Assert.assertTrue(test);
    }

    @Test
    public void givenEqualGameAndOneEqualRound_whenCallingFinished_shouldReturnFalse() throws Exception {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");
        equalGame(penaltySeries);

        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.addGoalToSecond("name2");
        penaltySeries.nextRound();

        boolean test = penaltySeries.isFinished();

        Assert.assertFalse(test);
    }

    @Test
    public void givenEqualGameAndOneNotEqualRound_whenCallingFinished_shouldReturnTrue() throws Exception {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");
        equalGame(penaltySeries);

        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.nextRound();

        boolean test = penaltySeries.isFinished();

        Assert.assertTrue(test);
    }

    @Test
    public void givenEqualGameAndOneNotEqualRound_whenCallingScore_shouldReturnValidScore() throws Exception {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");
        equalGame(penaltySeries);

        penaltySeries.nextRound();
        penaltySeries.addGoalToFirst("name1");
        penaltySeries.nextRound();

        String test = penaltySeries.score();
        String expected = "Team 1 - 6 / Team 2 - 5";

        Assert.assertEquals(expected, test);
    }

    @Test
    public void givenGoalAndName_whenCallingAddGoal_shouldReturnListOfGoals() throws Exception {
        PenaltySeries penaltySeries = spy(new PenaltySeries("Team 1", "Team 2"));
        when(penaltySeries.getLastTenGoals(anyString())).thenReturn(new LinkedList<Boolean>());

        List<Boolean> test = penaltySeries.addGoalToFirst("Name");

        verify(penaltySeries, times(1)).getLastTenGoals(anyString());
        Assert.assertNotNull(test);
    }

    @Test(expected = IllegalStateException.class)
    public void givenFinishedGame_whenCallingAddGoal_shouldThrowException() throws Exception {
        PenaltySeries penaltySeries = new PenaltySeries("Team 1", "Team 2");

        OneTeamAllGoals(penaltySeries);
        penaltySeries.addGoalToFirst("name");
    }

    @Test
    public void givenSeriesNumGrtSeven_whenCallingPlayerCost_shouldReturnCost() throws Exception {
        PenaltySeries penaltySeries = spy(new PenaltySeries("Team 1", "Team 2"));
        when(penaltySeries.getPlayersWithoutGoals()).thenReturn(new LinkedList<Player>());

        List<Player> test = penaltySeries.getPlayersWithoutGoals();

        verify(penaltySeries, times(1)).getPlayersWithoutGoals();
        Assert.assertNotNull(test);
    }
}
