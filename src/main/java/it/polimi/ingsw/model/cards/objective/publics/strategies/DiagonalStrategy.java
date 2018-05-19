package it.polimi.ingsw.model.cards.objective.publics.strategies;

import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ValuePublicObjectiveCard;

public class DiagonalStrategy implements IScoreComputationStrategy {

    /**
     * This method computes the score from the card by checking how many diagonally adjacent dice
     * there are in the {@link WindowPatternCard}, for each {@link it.polimi.ingsw.model.Color}.
     * @param card from which taking the parameters.
     * @param window to which the strategy is applied.
     * @return points given by the card.
     */
    @Override
    public int applyColorStrategy(ColorPublicObjectiveCard card, WindowPatternCard window) {
        boolean[][] checkedCells = new boolean[WindowPatternCard.getMaxRow()][WindowPatternCard.getMaxCol()];
        int cardPoints = 0;
        for(int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for(int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if(!checkedCells[i][j]) {
                    //The points given from diagonally adjacent dice of a color aren't necessarily found in a single run.
                    cardPoints += countDiagonalAdjacentDice(window, window.getGlassWindow()[i][j], checkedCells);
                }
            }
        }
        return cardPoints * card.getPointsForCompletion();
    }

    /**
     * Checks if the cell the algorithm wants to access actually exists (if it is in the matrix bounds) and
     * contains a die.
     * @param row of the cell to check.
     * @param col of the cell to check.
     * @param window of the cell to check.
     * @return {@code true} if the cell exists and contains a die, {@code false} otherwise.
     */
    private boolean canBeChecked(int row, int col, WindowPatternCard window) {
        if(row >= 0 && row < WindowPatternCard.getMaxRow() && col >= 0 && col < WindowPatternCard.getMaxCol()) {
            if(!window.getGlassWindow()[row][col].isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method contains a recursive algorithm that counts the diagonally adjacent cells containing dice with the
     * same {@link it.polimi.ingsw.model.Color}. Each if stands for a direction:
     * bottom-left, bottom-right, top-right, top-left.
     * @param window to be checked.
     * @param currentCell cell at which the iteration has arrived.
     * @param checked a matrix as big as the {@link WindowPatternCard}, which tells what cells have already been
     *                checked during the execution of the algorithm.
     * @return the number of diagonally adjacent dice of a color found during the recursion.
     */
    private int countDiagonalAdjacentDice(WindowPatternCard window, Cell currentCell, boolean[][] checked) {
        int row = currentCell.getRow();
        int col = currentCell.getCol();

        //Checks the bottom-left cell. Enters the branch if the cell is valid and has the same color of currentCell.
        if(canBeChecked(row + 1, col - 1, window) && !currentCell.isEmpty() &&
                window.getGlassWindow()[row + 1][col - 1].getContainedDie().getDieColor().equals(
                        currentCell.getContainedDie().getDieColor()) && !checked[row][col]) {
            checked[row][col] = true;
            return countDiagonalAdjacentDice(window, window.getGlassWindow()[row + 1][col - 1], checked) + 1;
        }

        //Checks the bottom-right cell. Enters the branch if the cell is valid and has the same color of currentCell.
        else if(canBeChecked(row + 1, col + 1, window) && !currentCell.isEmpty() &&
                window.getGlassWindow()[row + 1][col + 1].getContainedDie().getDieColor().equals(
                        currentCell.getContainedDie().getDieColor()) && !checked[row][col]) {
            checked[row][col] = true;
            return countDiagonalAdjacentDice(window, window.getGlassWindow()[row + 1][col + 1], checked) + 1;
        }

        //Checks the top-right cell. Enters the branch if the cell is valid and has the same color of currentCell.
        else if(canBeChecked(row - 1, col + 1, window) && !currentCell.isEmpty() &&
                window.getGlassWindow()[row - 1][col + 1].getContainedDie().getDieColor().equals(
                        currentCell.getContainedDie().getDieColor()) && !checked[row][col]) {
            checked[row][col] = true;
            return countDiagonalAdjacentDice(window, window.getGlassWindow()[row - 1][col + 1], checked) + 1;
        }

        //Checks the top-left cell. Enters the branch if the cell is valid and has the same color of currentCell.
        else if(canBeChecked(row - 1, col - 1, window) && !currentCell.isEmpty() &&
                window.getGlassWindow()[row - 1][col - 1].getContainedDie().getDieColor().equals(
                        currentCell.getContainedDie().getDieColor()) && !checked[row][col]) {
            checked[row][col] = true;
            return countDiagonalAdjacentDice(window, window.getGlassWindow()[row - 1][col - 1], checked) + 1;
        }

        //Base case. The algorithm arrives here if either the cell is empty or the diagonally adjacent cells haven't the
        //same color (or aren't valid cells).
        else {
            checked[row][col] = true;
            return 0;
        }
    }

    /**
     * This strategy is not present in the game, because there is no such card that have this effect.
     * However, it could be easily implemented.
     * For now it just throws an {@link UnsupportedOperationException}.
     */
    @Override
    public int applyValueStrategy(ValuePublicObjectiveCard card, WindowPatternCard window) {
        throw new UnsupportedOperationException("There is no such effect in the default game.");
    }
}
