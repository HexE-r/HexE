import java.util.Random;

public class PuzzleGen {
    private Solver solver;

    public PuzzleGen() {
        this.solver = new Solver();
    }

    public PuzzleGrid generate(int numberOfEmptyCells) {
        PuzzleGrid grid = generate();

        eraseCells(grid, numberOfEmptyCells);

        return grid;
    }

    private void eraseCells(PuzzleGrid grid, int numberOfEmptyCells) {
        Random random = new Random();
        for (int i = 0; i < numberOfEmptyCells; i++) {
            int randomRow = random.nextInt(9);
            int randomColumn = random.nextInt(9);

            PuzzleGrid.Cell cell = grid.getCell(randomRow, randomColumn);
            if (!cell.isEmpty()) {
                cell.setValue(0);
            } else {
                i--;
            }
        }
    }

    private PuzzleGrid generate() {
        PuzzleGrid grid = PuzzleGrid.emptyGrid();

        solver.solve(grid);

        return grid;
    }
}
